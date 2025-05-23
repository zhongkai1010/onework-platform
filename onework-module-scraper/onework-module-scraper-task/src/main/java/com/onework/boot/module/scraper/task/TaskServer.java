package com.onework.boot.module.scraper.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public abstract class TaskServer implements ITaskServer {

    /**
     * 服务标识类型
     * @return 服务类型
     */
    public abstract TaskServerType getTaskServerType();

    /**
     *  服务内容
     */
    protected abstract void run();


    protected void beforeStart() {

    }

    /**
     * 执行服务
     */
    public void execute() {

        log.info("[{}],开始执行任务", getTaskServerType().getDescription());
        log.info("[{}],开始初始化服务", getTaskServerType().getDescription());
        long initTime = ScrapeHelper.runTime(this::beforeStart);
        log.info("[{}],完成初始化服务,共计{}秒", getTaskServerType().getDescription(), initTime);
        long runTime = ScrapeHelper.runTime(this::run);
        log.info("[{}],完成执行任务,共计{}秒", getTaskServerType().getDescription(), runTime);
    }
}
