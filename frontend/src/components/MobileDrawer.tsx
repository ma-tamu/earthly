import {type FC, type ReactNode} from 'react';
import {FiX} from 'react-icons/fi';
import { Link } from "react-router";

type MenuItem = { path: string; label: string; icon: ReactNode };
type MobileDrawerProps = { isOpen: boolean; onClose: () => void; menuItems: MenuItem[]; currentPath: string };

export const MobileDrawer: FC<MobileDrawerProps> = ({ isOpen, onClose, menuItems, currentPath }) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex md:hidden">
      <div className="fixed inset-0 bg-slate-900/40 backdrop-blur-sm" onClick={onClose} />
      <div className="relative flex flex-col w-full max-w-xs bg-white h-full p-4 shadow-xl z-50">
        <div className="flex items-center justify-between pb-4 border-b border-slate-100 mb-4">
          <span className="text-lg font-bold text-slate-900">メニュー</span>
          <button onClick={onClose} className="p-2 text-slate-400 hover:bg-slate-50 rounded-lg">
            <FiX className="w-6 h-6" />
          </button>
        </div>
        <nav className="flex-1 space-y-1">
          {menuItems.map((item) => {
            const isActive = currentPath === item.path;
            return (
              <Link
                key={item.path}
                to={item.path}
                onClick={onClose}
                className={`flex items-center space-x-3 px-4 py-3 rounded-xl text-sm font-semibold transition-all
                  ${isActive ? 'bg-blue-50 text-blue-600' : 'text-slate-500 hover:bg-slate-50'}`}
              >
                <span className={isActive ? 'text-blue-600' : 'text-slate-400'}>{item.icon}</span>
                <span>{item.label}</span>
              </Link>
            );
          })}
        </nav>
      </div>
    </div>
  );
};