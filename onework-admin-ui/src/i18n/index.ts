/**
 * 国际化配置
 */
import { createI18n } from 'vue-i18n';
import { getCacheLang } from './use-locale';
import zh_CN from './lang/zh_CN';
import zh_TW from './lang/zh_TW';
import en from './lang/en';

const messages = { zh_CN, zh_TW, en };

function getValidLocale() {
  const lang = getCacheLang();
  return Object.keys(messages).includes(lang) ? lang : 'zh_CN';
}

const i18n = createI18n({
  messages,
  legacy: false,
  // silentTranslationWarn: true, // vue-i18n v9 已无此选项
  locale: getValidLocale(),
  fallbackLocale: 'zh_CN'
});

export default i18n;
