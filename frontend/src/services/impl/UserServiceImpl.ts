import type {User, UserPaginatedResponse, UserService} from "../UserService.ts";
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
    const list = users.map(user => ({
      id: user.id,
      name: user.name,
      email: user.email,
      role: 'admin',
      status: 'active',
      createdAt: user.createdAt.toISOString()
    }) as User);
    return {
      data: list,
      meta: {
        currentPage: 1,
        totalCount: 1,
        totalPages: 1
      }
    };
  }
}