import {useRouteLoaderData} from "react-router";
import {FiBarChart2, FiClock, FiSettings} from "react-icons/fi";
import type {UserDetail} from "../core/security/UserDetail.ts";

export function Dashboard() {

  const { user } = useRouteLoaderData("AuthenticationPrincipal") as { user: UserDetail };

  return (
    <div className="w-full max-w-5xl mx-auto space-y-8">
      <div className="bg-linear-to-r from-blue-600 to-indigo-600 rounded-2xl p-8 text-white shadow-xl shadow-blue-500/10">
        <h2 className="text-2xl sm:text-3xl font-bold mb-2">システムへようこそ</h2>
        <p className="text-blue-100 text-sm sm:text-base">
          現在、<span className="font-semibold text-white underline decoration-wavy">{user.name}</span> としてログインしています。
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-2xl border border-slate-200/60 shadow-sm">
          <div className="h-10 w-10 bg-blue-50 rounded-xl flex items-center justify-center text-blue-600 mb-4">
            <FiClock className="w-5 h-5" />
          </div>
          <h3 className="font-bold text-slate-900 mb-1">最近のアクティビティ</h3>
          <p className="text-slate-500 text-sm">過去24時間のシステムログや変更履歴を確認できます。</p>
        </div>

        <div className="bg-white p-6 rounded-2xl border border-slate-200/60 shadow-sm">
          <div className="h-10 w-10 bg-indigo-50 rounded-xl flex items-center justify-center text-indigo-600 mb-4">
            <FiBarChart2 className="w-5 h-5" />
          </div>
          <h3 className="font-bold text-slate-900 mb-1">データ分析レポート</h3>
          <p className="text-slate-500 text-sm">統計データの閲覧やグラフを用いたグラフィカルな分析を行います。</p>
        </div>

        <div className="bg-white p-6 rounded-2xl border border-slate-200/60 shadow-sm">
          <div className="h-10 w-10 bg-emerald-50 rounded-xl flex items-center justify-center text-emerald-600 mb-4">
            <FiSettings className="w-5 h-5" />
          </div>
          <h3 className="font-bold text-slate-900 mb-1">システム設定</h3>
          <p className="text-slate-500 text-sm">プロフィールの編集、通知設定、連携アプリの管理が可能です。</p>
        </div>
      </div>
    </div>
  );
}