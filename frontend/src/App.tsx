import './App.css'
import {createBrowserRouter, Navigate, redirect, RouterProvider} from "react-router";
import UserList from "./pages/users/UserList.tsx";
import {container} from "./core/config/inversify.config.ts";
import {Dashboard} from "./pages/Dashboard.tsx";
import {ErrorPage} from "./pages/ErrorPage.tsx";
import {Login} from "./pages/Login.tsx";
import type {AuthService} from "./core/security/AuthService.ts";
import {TYPES} from "./core/types/di.ts";
import {AppLayout} from "./layouts/AppLayout.tsx";
import {authGuard} from "./core/security/authGuard.ts";


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
          loader: () => authGuard()
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
          children: [
            {
              index: true,
              element: <UserList/>,
              loader: () => authGuard()
            },
            {
              path: ":id",
              element: <></>,
              loader: async () => {
                try {
                  await container.get<AuthService>(TYPES.AuthService).getCurrentUser();
                } catch {
                  return redirect("/login");
                }
              }
            },
            {
              path: "entries",
              element: <></>,
              loader: () => authGuard()
            }
          ]
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
