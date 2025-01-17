package com.onework.boot.task.collection.core;

import java.util.List;

public interface IListWorkerExecute<T> {
    void execute(int start, int end, List<T> items);
}
