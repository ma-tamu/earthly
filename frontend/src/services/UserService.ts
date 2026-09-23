import type {User} from "../repositories/entities/User.ts";

export type UserPaginatedResponse = {
  data: User[];
  meta: {
    offset: number;
    length: number;
    total: number;
  };
};

export interface UserService {
  getUsers(page: number): Promise<UserPaginatedResponse>;
}