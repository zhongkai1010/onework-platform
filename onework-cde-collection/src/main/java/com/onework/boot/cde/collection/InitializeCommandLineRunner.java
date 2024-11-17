package com.onework.boot.cde.collection;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.data.entity.CDECollectionRecord;
import com.onework.boot.data.mapper.CDECollectionRecordMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Order(1)
@Component
public class InitializeCommandLineRunner implements CommandLineRunner {

    private final ServerConfiguration serverConfiguration;

    private final CDECollectionRecordMapper recordMapper;

    public InitializeCommandLineRunner(ServerConfiguration serverConfiguration, CDECollectionRecordMapper recordMapper) {
        this.serverConfiguration = serverConfiguration;
        this.recordMapper = recordMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (serverConfiguration.isInitData()) {
            Path dir = Paths.get(serverConfiguration.getSavePath()); // 替换为目标文件夹的路径
            Files.walk(dir)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String filePath = path.toAbsolutePath().toString();
                        String registrationNumber = path.getFileName().toString().replaceAll(".html", "");
                        boolean exist = recordMapper.exists(Wrappers.<CDECollectionRecord>lambdaQuery().eq(CDECollectionRecord::getRegistrationNumber, registrationNumber));
                        if (!exist) {
                            CDECollectionRecord record = new CDECollectionRecord();
                            record.setRegistrationNumber(registrationNumber);
                            record.setCollectionDate(LocalDateTime.now());
                            record.setParseHandle(false);
                            record.setFilePath(filePath);
                            recordMapper.insert(record);
                        }
                    });
        }

    }
}
