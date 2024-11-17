package com.onework.boot.ctr.collection;

import com.onework.boot.ctr.collection.process.FileDownloadProcessServer;
import com.onework.boot.ctr.collection.process.ScanListProcessServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class CollectionCommandLineRunner implements CommandLineRunner {

    private final ScanListProcessServer scanListProcessServer;
    private final FileDownloadProcessServer fileParseProcessServer;
    private final ServerConfiguration serverConfiguration;

    public CollectionCommandLineRunner(ScanListProcessServer scanListProcessServer, FileDownloadProcessServer fileParseProcessServer, ServerConfiguration serverConfiguration) {
        this.scanListProcessServer = scanListProcessServer;
        this.fileParseProcessServer = fileParseProcessServer;
        this.serverConfiguration = serverConfiguration;
    }

    @Override
    public void run(String... args) {

        switch (serverConfiguration.getServerType()) {
            case SCAN_LIST:
                scanListProcessServer.run();
                break;
            case FILE_DOWNLOAD:
                fileParseProcessServer.run();
                break;
            default:
                break;
        }
    }
}
