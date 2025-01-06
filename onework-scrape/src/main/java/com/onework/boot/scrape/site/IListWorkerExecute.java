package com.onework.boot.scrape.site;

import java.util.List;

public interface IListWorkerExecute<T> {
    void execute(int start, int end, List<T> items);
}
