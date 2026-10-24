import type {BaseSearchRequest} from "./BaseSearchRequest.ts";

export interface UserRoleSearchRequest extends BaseSearchRequest{
  search: string,
}