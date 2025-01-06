package com.onework.boot.scrape.site;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
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
     * 循环执行操作WebDriver，执行完成退出WebDriver，出现错误执行consumer方法进行处理
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
                    } catch (Throwable exception) {
                        log.warn("执行continueExecute方法,内部出现异常，错误消息:{}", exception.getMessage());
                        consumer.accept(webDriver);
                    }
                }
                webDriver.quit();
                return true;
            } catch (Throwable exception) {
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
     * 循环执行操作WebDriver，执行完成退出WebDriver，出现异常循环执行
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
                    } catch (Throwable exception) {
                        log.warn("执行loopExecute(supplier,predicate)方法,内部出现异常，错误消息:{}", exception.getMessage(), exception);
                        webDriver.quit();
                        return false;
                    }
                }
                webDriver.quit();
                return true;
            } catch (Throwable exception) {
                log.warn("执行loopExecute(supplier,predicate)方法，WebDriver对象异常，错误消息:{}", exception.getMessage(), exception);
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
     * 循环执行程序，操作timeout秒，停止执行，抛出异常
     * @param execute 执行程序
     * @param timeout 秒，超时时间
     */
    public static void loopExecute(Supplier<Boolean> execute, long timeout) {
        Instant start = Instant.now();
        while (true) {
            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            if (duration.toSeconds() > timeout) {
                throw new TimeoutException(String.format("执行loopExecute(execute,timeout)方法异常，超过%s秒", duration.toSeconds()));
            }
            try {
                if (execute.get()) {
                    break;
                }
            } catch (Throwable throwable) {
                log.warn("执行loopExecute(execute,timeout)方法,错误消息:{}", throwable.getMessage(), throwable);
            }
        }
    }

    public static void waitForPageLoad(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // 执行 JavaScript 检查 document.readyState
        String readyState = jsExecutor.executeScript("return document.readyState").toString();

        // 等待 readyState 为 'complete'
        while (!readyState.equals("complete")) {
            try {
                Thread.sleep(100);  // 每100ms检查一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readyState = jsExecutor.executeScript("return document.readyState").toString();
        }
    }

    /**
     * 执行程序，计数允许时间
     * @param runnable 执行程序
     * @return 秒，返回程序允许时间
     */
    public static long runTime(Runnable runnable) {
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        return duration.toSeconds();
    }

    /**
     * 根据总数，按指定线程数进行处理
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
     * 根据集合数据，按指定线程进行处理
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
     * 满足某条件，执行验证判断逻辑后，执行脚本
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
     * 监听指定筛选元素，默认等待指定时间返回标签元素对象
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     * @return 标签元素
     */
    public static WebElement waitVisible(WebDriver webDriver, String selector, Duration duration) {
        ExpectedCondition<WebElement> conditions = ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector));
        return new WebDriverWait(webDriver, duration).until(conditions);
    }

    /**
     * 监听指定筛选元素，默认等待后10秒返回标签元素对象
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     * @return 标签元素
     */
    public static WebElement waitVisible(WebDriver webDriver, String selector) {
        return waitVisible(webDriver, selector, Duration.ofSeconds(10));
    }

    /**
     * 监听指定筛选元素，等待后，点击操作
     *
     * @param webDriver 浏览器驱动
     * @param selector  元素筛选器
     */
    public static boolean clickElement(WebDriver webDriver, String selector) {
        return clickElement(webDriver, selector, Duration.ofSeconds(10));
    }

    /**
     * 监听指定筛选元素，等待后，点击操作
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     */
    public static boolean clickElement(WebDriver webDriver, String selector, Duration duration) {
        ExpectedCondition<WebElement> conditions = ExpectedConditions.elementToBeClickable(By.cssSelector(selector));
        WebElement webElement = new WebDriverWait(webDriver, duration).until(conditions);
        webElement.click();
        return true;
    }

    /**
     * 监听指定筛选元素，等待后，点击操作
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     * @return 元素文本
     */
    public static String getText(WebDriver webDriver, String selector) {
        WebElement webElement = waitVisible(webDriver, selector);
        return StrUtil.trim(webElement.getText());
    }

    /**
     *  判断筛选的元素是否存在
     * @param webDriver 浏览器驱动
     * @param selector 元素筛选器
     * @return true：存在，false：不存在
     */
    public static boolean existElement(WebDriver webDriver, String selector) {
        WebElement webElement = waitVisible(webDriver, selector);
        return webElement.isDisplayed();
    }

    /**
     * 根据元素节点，筛选指定标签，获取文本
     * @param webElement 父级元素节点
     * @param selector 元素筛选器
     * @return 值
     */
    public static String getText(WebElement webElement, String selector) {
        return StrUtil.trim(webElement.findElement(By.cssSelector(selector)).getText());
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
