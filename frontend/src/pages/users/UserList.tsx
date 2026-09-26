import {useSearchParams} from "react-router";
import {FiArrowDown, FiArrowUp, FiSearch, FiUserPlus} from "react-icons/fi";
import type {UserPaginatedResponse, UserService} from "../../services/UserService.ts";
import {useEffect, useState} from "react";
import {useInjection} from "../../hooks/useInjection.ts";
import {TYPES} from "../../types/di.ts";
import type {UserSearch} from "../../services/dto/UserSearch.ts";
import {Pagination} from "../../components/Pagination.tsx";
import {Loading} from "../../components/Loading.tsx";

function UserList() {
  const [searchParams, setSearchParams] = useSearchParams();
  const userService = useInjection<UserService>(TYPES.UserService)

  // URLパラメータの解析とデフォルト値の定義
  const currentPage = Number.parseInt(searchParams.get('page') || '1', 10);
  const currentSearch = searchParams.get('search') || '';
  const currentLimit = Number.parseInt(searchParams.get('limit') || '10', 10);
  const currentSortBy = searchParams.get('sortBy') || 'createdAt';
  const currentSortOrder = (searchParams.get('sortOrder') || 'desc') as 'asc' | 'desc';

  const [usersData, setUsersData] = useState<UserPaginatedResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [searchInputValue, setSearchInputValue] = useState(currentSearch);

  // パラメータ変更時の自律駆動 Fetch
  useEffect(() => {
    let isMounted = true;

    const params: UserSearch = {
      page: currentPage,
      search: currentSearch,
      limit: currentLimit,
      sortBy: currentSortBy,
      sortOrder: currentSortOrder
    };

    userService.getUsers(params)
      .then((data) => {
        if (isMounted) {
          setUsersData(data);
        }
      }).finally(() => setIsLoading(false));

    return () => {
      isMounted = false;
    };
  }, [currentPage, currentSearch, currentLimit, currentSortBy, currentSortOrder, userService]);

  useEffect(() => {
    setSearchInputValue(currentSearch);
  }, [currentSearch]);

  // ヘッダークリック時のソートトリガー
  const handleSort = (columnKey: string) => {
    let order: 'asc' | 'desc' = 'asc';
    if (currentSortBy === columnKey && currentSortOrder === 'asc') {
      order = 'desc'; // 同じ列がクリックされたら順序を反転
    }

    setSearchParams({
      page: '1', // ソート時は1ページ目に戻す
      search: currentSearch,
      limit: currentLimit.toString(),
      sortBy: columnKey,
      sortOrder: order
    });
  };

  // 表示件数（Limit）変更時のトリガー
  const handleLimitChange = (newLimit: number) => {
    setSearchParams({
      page: '1', // 件数変更時も1ページ目に戻す
      search: currentSearch,
      limit: newLimit.toString(),
      sortBy: currentSortBy,
      sortOrder: currentSortOrder
    });
  };

  const handleSearchSubmit = (e: SubmitEvent) => {
    e.preventDefault();
    setSearchParams({
      page: '1',
      search: searchInputValue,
      limit: currentLimit.toString(),
      sortBy: currentSortBy,
      sortOrder: currentSortOrder
    });
  };

  // 💡 ソートアイコンを美しく描画するヘルパー関数
  const renderSortIcon = (columnKey: string) => {
    if (currentSortBy !== columnKey) return null;
    return currentSortOrder === 'asc'
      ? <FiArrowUp className="inline ml-1.5 w-3.5 h-3.5 text-blue-600 animate-in fade-in duration-200"/>
      : <FiArrowDown className="inline ml-1.5 w-3.5 h-3.5 text-blue-600 animate-in fade-in duration-200"/>;
  };


  return (
    <div className="space-y-6">
      {/* 画面トップバー */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-extrabold text-slate-900 tracking-tight">ユーザー管理</h1>
          <p className="text-slate-500 text-sm mt-1">アカウントの高度な一覧検索・ソート管理が可能です。</p>
        </div>
        <button
          className="inline-flex items-center justify-center gap-2 px-4 py-2.5 bg-blue-600 hover:bg-blue-700 text-white text-sm font-semibold rounded-xl shadow-md">
          <FiUserPlus className="w-4 h-4"/>
          <span>新規ユーザー追加</span>
        </button>
      </div>

      {/* 検索 ＆ 💡表示件数セレクト コントロールバー */}
      <div
        className="bg-white p-4 rounded-2xl border border-slate-200/60 shadow-sm flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <form onSubmit={() => handleSearchSubmit} className="relative flex-1 max-w-md">
          <FiSearch className="absolute left-3.5 top-1/2 -translate-y-1/2 text-slate-400 w-4 h-4"/>
          <input
            type="text"
            placeholder="ユーザー名、またはメールアドレスで検索..."
            value={searchInputValue}
            onChange={(e) => setSearchInputValue(e.target.value)}
            className="w-full pl-10 pr-20 py-2 bg-slate-50/50 border border-slate-200 text-sm rounded-xl focus:outline-none text-slate-900"
          />
          <button type="submit"
                  className="absolute right-1.5 top-1/2 -translate-y-1/2 px-3 py-1 bg-white border border-slate-200 text-slate-600 text-xs font-bold rounded-lg shadow-sm">検索
          </button>
        </form>

        {/* 💡 表示件数切り替え UI */}
        <div className="flex items-center space-x-2 text-xs font-semibold text-slate-500">
          <span>表示件数:</span>
          <select
            value={currentLimit}
            onChange={(e) => handleLimitChange(Number.parseInt(e.target.value, 10))}
            className="bg-slate-50 border border-slate-200 rounded-lg px-2.5 py-1.5 text-slate-700 font-bold focus:outline-none cursor-pointer"
          >
            <option value="10">10件</option>
            <option value="25">25件</option>
            <option value="50">50件</option>
          </select>
        </div>
      </div>

      {/* テーブル */}
      <div className="bg-white rounded-2xl border border-slate-200/80 shadow-sm overflow-hidden">
        {isLoading || !usersData ? (
          <Loading variant="table" rows={currentLimit === 10 ? 5 : 8}/>
        ) : usersData.data.length === 0 ? (
          <div className="text-center py-16 text-slate-400">
            <FiSearch className="w-10 h-10 mx-auto text-slate-300 mb-3"/>
            <p className="text-sm font-semibold text-slate-500">一致するユーザーが見つかりません</p>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
              <tr
                className="bg-slate-50/70 border-b border-slate-200/80 text-xs font-semibold uppercase tracking-wider text-slate-500 select-none">
                {/* 💡 各ヘッダーをクリック可能にし、ソート条件をバインド */}
                <th onClick={() => handleSort('name')}
                    className="px-6 py-4 cursor-pointer hover:bg-slate-100/70 transition-colors">
                  名前 / メールアドレス {renderSortIcon('name')}
                </th>
                <th onClick={() => handleSort('role')}
                    className="px-6 py-4 cursor-pointer hover:bg-slate-100/70 transition-colors">
                  権限ロール {renderSortIcon('role')}
                </th>
                <th onClick={() => handleSort('status')}
                    className="px-6 py-4 cursor-pointer hover:bg-slate-100/70 transition-colors">
                  ステータス {renderSortIcon('status')}
                </th>
                <th onClick={() => handleSort('createdAt')}
                    className="px-6 py-4 cursor-pointer hover:bg-slate-100/70 transition-colors">
                  登録日 {renderSortIcon('createdAt')}
                </th>
                <th className="px-6 py-4 text-right">操作</th>
              </tr>
              </thead>
              <tbody className="divide-y divide-slate-100 text-sm">
              {usersData.data.map((user) => (
                <tr key={user.id} className="hover:bg-slate-50/50 transition-colors">
                  <td className="px-6 py-4.5">
                    <div className="flex items-center space-x-3">
                      <div
                        className="h-9 w-9 bg-slate-100 rounded-full flex items-center justify-center font-bold text-slate-600 text-xs">
                        {user.name.replace(/[^A-Za-z0-9]/g, '').charAt(0) || user.name.charAt(0)}
                      </div>
                      <div>
                        <div className="font-semibold text-slate-900">{user.name}</div>
                        <div className="text-slate-400 text-xs">{user.email}</div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4.5 font-medium text-slate-700">
                    {/*{user.role === 'admin' ? <span className="text-indigo-600 font-semibold bg-indigo-50 px-2 py-1 rounded-md text-xs">管理者</span> : <span className="text-slate-600">一般ユーザー</span>}*/}
                  </td>
                  <td className="px-6 py-4.5">
                      <span
                        className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border ${user.lockout ? 'bg-emerald-50 text-emerald-700 border-emerald-200/60' : 'bg-slate-50 text-slate-400 border-slate-200'}`}>
                        <span
                          className={`h-1.5 w-1.5 rounded-full mr-1.5 ${user.lockout ? 'bg-emerald-500' : 'bg-slate-300'}`}/>
                        {user.lockout ? '有効' : '無効'}
                      </span>
                  </td>
                  <td
                    className="px-6 py-4.5 text-slate-500 text-xs">{new Date(user.createdAt).toLocaleDateString('ja-JP')}</td>
                  <td className="px-6 py-4.5 text-right">
                    <button
                      className="text-xs font-bold text-slate-600 hover:text-blue-600 px-3 py-1.5 hover:bg-slate-50 rounded-lg">編集
                    </button>
                  </td>
                </tr>
              ))}
              </tbody>
            </table>
          </div>
        )}

        {usersData && usersData.data.length > 0 && (
          <Pagination
            currentPage={usersData.meta.offset}
            totalPages={usersData.meta.total}
            totalCount={usersData.meta.length}
            limit={currentLimit} // 💡 選択されたLimit数を渡す
          />
        )}
      </div>
    </div>
  );
}

export default UserList