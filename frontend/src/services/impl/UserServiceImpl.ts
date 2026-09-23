import type {UserPaginatedResponse, UserService} from "../UserService.ts";
import {inject, injectable} from "inversify";
import {TYPES} from "../../types/di.ts";
import type {UserRepository} from "../../repositories/UserRepository.ts";

@injectable()
export class UserServiceImpl implements UserService {

  private readonly userRepository: UserRepository;

  constructor(@inject(TYPES.UserRepository) userRepository: UserRepository) {
    this.userRepository = userRepository;
  }

  async getUsers(page: number): Promise<UserPaginatedResponse> {
    const users = await this.userRepository.find(page);
console.log("users ->", users);
    return {
      data: users.users,
      meta: {
        offset: users.attribute.offset,
        length: users.attribute.length,
        total: users.attribute.total
      }
    };
  }
}