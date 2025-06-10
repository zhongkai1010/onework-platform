package com.onework.boot.module.infra.framework.file.core.client.db;

import com.onework.boot.module.infra.framework.file.core.client.AbstractFileClient;

/**
 * 基于 DB 存储的文件客户端的配置类
 *

 */
public class DBFileClient extends AbstractFileClient<DBFileClientConfig> {


    public DBFileClient(Long id, DBFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {

    }

    @Override
    public String upload(byte[] content, String path, String type) throws Exception {
        return "";
    }

    @Override
    public void delete(String path) throws Exception {

    }

    @Override
    public byte[] getContent(String path) throws Exception {
        return new byte[0];
    }
}
