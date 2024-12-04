package com.onework.boot.scrape.bohe;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.scrape.*;
import com.onework.boot.scrape.data.entity.BoheCompany;
import com.onework.boot.scrape.data.mapper.BoheCompanyMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BoheCompanyTaskServer implements ITaskServer {

    private final ScrapeConfiguration scrapeConfiguration;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    private final BoheCompanyMapper boheCompanyMapper;

    public BoheCompanyTaskServer(ScrapeConfiguration scrapeConfiguration, BoheCompanyMapper boheCompanyMapper) {
        this.scrapeConfiguration = scrapeConfiguration;
        this.boheCompanyMapper = boheCompanyMapper;
    }

    @Override
    public void run() {
        AtomicInteger totalPages = new AtomicInteger();

        ScrapeHelper.whileExecute(() -> {
            WebDriver webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
            webDriver.get("https://yao.bohe.cn/company/");
            // 输入登记号
            WebDriverWait inputWait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 设置等待时间为5秒
            WebElement pageElement = inputWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("html > body > div:nth-of-type(2) > div:nth-of-type(3) > div:nth-of-type(1) > div:nth-of-type(3) > ul > li:nth-of-type(6) > a")));
            String totalPageStr = pageElement.getText();
            totalPages.set(Integer.parseInt(totalPageStr));
            webDriver.quit();
            return true;
        });
        LOG.info("采集yao.bohe.cn，总页数: {}", totalPages);

        ScrapeHelper.workerExecute(totalPages.get(), 20, (start, end) -> {
            WebDriver webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
            for (int i = start; i <= end; i++) {
                String url = String.format("https://yao.bohe.cn/company/page_%s/", i);
                webDriver.get(url);
                int finalI = i;
                ScrapeHelper.whileExecute(new IWhileExecute() {
                    @Override
                    public boolean execute() {
                        List<WebElement> webElementList = ScrapeHelper.findList(webDriver, "ul[class='cl-list'] li");
                        for (WebElement webElement : webElementList) {
                            // 企业名称
                            String companyName = ScrapeHelper.findValue(webElement, "div > a");
                            // 法定代表人
                            String legalRepresentative = ScrapeHelper.findReplaceValue(webElement, "div > div:nth-of-type(2) > p:nth-of-type(1)", "法定代表人：");
                            // 注册资本
                            String registeredCapital = ScrapeHelper.findReplaceValue(webElement, "div > div:nth-of-type(2) > p:nth-of-type(2)", "注册资本：");
                            // 成立时间
                            String establishmentDate = ScrapeHelper.findReplaceValue(webElement, "div > div:nth-of-type(2) > p:nth-of-type(3)", "成立时间：");
                            // 地址
                            String address = ScrapeHelper.findReplaceValue(webElement, "div > div:nth-of-type(2) > p:nth-of-type(4)", "地址：");
                            // 经营范围
                            String businessScope = ScrapeHelper.findReplaceValue(webElement, "div > div:nth-of-type(2) > p:nth-of-type(5)", "经营范围：");
                            // 图标
                            String logoUrl = ScrapeHelper.findAttributeValue(webElement, "a > img", "src");
                            // 链接
                            String websiteUrl = ScrapeHelper.findAttributeValue(webElement, ("div > a"), "href");
                            // LOG.info("企业名称：{},法定代表人：{},注册资本：{},成立时间：{},地址：{},经营范围：{},图标：{},链接：{}", companyName, legalRepresentative, registeredCapital, establishmentDate, address, businessScope, logoUrl, websiteUrl);
                            BoheCompany boheCompany = boheCompanyMapper.selectOne(Wrappers.<BoheCompany>lambdaQuery().eq(BoheCompany::getWebsiteUrl, websiteUrl));
                            if (boheCompany == null) {
                                boheCompany = new BoheCompany();
                                boheCompany.setCompanyName(companyName);
                                boheCompany.setLegalRepresentative(legalRepresentative);
                                boheCompany.setRegisteredCapital(registeredCapital);
                                boheCompany.setEstablishmentDate(_getTryLocalDateTime(establishmentDate));
                                boheCompany.setAddress(address);
                                boheCompany.setBusinessScope(businessScope);
                                boheCompany.setLogoUrl(logoUrl);
                                boheCompany.setWebsiteUrl(websiteUrl);
                                boheCompanyMapper.insert(boheCompany);
                                LOG.info("第{}项，新增企业：{}", finalI, companyName);
                            } else {
                                boheCompany.setCompanyName(companyName);
                                boheCompany.setLegalRepresentative(legalRepresentative);
                                boheCompany.setRegisteredCapital(registeredCapital);
                                boheCompany.setEstablishmentDate(_getTryLocalDateTime(establishmentDate));
                                boheCompany.setAddress(address);
                                boheCompany.setBusinessScope(businessScope);
                                boheCompany.setLogoUrl(logoUrl);
                                boheCompany.setWebsiteUrl(websiteUrl);
                                boheCompanyMapper.updateById(boheCompany);
                                LOG.info("第{}项，更新企业：{}", finalI, companyName);

                            }
                        }
                        return true;
                    }

                    @Override
                    public boolean errorHandle(Exception exception) {
                        LOG.info("获取页面元素异常，错误消息：{}", exception.getMessage());
                        return false;
                    }
                });
            }
            webDriver.quit();
        });
    }

    /**
     *  处理异常值
     * @param value 值
     * @return 数值
     */
    private static LocalDateTime _getTryLocalDateTime(String value) {
        try {
            return LocalDateTimeUtil.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception exception) {
            return null;
        }
    }

}
