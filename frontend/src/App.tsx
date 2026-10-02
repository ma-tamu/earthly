import './App.css'
import {createBrowserRouter, Navigate, redirect, RouterProvider} from "react-router";
import UserList from "./ui/pages/users/UserList.tsx";
import {container} from "./core/config/inversify.config.ts";
import {Dashboard} from "./ui/pages/Dashboard.tsx";
import {ErrorPage} from "./ui/pages/ErrorPage.tsx";
import {Login} from "./ui/pages/Login.tsx";
import type {AuthService} from "./core/security/AuthService.ts";
import {TYPES} from "./core/types/di.ts";
import {AppLayout} from "./ui/layouts/AppLayout.tsx";
import {authGuard} from "./core/security/authGuard.ts";
import type {LocaleService} from "./services/LocaleService.ts";
import {UserEntry} from "./ui/pages/users/UserEntry.tsx";


function App() {

  const router = createBrowserRouter([
    {
      path: "/",
      id: "AuthenticationPrincipal",
      element: <AppLayout/>,
      errorElement: <ErrorPage/>,
      loader: async (request) => {
        const authService = container.get<AuthService>(TYPES.AuthService);
        const localeService = container.get<LocaleService>(TYPES.LocaleService);

        // URLパラメータ等から現在の言語（デフォルト: ja）を取得
        const url = new URL(request.url);
        const currentLocale = (url.searchParams.get("lang") || 'ja') as 'ja' | 'en';

        // バックエンドから最新データを並列（Promise.all）で直接フェッチ
        const [user, translations] = await Promise.all([
          authService.getCurrentUser().catch(() => null), // トップやログイン用に、rootでの認証落ち（401）はnullとして優しく許容
          localeService.fetchTranslations(currentLocale)
        ]);

        // 子階層の全コンポーネントから useRouteLoaderData("root") で0秒参照できるように一元分配
        return {user, translations, locale: currentLocale};
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
              element: <UserEntry />,
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
