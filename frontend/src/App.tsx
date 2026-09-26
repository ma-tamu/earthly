import './App.css'
import {createBrowserRouter, Navigate, redirect, RouterProvider} from "react-router";
import UserList from "./pages/users/UserList.tsx";
import {container} from "./config/inversify.config.ts";
import {Dashboard} from "./pages/Dashboard.tsx";
import {ErrorPage} from "./pages/ErrorPage.tsx";
import {Login} from "./pages/Login.tsx";
import type {AuthService} from "./security/AuthService.ts";
import {TYPES} from "./types/di.ts";
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
          loader: async () => {
            try {
              await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
              return redirect("/dashboard");
            } catch {
              return redirect("/login");
            }
          }
        },
        {
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
          loader: async () => {
            try {
              await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
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
          children: [
            {path: "general", element: <div className="text-2xl font-bold text-slate-900">一般設定画面</div>},
            {path: "security", element: <div className="text-2xl font-bold text-slate-900">セキュリティ設定画面</div>},
            {path: "notifications", element: <div className="text-2xl font-bold text-slate-900">通知設定画面</div>},
          ],
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
