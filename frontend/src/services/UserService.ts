import type {User} from "../repositories/entities/User.ts";
import type {UserSearch} from "./dto/UserSearch.ts";

export type UserPaginatedResponse = {
  data: User[];
  meta: {
    offset: number;
    length: number;
    total: number;
  };
};

/**
 * ユーザーサービス
 */
export interface UserService {

  /**
   * ユーザー一覧を取得する。
   * @param param 検索条件
   * @return ユーザー一覧
   */
  getUsers(param: UserSearch): Promise<UserPaginatedResponse>;
}