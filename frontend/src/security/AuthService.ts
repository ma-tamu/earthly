import type {UserDetail} from "./UserDetail.ts";

export interface AuthService {
  getCurrentUser(): Promise<UserDetail>
  login(loginId: string, password: string): Promise<void>;
  logout(): Promise<void>;
}