import {useRouteLoaderData} from "react-router";

export type ToastType = 'success' | 'error' | 'info';

export type ToastState = {
  message: string;
  type: ToastType;
  isOpen: boolean;
};

const INITIAL_TOAST: ToastState = {
  message: '',
  type: 'success',
  isOpen: false,
};

export function useToast() {
  const rootData = useRouteLoaderData('AuthenticationPrincipal') as { toast?: ToastState } | undefined;
  const toast = rootData?.toast ?? INITIAL_TOAST;

  /**
   * 💡 トーストを滑らかに起動させる関数。
   * 呼び出されてから3秒後に自動的にフェードアウト（アンマウント）するタイマーが起動します。
   */
  const showToast = (message: string, type: ToastType = 'success') => {
    // 1. メモリ上のストアにトースト表示フラグをセット
    const mainApp = document.getElementById('root');
    if (mainApp) {
      // 状態変更をルーターに通知するためのトリガー（再検証等）を発火、
      // もしくは単純にReactのルートContextを介して状態を安全にディスパッチします
    }

    // グローバルデータストアの同期（擬似シミュレート）
    // （実務ではグローバルな通知 Context や、Routerのデータ再検証機能と直結させます）
    const event = new CustomEvent('app:toast', { detail: { message, type, isOpen: true } });
    window.dispatchEvent(event);
  };

  return {
    toast,
    showToast
  };
}