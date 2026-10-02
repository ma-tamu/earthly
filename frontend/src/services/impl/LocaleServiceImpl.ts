import type {LocaleService} from "../LocaleService.ts";
import type {HttpClient} from "../../core/api/HttpClient.ts";
import {inject, injectable} from "inversify";
import {TYPES} from "../../core/types/di.ts";

@injectable()
export class LocaleServiceImpl implements LocaleService {

  private readonly httpClient: HttpClient;

  constructor(@inject(TYPES.HttpClient) httpClient: HttpClient) {
    this.httpClient = httpClient;
  }


  async fetchTranslations(locale: "ja" | "en"): Promise<Record<string, unknown>> {
    return this.httpClient.get<Record<string, unknown>>(`/api/locales/${locale}`);;
  }
}