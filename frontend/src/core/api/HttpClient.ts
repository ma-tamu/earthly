// サポートする Content-Type の型定義
export type HttpContentType = 'json' | 'form';

export interface HttpClient {
  get<T>(endpoint: string): Promise<T>;

  post<T>(endpoint: string, contentType: string, body?: URLSearchParams|string): Promise<T>;

  put<T>(endpoint: string, body?: string): Promise<T>;

  delete<T>(endpoint: string): Promise<T>;
}