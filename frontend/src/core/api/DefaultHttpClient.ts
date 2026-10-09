import {injectable} from "inversify";
import type {HttpClient} from "./HttpClient.ts";


@injectable()
export class DefaultHttpClient implements HttpClient {
  private readonly baseUrl: string = import.meta.env.VITE_BASE_URL || 'http://localhost:5173';

  private async request<T>(endpoint: string, options: RequestInit): Promise<T> {

    const url = `${this.baseUrl}${endpoint}`;
    options.credentials = 'include';

    const response = await fetch(url, options);

    if (response.status === 401) {
      throw new Error(`Unauthorized request: ${response.status}`);
    }

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.message || 'API_ERROR');
    }

    if (response.status === 204) {
      return null as T;
    }

    return await response.json();
  }


  async get<T>(endpoint: string): Promise<T> {
    return await this.request<T>(endpoint, {method: 'GET'});
  }

  async post<T>(endpoint: string, contentType: string = 'application/json', body?: URLSearchParams | string): Promise<T> {
    return this.request<T>(endpoint, {
      method: 'POST',
      headers: {
        'Content-Type': contentType
      },
      body: body
    });
  }

  async put<T>(endpoint: string, body?: string): Promise<T> {
    return this.request<T>(endpoint, {
      method: 'PUT',
      body: body,
    });
  }

  async delete<T>(endpoint: string): Promise<T> {
    return await this.request<T>(endpoint, {method: 'DELETE'});
  }
}