package com.onework.parse.cde;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class OneworkParseCdeServerApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkParseCdeServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OneworkParseCdeServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            AtomicInteger atomicInteger = new AtomicInteger(0);

            Path dir = Paths.get("D:\\药物临床试验登记与信息公示平台"); // 替换为目标文件夹的路径
            Files.walk(dir)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        if (atomicInteger.incrementAndGet() < 100) {
                            try {
                                Document doc = Jsoup.parse(path.toFile(), "UTF-8");
                                // 基本信息
                                // 首次公示信息日期
                                System.out.println("首次公示信息日期：" + ParseHelper.getDateOfFirstPublicDisclosure(doc));
                                //（一）题目和背景信息
                                // 1．登记号（不可更新项、公示项）
                                System.out.println("登记号：" + ParseHelper.getRegistrationNumber(doc));
                                // 2．相关登记号（可更新项、公示项）
                                System.out.println("相关登记号：" + ParseHelper.getRelatedRegistrationNumber(doc));
                                // 3．药物名称*（不可更新项、选择公示项）
                                System.out.println("药物名称：" + ParseHelper.getDrugName(doc));
                                // 4．曾用名（可更新项、选择公示项）
                                // 5．药物类型*（不可更新项、公示项）
                                System.out.println("药物类型：" + ParseHelper.getDrugType(doc));
                                // 6．受理号/备案号*（不可更新项、选择公示项）
                                System.out.println("受理号/备案号：" + ParseHelper.getClinicalTrialApplicationAcceptanceNumber(doc));
                                // 7．批件号（备案号）/批准日期（默示许可日期/备案日期）*（不可更新项、不公示项）
                                // 8．适应症*（不可更新项、公示项）
                                System.out.println("适应症：" + ParseHelper.getIndications(doc));
                                // 9．试验专业题目*（不可更新项、公示项）
                                System.out.println("试验专业题目：" + ParseHelper.getTrialProfessionalTitle(doc));
                                // 10．试验通俗题目*（可更新项、公示项）
                                System.out.println("试验通俗题目：" + ParseHelper.getTrialPopularTitle(doc));
                                // 11．试验方案编号*（不可更新项、公示项）
                                System.out.println("试验方案编号：" + ParseHelper.getTrialProtocolNumber(doc));
                                // 12．试验方案编号重复原因（可更新项、不公示项）
                                // 13．方案最新版本号*（可更新项、公示项）
                                System.out.println("方案最新版本号：" + ParseHelper.getLatestProtocolVersionNumber(doc));
                                // 14．版本日期*（可更新项、公示项）
                                System.out.println("版本日期：" + ParseHelper.getVersionDate(doc));
                                // 15．是否联合用药*（不可更新项、公示项）
                                System.out.println("是否联合用药：" + ParseHelper.getIsTheProtocolForCombinationTherapy(doc));

                                //（二）申请人信息
                                // 1．申请人名称（不可更新项、公示项）
                                System.out.println("申请人信息：" + String.join("，", ParseHelper.getApplicantContact(doc)));
                                // 2．联系人姓名*（可更新项、公示项）
                                System.out.println("联系人姓名：" + ParseHelper.getContactPersonName(doc));
                                // 3．联系人座机*（可更新项、公示项）
                                System.out.println("联系人座机：" + ParseHelper.getContactPersonTelephone(doc));
                                // 4．联系人手机号（可更新项、公示项）
                                System.out.println("联系人手机号：" + ParseHelper.getContactPersonMobilePhone(doc));
                                // 5．联系人 Email*（可更新项、公示项）
                                System.out.println("联系人 Email：" + ParseHelper.getContactPersonEmail(doc));
                                // 6．联系人邮政地址*（可更新项、公示项）
                                System.out.println("联系人邮政地址：" + ParseHelper.getContactPersonPostalAddress(doc));
                                // 7．联系人邮编*（可更新项、公示项）
                                System.out.println("联系人邮编：" + ParseHelper.getContactPersonPostalCode(doc));
                                // 8．经费来源*（可更新项、不公示项）

                                //（三）临床试验信息
                                // 1. 试验目的*（不可更新项、公示项）
                                System.out.println("试验目的：" + ParseHelper.getTrialPurpose(doc));
                                // 2.1 试验分类（不可更新项、公示项）
                                System.out.println("试验分类：" + ParseHelper.getTrialClassification(doc));
                                // 2.2 试验分期（不可更新项、公示项）
                                System.out.println("试验分期：" + ParseHelper.getTrialPhase(doc));
                                // 2.3 设计类型（不可更新项、公示项）
                                System.out.println("设计类型：" + ParseHelper.getDesignType(doc));
                                // 2.4 随机化（不可更新项、公示项）
                                System.out.println("随机化：" + ParseHelper.getRandomization(doc));
                                // 2.5 盲法（不可更新项、公示项）
                                System.out.println("盲法：" + ParseHelper.getBlinding(doc));
                                // 2.6 试验范围（不可更新项、公示项）
                                System.out.println("试验范围：" + ParseHelper.getTrialScope(doc));
                                // 3. 受试者信息
                                // 3.1 年龄*（不可更新项、公示项）
                                System.out.println("年龄：" + ParseHelper.getAge(doc));
                                // 3.2 性别*（不可更新项、公示项）
                                System.out.println("性别：" + ParseHelper.getGender(doc));
                                // 3.3 健康受试者*（不可更新项、公示项）
                                System.out.println("健康受试者：" + ParseHelper.getHealthyVolunteers(doc));
                                // 3.4 入选标准*（可更新项、公示项）
                                System.out.println("入选标准：");
                                ParseHelper.getInclusionCriteria(doc).forEach(System.out::println);
                                // 3.5 排除标准*（可更新项、公示项）
                                System.out.println("排除标准：");
                                ParseHelper.getExclusionCriteria(doc).forEach(System.out::println);
                                // 4. 试验分组
                                // 4.1 试验药*
                                System.out.println("试验药：");
                                ParseHelper.getTrialMedication(doc).forEach(System.out::println);
                                // 4.1.1 名称（不可更新项、公示项）
                                // 4.1.2 生产信息/批号（不可更新项、不公示项）
                                // 4.1.3 用法（不可更新项、公示项）
                                // 4.2 对照药
                                System.out.println("对照药：");
                                ParseHelper.getControlMedication(doc).forEach(System.out::println);
                                // 4.2.1 名称（不可更新项、公示项）
                                // 4.2.2 生产信息/批号（不可更新项、不公示项）
                                // 4.2.3 用法（不可更新项、公示项）
                                // 5. 终点指标
                                // 5.1 主要终点指标及评价时间*（不可更新项、公示项）
                                System.out.println("主要终点指标及评价时间：");
                                ParseHelper.getPrimaryEndpointAndTimingOfAssessment(doc).forEach(System.out::println);
                                // 5.2 次要终点指标及评价时间（不可更新项、公示项）
                                System.out.println("次要终点指标及评价时间：");
                                ParseHelper.getSecondaryEndpointAndTimingOfAssessment(doc).forEach(System.out::println);
                                // 6. 数据安全监查委员会（DMC）*（可更新项、公示项）
                                System.out.println("数据安全监查委员会：" + ParseHelper.getDataMonitoringCommittee(doc));
                                // 7. 为受试者购买试验伤害保险*（可更新项、公示项）
                                System.out.println("为受试者购买试验伤害保险：" + ParseHelper.getInsurancePurchased(doc));
                                //（四）研究者信息
                                // 1. 主要研究者信息（可更新项、公示项）
                                System.out.println("主要研究者信息：");
                                ParseHelper.getPrincipalInvestigatorInformation(doc).forEach(System.out::println);
                                // ●姓名*
                                // ●学位：
                                // ●职称：
                                // ●电话：
                                // ●Email：
                                // ●邮政地址
                                // ●邮编
                                // ●单位名称
                                // 2. 各参加机构信息（可更新项、公示项）
                                System.out.println("各参加机构信息：");
                                ParseHelper.getInformationOnParticipatingInstitutions(doc).forEach(System.out::println);
                                // ●机构名称：
                                // ●（主要）研究者
                                // ●国家或地区：
                                // ●省（州）：
                                // ●城市
                                //（五）伦理委员会信息*（不可更新项、公示项）
                                System.out.println("伦理委员会信息：");
                                ParseHelper.getEthicsCommitteeInformation(doc).forEach(System.out::println);
                                // ●名称：
                                // ●审查结论
                                // ●伦理批件/备案证明文件
                                // ●批准日期/备案日期：
                                // ●知情同意书：
                                // ●成员姓名：
                                // ●IRB/EC

                                //（六）试验状态信息
                                // 1. 试验状态*（可更新项、公示项）
                                System.out.println("试验状态信息：" + ParseHelper.getTrialStatus(doc));
                                // 2. 试验人数
                                System.out.println("试验人数：");
                                // 2.1 目标入组人数
                                // 2.3 已入组人数
                                // 2.5 实际入组总人数
                                ParseHelper.getNumberOfParticipants(doc).forEach(System.out::println);
                                // 3. 受试者招募及试验完成日期
                                ParseHelper.getSubjectRecruitmentAndTrialCompletionDate(doc).forEach(System.out::println);
                                // 第一例受试者签署知情同意书日期
                                // 第一例受试者入组日期
                                // 试验完成日期
                                // 试验暂停日期
                                // 试验终止日期
                                //（八）临床试验结果摘要（不可更新项、公示项）
                                System.out.println("临床试验结果摘要：");
                                ParseHelper.getTrialResultsSummary(doc).forEach(System.out::println);
                                System.out.println("----------------------------------------------------------------------");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
