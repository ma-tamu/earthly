import {useState} from "react";
import {useNavigate} from "react-router";
import {InputField} from "../parts/InputField.tsx";
import {FiLock, FiMail} from "react-icons/fi";
import {useInjection} from "../hooks/useInjection.ts";
import type {AuthService} from "../security/AuthService.ts";
import {TYPES} from "../types/di.ts";

/**
 * ログインページ
 * @constructor
 */
export function Login() {

  const [loginId, setLoginId] = useState('');
  const [password, setPassword] = useState('');

  // エラー状態の管理
  const [errors, setErrors] = useState({loginId: '', password: ''});
  const navigate = useNavigate();
  const authService = useInjection<AuthService>(TYPES.AuthService);

  // メールアドレスの検証
  const validateLoginId = (value: string) => {
    if (!value) {
      return 'ログインIDを入力してください。';
    }
    return '';
  };

  // パスワードの検証
  const validatePassword = (value: string) => {
    if (!value) {
      return 'パスワードを入力してください。';
    }
    if (value.length < 8) {
      return 'パスワードは8文字以上で入力してください。';
    }
    return '';
  };

  // フォーム送信時の処理
  const handleSubmit = async (e: SubmitEvent) => {
    e.preventDefault();

    const loginIdError = validateLoginId(loginId);
    const passwordError = validatePassword(password);

    if (loginIdError || passwordError) {
      setErrors({loginId: loginIdError, password: passwordError});
      return;
    }

    try {
      await authService.login(loginId, password);
      navigate('/', {replace: true});
    } catch (error) {
      console.log(error);
      alert('メールアドレスまたはパスワードが正しくありません。');
    }

  };

  return (
    <div className="w-full max-w-110">
      <div className="text-center mb-10">
        <h1 className="text-3xl font-extrabold tracking-tight text-slate-900 mb-3">Welcome Earthly</h1>
        <p className="text-slate-500 text-sm">システムを利用するにはアカウント情報を入力してください</p>
      </div>

      <div className="bg-white p-8 rounded-2xl border border-slate-100 shadow-xl shadow-slate-100/50">
        <form onSubmit={() => handleSubmit} className="space-y-6" noValidate>
          <InputField
            id="loginId"
            type="loginId"
            label="ログインID"
            placeholder="LoginID"
            value={loginId}
            error={errors.loginId}
            onChange={(e) => {
              setLoginId(e.target.value);
              if (errors.loginId) setErrors(prev => ({...prev, email: ''}));
            }}
            onBlur={() => setErrors(prev => ({...prev, email: validateLoginId(loginId)}))}
            icon={<FiMail className="w-5 h-5"/>}
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
              value={password}
              error={errors.password}
              onChange={(e) => {
                setPassword(e.target.value);
                if (errors.password) setErrors(prev => ({...prev, password: ''}));
              }}
              onBlur={() => setErrors(prev => ({...prev, password: validatePassword(password)}))}
              icon={<FiLock className="w-5 h-5"/>}
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
            className="w-full py-3 bg-blue-600 text-white font-semibold text-sm rounded-xl shadow-lg shadow-blue-600/20 hover:bg-blue-700 hover:shadow-blue-600/30 active:scale-[0.99] focus:outline-none focus:ring-4 focus:ring-blue-600/20 transition-all"
          >
            ログイン
          </button>
        </form>
      </div>

      <p className="text-center mt-6 text-sm text-slate-500">
        アカウントをお持ちでないですか？{' '}
        <a href="#" className="font-semibold text-blue-600 hover:text-blue-700 transition-colors">新しく作成する</a>
      </p>
    </div>

  );
}