import 'reflect-metadata';
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from "./App.tsx";

// アプリ起動とMSWの競合を防ぐ非同期ラッパー関数
async function enableMocking() {
  // Viteの開発環境かつ、モックを有効にしたい場合のみ動かす
  if (import.meta.env.DEV) {
    const { worker } = await import("./mocks/browser");
    // MSWがネットワークのインターセプタを貼り終えるまで確実に待機する
    await worker.start({
      onUnhandledRequest: "bypass", // モック定義外の通信（ViteのHMR等）はスルーする
    });
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
