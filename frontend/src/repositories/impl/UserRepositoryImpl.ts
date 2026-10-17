import type {UserRepository} from "../UserRepository.ts";
import {inject, injectable} from "inversify";
import type {UserEntity} from "../entities/UserEntity.ts";
import type {HttpClient} from "../../core/api/HttpClient.ts";
import {TYPES} from "../../core/types/di.ts";
import type {UserList} from "../entities/UserList.ts";
import type {UserSearchRequest} from "../request/UserSearchRequest.ts";
import type {UserEntryRequest} from "../request/UserEntryRequest.ts";
import type {MessageResponse} from "../response/MessageResponse.ts";
import type {AuthenticationRequest} from "../request/AuthenticationRequest.ts";

@injectable()
export class UserRepositoryImpl implements UserRepository {

  private readonly httpClient: HttpClient;

  constructor(@inject(TYPES.HttpClient) httpClient: HttpClient) {
    this.httpClient = httpClient;
  }


  async me(): Promise<UserEntity> {
    return await this.httpClient.get<UserEntity>("/api/users/me");
  }

  async authenticate(request: AuthenticationRequest): Promise<void> {
    const formData = new URLSearchParams()
    formData.append('loginId', request.loginId);
    formData.append('password', request.password);
    await this.httpClient.post<string>('/api/login', 'application/x-www-form-urlencoded', formData);
  }

  async logout(): Promise<void> {
    await this.httpClient.post<string>("/api/logout");
  }

  async find(request: UserSearchRequest): Promise<UserList> {
    const query = new URLSearchParams({
      page: request.page.toString(),
      search: request.search,
      limit: request.size.toString(),
      sortBy: request.sort,
      sortOrder: request.order,
    }).toString();
    return await this.httpClient.get<UserList>(`/api/users?${query}`);
  }

  async entry(request: UserEntryRequest): Promise<MessageResponse> {
    const json = JSON.stringify(request);
    return await this.httpClient.post<MessageResponse>("/api/users/entries", json);
  }

  async entryConfirm(request: UserEntryRequest): Promise<void> {
    const json = JSON.stringify(request);
    await this.httpClient.post<MessageResponse>("/api/users/entries/confirms", json);
  }

  async get(id: string): Promise<UserEntity> {
    return await this.httpClient.get<UserEntity>(`/api/users/${id}`);
  }

}