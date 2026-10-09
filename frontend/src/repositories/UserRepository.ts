import type {User} from "./entities/User.ts";
import type {UserList} from "./entities/UserList.ts";
import type {UserSearchRequest} from "./request/UserSearchRequest.ts";
import type {UserEntryRequest} from "./request/UserEntryRequest.ts";
import type {MessageResponse} from "./response/MessageResponse.ts";
import type {AuthenticationRequest} from "./request/AuthenticationRequest.ts";

export interface UserRepository {

  authenticate(request: AuthenticationRequest): Promise<void>;
  logout(): Promise<void>;
  me(): Promise<User>;
  find(request: UserSearchRequest): Promise<UserList>;
  entry(request: UserEntryRequest): Promise<MessageResponse>
  entryConfirm(request: UserEntryRequest): Promise<void>;
}