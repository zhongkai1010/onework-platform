import request from '@/utils/request';
import type { ApiResult } from '@/api';
import type { PayNumData, SaleroomResult, VisitData, CloudData } from './model';

/**
 * 获取支付笔数数据
 */
export async function getPayNumList() {
  const res = await request.get<ApiResult<PayNumData[]>>(
    'https://cdn.eleadmin.com/20200610/analysis-pay-num.json'
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 获取销售量数据
 */
export async function getSaleroomList() {
  const res = await request.get<ApiResult<SaleroomResult>>(
    'https://cdn.eleadmin.com/20200610/analysis-saleroom.json'
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 获取最近 1 小时访问情况数据
 * @returns {Promise<Object>}
 */
export async function getVisitHourList() {
  const res = await request.get<ApiResult<VisitData[]>>(
    'https://cdn.eleadmin.com/20200610/analysis-visits.json'
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 获取词云数据
 */
export async function getWordCloudList() {
  const data: CloudData[] = [
    { name: '社死', value: 28 },
    { name: '集美', value: 19 },
    { name: '神兽', value: 12 },
    { name: '夺笋', value: 12 },
    { name: '直播', value: 19 },
    { name: '懂王', value: 16 },
    { name: '硬核', value: 21 },
    { name: '国潮', value: 17 },
    { name: '冲鸭', value: 13 },
    { name: 'C位', value: 22 },
    { name: '上头', value: 23 },
    { name: '安排', value: 13 },
    { name: '好方', value: 13 },
    { name: '社畜', value: 22 },
    { name: 'CP', value: 8 },
    { name: '笑哭', value: 11 },
    { name: '吃土', value: 28 },
    { name: '狗带', value: 27 },
    { name: '懵逼', value: 19 },
    { name: '宝宝', value: 19 },
    { name: '破防', value: 12 },
    { name: '小丑', value: 22 },
    { name: '萝莉', value: 8 },
    { name: '辣~', value: 22 },
    { name: 'K歌', value: 14 },
    { name: '女神', value: 28 },
    { name: '米粉', value: 28 },
    { name: '逛街', value: 18 },
    { name: '坚持', value: 8 },
    { name: '话唠', value: 8 },
    { name: '果粉', value: 12 },
    { name: '花粉', value: 22 },
    { name: '宅男', value: 10 },
    { name: '琼瑶', value: 11 },
    { name: '正太', value: 22 },
    { name: '逗比', value: 19 },
    { name: '腹黑', value: 8 },
    { name: '吃鸡', value: 21 },
    { name: '民谣', value: 14 },
    { name: '追剧', value: 27 },
    { name: '锤粉', value: 12 },
    { name: '欧皇', value: 26 },
    { name: '软妹子', value: 19 },
    { name: '汪星人', value: 26 },
    { name: '大长腿', value: 23 },
    { name: '川妹子', value: 20 },
    { name: '黑长直', value: 17 },
    { name: '萌萌哒', value: 10 },
    { name: '喵星人', value: 12 },
    { name: '衬衫控', value: 15 },
    { name: '小清新', value: 26 },
    { name: '眼镜男', value: 9 },
    { name: '穷游党', value: 21 },
    { name: '铲屎官', value: 11 },
    { name: '中二病', value: 26 },
    { name: '夜猫子', value: 22 },
    { name: '背包客', value: 23 },
    { name: '官宣体', value: 15 },
    { name: '两年半', value: 12 },
    { name: '年代感', value: 17 },
    { name: '绝绝子', value: 24 },
    { name: 'YYDS', value: 18 },
    { name: 'emo了', value: 25 },
    { name: '长腿欧巴', value: 24 },
    { name: '专注设计', value: 9 },
    { name: '海纳百川', value: 17 },
    { name: '为了联盟', value: 13 },
    { name: '为了部落', value: 23 },
    { name: '懒癌患者', value: 9 },
    { name: 'IT民工', value: 12 },
    { name: '选择困难', value: 16 },
    { name: '仙气十足', value: 28 },
    { name: '杠精附体', value: 8 },
    { name: '佛系青年', value: 14 },
    { name: '内卷到底', value: 14 },
    { name: '躺平青年', value: 18 },
    { name: '社恐达人', value: 23 },
    { name: '锦鲤附体', value: 26 },
    { name: '鸡你太美', value: 9 },
    { name: '秃然袭击', value: 19 },
    { name: '带货女王', value: 13 },
    { name: 'C位出道', value: 8 },
    { name: '真香定律', value: 10 },
    { name: '尬聊王者', value: 18 },
    { name: '补刀能手', value: 12 },
    { name: '流量密码', value: 8 },
    { name: '爱了爱了', value: 13 },
    { name: '溜了溜了', value: 21 },
    { name: '蓝瘦香菇', value: 18 },
    { name: '撩妹高手', value: 22 },
    { name: '套路满满', value: 10 },
    { name: '洪荒之力', value: 9 },
    { name: '浪姐同款', value: 20 },
    { name: '躺平摆烂', value: 27 },
    { name: '消费主义', value: 25 },
    { name: '伤害不大', value: 20 },
    { name: '不懂就问', value: 17 },
    { name: '有被冒犯', value: 25 },
    { name: '吃瓜群众', value: 25 },
    { name: 'CNB成员', value: 16 }
  ];
  return data;
}
