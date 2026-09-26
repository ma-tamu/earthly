import type {AuthService} from "../AuthService.ts";
import type {UserDetail} from "../UserDetail.ts";
import {inject, injectable} from "inversify";
import type {UserRepository} from "../../../repositories/UserRepository.ts";
import {TYPES} from "../../types/di.ts";

@injectable()
export class AuthServiceImpl implements AuthService {

  private readonly userRepository: UserRepository;

  constructor(@inject(TYPES.UserRepository) userRepository: UserRepository) {
    this.userRepository = userRepository
  }

  async getCurrentUser(): Promise<UserDetail> {
    const user = await this.userRepository.me();
    return {
      loginId: user.loginId,
      name: user.name,
      language: user.language,
      timezone: user.timezone,
      permissions: []
    };
  }

  async login(loginId: string, password: string): Promise<void> {
    await this.userRepository.authenticate(loginId, password);
  }

  async logout(): Promise<void> {
    await this.userRepository.logout();
  }
}