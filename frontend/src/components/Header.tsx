import {type FC, useEffect, useRef, useState} from "react";
import type {UserDetail} from "../core/security/UserDetail.ts";
import {useNavigate} from "react-router";
import {TYPES} from "../core/types/di.ts";
import type {AuthService} from "../core/security/AuthService.ts";
import {useInjection} from "../hooks/useInjection.ts";
import {FiClock, FiGlobe, FiLogOut, FiMenu, FiChevronDown} from "react-icons/fi";

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
  const [isMenuOpen, setIsMenuOpen] = useState(false); // ドロップダウンメニューの開閉状態

  const menuRef = useRef<HTMLDivElement>(null);

  // メニューの外側をクリックしたときに自動で閉じる処理（バグ防止）
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
        setIsMenuOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);


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
    <header className="h-16 border-b border-slate-100 px-4 sm:px-6 flex items-center justify-between sticky top-0 bg-white/80 backdrop-blur-md z-50 shadow-sm shadow-slate-100/10">

      {/* 左エリア：ロゴ ＆ サイドバースイッチ */}
      <div className="flex items-center space-x-3">
        {user && (
          <>
            <button
              onClick={onToggleSidebar}
              className="hidden md:flex p-2 text-slate-500 hover:bg-slate-50 border border-slate-200 rounded-lg transition-colors mr-1"
              title={isSidebarOpen ? "サイドバーを閉じる" : "サイドバーを開く"}
            >
              <FiMenu className="w-4 h-4" />
            </button>
            <button
              onClick={onToggleMobileMenu}
              className="flex md:hidden p-2 text-slate-500 hover:bg-slate-50 rounded-lg transition-colors mr-1"
            >
              <FiMenu className="w-5 h-5" />
            </button>
          </>
        )}

        <div className="flex items-center space-x-2.5">
          <span className="text-lg font-bold tracking-tight bg-linear-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
            Earthly
          </span>
        </div>
      </div>

      {/* 右エリア：ユーザーメニュー または 未ログイン用固定表示 */}
      <div className="flex items-center" ref={menuRef}>
        {user ? (
          /* 【認証後】アバターとemailをクリックでメニューを開くトグル構造 */
          <div className="relative">
            <button
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              className="flex items-center space-x-2.5 p-1.5 pr-3 hover:bg-slate-50 border border-slate-100 rounded-xl transition-all select-none group"
            >
              {/* 丸型アバタープレースホルダー */}
              <div className="h-7 w-7 bg-linear-to-tr from-blue-600 to-indigo-500 rounded-lg flex items-center justify-center text-white shadow-sm shadow-blue-500/20 font-bold text-xs uppercase">
                {user.name.charAt(0)}
              </div>
              <span className="text-xs font-semibold text-slate-600 group-hover:text-slate-900 transition-colors max-w-35 truncate">
                {user.name}
              </span>
              <FiChevronDown className={`w-3.5 h-3.5 text-slate-400 transition-transform duration-200 ${isMenuOpen ? 'rotate-180 text-slate-600' : ''}`} />
            </button>

            {/* 浮き出るモダンなドロップダウンメニューカード */}
            {isMenuOpen && (
              <div className="absolute right-0 mt-2 w-64 bg-white border border-slate-100 rounded-2xl shadow-xl shadow-slate-200/60 p-2.5 space-y-3 z-50 animate-in fade-in zoom-in-95 duration-100">
                {/* ユーザー簡易プロフィールヘッダー */}
                <div className="px-2.5 py-2 border-b border-slate-100/80">
                  <div className="text-[10px] font-bold text-slate-400 uppercase tracking-wider">ログイン中のアカウント</div>
                  <div className="text-sm font-bold text-slate-800 truncate mt-0.5">{user.name}</div>
                </div>

                {/* メニュー中身：コントロール系（インラインスタイル化） */}
                <div className="space-y-2">
                  {/* 言語設定エリア */}
                  <div className="flex items-center justify-between px-2.5 py-1">
                    <div className="flex items-center space-x-2 text-xs font-semibold text-slate-500">
                      <FiGlobe className="w-4 h-4 text-slate-400" />
                      <span>言語</span>
                    </div>
                    <select
                      value={lang}
                      onChange={(e) => setLang(e.target.value)}
                      className="bg-slate-50 border border-slate-200 text-xs rounded-lg px-2.5 py-1 text-slate-700 font-semibold cursor-pointer focus:outline-none"
                    >
                      <option value="ja">日本語</option>
                      <option value="en">English</option>
                    </select>
                  </div>

                  {/* タイムゾーン設定エリア */}
                  <div className="flex items-center justify-between px-2.5 py-1">
                    <div className="flex items-center space-x-2 text-xs font-semibold text-slate-500">
                      <FiClock className="w-4 h-4 text-slate-400" />
                      <span>タイムゾーン</span>
                    </div>
                    <select
                      value={timezone}
                      onChange={(e) => setTimezone(e.target.value)}
                      className="bg-slate-50 border border-slate-200 text-xs rounded-lg px-2.5 py-1 text-slate-700 font-semibold cursor-pointer focus:outline-none"
                    >
                      <option value="JST">東京 (JST)</option>
                      <option value="UTC">標準時 (UTC)</option>
                    </select>
                  </div>
                </div>

                <div className="border-t border-slate-100/80 pt-1.5">
                  {/* ログアウトアクションボタン */}
                  <button
                    onClick={handleLogout}
                    className="w-full flex items-center space-x-2.5 px-2.5 py-2 text-xs font-bold text-red-600 hover:bg-red-50 rounded-xl transition-all"
                  >
                    <FiLogOut className="w-4 h-4 text-red-500" />
                    <span>システムからログアウト</span>
                  </button>
                </div>
              </div>
            )}
          </div>
        ) : (
          /* 【認証前】未ログイン時は言語切替のみを端正に表示 */
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
        )}
      </div>
    </header>
  );
}