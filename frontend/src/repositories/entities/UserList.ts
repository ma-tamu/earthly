import type {UserEntity} from "./UserEntity.ts";
import type {Attribute} from "./Attribute.ts";

export interface UserList {
  attribute: Attribute;
  users: UserEntity[];
}