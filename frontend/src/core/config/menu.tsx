import type {ReactNode} from "react";
import {FiGrid, FiUsers, FiSettings} from 'react-icons/fi';
import {FaBuilding, FaGamepad} from "react-icons/fa6";

export type SubMenuItem = {
  path: string;
  label: string;
};

export type MenuItem = {
  path?: string; // 子メニューを持たない場合のみ必須
  label: string;
  icon: ReactNode;
  children?: SubMenuItem[]; // アコーディオン用の子階層
};

export const DASHBOARD_MENU_CONFIG: MenuItem[] = [
  {
    path: '/dashboard',
    label: '概要ダッシュボード',
    icon: <FiGrid className="w-5 h-5"/>
  },
  {
    path: '/users',
    label: 'ユーザー管理',
    icon: <FiUsers className="w-5 h-5"/>
  },
  {
    path: '/compaies',
    label: '会社管理',
    icon: <FaBuilding className="w-5 h-5" />
  },
  {
    path: '/clients',
    label: 'OAuthクライアント管理',
    icon: <FaGamepad className="w-5 h-5" />
  },
  {
    label: 'システム設定',
    icon: <FiSettings className="w-5 h-5"/>,
    children: [
      {path: '/settings/general', label: '一般設定'},
      {path: '/settings/security', label: 'セキュリティ'},
      {path: '/settings/notifications', label: '通知設定'},
    ],
  },
];