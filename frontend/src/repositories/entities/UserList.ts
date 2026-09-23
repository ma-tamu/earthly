import type {User} from "./User.ts";
import type {Attribute} from "./Attribute.ts";

export interface UserList {
  attribute: Attribute;
  users: User[];
}