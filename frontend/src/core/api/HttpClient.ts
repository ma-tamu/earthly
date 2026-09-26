// サポートする Content-Type の型定義
export type HttpContentType = 'json' | 'form';

export interface HttpOptions extends RequestInit {
  contentType?: HttpContentType; // 'json' または 'form' を指定可能にする
}
export interface HttpClient {
  get<T>(endpoint: string, options?: HttpOptions): Promise<T>;

  post<T>(endpoint: string, body?: any, options?: HttpOptions): Promise<T>;

  put<T>(endpoint: string, body?: any, options?: HttpOptions): Promise<T>;

  delete<T>(endpoint: string, options?: HttpOptions): Promise<T>;
}