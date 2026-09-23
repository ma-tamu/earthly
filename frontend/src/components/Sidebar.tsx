import {type FC, type ReactNode} from 'react';
import {Link} from 'react-router';
import {FiLogOut, FiZap} from 'react-icons/fi';

type MenuItem = {
  path: string;
  label: string;
  icon: ReactNode;
};

type SidebarProps = {
  menuItems: MenuItem[];
  currentPath: string;
  onLogout: () => void;
};

export const Sidebar: FC<SidebarProps> = ({menuItems, currentPath, onLogout}) => {
  return (
    <aside className="hidden md:flex flex-col w-64 bg-white border-r border-slate-200 fixed inset-y-0 left-0 z-40">
      <div className="h-16 px-6 border-b border-slate-100 flex items-center space-x-3">
        <div className="h-8 w-8 bg-blue-600 rounded-lg flex items-center justify-center shadow-md shadow-blue-500/10">
          <FiZap className="w-4 h-4 text-white"/>
        </div>
        <span className="text-lg font-bold bg-linear-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
          Earthly
        </span>
      </div>

      <nav className="flex-1 px-4 py-6 space-y-1">
        {menuItems.map((item) => {
          const isActive = currentPath === item.path;
          return (
            <Link
              key={item.path}
              to={item.path}
              className={`flex items-center space-x-3 px-4 py-3 rounded-xl text-sm font-semibold transition-all group
                ${isActive
                ? 'bg-blue-50 text-blue-600'
                : 'text-slate-500 hover:bg-slate-50 hover:text-slate-900'
              }`}
            >
              <span className={isActive ? 'text-blue-600' : 'text-slate-400 group-hover:text-slate-600'}>
                {item.icon}
              </span>
              <span>{item.label}</span>
            </Link>
          );
        })}
      </nav>

      <div className="p-4 border-t border-slate-100">
        <button
          onClick={onLogout}
          className="w-full flex items-center space-x-3 px-4 py-3 text-sm font-semibold text-red-600 hover:bg-red-50 rounded-xl transition-all"
        >
          <FiLogOut className="w-5 h-5 text-red-500"/>
          <span>ログアウト</span>
        </button>
      </div>
    </aside>
  );
};