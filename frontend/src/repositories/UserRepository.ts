import type {User} from "./entities/User.ts";

export interface UserRepository {

  authenticate(loginId: string, password: string): Promise<void>;
  logout(): Promise<void>;
  me(): Promise<User>;
}