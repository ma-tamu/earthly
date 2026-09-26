import type {UserRepository} from "../UserRepository.ts";
import {inject, injectable} from "inversify";
import type {User} from "../entities/User.ts";
import type {HttpClient} from "../../api/HttpClient.ts";
import {TYPES} from "../../types/di.ts";
import type {UserList} from "../entities/UserList.ts";
import type {UserSearchRequest} from "../request/UserSearchRequest.ts";

@injectable()
export class UserRepositoryImpl implements UserRepository {

  private readonly httpClient: HttpClient;

  constructor(@inject(TYPES.HtpClient) httpClient: HttpClient) {
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

  async find(request: UserSearchRequest): Promise<UserList> {
    const query = new URLSearchParams({
      page: request.page.toString(),
      search: request.search,
      limit: request.size.toString(),
      sortBy: request.sort,
      sortOrder: request.order,
    }).toString();
    return this.httpClient.get<UserList>(`/api/users?${query}`);
  }

}