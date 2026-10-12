import i18n from 'i18next';
import {container} from "./core/config/inversify.config.ts";
import {TYPES} from "./core/types/di.ts";
import type {HttpClient} from "./core/api/HttpClient.ts";
import {initReactI18next} from "react-i18next";

const httpClient = container.get<HttpClient>(TYPES.HttpClient);

const dictionary = {
  type: 'backend' as const,
  read: async (language: string, namespace: string, callback: (err: any, data: any) => void) => {
    try {
      const query = new URLSearchParams({
        lang: language,
      }).toString();
      const json = await httpClient.get(`/api/languages?${query}`);
      console.log("dic ->", json);
      callback(null, json);
    } catch (err) {
      console.error(err);
      callback(err, null);
    }
  }
};

export const supportedLngs = {
  en: "English",
  ja: "日本語",
};

i18n
  .use(dictionary)
  .use(initReactI18next)
  .init({
    lng: 'ja',
    fallbackLng: "ja",
    returnEmptyString: false,
    supportedLngs: Object.keys(supportedLngs),
    debug: true,
    interpolation: { escapeValue: false },
  });

export default i18n;