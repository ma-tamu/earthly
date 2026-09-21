import type {UserRepository} from "../UserRepository.ts";
import {inject, injectable} from "inversify";
import type {User} from "../entities/User.ts";
import type {HttpClient} from "../../api/HttpClient.ts";

@injectable()
export class UserRepositoryImpl implements UserRepository {

  private readonly httpClient: HttpClient;

  constructor(@inject("HttpClient") httpClient: HttpClient) {
    this.httpClient = httpClient;
  }


  async me(): Promise<User> {
    return await this.httpClient.get<User>("/api/users/me");
  }

  async authenticate(loginId: string, password: string): Promise<void> {
    await this.httpClient.post<string>('/api/login', {
      contentType: "form",
      body: {loginId: loginId, password: password}
    });
  }

  async logout(): Promise<void> {
    await this.httpClient.delete<void>("/api/logout");
  }

}