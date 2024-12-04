package com.onework.boot.scrape;


public interface IWhileExecute {
    boolean execute();

    default boolean errorHandle(Exception exception) {
        return false;
    }
}
