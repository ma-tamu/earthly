import {http, HttpResponse} from 'msw';

const BASE_URL = 'http://localhost:8000';

// ダミー用のユーザーデータプール
const mockUsers = Array.from({length: 25}, (_, i) => ({
  id: `user-${i + 1}`,
  loginId: `loginId-${i + 1}`,
  name: `ユーザー ${i + 1}`,
  email: `user${i + 1}@example.com`,
  lockout: (i + 1) % 7 == 0,
  language: (i + 1) % 2 == 0 ? 'ja' : 'en',
  timezone: (i + 1) % 2 == 0 ? 'Asia/Tokyo' : 'UTC',
  createdAt: new Date(2026, 0, 1 + i).toISOString(), // 2026年想定
  createdBy: 'NULL',
  updatedAt: new Date(2026, 0, 1 + i).toISOString(), // 2026年想定
  updatedBy: 'NULL',
}));

export const handlers = [
  // 1. 現在のログインユーザー取得（Cookie検証のモック）
  http.get(`${BASE_URL}/api/users/me`, () => {
    // Cookieのシミュレートとして、簡易的にチェック（通常はブラウザが自動送信）
    // デバッグ用に、常にログイン成功状態とするか、任意で401エラーを切り替える
    return HttpResponse.json({
      id: "NULL",
      loginId: "admin",
      name: "Administrator",
      email: "example@planet.com",
      lockout: false,
      language: "ja",
      timezone: "UTC",
      createdAt: "1970/01/01 00:00:00",
      createdBy: "NULL",
      updatedAt: "1970/01/01 00:00:00",
      updatedBy: "NULL",

    });
    // 未ログイン状態を試したい場合は以下を有効化
    // return new HttpResponse(null, { status: 401 });
  }),

  // 2. ログインAPI
  http.post(`${BASE_URL}/api/auth/login`, async () => {
    // 成功時：サーバー側でCookieをセットしたと仮定して200を返す
    return new HttpResponse(null, {status: 200});
  }),

  // 3. ログアウトAPI
  http.post(`${BASE_URL}/api/auth/logout`, () => {
    return new HttpResponse(null, {status: 200});
  }),

  // 4. ページネーション付きユーザー一覧取得API
  http.get(`${BASE_URL}/api/users`, ({request}) => {
    const url = new URL(request.url);
    const page = Number.parseInt(url.searchParams.get('page') || '1', 10);
    const limit = 10;

    const totalCount = mockUsers.length;
    // const totalPages = Math.ceil(totalCount / limit);
    const start = (page - 1) * limit;
    const end = start + limit;
    const paginatedData = mockUsers.slice(start, end);

    return HttpResponse.json({
      users: paginatedData,
      attribute: {
        offset: page,
        length: totalCount,
        total: paginatedData.length,
      },
    });
  }),
];