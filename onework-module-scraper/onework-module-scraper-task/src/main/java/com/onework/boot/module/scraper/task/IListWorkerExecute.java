package com.onework.boot.module.scraper.task;

import java.util.List;

public interface IListWorkerExecute<T> {
    void execute(int start, int end, List<T> items);
}
