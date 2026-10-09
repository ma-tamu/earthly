import {InputField} from "../components/parts/InputField.tsx";
import {FiLock, FiMail} from "react-icons/fi";
import {useLocale} from "../../hooks/useLocale.ts";
import {useAuth} from "../../hooks/useAuth.ts";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {type LoginParams, loginSchema} from "../../types/auth.ts";
import {Header} from "../components/Header.tsx";
import {Footer} from "../components/Footer.tsx";
import {useState} from "react";

/**
 * ログインページ
 * @constructor
 */
export function Login() {

  const {t} = useLocale();
  const {login} = useAuth();

  // 認証後専用の状態管理
  const [isSidebarOpen, setIsSidebarOpen] = useState(true); // デスクトップサイドバーの開閉
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false); // スマホドロワーの開閉


  const {
    register,
    handleSubmit,
    formState: {errors, isSubmitting},
  } = useForm<LoginParams>({
    resolver: zodResolver(loginSchema),
    mode: 'onBlur',
  });

  const onSubmit = async (data: LoginParams, e: SubmitEvent) => {
    e.preventDefault();
    try {
      await login(data);
    } catch(error) {
      console.error(error);
      // パスワード間違いや、401等の認証エラーが起きた場合のハンドリング
      alert(t('validation.authFailed'));
    }
  };

  return (
    <div className="min-h-screen bg-white text-slate-800 flex flex-col font-sans antialiased selection:bg-blue-500 selection:text-white">

      {/* どんな画面でも常に表示される共通ヘッダー */}
      <Header
        isSidebarOpen={isSidebarOpen}
        onToggleSidebar={() => setIsSidebarOpen(!isSidebarOpen)}
        onToggleMobileMenu={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
      />

      {/* メインレイアウトコンテナ */}
      <div className="flex-1 flex bg-slate-50/50">

        {/* コンテンツ展開エリア（認証状況やサイドバーの開閉状態で左側の余白を動的に変化） */}
        <div className={`flex-1 flex flex-col min-w-0 min-h-[calc(100vh-64px)] transition-all duration-300 md:pl-0`}
        >
          <main className="flex-1 flex p-4 sm:p-6 lg:p-8 bg-white justify-center">
            <div className="w-full max-w-110">
              <div className="text-center mb-10">
                <h1 className="text-3xl font-extrabold tracking-tight text-slate-900 mb-3">Welcome Earthly</h1>
                <p className="text-slate-500 text-sm">システムを利用するにはアカウント情報を入力してください</p>
              </div>

              <div className="bg-white p-8 rounded-2xl border border-slate-100 shadow-xl shadow-slate-100/50">
                <form onSubmit={(e) => handleSubmit(onSubmit)(e)} className="space-y-6" noValidate>
                  <InputField
                    id="loginId"
                    type="loginId"
                    label="ログインID"
                    placeholder="LoginID"
                    disabled={isSubmitting}
                    error={errors.loginId?.message}
                    icon={<FiMail className="w-5 h-5"/>}
                    {...register('loginId')}
                  />

                  <div>
                    <div className="flex justify-between items-center mb-2">
                      <span className="text-xs font-semibold uppercase tracking-wider text-slate-500">パスワード</span>
                      <a href="#" className="text-xs font-semibold text-blue-600 hover:text-blue-700 transition-colors">
                        パスワードをお忘れですか？
                      </a>
                    </div>
                    <InputField
                      id="password"
                      type="password"
                      label=""
                      placeholder="••••••••"
                      disabled={isSubmitting}
                      error={errors.password?.message}
                      icon={<FiLock className="w-5 h-5"/>}
                      {...register('password')}
                    />
                  </div>

                  <div className="flex items-center">
                    <input id="remember-me" type="checkbox"
                           className="h-4 w-4 rounded border-slate-300 text-blue-600 focus:ring-blue-500/20 cursor-pointer"/>
                    <label htmlFor="remember-me"
                           className="ml-2.5 text-sm text-slate-600 select-none cursor-pointer font-medium">
                      ログイン状態を保持する
                    </label>
                  </div>

                  <button
                    type="submit"
                    disabled={isSubmitting} // 多重送信（連打バグ）を物理的に100%シャットアウト
                    className={`w-full py-3 text-white font-semibold text-sm rounded-xl shadow-lg transition-all active:scale-[0.99] focus:outline-none
              ${isSubmitting
                      ? 'bg-blue-400 cursor-not-allowed shadow-none'
                      : 'bg-blue-600 shadow-blue-600/20 hover:bg-blue-700 hover:shadow-blue-600/30'
                    }`}
                  >
                    {isSubmitting ? t('login.submitting') : t('login.submit')}
                  </button>
                </form>
              </div>
            </div>
          </main>
          <Footer />
        </div>
      </div>

    </div>



  );
}