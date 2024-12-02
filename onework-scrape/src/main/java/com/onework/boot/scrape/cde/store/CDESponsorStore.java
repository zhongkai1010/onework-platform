package com.onework.boot.scrape.cde.store;

import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.data.entity.CDESponsor;
import com.onework.boot.scrape.data.mapper.CDESponsorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CDESponsorStore {

    private final CDESponsorMapper sponsorMapper;

    private final Map<String, CDESponsor> sponsorStore = Collections.synchronizedMap(new HashMap<>());

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public CDESponsorStore(CDESponsorMapper sponsorMapper) {
        this.sponsorMapper = sponsorMapper;
        initDate();
    }

    private void initDate() {
        List<CDESponsor> sponsors = sponsorMapper.selectList(null);
        for (CDESponsor sponsor : sponsors) {
            sponsorStore.put(sponsor.getSponsorName(), sponsor);
        }
    }

    public void checkAndInsertIfNotExist(CDESponsor sponsor) {

        try {
            if (!sponsorStore.containsKey(sponsor.getSponsorName())) {
                sponsorMapper.insert(sponsor);
                sponsorStore.put(sponsor.getSponsorName(), sponsor);
                LOG.error("新增申办方：{}", sponsor.getSponsorName());
            }
        } catch (Exception exception) {
            LOG.error("申办方：{}，错误消息：{}", sponsor.getSponsorName(), exception.getMessage());
        }
    }


}
