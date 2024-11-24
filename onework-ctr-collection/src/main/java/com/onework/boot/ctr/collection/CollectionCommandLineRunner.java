package com.onework.boot.ctr.collection;

import com.onework.boot.ctr.collection.tasks.FileDownloadTaskServer;
import com.onework.boot.ctr.collection.tasks.FileParseTaskServer;
import com.onework.boot.ctr.collection.tasks.ScanListTaskServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class CollectionCommandLineRunner implements CommandLineRunner {

    private final ScanListTaskServer scanListProcessServer;
    private final FileDownloadTaskServer fileParseProcessServer;
    private final ServerConfiguration serverConfiguration;
    private final ProjectRecordStore projectRecordStore;
    private FileParseTaskServer fileParseTaskServer;

    public CollectionCommandLineRunner(ScanListTaskServer scanListProcessServer, FileDownloadTaskServer fileParseProcessServer, ServerConfiguration serverConfiguration, ProjectRecordStore projectRecordStore, FileParseTaskServer fileParseTaskServer) {
        this.scanListProcessServer = scanListProcessServer;
        this.fileParseProcessServer = fileParseProcessServer;
        this.serverConfiguration = serverConfiguration;
        this.projectRecordStore = projectRecordStore;
        this.fileParseTaskServer = fileParseTaskServer;
    }

    @Override
    public void run(String... args) {

        projectRecordStore.initData();
        if (!serverConfiguration.isInitData()) {
            switch (serverConfiguration.getServerType()) {
                case SCAN_LIST:
                    scanListProcessServer.run();
                    break;
                case FILE_DOWNLOAD:
                    fileParseProcessServer.run();
                    break;
                case FILE_PARSE:
                    fileParseTaskServer.run();
                    break;
                default:
                    break;
            }
        }
    }
}
