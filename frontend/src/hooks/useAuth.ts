import {useNavigate, useRouteLoaderData} from "react-router";
import {useInjection} from "./useInjection.ts";
import type {AuthService} from "../core/security/AuthService.ts";
import {TYPES} from "../core/types/di.ts";
import type {UserDetail} from "../core/security/UserDetail.ts";
import type {LoginParams} from "../types/auth.ts";

export function useAuth() {

  const navigate = useNavigate();
  const authService = useInjection<AuthService>(TYPES.AuthService);

  const rootData = useRouteLoaderData('AuthenticationPrincipal') as { user: UserDetail | null } | undefined;
  const user = rootData?.user ?? null;

  // ログイン処理
  const login = async (body: LoginParams) => {
    // 1. シングルトンサービス経由でバックエンドAPIを実行（Cookieが書き込まれる）
    await authService.login(body.loginId, body.password);

    // 2. 💡 ログイン成功後、画面全体をリロードすることなく
    // 最上位（/）へ遷移させて root の loader を再発火させ、最新の user 状態に自動更新します
    navigate('/dashboard', {replace: true});
  };

  // ログアウト処理
  const logout = async () => {
    try {
      // 1. バックエンドAPIを叩いてサーバー側のセッションCookieを消去
      await authService.logout();
    } catch (error) {
      console.error('ログアウトAPIエラー:', error);
    } finally {
      // 2. 💡 ログイン画面へ強制リダイレクト
      // 遷移と同時に root loader が自動再検証され、user は自動的に null に切り替わります
      navigate('/login', {replace: true});
    }
  };

  // 認可チェック（役割ベースのアクセス制御）
  const hasPermission = (permission: string): boolean => {
    if (!user?.permissions) {
      return false;
    }
    return user.permissions.includes(permission);
  };

  return {
    user,
    isLoading: false, // 💡 loader 側で通信が完了してから画面が切り替わる（Dataモードの特性）ため、画面側でのロード待機フラグは「不要（常にfalse）」になります
    isLoggingIn: false, // フォーム送信中の多重連打を防止したい場合は、LoginPage 側のローカル State でシンプルに管理するのがベストです
    login,
    logout,
    hasPermission,
  };
}