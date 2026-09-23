import './App.css'
import {createBrowserRouter, Navigate, redirect, RouterProvider} from "react-router";
import {UserList} from "./pages/UserList.tsx";
import {container} from "./config/inversify.config.ts";
import {Dashboard} from "./pages/Dashboard.tsx";
import {ErrorPage} from "./pages/ErrorPage.tsx";
import {Login} from "./pages/Login.tsx";
import type {AuthService} from "./security/AuthService.ts";
import {TYPES} from "./types/di.ts";
import type {UserService} from "./services/UserService.ts";
import {AppLayout} from "./layouts/AppLayout.tsx";


function App() {

  const router = createBrowserRouter([
    {
      path: "/",
      id: "AuthenticationPrincipal",
      element: <AppLayout/>,
      errorElement: <ErrorPage/>,
      loader: async () => {
        try {
          const user = await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
          return {user};
        } catch {
          return {user: null}
        }
      },
      children: [
        {
          index: true,
          path: "/login",
          element: <Login/>,
          loader: async () => {
            const rootData = await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
            return rootData ? redirect('/dashboard') : null;
          }
        },
        {
          path: "dashboard",
          element: <Dashboard/>,
          loader: async () => {
            const rootData = await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
            return !rootData ? redirect('/login') : null;
          }
        },
        {
          path: "users",
          element: <UserList/>,
          loader: async ({request}) => {
            try {
              await container.get<AuthService>(TYPES.AuthService).getCurrentUser();

              // 2. 認証OKなら一覧データ取得
              const url = new URL(request.url);
              const page = Number.parseInt(url.searchParams.get("page") || "1", 10);
              const userService = container.get<UserService>(TYPES.UserService);
              const usersData = await userService.getUsers(page);
              return {usersData};
            } catch (error: unknown) {
              // 認証エラー（401）の場合はログイン画面へ
              if (error instanceof Error && error.message === 'UNAUTHORIZED') {
                return redirect('/');
              }
              throw error; // 500エラーやサーバーダウン等は ErrorBoundary へ丸投げ
            }
          }
        },
        {
          path: "settings",
          element: <div className="text-2xl font-bold text-slate-900">システム設定画面（開発中）</div>,
          loader: async () => {
            const rootData = await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
            return !rootData ? redirect('/') : null;
          }
        }
      ]
    },

    // その他の未知のURLはすべてログイン（またはリダイレクト）へ
    {
      path: "*",
      element: <Navigate to="/login" replace/>,
    }
  ]);
  return <RouterProvider router={router}/>;
}

export default App
