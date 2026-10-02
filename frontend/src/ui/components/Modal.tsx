import {type FC, type ReactNode, useEffect, useRef} from 'react';
import {FiX} from 'react-icons/fi';

type ModalSize = 'sm' | 'md' | 'lg' | 'xl' | 'full';

type ModalProps = {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: ReactNode;
  size?: ModalSize; // 💡 パラメータ（Props）を渡すことで、横幅を動的に変更可能
};

export const Modal: FC<ModalProps> = ({
                                        isOpen,
                                        onClose,
                                        title,
                                        children,
                                        size = 'md', // デフォルトは標準の md サイズ
                                      }) => {
  const modalRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!isOpen) {
      return;
    }

    // 💡 A11y: モーダル表示中は、裏側のメインHTML要素をスクリーンリーダー等の読み上げから隔離
    const mainApp = document.getElementById('root');
    if (mainApp) {
      mainApp.setAttribute('aria-hidden', 'true');
    }

    // 💡 フォーカストラップ：モーダル内のすべての「フォーカスが当たるべき要素」を動的に自動抽出
    const focusableElements = modalRef.current?.querySelectorAll(
      'button, [href], input, select, textarea, [tabindex="0"]'
    );

    if (focusableElements && focusableElements.length > 0) {
      const firstElement = focusableElements[0] as HTMLElement;
      const lastElement = focusableElements[focusableElements.length - 1] as HTMLElement;

      // 開いた瞬間、最上部の要素（通常は閉じるボタン）に自動で初期焦点を当てる
      firstElement.focus();

      const handleKeyDown = (e: KeyboardEvent) => {
        // Esc キーが押されたら、優しくモーダルを閉じる
        if (e.key === 'Escape') {
          onClose();
          return;
        }

        if (e.key !== 'Tab') {
          return;
        }

        if (e.shiftKey) {
          // Shift + Tab：最初の要素で後ろに戻ろうとしたら、最後の要素へジャンプ
          if (document.activeElement === firstElement) {
            lastElement.focus();
            e.preventDefault();
          }
        } else {
          // Tab：最後の要素で次に進もうとしたら、最初の要素へループ
          if (document.activeElement === lastElement) {
            firstElement.focus();
            e.preventDefault();
          }
        }
      };

      document.addEventListener('keydown', handleKeyDown);
      return () => {
        document.removeEventListener('keydown', handleKeyDown);
        if (mainApp) mainApp.removeAttribute('aria-hidden');
      };
    }
  }, [isOpen, onClose]);

  // 💡 パラメータ（size）に応じた Tailwind CSS マックス幅クラスのマッピング
  const sizeClasses: Record<ModalSize, string> = {
    sm: 'max-w-sm',     // 384px (小さな警告メッセージ用)
    md: 'max-w-md',     // 448px (通常の入力確認用)
    lg: 'max-w-lg',     // 512px (詳細データの展開用)
    xl: 'max-w-2xl',    // 672px (入力フォームが多いドッキング用)
    full: 'max-w-[94vw] h-[88vh]', // フルスクリーンに近い業務特化サイズ
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center" role="dialog" aria-modal="true">
      {/* 💡 オーバーレイ（背景マスク）：ここをクリックされても安全に onClose を発火して閉じる */}
      <div
        className="fixed inset-0 bg-slate-900/40 backdrop-blur-sm animate-in fade-in duration-200"
        onClick={onClose}
      />

      {/* モーダルウィンドウ本体 */}
      <div
        ref={modalRef}
        /* 💡 渡された size パラメータを動的にドッキングして横幅を可変させます */
        className={`relative bg-white w-full mx-4 rounded-2xl shadow-2xl border border-slate-100 z-50 animate-in fade-in zoom-in-95 duration-200 overflow-hidden flex flex-col max-h-[85vh] transition-all duration-300
          ${sizeClasses[size]}`}
      >
        {/* ヘッダー */}
        <div className="px-6 py-4 border-b border-slate-100 flex items-center justify-between bg-slate-50/50">
          <h3 className="text-base font-bold text-slate-900">{title}</h3>
          <button
            onClick={onClose}
            className="p-2 text-slate-400 hover:bg-slate-100 rounded-xl transition-colors focus:outline-none focus:ring-2 focus:ring-blue-500/20"
            aria-label="閉じる"
          >
            <FiX className="w-4 h-4"/>
          </button>
        </div>

        {/* コンテンツエリア（溢れた場合は内部スクロール） */}
        <div className="flex-1 overflow-y-auto p-6">
          {children}
        </div>
      </div>
    </div>
  );
};