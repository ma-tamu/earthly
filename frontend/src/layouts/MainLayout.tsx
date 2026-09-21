import {Outlet, useLocation, useNavigate} from "react-router";
import {useInjection} from "../hooks/useInjection.ts";
import {TYPES} from "../types/di.ts";
import type {AuthService} from "../security/AuthService.ts";
import {useState} from "react";
import {FiGrid, FiSettings, FiUsers} from "react-icons/fi";
import {Sidebar} from "../components/Sidebar.tsx";
import {MobileHeader} from "../components/MobileHeader.tsx";
import {MobileDrawer} from "../components/MobileDrawer.tsx";

export function MainLayout() {

  const navigate = useNavigate();
  const location = useLocation();
  const authService = useInjection<AuthService>(TYPES.AuthService);
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const menuItems = [
    {
      path: '/dashboard',
      label: '概要ダッシュボード',
      icon: <FiGrid className="w-5 h-5" />
    },
    {
      path: '/dashboard/users',
      label: 'ユーザー管理',
      icon: <FiUsers className="w-5 h-5" />
    },
    {
      path: '/dashboard/settings',
      label: 'システム設定',
      icon: <FiSettings className="w-5 h-5" />
    }
  ];

  const handleLogout = async () => {
    try {
      await authService.logout();
    } finally {
      navigate('/', { replace: true });
    }
  };

  return (
    <div className="min-h-screen bg-slate-50 flex">
      <Sidebar
        menuItems={menuItems}
        currentPath={location.pathname}
        onLogout={handleLogout}
      />

      <div className="flex-1 flex flex-col md:pl-64 min-w-0">
        <MobileHeader onMenuOpen={() => setIsMobileMenuOpen(true)} />

        <main className="flex-1 p-4 sm:p-6 lg:p-8">
          <Outlet />
        </main>
      </div>

      <MobileDrawer
        isOpen={isMobileMenuOpen}
        onClose={() => setIsMobileMenuOpen(false)}
        menuItems={menuItems}
        currentPath={location.pathname}
        onLogout={handleLogout}
      />
    </div>
  );
}