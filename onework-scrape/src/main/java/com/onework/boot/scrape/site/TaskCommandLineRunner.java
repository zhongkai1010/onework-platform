package com.onework.boot.scrape.site;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskCommandLineRunner implements CommandLineRunner {

//    private final TaskServerFactory taskServerFactory;
//
//    private final NMPAPharmaceuticalCompanyConfiguration configuration;

//    public TaskCommandLineRunner(TaskServerFactory taskServerFactory, NMPAPharmaceuticalCompanyConfiguration configuration) {
//        this.taskServerFactory = taskServerFactory;
//        this.configuration = configuration;
//    }

    @Override
    public void run(String... args) throws Exception {

//        // 1. 启动 BrowserMob Proxy
//        BrowserMobProxy proxy = new BrowserMobProxyServer();
//        proxy.addRequestFilter((httpRequest, httpMessageContents, httpMessageInfo) -> {
//            log.info("method:{},url:{}", httpRequest.method(), httpRequest.uri());
//            return null;
//        });
//        proxy.addResponseFilter((httpResponse, httpMessageContents, httpMessageInfo) -> log.info("Response: " + httpMessageContents.toString()));
//        proxy.setTrustAllServers(true); // 信任所有服务器证书
//        proxy.start(0); // 自动选择可用端口
//
//        // 2. 配置代理到 Selenium
//        Proxy seleniumProxy = new Proxy();
//        seleniumProxy.setHttpProxy("localhost:" + proxy.getPort());
//        seleniumProxy.setSslProxy("localhost:" + proxy.getPort());
//        int port = proxy.getPort(); // get the JVM-assigned port
//        log.info("Server started on port {}", port);
//
//        // 3. 配置 FirefoxOptions
//        FirefoxOptions options = new FirefoxOptions();
//        options.setCapability(CapabilityType.PROXY, seleniumProxy);
//        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true); // 允许非安全证书
//
//        // 4. 启动 WebDriver
//        FirefoxDriver webDriver = new FirefoxDriver(options);
//
//        // 6. 执行测试操作
//        webDriver.get("https://www.nmpa.gov.cn/datasearch/home-index.html");
//
//        // 8. 关闭资源
//        webDriver.quit();
//        proxy.stop();
//
//        taskServerFactory.executeTask(TaskServerType.CDE_All_PROJECT);
//        taskServerFactory.executeTasks(new TaskServerType[]{TaskServerType.CDE_PROJECT, TaskServerType.CDE_PROJECT_FILE, TaskServerType.CDE_PROJECT_FILE_PARSE, TaskServerType.CDE_ANALYSIS});
//        taskServerFactory.executeTasks(new TaskServerType[]{TaskServerType.CTMDS_DRUG, TaskServerType.CTMDS_DRUG_FILE, TaskServerType.CTMDS_INSTRUMENT, TaskServerType.CTMDS_INSTRUMENT_FILE});
//        taskServerFactory.executeTask(TaskServerType.BOHE_COMPANY);
//        taskServerFactory.executeTasks(new TaskServerType[]{TaskServerType.CTR_PROJECT, TaskServerType.CTR_PROJECT_FILE, TaskServerType.CTR_PROJECT_FILE_PARSE});
//        taskServerFactory.executeTask(TaskServerType.NMP_PHARMACEUTICAL_COMPANY);
    }

//    @PreDestroy
//    public void destroy() {
//        WebDriverFactory.drivers.forEach(WebDriver::quit);
//    }
}
