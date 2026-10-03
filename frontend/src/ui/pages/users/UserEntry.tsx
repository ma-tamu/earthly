import {useState} from "react";
import {useNavigate} from "react-router";
import {useLocale} from "../../../hooks/useLocale.ts";
import {useInjection} from "../../../hooks/useInjection.ts";
import {TYPES} from "../../../core/types/di.ts";
import type {UserService} from "../../../services/UserService.ts";
import {useForm} from "react-hook-form";
import {type UserEntryParam, UserEntrySchema} from "../../../types/user.ts";
import {zodResolver} from "@hookform/resolvers/zod";
import {FiArrowLeft, FiMail, FiUser} from "react-icons/fi";
import {InputField} from "../../components/parts/InputField.tsx";
import {useToast} from "../../../hooks/useToast.ts";
import {UserConfirmModal} from "./modal/UserConfirmModal.tsx";

export function UserEntry() {

  const navigate = useNavigate();
  const {t} = useLocale();
  const {showToast} = useToast();
  const userService = useInjection<UserService>(TYPES.UserService);

  const [isConfirmOpen, setIsConfirmOpen] = useState(false);


  const {register, handleSubmit, getValues, formState: {errors, isSubmitting}} = useForm<UserEntryParam>({
    resolver: zodResolver(UserEntrySchema),
    mode: 'onBlur',
  });

  const onValidSubmit = () => {
    setIsConfirmOpen(true);
  };

  const handleFinalConfirm = async () => {
    try {
      const formData = getValues();

      // 💡 シングルトンサービスを直接駆動。実際の通信結果（採番された最新のUser詳細）を受け取る
      const id = await userService.entry(formData);

      setIsConfirmOpen(false);
      showToast(t('userCreate.toastSuccess'), 'success');

      // 💡 採番されたIDの「詳細画面」へ replace: true を伴って滑らかに画面遷移
      navigate(`/users/${id}`, {replace: true});
    } catch(error) {
      console.log(error);
      showToast(t('userCreate.toastError'), 'error');
    }
  };

  return (
    <div className="w-full max-w-2xl mx-auto space-y-6">
      <div className="flex items-center space-x-4">
        <button onClick={() => navigate('/users')}
                className="p-2 text-slate-500 hover:bg-slate-100 rounded-xl border bg-white shadow-sm focus:outline-none">
          <FiArrowLeft className="w-4 h-4"/>
        </button>
        <div>
          <div className="text-xs font-medium text-slate-400">{t('userCreate.subtitle')}</div>
          <h1 className="text-xl font-extrabold text-slate-900 tracking-tight mt-0.5">{t('userCreate.title')}</h1>
        </div>
      </div>

      <div className="bg-white p-6 sm:p-8 rounded-2xl border border-slate-200/60 shadow-sm">
        <form onSubmit={handleSubmit(onValidSubmit)} className="space-y-5" noValidate>
          <InputField
            id="loginId"
            label={t('userCreate.loginIdLabel')}
            placeholder={t('userCreate.loginIdPlaceholder')}
            disabled={isSubmitting}
            error={errors.loginId?.message}
            icon={<FiUser className="w-5 h-5"/>}
            {...register('loginId')}
          />

          <InputField
            id="name"
            label={t('userCreate.nameLabel')}
            placeholder={t('userCreate.namePlaceholder')}
            disabled={isSubmitting}
            error={errors.name?.message}
            icon={<FiUser className="w-5 h-5"/>}
            {...register('name')}
          />

          <InputField
            id="email"
            type="email"
            label={t('userCreate.emailLabel')}
            placeholder={t('userCreate.emailPlaceholder')}
            disabled={isSubmitting}
            error={errors.email?.message}
            icon={<FiMail className="w-5 h-5"/>}
            {...register('email')}
          />

          <label htmlFor={"language"} className="block text-xs font-semibold uppercase tracking-wider text-slate-500 mb-2">
            言語
          </label>
          <select id="language"
                  className={`w-full pr-4 py-3 bg-slate-50/50 border text-sm rounded-xl focus:outline-none placeholder-slate-400 transition-all
                  ${errors.language
                    ? 'border-red-400 focus:ring-4 focus:ring-red-500/10'
                    : 'border-slate-200 focus:ring-4 focus:ring-blue-500/10'
                  }`}
                  {...register('language')}>
            <option></option>
            <option value="ja">日本語</option>
            <option value="en">英語</option>
          </select>

          <label htmlFor={"timezone"} className="block text-xs font-semibold uppercase tracking-wider text-slate-500 mb-2">
            タイムゾーン
          </label>
          <select id="timezone"
                  className={`w-full pr-4 py-3 bg-slate-50/50 border text-sm rounded-xl focus:outline-none placeholder-slate-400 transition-all
                  ${errors.language
                    ? 'border-red-400 focus:ring-4 focus:ring-red-500/10'
                    : 'border-slate-200 focus:ring-4 focus:ring-blue-500/10'
                  }`}
                  {...register('timezone')}>
            <option></option>
            <option value="utc">UTC</option>
            <option value="asia/tokyo">アジア/東京</option>
          </select>

          <div className="pt-3 border-t border-slate-100 flex items-center justify-end space-x-3">
            <button type="button" disabled={isSubmitting} onClick={() => navigate('/users')}
                    className="px-4 py-2.5 text-xs font-bold text-slate-500 hover:bg-slate-50 rounded-xl transition-colors">
              {t('userCreate.btnCancel')}
            </button>
            <button type="submit"
                    className="px-5 py-2.5 bg-blue-600 text-white text-xs font-bold rounded-xl hover:bg-blue-700 shadow-md transition-colors">
              {t('userCreate.btnSubmit')}
            </button>
          </div>
        </form>
      </div>

      <UserConfirmModal
        isOpen={isConfirmOpen}
        onClose={() => setIsConfirmOpen(false)}
        onConfirm={handleFinalConfirm}
        formData={getValues()}
        isSubmitting={isSubmitting}
      />
    </div>
  );
}