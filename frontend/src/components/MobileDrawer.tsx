import {type FC, useState,} from 'react';
import {FiChevronDown, FiX} from 'react-icons/fi';
import {Link} from "react-router";
import {DASHBOARD_MENU_CONFIG} from '../core/config/menu';

type MobileDrawerProps = {
  isOpen: boolean;
  onClose: () => void;
  currentPath: string;
};


export const MobileDrawer: FC<MobileDrawerProps> = ({isOpen, onClose, currentPath}) => {
  const [isSettingsOpen, setIsSettingsOpen] = useState(currentPath.startsWith('/settings'));

  if (!isOpen) {
    return null;
  }

  return (
    <div className="fixed inset-0 z-50 flex md:hidden">
      {/* 背景の黒マスク */}
      <div className="fixed inset-0 bg-slate-900/40 backdrop-blur-sm" onClick={onClose}/>

      {/* メニュー本体 */}
      <div
        className="relative flex flex-col w-full max-w-xs bg-white h-full p-4 shadow-xl z-50 animate-in slide-in-from-left duration-200">
        <div className="flex items-center justify-between pb-4 border-b border-slate-100 mb-4">
          <span className="text-lg font-bold text-slate-900">メニュー</span>
          <button onClick={onClose} className="p-2 text-slate-400 hover:bg-slate-50 rounded-lg"
                  aria-label="メニューを閉じる">
            <FiX className="w-6 h-6"/>
          </button>
        </div>

        <nav className="flex-1 space-y-1">
          {DASHBOARD_MENU_CONFIG.map((menu, index) => {
            const hasChildren = !!menu.children;
            const isActive = menu.path ? currentPath === menu.path : currentPath.startsWith('/settings');

            if (!hasChildren && menu.path) {
              /* 通常のメインメニューリンク */
              return (
                <Link
                  key={menu.path}
                  to={menu.path}
                  onClick={onClose}
                  className={`flex items-center space-x-3 px-4 py-3 rounded-xl text-sm font-semibold transition-all
                    ${isActive ? 'bg-blue-50 text-blue-600' : 'text-slate-500 hover:bg-slate-50'}`}
                >
                  <span className={isActive ? 'text-blue-600' : 'text-slate-400'}>{menu.icon}</span>
                  <span>{menu.label}</span>
                </Link>
              );
            }

            /* アコーディオン型メニューリンク */
            return (
              <div key={`mobile-accordion-${index}`} className="space-y-1">
                <button
                  onClick={() => setIsSettingsOpen(!isSettingsOpen)}
                  className={`w-full flex items-center justify-between px-4 py-3 rounded-xl text-sm font-semibold transition-all
                    ${isActive ? 'text-blue-600' : 'text-slate-500 hover:bg-slate-50'}`}
                >
                  <div className="flex items-center space-x-3">
                    <span className={isActive ? 'text-blue-600' : 'text-slate-400'}>{menu.icon}</span>
                    <span>{menu.label}</span>
                  </div>
                  <FiChevronDown
                    className={`w-4 h-4 text-slate-400 transition-transform duration-200 ${isSettingsOpen ? 'rotate-180' : ''}`}/>
                </button>

                {/* サブメニュー展開 */}
                {isSettingsOpen && menu.children && (
                  <div className="pl-11 space-y-1 animate-in fade-in slide-in-from-top-1 duration-150">
                    {menu.children.map((sub) => {
                      const isSubActive = currentPath === sub.path;
                      return (
                        <Link
                          key={sub.path}
                          to={sub.path}
                          onClick={onClose}
                          className={`block px-3 py-2 rounded-lg text-sm font-semibold transition-all
                            ${isSubActive ? 'text-blue-600 bg-blue-50/50' : 'text-slate-400 hover:bg-slate-50'}`}
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
      </div>
    </div>
  );
};