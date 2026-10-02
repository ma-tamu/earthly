import type {FC} from "react";
import type {UserConfirmModalProps} from "./props/UserConfirmModalProps.ts";
import {useLocale} from "../../../hooks/useLocale.ts";
import {Modal} from "../../components/Modal.tsx";
import {FiAlertCircle} from "react-icons/fi";

export const UserConfirmModal: FC<UserConfirmModalProps> = ({
                                                              isOpen,
                                                              onClose,
                                                              onConfirm,
                                                              formData,
                                                              isSubmitting,
                                                            }) => {
  const {t} = useLocale();

  return (
    /* 💡 複雑なDOM制御やサイズ切り替えは、すべて共通化した Modal コンポーネントが処理します */
    <Modal
      isOpen={isOpen}
      onClose={onClose}
      title={t('userCreate.modalTitle')}
      size="md" // 💡 用途に合わせて標準の md サイズを指定
    >
      <div className="space-y-6">

        {/* アイコン＆補助テキストエリア */}
        <div className="flex items-start space-x-3.5">
          <div className="h-10 w-10 bg-blue-50 text-blue-600 rounded-xl flex items-center justify-center shrink-0">
            <FiAlertCircle className="w-5 h-5"/>
          </div>
          <div>
            <p className="text-slate-500 text-sm leading-relaxed">
              {t('userCreate.modalSubtitle')}
            </p>
          </div>
        </div>

        {/* 💡 ベタ書きを完全に排除した、登録内容の確認データ一覧 */}
        <div className="bg-slate-50/70 border border-slate-100 rounded-xl p-4 space-y-3 text-sm">
          <div className="flex justify-between border-b border-slate-200/50 pb-2">
            <span className="text-slate-400 font-medium text-xs">{t('userCreate.nameLabel')}</span>
            <span className="font-bold text-slate-800">{formData.name}</span>
          </div>
          <div className="flex justify-between border-b border-slate-200/50 pb-2">
            <span className="text-slate-400 font-medium text-xs">{t('userCreate.emailLabel')}</span>
            <span className="font-bold text-slate-800">{formData.email}</span>
          </div>
{/*          <div className="flex justify-between border-b border-slate-200/50 pb-2">
            <span className="text-slate-400 font-medium text-xs">{t('userCreate.deptLabel')}</span>
            <span className="font-bold text-slate-800">{formData.department}</span>
          </div>
          <div className="flex justify-between pb-1">
            <span className="text-slate-400 font-medium text-xs">{t('userCreate.roleLabel')}</span>
            <span className="font-bold text-slate-800">
              {formData.role === 'admin' ? t('userCreate.roleAdmin') : t('userCreate.roleUser')}
            </span>
          </div>*/}
        </div>

        {/* 下部アクションボタンエリア */}
        <div className="flex items-center justify-end space-x-3 pt-4 border-t border-slate-100/60">
          <button
            type="button"
            disabled={isSubmitting}
            onClick={onClose}
            className="px-4 py-2.5 bg-slate-50 border border-slate-200 text-slate-700 text-xs font-bold rounded-xl hover:bg-slate-100 transition-colors focus:outline-none"
          >
            {t('userCreate.btnCancel')}
          </button>
          <button
            type="button"
            disabled={isSubmitting}
            onClick={onConfirm}
            className="px-5 py-2.5 bg-blue-600 text-white text-xs font-bold rounded-xl hover:bg-blue-700 shadow-md shadow-blue-600/10 transition-colors disabled:opacity-50 focus:outline-none"
          >
            {isSubmitting ? t('userCreate.modalSubmitting') : t('userCreate.modalConfirm')}
          </button>
        </div>

      </div>
    </Modal>
  );
};