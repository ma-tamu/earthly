import {injectable} from "inversify";
import type {HttpClient, HttpContentType, HttpOptions} from "./HttpClient.ts";



@injectable()
export class DefaultHttpClient implements HttpClient {
  private readonly baseUrl: string = import.meta.env.VITE_BASE_URL || 'http://localhost:5173';

  private prepareHeadersAndBody(
    headers: Headers,
    body: any,
    contentType: HttpContentType
  ): any {
    if (!body) return undefined;

    switch (contentType) {
      case 'json':
        headers.set('Content-Type', 'application/json');
        return JSON.stringify(body);

      case 'form':
        headers.set('Content-Type', 'application/x-www-form-urlencoded');
        // オブジェクトを key=value&key=value の形式に自動変換
        return new URLSearchParams(body).toString();

      default:
        return body;
    }
  }


  private async request<T>(endpoint: string, options: HttpOptions = {}): Promise<T> {

    const url = `${this.baseUrl}${endpoint}`;
    const { contentType = 'json', method = 'GET', body, ...restOptions } = options;

    // 1. デフォルトヘッダーの設定
    const headers = new Headers(restOptions.headers);

    // 2. Content-Type の動的切り替えと Body の変換
    const finalBody = (method === 'POST' && body)
      ? this.prepareHeadersAndBody(headers, body, contentType)
      : body;


    const defaultOptions: RequestInit = {
      ...restOptions,
      headers,
      body: finalBody,
      credentials: 'include',
    }

    const response = await fetch(url, defaultOptions);

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

    return response.json();
  }


  async get<T>(endpoint: string, options?: HttpOptions): Promise<T> {
    return await this.request<T>(endpoint, {...options, method: 'GET'});
  }

  async post<T>(endpoint: string, body?: any, options?: HttpOptions): Promise<T> {
    return this.request<T>(endpoint, {
      ...options,
      method: 'POST',
      body: body ? JSON.stringify(body) : undefined,
    });
  }

  async put<T>(endpoint: string, body?: any, options?: HttpOptions): Promise<T> {
    return this.request<T>(endpoint, {
      ...options,
      method: 'put',
      body: body ? JSON.stringify(body) : undefined,
    });
  }

  async delete<T>(endpoint: string, options?: HttpOptions): Promise<T> {
    return await this.request<T>(endpoint, {...options, method: 'DELETE'});
  }
}