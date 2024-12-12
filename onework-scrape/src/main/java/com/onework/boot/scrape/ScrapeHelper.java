package com.onework.boot.scrape;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ScrapeHelper {

    /**
     *  循环执行获取 WebDriver，根据 WebDriver 循环执行操作逻辑，出现错误执行 consumer 进行处理
     * @param supplier 获取 WebDriver 方法
     * @param predicate WebDriver 操作方法
     * @param consumer WebDriver 操作方法
     */
    public static void continueExecute(Supplier<WebDriver> supplier, Predicate<WebDriver> predicate, Consumer<WebDriver> consumer) {
        Supplier<Boolean> execute = () -> {
            try {
                WebDriver webDriver = supplier.get();
                while (true) {
                    try {
                        boolean success = predicate.test(webDriver);
                        if (success) {
                            break;
                        }
                    } catch (Exception exception) {
                        log.warn("执行continueExecute方法,内部出现异常，错误消息:{}", exception.getMessage());
                        consumer.accept(webDriver);
                    }
                }
                webDriver.quit();
                return true;
            } catch (Exception exception) {
                log.warn("执行continueExecute方法，WebDriver对象异常，错误消息:{}", exception.getMessage());
                return false;
            }
        };
        while (true) {
            boolean result = execute.get();
            if (result) {
                break;
            }
        }
    }

    /**
     * 循环执行获取 WebDriver，根据 WebDriver 循环执行操作逻辑
     * @param supplier 获取 WebDriver 方法
     * @param predicate WebDriver 操作方法
     */
    public static void loopExecute(Supplier<WebDriver> supplier, Predicate<WebDriver> predicate) {
        Supplier<Boolean> execute = () -> {
            try {
                WebDriver webDriver = supplier.get();
                while (true) {
                    try {
                        boolean success = predicate.test(webDriver);
                        if (success) {
                            break;
                        }
                    } catch (Exception exception) {
                        log.warn("执行loopExecute方法,内部出现异常，错误消息:{}", exception.getMessage());
                        webDriver.quit();
                        return false;
                    }
                }
                webDriver.quit();
                return true;
            } catch (Exception exception) {
                log.warn("执行loopExecute方法，WebDriver对象异常，错误消息:{}", exception.getMessage());
                return false;
            }
        };
        while (true) {
            boolean result = execute.get();
            if (result) {
                break;
            }
        }
    }

    /**
     *
     * @param items 数据集合
     * @param workerNum 线程数
     * @param execute 处理方法
     * @param <T> 数据类型
     */
    public static <T> void listWorkerExecute(List<T> items, int workerNum, IListWorkerExecute<T> execute) {
        if (items.isEmpty()) {
            return;
        }
        if (items.size() <= workerNum) {
            execute.execute(1, items.size(), items);
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(workerNum);
        List<Future<?>> futures = new ArrayList<>();
        int n = items.size();
        int chunkSize = n / workerNum;
        int remainder = n % workerNum;
        for (int i = 0; i < workerNum; i++) {
            int start = i * chunkSize + Math.min(i, remainder);  // 处理区间的开始索引
            int end = (i + 1) * chunkSize + Math.min(i + 1, remainder);  // 处理区间的结束索引
            // 确保结束索引不越界
            end = Math.min(end, n);
            List<T> data = items.subList(start, end);
            int finalEnd = end;
            Future<?> future = executor.submit(() -> execute.execute(start, finalEnd, data));
            futures.add(future);
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException ignored) {
            }
        }
        executor.shutdown();
    }

    /**
     *
     * @param total 数据总量
     * @param workerNum 线程数
     * @param execute 处理方法
     */
    public static void workerExecute(int total, int workerNum, IWorkerExecute execute) {
        if (total == 0) {
            return;
        }
        if (total <= workerNum) {
            execute.execute(1, total);
            return;
        }
        int chunkSize = total / workerNum;
        int remainder = total % workerNum;
        ExecutorService executor = Executors.newFixedThreadPool(workerNum);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < workerNum; i++) {
            int start = (i * chunkSize) + 1 + Math.min(i, remainder);  // 从1开始的起始索引
            int end = (i + 1) * chunkSize + Math.min(i + 1, remainder); // 结束索引
            // 确保结束索引不越界
            end = Math.min(end, total);
            int finalEnd = end;
            Future<?> future = executor.submit(() -> execute.execute(start, finalEnd));
            futures.add(future);
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException ignored) {
            }
        }
        executor.shutdown();
    }

    /**
     * 切换最后一个标签
     * @param webDriver 浏览器驱动
     * @param isClose 释放关闭其他标签
     */
    public static void switchLastTab(WebDriver webDriver, boolean isClose) {
        //关闭其他标签，切换详情页标签
        String[] windowHandles = webDriver.getWindowHandles().toArray(new String[0]);
        for (int i = 0; i < windowHandles.length; i++) {
            webDriver.switchTo().window(windowHandles[i]);
            if (i != windowHandles.length - 1 && isClose) {
                webDriver.close();
            }
        }
    }

    /**
     *  切换到第一个标签
     * @param webDriver 浏览器驱动
     * @param isClose 释放关闭其他标签
     */
    public static void switchFirstTab(WebDriver webDriver, boolean isClose) {
        String[] windowHandles = webDriver.getWindowHandles().toArray(new String[0]);
        // 从数组的最后一个元素开始，反向遍历
        for (int i = windowHandles.length - 1; i >= 0; i--) {
            webDriver.switchTo().window(windowHandles[i]);
            if (i != 0 && isClose) {
                webDriver.close();
            }
        }
    }

    /**
     *  执行脚本
     * @param webDriver 浏览器驱动
     * @param script 脚本
     */
    public static void executeScript(WebDriver webDriver, String script) {
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript(script);
    }

    /**
     * 执行验证判断逻辑后，执行脚本
     * @param webDriver 浏览器驱动
     * @param function 验证程序，true：执行脚本，false：循环执行
     * @param script 脚本
     */
    public static void executeScript(WebDriver webDriver, String script, Function<WebDriver, Boolean> function) {
        while (true) {
            boolean result = function.apply(webDriver);
            if (result) {
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                executor.executeScript(script);
                break;
            }
        }
    }

    /**
     * 监听指定筛选元素，等待后，返回标签元素对象
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     * @return 标签元素
     */
    public static WebElement waitElement(WebDriver webDriver, String selector) {
        ExpectedCondition<WebElement> conditions = ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector));
        return new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(conditions);
    }

    /**
     * 保存当前页面内容
     * @param webDriver 浏览器驱动
     * @param filePath 文件保存路径
     */
    public static void savePage(WebDriver webDriver, String filePath) {
        String html = webDriver.getPageSource();
        Pattern scriptPattern = Pattern.compile("<script.*?>.*?</script>", Pattern.DOTALL);
        Matcher matcher = scriptPattern.matcher(html);
        String newHtmlContent = matcher.replaceAll("");
        FileUtil.writeString(newHtmlContent, filePath, StandardCharsets.UTF_8);
    }

    /**
     * 监听指定筛选元素，等待后，点击操作
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     */
    public static void buttonElement(WebDriver webDriver, String selector) {
        WebElement webElement = waitElement(webDriver, selector);
        webElement.click();
    }

    /**
     * 监听指定筛选元素，等待后，点击操作
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     * @return 元素文本
     */
    public static String getText(WebDriver webDriver, String selector) {
        WebElement webElement = waitElement(webDriver, selector);
        return webElement.getText();
    }

    /**
     *  判断筛选的元素是否存在
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     * @return true：存在，false：不存在
     */
    public static boolean existElement(WebDriver webDriver, String selector) {
        WebElement webElement = waitElement(webDriver, selector);
        return webElement.isDisplayed();
    }

    /**
     * 根据元素节点，筛选指定标签，获取文本
     * @param webElement 父级元素节点
     * @param selector 元素筛选器
     * @return 值
     */
    public static String getText(WebElement webElement, String selector) {
        return webElement.findElement(By.cssSelector(selector)).getText();
    }

    /**
     *  根据元素节点，筛选指定标签，获取文本，根据规则进行替换
     * @param webElement 父级元素节点
     * @param selector 元素筛选器
     * @param replaceValue 替换的值
     * @return 文本
     */
    public static String getTextReplaceText(WebElement webElement, String selector, String replaceValue) {
        return webElement.findElement(By.cssSelector(selector)).getText().replaceAll(replaceValue, "");
    }

    /**
     *  根据元素节点，筛选指定标签，获取元素指定属性
     * @param webElement 父级元素节点
     * @param selector 元素筛选器
     * @param attribute 元素属性
     * @return 属性值
     */
    public static String getAttributeValue(WebElement webElement, String selector, String attribute) {
        try {
            return webElement.findElement(By.cssSelector(selector)).getAttribute(attribute);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     *  处理异常值
     * @param value 值
     * @return 数值
     */
    public static Integer getTryInteger(String value) {
        try {
            return Convert.toInt(value);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     *  处理异常值
     * @param value 值
     * @return 数值
     */
    public static LocalDateTime getTryLocalDateTime(String value) {
        try {
            return LocalDateTimeUtil.parse(value);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     *  处理异常值
     * @param value 值
     * @return 数值
     */
    public static LocalDateTime getTryLocalDateTime(String value, String formatter) {
        try {
            return LocalDateTimeUtil.parse(value, formatter);
        } catch (Exception exception) {
            return null;
        }
    }
}
