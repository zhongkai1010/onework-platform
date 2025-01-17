package com.onework.boot.task.collection.core.bohe;

import com.onework.boot.task.collection.core.ScrapeHelper;
import com.onework.boot.task.collection.core.TaskServer;
import com.onework.boot.task.collection.core.TaskServerType;
import com.onework.boot.task.collection.core.WebDriverFactory;
import com.onework.boot.task.collection.core.bohe.config.BoheCompanyConfiguration;
import com.onework.boot.task.collection.dao.entity.BOHECompany;
import com.onework.boot.task.collection.dao.mapper.BOHECompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class BoheCompanyTaskServer extends TaskServer {

    private final BoheCompanyConfiguration configuration;

    private final BOHECompanyMapper boheCompanyMapper;

    public BoheCompanyTaskServer(BoheCompanyConfiguration configuration, BOHECompanyMapper boheCompanyMapper) {
        this.configuration = configuration;
        this.boheCompanyMapper = boheCompanyMapper;
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.BOHE_COMPANY;
    }

    @Override
    public void run() {

        String desc = getTaskServerType().getDescription();
        AtomicInteger totalPages = new AtomicInteger();
        ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration), webDriver -> {
            webDriver.get(configuration.getUrl());
            String selector = "html > body > div:nth-of-type(2) > div:nth-of-type(3) > div:nth-of-type(1) > div:nth-of-type(3) > ul > li:nth-of-type(6) > a";
            String text = ScrapeHelper.getText(webDriver, selector);
            totalPages.set(Integer.parseInt(text));
            return true;
        });
        log.info("[{}],共有{}页", desc, totalPages.get());

        ScrapeHelper.workerExecute(totalPages.get(), configuration.getThreadCount(), (start, end) -> ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration),
                webDriver -> {
                    for (int i = start; i <= end; i++) {
                        String url = String.format(configuration.getPageUrl(), i);
                        webDriver.get(url);
                        WebElement ul = ScrapeHelper.waitVisible(webDriver, "ul[class='cl-list']");
                        List<WebElement> lis = ul.findElements(By.cssSelector("li"));
                        for (WebElement li : lis) {
                            String websiteUrl = ScrapeHelper.getAttributeValue(li, ("div > a"), "href");
                            BOHECompany boheCompany = boheCompanyMapper.selectOne(BOHECompany::getWebsiteUrl, websiteUrl);
                            boolean isNew = false;
                            if (boheCompany == null) {
                                boheCompany = new BOHECompany();
                                isNew = true;
                            }
                            parseWebElement(li, boheCompany);
                            try {
                                if (isNew) {
                                    boheCompanyMapper.insert(boheCompany);
                                } else {
                                    boheCompanyMapper.updateById(boheCompany);
                                }
                                log.info("[{}],第{}页,{},记录成功", desc, i, boheCompany.getCompanyName());
                            } catch (Exception exception) {
                                log.warn("[{}],第{}页,{},记录失败,错误消息:{}", desc, i, boheCompany.getCompanyName(), exception.getMessage());
                            }
                        }
                    }
                    return true;
                }));
    }

    private void parseWebElement(WebElement element, BOHECompany boheCompany) {
        // 企业名称
        String companyName = ScrapeHelper.getText(element, "div > a");
        boheCompany.setCompanyName(companyName);
        // 法定代表人
        String legalRepresentative = ScrapeHelper.getTextReplaceText(element, "div > div:nth-of-type(2) > p:nth-of-type(1)", "法定代表人：");
        boheCompany.setLegalRepresentative(legalRepresentative);
        // 注册资本
        String registeredCapital = ScrapeHelper.getTextReplaceText(element, "div > div:nth-of-type(2) > p:nth-of-type(2)", "注册资本：");
        boheCompany.setRegisteredCapital(registeredCapital);
        // 成立时间
        String establishmentDate = ScrapeHelper.getTextReplaceText(element, "div > div:nth-of-type(2) > p:nth-of-type(3)", "成立时间：");
        boheCompany.setEstablishmentDate(ScrapeHelper.getTryLocalDateTime(establishmentDate));
        // 地址
        String address = ScrapeHelper.getTextReplaceText(element, "div > div:nth-of-type(2) > p:nth-of-type(4)", "地址：");
        boheCompany.setAddress(address);
        // 经营范围
        String businessScope = ScrapeHelper.getTextReplaceText(element, "div > div:nth-of-type(2) > p:nth-of-type(5)", "经营范围：");
        boheCompany.setBusinessScope(businessScope);
        // 图标
        String logoUrl = ScrapeHelper.getAttributeValue(element, "a > img", "src");
        boheCompany.setLogoUrl(logoUrl);
        // 链接
        String websiteUrl = ScrapeHelper.getAttributeValue(element, ("div > a"), "href");
        boheCompany.setWebsiteUrl(websiteUrl);
        // LOG.info("企业名称：{},法定代表人：{},注册资本：{},成立时间：{},地址：{},经营范围：{},图标：{},链接：{}", companyName, legalRepresentative, registeredCapital, establishmentDate, address, businessScope, logoUrl, websiteUrl);
    }
}
