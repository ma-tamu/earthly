export interface LocaleService {

  fetchTranslations(locale: 'ja' | 'en'): Promise<Record<string, unknown>>;
}