package com.onework.boot.scrape;


public interface IWhileExecute {

    /**
     *  执行
     * @return true，退出循环，反之继续执行
     */
    boolean execute();

    /**
     *  错误处理
     * @param exception 异常
     * @return true，退出循环，反之继续执行
     */
    default boolean errorHandle(Exception exception) {
        return false;
    }
}
