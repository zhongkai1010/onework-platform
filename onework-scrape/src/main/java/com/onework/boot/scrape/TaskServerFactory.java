package com.onework.boot.scrape;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class TaskServerFactory {

    private final ApplicationContext applicationContext;

    public TaskServerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     *  获取指定类型服务
     * @param type 服务类型
     * @return 服务
     */
    public ITaskServer getTaskServer(TaskServerType type) {
        TaskServer result = null;
        Map<String, TaskServer> taskServerMap = applicationContext.getBeansOfType(TaskServer.class);
        Collection<TaskServer> taskServers = taskServerMap.values();
        for (TaskServer taskServer : taskServers) {
            TaskServerType taskServerType = taskServer.getTaskServerType();
            if (taskServerType == type) {
                result = taskServer;
            }
        }
        if (result == null) {
            throw new NoSuchBeanDefinitionException(String.valueOf(type));
        } else {
            return result;
        }
    }
}
