import 'reflect-metadata';
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from "./App.tsx";

// アプリ起動とMSWの競合を防ぐ非同期ラッパー関数
async function enableMocking() {
  // 環境変数 VITE_ENABLE_MOCK が "true" の場合のみMSWを起動する
  if (import.meta.env.DEV && import.meta.env.VITE_ENABLE_MOCK === "true") {
    const { worker } = await import("./mocks/browser");
    // MSWがネットワークのインターセプタを貼り終えるまで確実に待機する
    await worker.start({
      onUnhandledRequest: "bypass", // モック定義外の通信（ViteのHMR等）はスルーする
    });
    console.log("🚀 [MSW] Mock Service Worker が有効です");
  }
}

// 初期化を待ってからアプリケーションをレンダリングする
enableMocking().then(() => {
  createRoot(document.getElementById("root")!).render(
    <StrictMode>
      <App />
    </StrictMode>
  );
});
