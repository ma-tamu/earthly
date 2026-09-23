import {type FC, useState} from "react";
import type {UserDetail} from "../security/UserDetail.ts";
import {useNavigate} from "react-router";
import {TYPES} from "../types/di.ts";
import type {AuthService} from "../security/AuthService.ts";
import {useInjection} from "../hooks/useInjection.ts";
import {FiClock, FiGlobe, FiLogOut, FiMenu, FiZap, FiMenu as FiSidebarToggle } from "react-icons/fi";

type HeaderProps = {
  user: UserDetail | null;
  isSidebarOpen: boolean;
  onToggleSidebar: () => void;
  onToggleMobileMenu: () => void;
};


export const Header: FC<HeaderProps> = ({
                                          user,
                                          isSidebarOpen,
                                          onToggleSidebar,
                                          onToggleMobileMenu,
                                        }) => {

  const navigate = useNavigate();
  const authService = useInjection<AuthService>(TYPES.AuthService);
  const [lang, setLang] = useState('ja');
  const [timezone, setTimezone] = useState('JST');

  const handleLogout = async () => {
    try {
      await authService.logout();
    } finally {
      // ページ全体のリロードを伴って最上位の共通 loader を再発火させ、
      // 確実に user=null 状態にしてログイン画面に安全に戻します
      navigate('/login', { replace: true });
    }
  };
  return (
    <header className="h-16 border-b border-slate-100 px-4 sm:px-6 flex items-center justify-between sticky top-0 bg-white/80 backdrop-blur-md z-50 shadow-sm shadow-slate-100/20">

      {/* 左エリア：ロゴ ＆ 【認証後のみ】サイドバー開閉スイッチ */}
      <div className="flex items-center space-x-3">
        {user && (
          <>
            {/* デスクトップ用サイドバー開閉（トグル）ボタン */}
            <button
              onClick={onToggleSidebar}
              className="hidden md:flex p-2 text-slate-500 hover:bg-slate-50 border border-slate-200 rounded-lg transition-colors mr-1"
              title={isSidebarOpen ? "サイドバーを閉じる" : "サイドバーを開く"}
            >
              <FiSidebarToggle className="w-4 h-4" />
            </button>
            {/* モバイル用メニュー展開ボタン */}
            <button
              onClick={onToggleMobileMenu}
              className="flex md:hidden p-2 text-slate-500 hover:bg-slate-50 rounded-lg transition-colors mr-1"
            >
              <FiMenu className="w-5 h-5" />
            </button>
          </>
        )}

        <div className="flex items-center space-x-2.5">
          <div className="h-8 w-8 bg-blue-600 rounded-lg flex items-center justify-center shadow-md shadow-blue-500/10">
            <FiZap className="w-4 h-4 text-white" />
          </div>
          <span className="text-lg font-bold tracking-tight bg-linear-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
            Earthly
          </span>
        </div>
      </div>

      {/* 右エリア：各種コントロールスイッチ群 */}
      <div className="flex items-center space-x-3">

        {/* ① 言語切替（認証の有無を問わず、常に表示） */}
        <div className="relative flex items-center">
          <FiGlobe className="absolute left-2.5 text-slate-400 w-4 h-4 pointer-events-none" />
          <select
            value={lang}
            onChange={(e) => setLang(e.target.value)}
            className="appearance-none bg-slate-50/50 border border-slate-200 text-xs rounded-lg pl-8 pr-7 py-2 text-slate-600 cursor-pointer focus:outline-none transition-all font-semibold"
          >
            <option value="ja">日本語</option>
            <option value="en">English</option>
          </select>
        </div>

        {/* 【認証後のみ解放される機能群】 */}
        {user && (
          <>
            {/* ② タイムゾーン切替 */}
            <div className="relative hidden sm:flex items-center">
              <FiClock className="absolute left-2.5 text-slate-400 w-4 h-4 pointer-events-none" />
              <select
                value={timezone}
                onChange={(e) => setTimezone(e.target.value)}
                className="appearance-none bg-slate-50/50 border border-slate-200 text-xs rounded-lg pl-8 pr-7 py-2 text-slate-600 cursor-pointer focus:outline-none transition-all font-semibold"
              >
                <option value="JST">東京 (JST)</option>
                <option value="UTC">世界標準時 (UTC)</option>
                <option value="EST">ニューヨーク (EST)</option>
              </select>
            </div>

            {/* ディバイダー線 */}
            <span className="h-4 w-px bg-slate-200 mx-1 hidden sm:inline" />

            {/* ユーザーアカウント情報の表示（メールアドレス） */}
            <span className="text-xs font-medium text-slate-500 max-w-30 truncate hidden md:inline">
              {user.name}
            </span>

            {/* ③ ログアウトボタン */}
            <button
              onClick={handleLogout}
              className="flex items-center gap-1.5 px-3 py-2 bg-red-50 hover:bg-red-100 text-red-600 text-xs font-bold rounded-lg border border-red-200/40 transition-all"
              title="システムからサインアウト"
            >
              <FiLogOut className="w-3.5 h-3.5" />
              <span className="hidden sm:inline">ログアウト</span>
            </button>
          </>
        )}
      </div>
    </header>
  );
}