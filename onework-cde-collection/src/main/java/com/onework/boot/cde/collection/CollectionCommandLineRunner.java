package com.onework.boot.cde.collection;

import com.onework.boot.cde.collection.process.AllProjectProcessServer;
import com.onework.boot.cde.collection.process.FileDownloadProcessServer;
import com.onework.boot.cde.collection.process.FileParseProcessServer;
import com.onework.boot.cde.collection.process.ScanListProcessServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class CollectionCommandLineRunner implements CommandLineRunner {

    private final ServerConfiguration serverConfiguration;

    private final AllProjectProcessServer allProjectProcessServer;

    private final FileDownloadProcessServer fileDownloadProcessServer;

    private final FileParseProcessServer fileParseProcessServer;

    private final ScanListProcessServer scanListProcessServer;

    public CollectionCommandLineRunner(ServerConfiguration serverConfiguration, AllProjectProcessServer allProjectProcessServer, FileDownloadProcessServer fileDownloadProcessServer, FileParseProcessServer fileParseProcessServer, ScanListProcessServer scanListProcessServer) {
        this.serverConfiguration = serverConfiguration;
        this.allProjectProcessServer = allProjectProcessServer;
        this.fileDownloadProcessServer = fileDownloadProcessServer;
        this.fileParseProcessServer = fileParseProcessServer;
        this.scanListProcessServer = scanListProcessServer;
    }


    @Override
    public void run(String... args) {

        switch (serverConfiguration.getServerType()) {
            case FILE_DOWNLOAD:
                fileDownloadProcessServer.run();
                break;
            case SCAN_LIST:
                scanListProcessServer.run();
                break;
            case FILE_PARSE:
                fileParseProcessServer.run();
                break;
            case SCAN_AND_FILE_DOWNLOAD_AND_FILE_PARSE:
                scanListProcessServer.run();
                fileDownloadProcessServer.run();
                fileParseProcessServer.run();
                break;
            default:
                allProjectProcessServer.run();
                break;
        }
    }
}
