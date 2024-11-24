package com.onework.boot.ctr.collection;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.data.entity.CTRCollectionRecord;
import com.onework.boot.data.mapper.CTRCollectionRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
@Component
public class InitializeCommandLineRunner implements CommandLineRunner {

    private final ServerConfiguration serverConfiguration;

    private final CTRCollectionRecordMapper recordMapper;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCTRCollectionApplication.class);

    public InitializeCommandLineRunner(ServerConfiguration serverConfiguration, CTRCollectionRecordMapper recordMapper) {
        this.serverConfiguration = serverConfiguration;
        this.recordMapper = recordMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, String> pathMaps = new HashMap<>();

        if (serverConfiguration.isInitData()) {
            Path dir = Paths.get(serverConfiguration.getSavePath()); // 替换为目标文件夹的路径
            Files.walk(dir)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String filePath = path.toAbsolutePath().toString();
                        String content = FileUtil.readString(filePath, StandardCharsets.UTF_8);

                        Pattern scriptPattern = Pattern.compile("<script.*?>.*?</script>", Pattern.DOTALL);
                        Matcher matcher = scriptPattern.matcher(content);
                        // 替换<script>标签内容为空字符串
                        String newHtmlContent = matcher.replaceAll("");

                        FileUtil.del(filePath);
                        FileUtil.touch(filePath);
                        FileUtil.writeString(newHtmlContent,filePath, StandardCharsets.UTF_8);

                        String registrationNumber = path.getFileName().toString().replaceAll(".html", "");
                        pathMaps.put(registrationNumber, filePath);

                    });
            List<CTRCollectionRecord> records = recordMapper.selectList(Wrappers.<CTRCollectionRecord>lambdaQuery().isNull(CTRCollectionRecord::getFilePath));
            for (CTRCollectionRecord record : records) {
                if (pathMaps.containsKey(record.getRegistrationNumber())) {
                    record.setFilePath(pathMaps.get(record.getRegistrationNumber()));
                    record.setIsDownload(true);
                    record.setDownloadDate(LocalDateTime.now());
                    recordMapper.updateById(record);
                    LOG.info("{}文件已同步", record.getRegistrationNumber());
                }
            }
        }
    }
}
