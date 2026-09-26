import {type FC, useState} from 'react';
import {Link} from 'react-router';
import {FiChevronDown} from "react-icons/fi";
import { DASHBOARD_MENU_CONFIG } from '../config/menu';

type SidebarProps = {
  currentPath: string;
};

export const Sidebar: FC<SidebarProps> = ({currentPath}) => {
  // 設定アコーディオンの開閉状態（現在のURLが設定関連なら初期値で開く）
  const [isSettingsOpen, setIsSettingsOpen] = useState(currentPath.startsWith('/settings'));

  return (
    <aside className="hidden md:flex flex-col w-64 bg-white border-r border-slate-200 fixed inset-y-0 left-0 pt-16 z-40">
      <nav className="flex-1 px-4 py-6 space-y-1">
        {DASHBOARD_MENU_CONFIG.map((menu, index) => {
          // 子階層（アコーディオン）を持つかどうかの条件分岐
          const hasChildren = !!menu.children;
          const isActive = menu.path ? currentPath === menu.path : currentPath.startsWith('/settings');

          if (!hasChildren && menu.path) {
            /* 通常の単一メニューリンク */
            return (
              <Link
                key={menu.path}
                to={menu.path}
                className={`flex items-center space-x-3 px-4 py-3 rounded-xl text-sm font-semibold transition-all group
                  ${isActive ? 'bg-blue-50 text-blue-600' : 'text-slate-500 hover:bg-slate-50 hover:text-slate-900'}`}
              >
                <span className={isActive ? 'text-blue-600' : 'text-slate-400 group-hover:text-slate-600'}>
                  {menu.icon}
                </span>
                <span>{menu.label}</span>
              </Link>
            );
          }

          /* アコーディオンメニューリンク */
          return (
            <div key={`accordion-${index}`} className="space-y-1">
              <button
                onClick={() => setIsSettingsOpen(!isSettingsOpen)}
                className={`w-full flex items-center justify-between px-4 py-3 rounded-xl text-sm font-semibold transition-all group
                  ${isActive ? 'text-blue-600 font-bold' : 'text-slate-500 hover:bg-slate-50 hover:text-slate-900'}`}
              >
                <div className="flex items-center space-x-3">
                  <span className={isActive ? 'text-blue-600' : 'text-slate-400 group-hover:text-slate-600'}>
                    {menu.icon}
                  </span>
                  <span>{menu.label}</span>
                </div>
                <FiChevronDown className={`w-4 h-4 text-slate-400 transition-transform duration-200 ${isSettingsOpen ? 'rotate-180 text-slate-600' : ''}`} />
              </button>

              {isSettingsOpen && menu.children && (
                <div className="pl-11 pr-2 py-1 space-y-1 animate-in fade-in slide-in-from-top-1 duration-150">
                  {menu.children.map((sub) => {
                    const isSubActive = currentPath === sub.path;
                    return (
                      <Link
                        key={sub.path}
                        to={sub.path}
                        className={`block px-3 py-2 rounded-lg text-xs font-semibold transition-all
                          ${isSubActive ? 'text-blue-600 bg-blue-50/50' : 'text-slate-400 hover:text-slate-800 hover:bg-slate-50'}`}
                      >
                        {sub.label}
                      </Link>
                    );
                  })}
                </div>
              )}
            </div>
          );
        })}
      </nav>
    </aside>
  );
};