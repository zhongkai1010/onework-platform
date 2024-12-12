package com.onework.boot.scrape.nmpa;

import com.onework.boot.scrape.TaskServer;
import com.onework.boot.scrape.TaskServerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PharmaceuticalCompanyTaskServer extends TaskServer {


    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.NMP_PHARMACEUTICAL_COMPANY;
    }


    @Override
    public void run() {
//        FirefoxDriver firefoxDriver = new FirefoxDriver();
//        firefoxDriver.get("https://www.nmpa.gov.cn/datasearch/home-index.html");
//
//        ScrapeHelper.whileExecute(() -> {
//            WebElement a1 = firefoxDriver.findElement(By.cssSelector("a[class='introjs-button introjs-skipbutton']"));
//            a1.click();
//            return true;
//        });
//
//        ScrapeHelper.whileExecute(() -> {
//            WebElement a2 = firefoxDriver.findElement(By.cssSelector("html > body > div > main > div:nth-of-type(2) > div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(1) > div:nth-of-type(5) > a"));
//            a2.click();
//            return true;
//        });
//
//        ScrapeHelper.whileExecute(() -> {
//            WebElement input2 = firefoxDriver.findElement(By.cssSelector("input[data-step='4']"));
//            input2.sendKeys("公司");
//            return true;
//        });
//
//        ScrapeHelper.whileExecute(() -> {
//            WebElement botton1 = firefoxDriver.findElement(By.cssSelector("button[data-step='5']"));
//            botton1.click();
//            return true;
//        });
//
//        ScrapeHelper.whileExecute(() -> {
//            ArrayList<String> tabs = new ArrayList<>(firefoxDriver.getWindowHandles());
//            firefoxDriver.switchTo().window(tabs.get(tabs.size() - 1));
//            WebElement a1 = firefoxDriver.findElement(By.cssSelector("a[class='introjs-button introjs-skipbutton']"));
//            a1.click();
//            return true;
//        });
//
//        ScrapeHelper.whileExecute(() -> {
//            WebElement t1 = firefoxDriver.findElement(By.cssSelector("html > body > div:nth-of-type(1) > div:nth-of-type(3) > div:nth-of-type(3) > div > div > span:nth-of-type(1)"));
//            int total = ScrapeHelper.getNum(t1.getText());
//            int totalPage = NumberUtil.round(total / 10.0, 0, RoundingMode.HALF_UP).intValue();
//            for (int i = 1; i <= totalPage; i++) {
//                int finalI = i;
//                ScrapeHelper.whileExecute(() -> {
//                    int no = (finalI - 1) * 10 + 1;
//                    WebElement noWebElement = firefoxDriver.findElement(By.cssSelector("html > body > div:nth-of-type(1) > div:nth-of-type(3) > div:nth-of-type(2) > div > div > div:nth-of-type(3) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(1) > div > div"));
//                    return String.valueOf(no).equals(noWebElement.getText());
//                });
//                List<WebElement> trs = firefoxDriver.findElements(By.cssSelector("html > body > div:nth-of-type(1) > div:nth-of-type(3) > div:nth-of-type(2) > div > div > div:nth-of-type(3) > table > tbody > tr"));
//                for (WebElement tr : trs) {
//                    String currHandle = firefoxDriver.getWindowHandle();
//                    String name = tr.findElement(By.cssSelector("td:nth-of-type(2) > div > p")).getText();
//                    log.info("{}",name);
//                    // 查看详情打开新标签
//                    WebElement btn = tr.findElement(By.cssSelector(" td:nth-of-type(5) > div > button"));
//                    btn.click();
//                    // 切换最后一个标签
//                    ArrayList<String> tabs = new ArrayList<>(firefoxDriver.getWindowHandles());
//                    firefoxDriver.switchTo().window(tabs.get(tabs.size() - 1));
//                    ScrapeHelper.whileExecute(() -> {
//                        WebElement nameWebElement = firefoxDriver.findElement(By.cssSelector("html > body > div > main > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(2) > div > div > div:nth-of-type(2) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(2) > div > div > div"));
//                        String text = nameWebElement.getText();
//                        if (Objects.equals(text, name)) {
//                            String html = firefoxDriver.getPageSource();
//                            String filePathName = String.format("D:\\国家药品监督管理局-药品生产企业\\%s.html", name);
//                            FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);
//                            firefoxDriver.close();
//                            firefoxDriver.switchTo().window(currHandle);
//                            try {
//                                Thread.sleep(3*1000);
//                            } catch (InterruptedException e) {
//                                throw new RuntimeException(e);
//                            }
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    });
//                }
//                WebElement nextBtn = firefoxDriver.findElement(By.cssSelector("html > body > div:nth-of-type(1) > div:nth-of-type(3) > div:nth-of-type(3) > div > div > button:nth-of-type(2)"));
//                nextBtn.click();
//            }
//            return true;
//        });
    }
}
