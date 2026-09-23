import './App.css'
import {createBrowserRouter, Navigate, redirect, RouterProvider} from "react-router";
import {UserList} from "./pages/UserList.tsx";
import {container} from "./config/inversify.config.ts";
import {Dashboard} from "./pages/Dashboard.tsx";
import {ErrorPage} from "./pages/ErrorPage.tsx";
import {MainLayout} from "./layouts/MainLayout.tsx";
import {Login} from "./pages/Login.tsx";
import {RootLayout} from "./layouts/RootLayout.tsx";
import type {AuthService} from "./security/AuthService.ts";
import {TYPES} from "./types/di.ts";
import type {UserService} from "./services/UserService.ts";


function App() {

  const router = createBrowserRouter([
    // ① 一般公開・認証前ルート群
    {
      element: <RootLayout/>,
      errorElement: <ErrorPage/>,
      children: [
        {
          path: "/login",
          element: <Login/>,
          loader: async () => {
            try {
              // セッションCookieがあれば自動でダッシュボードへリダイレクト
              await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
              return redirect('/');
            } catch {
              return null;
            }
          }
        },
      ]
    },
    // ② 認証後・システム内専用ルート群（サイドバー固定）
    {
      path: "/",
      id: "user",
      element: <MainLayout/>,
      errorElement: <ErrorPage/>,
      // 画面が描画される「前」に一括でCookie認証検証を行う堅牢なガード
      loader: async () => {
        try {
          const user = await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
          return {user};
        } catch {
          return redirect('/login'); // 未認証・セッション切れならログイン画面へ即リダイレクト
        }
      },
      children: [
        {
          index: true,
          element: <Dashboard/>,
        },
        {
          path: "users",
          loader: async ({request}) => {
            const url = new URL(request.url);
            const page = Number.parseInt(url.searchParams.get("page") || "1", 10);
            const userService = container.get<UserService>(TYPES.UserService);
            const usersData = await userService.getUsers(page);
            return {usersData};
          },
          element: <UserList/>,
        },
        {
          path: "settings",
          element: <div className="text-2xl font-bold text-slate-900">システム設定画面（開発中）</div>,
        },
        {
          path: "*",
          element: <Navigate to="/" replace/>,
        },
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
