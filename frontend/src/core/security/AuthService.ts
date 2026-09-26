import type {UserDetail} from "./UserDetail.ts";

/**
 * 認証サービス
 */
export interface AuthService {
  /**
   * 認証したユーザーを取得する。
   */
  getCurrentUser(): Promise<UserDetail>
  login(loginId: string, password: string): Promise<void>;
  logout(): Promise<void>;
}