package com.onework.boot.scrape;

import java.util.List;

public interface IListWorkerExecute<T> {
    void execute(int start, int end, List<T> items);
}
