import {useNavigate, useParams} from "react-router";
import {useTranslation} from "react-i18next";
import {useInjection} from "../../../hooks/useInjection.ts";
import type {UserService} from "../../../services/UserService.ts";
import {TYPES} from "../../../core/types/di.ts";
import {useEffect, useMemo, useState} from "react";
import type {User} from "../../../types/user.ts";
import {FiArrowLeft, FiLayers, FiSearch} from "react-icons/fi";


export default function UserDetail() {
  const {id} = useParams<{ id: string }>();
  const navigate = useNavigate();
  const {t} = useTranslation();
  const userService = useInjection<UserService>(TYPES.UserService);

  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // 検索パネル用のローカルState
  const [searchQuery, setSearchQuery] = useState('');
  const [searchInputValue, setSearchInputValue] = useState('');

  // 💡 URLのパラメータ (:id) が変わるたびに、自動で詳細データを取得
  useEffect(() => {
    let isMounted = true;

    userService.get(id!)
      .then((data) => {
        if (isMounted) {
          setUser(data);
          setIsLoading(false);
        }
      })
      .catch((err) => {
        if (isMounted) {
          setError(err.message || 'NOT_FOUND');
          setIsLoading(false);
        }
      });

    return () => {
      isMounted = false;
    };
  }, [id, userService]);

  const filteredRoles = useMemo(() => {
    return [1];
  }, [[], searchQuery]);

  const handleSearchSubmit = (e: SubmitEvent) => {
    e.preventDefault();
    setSearchQuery(searchInputValue);
  };

  // APIエラー（404等）が起きた場合の安全なフォールバック画面
  if (error) {
    return (
      <div className="text-center py-16 bg-white rounded-2xl border border-slate-100 shadow-sm">
        <p className="text-sm font-semibold text-red-500">{t('errors.notFound')}</p>
        <button onClick={() => navigate('/dashboard/users')}
                className="mt-4 text-xs font-bold text-blue-600 hover:underline">
          {t('menu.backToList')}
        </button>
      </div>
    );
  }

  return (
    <div className="w-full max-w-screen space-y-6">

      {/* 1. パンくずヘッダー ＆ 戻るボタン */}
      <div className="flex items-center space-x-4">
        <button
          onClick={() => navigate('/dashboard/users')} // 💡 一覧画面へ戻る
          className="p-2 text-slate-500 hover:bg-slate-100 rounded-xl border border-slate-200 bg-white transition-all shadow-sm"
        >
          <FiArrowLeft className="w-4 h-4"/>
        </button>
        <div>
          <div className="text-xs font-medium text-slate-400">{t('menu.users')} / Details</div>
          <h1 className="text-xl font-extrabold text-slate-900 tracking-tight mt-0.5">
            {isLoading ? '...' : user?.name}
          </h1>
        </div>
      </div>

      <div className="bg-white shadow overflow-hidden sm:rounded-lg">
        <div className="px-4 py-5 sm:px-6">
          <h3 className="text-lg leading-6 font-medium text-gray-900">
            {user?.loginId}
          </h3>
        </div>
        <div className="border-t border-gray-200">
          <dl>
            <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-6 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                Full name
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-4">
                Mickael Poulaz
              </dd>
            </div>
            <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-6 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                Best techno
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-4">
                React JS
              </dd>
            </div>
            <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-6 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                Email address
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-4">
                m.poul@example.com
              </dd>
            </div>
            <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-6 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                Salary
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-4">
                $10,000
              </dd>
            </div>
            <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-6 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                About
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-4">
                To get social media testimonials like these, keep your customers engaged with your social media accounts
                by posting regularly yourself
              </dd>
            </div>
          </dl>
        </div>
      </div>


      <div className="space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <h2 className="text-base font-bold text-slate-900 flex items-center gap-2">
            <FiLayers className="text-blue-600"/>
            <span>{t('userDetail.tabRoles')}</span>
          </h2>

          {/* 💡 検索パネル */}
          <form onSubmit={() => handleSearchSubmit} className="relative w-full sm:max-w-xs">
            <FiSearch className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 w-3.5 h-3.5"/>
            <input
              type="text"
              placeholder={t('userDetail.searchPlaceholder')}
              value={searchInputValue}
              onChange={(e) => setSearchInputValue(e.target.value)}
              className="w-full pl-9 pr-16 py-1.5 bg-white border border-slate-200 text-xs rounded-xl focus:outline-none focus:border-blue-500 text-slate-900 shadow-sm"
            />
            <button type="submit"
                    className="absolute right-1 top-1/2 -translate-y-1/2 px-2.5 py-1 bg-slate-100 border border-slate-200 hover:bg-slate-200 text-slate-600 text-[10px] font-bold rounded-lg transition-colors">
              {t('userDetail.searchBtn')}
            </button>
          </form>
        </div>

        {/* 💡 検索結果（ロールデータテーブル） */}
        <div className="bg-white rounded-2xl border border-slate-200/80 shadow-sm overflow-hidden">
          <div className="bg-white rounded-2xl border border-slate-200/80 shadow-sm overflow-hidden">
            {filteredRoles.length === 0 ? (
              <div className="text-center py-12 text-slate-400 text-xs font-semibold">
                {t('userDetail.noRoles')}
              </div>
            ) : (
              <div className="overflow-x-auto">
                <table className="w-full text-sm text-left rtl:text-right text-body">
                  <thead>
                  <tr className="bg-slate-50/70 border-b border-slate-200/80 text-xs font-semibold uppercase tracking-wider text-slate-500 select-none">
                    <th className="px-6 py-4 cursor-pointer hover:bg-slate-100/70 transition-colors">{t('userDetail.thRoleName')}</th>
                    <th className="px-6 py-4 cursor-pointer hover:bg-slate-100/70 transition-colors">&nbsp;</th>
                  </tr>
                  </thead>
                  <tbody className="divide-y divide-slate-100 text-sm">
                  <tr className="hover:bg-slate-50/50 transition-colors">
                    <td className="px-6 py-4.5">ロールA</td>
                    <td className="flex px-6 py-4.5 justify-end"><input type="checkbox"/></td>
                  </tr>
                  <tr className="hover:bg-slate-50/50 transition-colors">
                    <td className="px-6 py-4.5">ロールA</td>
                    <td className="flex px-6 py-4.5 justify-end"><input type="checkbox"/></td>
                  </tr>
                  <tr className="hover:bg-slate-50/50 transition-colors">
                    <td className="px-6 py-4.5">ロールA</td>
                    <td className="flex px-6 py-4.5 justify-end"><input type="checkbox"/></td>
                  </tr>
                  <tr className="hover:bg-slate-50/50 transition-colors">
                    <td className="px-6 py-4.5">ロールA</td>
                    <td className="flex px-6 py-4.5 justify-end"><input type="checkbox"/></td>
                  </tr>
                  <tr className="hover:bg-slate-50/50 transition-colors">
                    <td className="px-6 py-4.5">ロールA</td>
                    <td className="flex px-6 py-4.5 justify-end"><input type="checkbox"/></td>
                  </tr>
                  <tr className="hover:bg-slate-50/50 transition-colors">
                    <td className="px-6 py-4.5">ロールA</td>
                    <td className="flex px-6 py-4.5 justify-end"><input type="checkbox"/></td>
                  </tr>
                  <tr className="hover:bg-slate-50/50 transition-colors">
                    <td className="px-6 py-4.5">ロールA</td>
                    <td className="flex px-6 py-4.5 justify-end"><input type="checkbox"/></td>
                  </tr>
                  </tbody>
                </table>
              </div>
            )
            }
          </div>
        </div>
      </div>
    </div>
  );
}
