package com.onework.boot.scrape.cde.store;


import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.data.entity.CDEInstitution;
import com.onework.boot.scrape.data.mapper.CDEInstitutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CDEInstitutionStore {

    private final CDEInstitutionMapper institutionMapper;

    private final Map<String, CDEInstitution> institutionStore = new HashMap<String, CDEInstitution>();

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public CDEInstitutionStore(CDEInstitutionMapper institutionMapper) {
        this.institutionMapper = institutionMapper;
        initData();
    }

    private void initData() {
        List<CDEInstitution> institutions = institutionMapper.selectList(null);
        for (CDEInstitution institution : institutions) {
            institutionStore.put(institution.getInstitutionName(), institution);
        }
    }

    /**
     *  判断机构是否存在，不存在则新增
     * @param institution 机构信息
     */
    public void checkAndInsertIfNotExist(CDEInstitution institution) {
        try {
            if (institutionStore.containsKey(institution.getInstitutionName())) {
                CDEInstitution cdeInstitution = institutionStore.get(institution.getInstitutionName());
                cdeInstitution.setAddress(institution.getAddress());
                cdeInstitution.setPostalCode(institution.getPostalCode());
                institutionMapper.updateById(cdeInstitution);
                institutionStore.replace(cdeInstitution.getInstitutionName(), cdeInstitution);
                LOG.info("更新机构：{}", institution.getInstitutionName());
            } else {
                institutionMapper.insert(institution);
                institutionStore.put(institution.getInstitutionName(), institution);
                LOG.info("新增机构：{}", institution.getInstitutionName());
            }
        } catch (Exception exception) {
            LOG.error("机构：{}，出现异常，错误消息：{}", institution.getInstitutionName(), exception.getMessage());
        }
    }
}
