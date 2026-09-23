import {type FC, type ReactNode} from 'react';
import {Link} from 'react-router';

type MenuItem = { path: string; label: string; icon: ReactNode };
type SidebarProps = { menuItems: MenuItem[]; currentPath: string };

export const Sidebar: FC<SidebarProps> = ({menuItems, currentPath}) => {
  return (
    <aside
      className="hidden md:flex flex-col w-64 bg-white border-r border-slate-200 fixed inset-y-0 left-0 pt-16 z-40">
      <nav className="flex-1 px-4 py-6 space-y-1">
        {menuItems.map((item) => {
          const isActive = currentPath === item.path;
          return (
            <Link
              key={item.path}
              to={item.path}
              className={`flex items-center space-x-3 px-4 py-3 rounded-xl text-sm font-semibold transition-all group
                ${isActive ? 'bg-blue-50 text-blue-600' : 'text-slate-500 hover:bg-slate-50 hover:text-slate-900'}`}
            >
              <span className={isActive ? 'text-blue-600' : 'text-slate-400 group-hover:text-slate-600'}>
                {item.icon}
              </span>
              <span>{item.label}</span>
            </Link>
          );
        })}
      </nav>
    </aside>
  );
};