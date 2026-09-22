import {useLoaderData, useSearchParams} from "react-router";
import {FiChevronLeft, FiChevronRight, FiUserPlus} from "react-icons/fi";
import type {UserPaginatedResponse} from "../services/UserService.ts";

export function UserList() {
  const { usersData } = useLoaderData() as { usersData: UserPaginatedResponse };
  const [, setSearchParams] = useSearchParams();
  const { data: users, meta } = usersData;

  const handlePageChange = (newPage: number) => {
    if (newPage < 1 || newPage > meta.totalPages) return;
    setSearchParams({ page: newPage.toString() });
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-extrabold text-slate-900 tracking-tight">ユーザー管理</h1>
          <p className="text-slate-500 text-sm mt-1">システムに登録されている全アカウントの閲覧・編集が可能です。</p>
        </div>
        <button className="inline-flex items-center justify-center gap-2 px-4 py-2.5 bg-blue-600 hover:bg-blue-700 text-white text-sm font-semibold rounded-xl shadow-md shadow-blue-600/10 transition-all active:scale-[0.98]">
          <FiUserPlus className="w-4 h-4" />
          <span>新規ユーザー追加</span>
        </button>
      </div>

      <div className="bg-white rounded-2xl border border-slate-200/80 shadow-sm overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
            <tr className="bg-slate-50/70 border-b border-slate-200/80 text-xs font-semibold uppercase tracking-wider text-slate-500">
              <th className="px-6 py-4">名前 / メールアドレス</th>
              <th className="px-6 py-4">権限ロール</th>
              <th className="px-6 py-4">ステータス</th>
              <th className="px-6 py-4">登録日</th>
              <th className="px-6 py-4 text-right">操作</th>
            </tr>
            </thead>
            <tbody className="divide-y divide-slate-100 text-sm">
            {users.map((user) => (
              <tr key={user.id} className="hover:bg-slate-50/50 transition-colors">
                <td className="px-6 py-4.5">
                  <div className="flex items-center space-x-3">
                    <div className="h-9 w-9 bg-slate-100 rounded-full flex items-center justify-center font-bold text-slate-600 text-xs">
                      {user.name.charAt(0)}
                    </div>
                    <div>
                      <div className="font-semibold text-slate-900">{user.name}</div>
                      <div className="text-slate-400 text-xs">{user.email}</div>
                    </div>
                  </div>
                </td>
                <td className="px-6 py-4.5 font-medium text-slate-700">
                  {user.role === 'admin' ? (
                    <span className="text-indigo-600 font-semibold bg-indigo-50 px-2 py-1 rounded-md text-xs">管理者</span>
                  ) : (
                    <span className="text-slate-600">一般ユーザー</span>
                  )}
                </td>
                <td className="px-6 py-4.5">
                    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border
                      ${user.status === 'active'
                      ? 'bg-emerald-50 text-emerald-700 border-emerald-200/60'
                      : 'bg-slate-50 text-slate-400 border-slate-200'
                    }`}
                    >
                      <span className={`h-1.5 w-1.5 rounded-full mr-1.5 ${user.status === 'active' ? 'bg-emerald-500' : 'bg-slate-300'}`} />
                      {user.status === 'active' ? '有効' : '無効'}
                    </span>
                </td>
                <td className="px-6 py-4.5 text-slate-500 text-xs">
                  {new Date(user.createdAt).toLocaleDateString('ja-JP')}
                </td>
                <td className="px-6 py-4.5 text-right">
                  <button className="text-xs font-bold text-slate-600 hover:text-blue-600 px-3 py-1.5 hover:bg-slate-50 rounded-lg transition-all">
                    編集
                  </button>
                </td>
              </tr>
            ))}
            </tbody>
          </table>
        </div>

        <div className="px-6 py-4 border-t border-slate-100 flex items-center justify-between bg-slate-50/30">
          <div className="text-xs font-medium text-slate-400">
            全 <span className="text-slate-700">{meta.totalCount}</span> 件中 {(meta.currentPage - 1) * 10 + 1}〜{Math.min(meta.currentPage * 10, meta.totalCount)} 件を表示
          </div>
          <div className="flex items-center space-x-1">
            <button
              onClick={() => handlePageChange(meta.currentPage - 1)}
              disabled={meta.currentPage === 1}
              className="p-2 text-slate-500 hover:bg-white border border-transparent hover:border-slate-200 rounded-lg disabled:opacity-40 disabled:hover:bg-transparent transition-all"
            >
              <FiChevronLeft className="w-4 h-4" />
            </button>

            <span className="px-3 py-1 text-sm font-semibold bg-white border border-slate-200 text-blue-600 rounded-lg shadow-sm">
              {meta.currentPage} / {meta.totalPages}
            </span>

            <button
              onClick={() => handlePageChange(meta.currentPage + 1)}
              disabled={meta.currentPage === meta.totalPages}
              className="p-2 text-slate-500 hover:bg-white border border-transparent hover:border-slate-200 rounded-lg disabled:opacity-40 disabled:hover:bg-transparent transition-all"
            >
              <FiChevronRight className="w-4 h-4" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}