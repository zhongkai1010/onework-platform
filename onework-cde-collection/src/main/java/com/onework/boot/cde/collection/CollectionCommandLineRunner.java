package com.onework.boot.cde.collection;

import com.onework.boot.cde.collection.tasks.AllProjectFileDownloadTaskServer;
import com.onework.boot.cde.collection.tasks.FileParseTaskServer;
import com.onework.boot.cde.collection.tasks.ProjectFileDownloadTaskServer;
import com.onework.boot.cde.collection.tasks.ScanListTaskServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class CollectionCommandLineRunner implements CommandLineRunner {

    private final ServerConfiguration serverConfiguration;

    private final AllProjectFileDownloadTaskServer allProjectProcessServer;

    private final ProjectFileDownloadTaskServer fileDownloadProcessServer;

    private final FileParseTaskServer fileParseProcessServer;

    private final ScanListTaskServer scanListProcessServer;

    public CollectionCommandLineRunner(ServerConfiguration serverConfiguration, AllProjectFileDownloadTaskServer allProjectProcessServer, ProjectFileDownloadTaskServer fileDownloadProcessServer, FileParseTaskServer fileParseProcessServer, ScanListTaskServer scanListProcessServer) {
        this.serverConfiguration = serverConfiguration;
        this.allProjectProcessServer = allProjectProcessServer;
        this.fileDownloadProcessServer = fileDownloadProcessServer;
        this.fileParseProcessServer = fileParseProcessServer;
        this.scanListProcessServer = scanListProcessServer;
    }


    @Override
    public void run(String... args) {

        switch (serverConfiguration.getServerType()) {
            case ALL_FILE_DOWNLOAD:
                allProjectProcessServer.run();
                break;
            case FILE_DOWNLOAD:
                fileDownloadProcessServer.run();
                break;
            case SCAN_LIST:
                scanListProcessServer.run();
                break;
            case FILE_PARSE:
                fileParseProcessServer.run();
                break;
            default:
                break;
        }
    }
}
