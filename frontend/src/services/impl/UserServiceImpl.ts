import type {UserPaginatedResponse, UserService} from "../UserService.ts";
import {inject, injectable} from "inversify";
import {TYPES} from "../../types/di.ts";
import type {UserRepository} from "../../repositories/UserRepository.ts";
import type {UserSearch} from "../dto/UserSearch.ts";

@injectable()
export class UserServiceImpl implements UserService {

  private readonly userRepository: UserRepository;

  constructor(@inject(TYPES.UserRepository) userRepository: UserRepository) {
    this.userRepository = userRepository;
  }

  async getUsers(param: UserSearch): Promise<UserPaginatedResponse> {
    const users = await this.userRepository.find({
      page: param.page,
      size: param.limit,
      search: param.search,
      sort: param.sortBy,
      order: param.sortOrder
    });
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