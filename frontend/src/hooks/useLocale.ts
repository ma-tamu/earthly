import {useSearchParams, useRouteLoaderData} from 'react-router';

export function useLocale() {
  const [searchParams, setSearchParams] = useSearchParams();

  // 💡 1. 最上位（id: "root"）の loader が取得済みのデータを直接のぞき込む
  const rootData = useRouteLoaderData('root') as {
    translations: Record<string, any>;
    locale: 'ja' | 'en'
  } | undefined;

  // データの新鮮度を担保するためのフォールバック設定
  const dict = rootData?.translations ?? {};
  const locale = rootData?.locale ?? 'ja';

  /**
   * 💡 2. ドット区切りのパス文字列（"login.title"）から安全に多言語テキストを抽出する関数
   * 万が一バックエンド側でキーが削除・変更されていても、画面がクラッシュすることなく
   * パス文字列そのものが返る耐壊性（フォールトトレランス）を100%ピュアに維持しています。
   */
  const t = (path: string): string => {
    const keys = path.split('.');
    let current: any = dict;

    for (const key of keys) {
      if (current && typeof current === 'object' && key in current) {
        current = current[key];
      } else {
        return path; // キーが見つからなければ、デバッグしやすくするためにパス文字列そのものを返す
      }
    }

    return typeof current === 'string' ? current : path;
  };

  /**
   * 💡 3. 言語を切り替える関数
   * 内部のブラックボックスなキャッシュを書き換えるのではなく、URLパラメータ（?lang=en）をスマートに更新。
   * パラメータが変わった瞬間、最上位（root）の loader が自動検知してバックエンドAPI（MSW）から
   * 新しい言語の辞書データを一瞬で引き直し、画面全体が超高速で再レンダリングされます。
   */
  const setLocale = (newLocale: 'ja' | 'en') => {
    const newParams = new URLSearchParams(searchParams);
    newParams.set('lang', newLocale);
    setSearchParams(newParams, {replace: true}); // ブラウザ履歴を無駄に汚さないよう replace を指定
  };

  return {locale, setLocale, t};
}