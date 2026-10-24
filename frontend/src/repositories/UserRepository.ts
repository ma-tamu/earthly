import type {UserEntity} from "./entities/UserEntity.ts";
import type {UserList} from "./entities/UserList.ts";
import type {UserSearchRequest} from "./request/UserSearchRequest.ts";
import type {UserEntryRequest} from "./request/UserEntryRequest.ts";
import type {MessageResponse} from "./response/MessageResponse.ts";
import type {AuthenticationRequest} from "./request/AuthenticationRequest.ts";
import type {RoleList} from "./entities/RoleList.ts";
import type {UserRoleSearchRequest} from "./request/UserRoleSearchRequest.ts";

export interface UserRepository {

  authenticate(request: AuthenticationRequest): Promise<void>;
  logout(): Promise<void>;
  me(): Promise<UserEntity>;
  find(request: UserSearchRequest): Promise<UserList>;
  entry(request: UserEntryRequest): Promise<MessageResponse>
  entryConfirm(request: UserEntryRequest): Promise<void>;
  get(id: string): Promise<UserEntity>;
  findRole(id: string, request: UserRoleSearchRequest): Promise<RoleList>;
}