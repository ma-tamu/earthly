import type {FC} from "react";
import {useNavigate, useRouteError} from "react-router";
import {FiAlertTriangle} from "react-icons/fi";

export const ErrorPage: FC = () => {

  const error = useRouteError();
  const navigate = useNavigate();

  console.error('ErrorBoundary caught an error:', error);

  return (
    <div
      className="min-h-screen bg-white text-slate-800 flex flex-col items-center justify-center px-4 font-sans antialiased">
      <div className="w-full max-w-md text-center">
        <div
          className="mx-auto h-16 w-16 bg-red-50 rounded-2xl flex items-center justify-center text-red-600 mb-6 shadow-sm">
          <FiAlertTriangle className="w-8 h-8"/>
        </div>

        <h1 className="text-3xl font-extrabold tracking-tight text-slate-900 mb-3">
          問題が発生しました
        </h1>

        <p className="text-slate-500 text-sm mb-8 leading-relaxed">
          一時的にサーバーへの通信ができないか、システム内で予期せぬエラーが発生しました。時間を置いてもう一度お試しください。
        </p>

        <div className="space-y-3">
          <button
            onClick={() => window.location.reload()}
            className="w-full py-3 bg-blue-600 text-white font-semibold text-sm rounded-xl shadow-lg shadow-blue-600/20 hover:bg-blue-700 transition-all active:scale-[0.99]"
          >
            ページを再読み込みする
          </button>

          <button
            onClick={() => navigate('/', {replace: true})}
            className="w-full py-3 bg-slate-50 hover:bg-slate-100 text-slate-700 font-semibold text-sm rounded-xl border border-slate-200 transition-all"
          >
            ログイン画面に戻る
          </button>
        </div>
      </div>
    </div>
  );
}