import type {User} from "./entities/User.ts";
import type {UserList} from "./entities/UserList.ts";
import type {UserSearchRequest} from "./request/UserSearchRequest.ts";

export interface UserRepository {

  authenticate(loginId: string, password: string): Promise<void>;
  logout(): Promise<void>;
  me(): Promise<User>;
  find(request: UserSearchRequest): Promise<UserList>;
}