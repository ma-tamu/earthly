import type {BaseSearchRequest} from "./BaseSearchRequest.ts";

export interface UserSearchRequest extends BaseSearchRequest{
  search: string,
}