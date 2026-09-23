import {Outlet, useLocation, useRouteLoaderData} from "react-router";
import type {UserDetail} from "../security/UserDetail.ts";
import {useState} from "react";
import {FiGrid, FiSettings, FiUsers} from "react-icons/fi";
import {Sidebar} from "../components/Sidebar.tsx";
import {Footer} from "../components/Footer.tsx";
import {MobileDrawer} from "../components/MobileDrawer.tsx";
import {Header} from "../components/Header.tsx";

export function AppLayout() {

  const location = useLocation();
  const rootData = useRouteLoaderData("AuthenticationPrincipal") as {user: UserDetail | null} | undefined;
  const user = rootData?.user ?? null;

  // 認証後専用の状態管理
  const [isSidebarOpen, setIsSidebarOpen] = useState(true); // デスクトップサイドバーの開閉
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false); // スマホドロワーの開閉

  // ナビゲーションメニュー
  const menuItems = [
    { path: '/dashboard', label: '概要ダッシュボード', icon: <FiGrid className="w-5 h-5" /> },
    { path: '/users', label: 'ユーザー管理', icon: <FiUsers className="w-5 h-5" /> },
    { path: '/settings', label: 'システム設定', icon: <FiSettings className="w-5 h-5" /> }
  ];

  return (
    <div className="min-h-screen bg-white text-slate-800 flex flex-col font-sans antialiased selection:bg-blue-500 selection:text-white">

      {/* どんな画面でも常に表示される共通ヘッダー */}
      <Header
        user={user}
        isSidebarOpen={isSidebarOpen}
        onToggleSidebar={() => setIsSidebarOpen(!isSidebarOpen)}
        onToggleMobileMenu={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
      />

      {/* メインレイアウトコンテナ */}
      <div className="flex-1 flex bg-slate-50/50">

        {/* 【認証後かつサイドバー開状態のみ】デスクトップサイドバーを表示 */}
        {user && isSidebarOpen && (
          <Sidebar menuItems={menuItems} currentPath={location.pathname} />
        )}

        {/* コンテンツ展開エリア（認証状況やサイドバーの開閉状態で左側の余白を動的に変化） */}
        <div className={`flex-1 flex flex-col min-w-0 min-h-[calc(100vh-64px)] transition-all duration-300
          ${(user && isSidebarOpen) ? 'md:pl-64' : 'md:pl-0'}`}
        >
          <main className="flex-1 p-4 sm:p-6 lg:p-8 bg-white">
            <Outlet />
          </main>

          {/* どんな画面でも常に表示される共通フッター */}
          <Footer />
        </div>
      </div>

      {/* 【認証後のみ】モバイル用ドロワーメニュー */}
      {user && (
        <MobileDrawer
          isOpen={isMobileMenuOpen}
          onClose={() => setIsMobileMenuOpen(false)}
          menuItems={menuItems}
          currentPath={location.pathname}
        />
      )}
    </div>
  );
}