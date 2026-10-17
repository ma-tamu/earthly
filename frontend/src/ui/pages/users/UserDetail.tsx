import {useNavigate, useParams} from "react-router";
import {useTranslation} from "react-i18next";
import {useInjection} from "../../../hooks/useInjection.ts";
import type {UserService} from "../../../services/UserService.ts";
import {TYPES} from "../../../core/types/di.ts";
import {useEffect, useState} from "react";
import type {User} from "../../../types/user.ts";


export default function UserDetail() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const userService = useInjection<UserService>(TYPES.UserService);

  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

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

    return () => { isMounted = false; };
  }, [id, userService]);

  // APIエラー（404等）が起きた場合の安全なフォールバック画面
  if (error) {
    return (
      <div className="text-center py-16 bg-white rounded-2xl border border-slate-100 shadow-sm">
        <p className="text-sm font-semibold text-red-500">{t('errors.notFound')}</p>
        <button onClick={() => navigate('/dashboard/users')} className="mt-4 text-xs font-bold text-blue-600 hover:underline">
          {t('menu.backToList')}
        </button>
      </div>
    );
  }

  return (
    <div className="w-full max-w-4xl mx-auto space-y-6">

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

      <div className="bg-white max-w-2xl shadow overflow-hidden sm:rounded-lg">
        <div className="px-4 py-5 sm:px-6">
          <h3 className="text-lg leading-6 font-medium text-gray-900">
            User database
          </h3>
          <p className="mt-1 max-w-2xl text-sm text-gray-500">
            Details and informations about user.
          </p>
        </div>
        <div className="border-t border-gray-200">
          <dl>
            <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                Full name
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                Mickael Poulaz
              </dd>
            </div>
            <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                Best techno
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                React JS
              </dd>
            </div>
            <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                Email address
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                m.poul@example.com
              </dd>
            </div>
            <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                Salary
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                $10,000
              </dd>
            </div>
            <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">
                About
              </dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                To get social media testimonials like these, keep your customers engaged with your social media accounts
                by posting regularly yourself
              </dd>
            </div>
          </dl>
        </div>
      </div>
    </div>
  );
}
