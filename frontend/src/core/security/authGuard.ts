import {container} from "../config/inversify.config.ts";
import type {AuthService} from "./AuthService.ts";
import {TYPES} from "../types/di.ts";
import {redirect} from "react-router";

export const authGuard = async (permission?: string) => {
  const authService = container.get<AuthService>(TYPES.AuthService);
  try {
    const user = await authService.getCurrentUser();

    // 💡 権限（認可）のチェックもここに集約して一網打尽にできます
    if (permission && !user.permissions.includes(permission)) {
      throw new Error('FORBIDDEN');
    }

    return user;
  } catch (error: any) {
    if (error.message === 'FORBIDDEN') {
      return redirect('/dashboard'); // 権限なしはダッシュボードへ
    }
    throw redirect('/login'); // 未認証はログイン画面へ強制リダイレクト
  }
}