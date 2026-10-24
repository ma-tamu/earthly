import type {Attribute} from "./Attribute.ts";
import type {RoleEntity} from "./RoleEntity.ts";

export interface RoleList {
  attribute: Attribute;
  roles: RoleEntity[];
}