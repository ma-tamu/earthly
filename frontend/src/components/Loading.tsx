import type {FC} from "react";

type LoadingVariant = 'table' | 'card' | 'spinner';

type LoadingProps = {
  variant?: LoadingVariant; // 💡 表示スタイルを動的に選べる
  rows?: number;            // table/card スタイル時のダミー行数
};

export const Loading: FC<LoadingProps> = ({variant = 'table', rows = 5}) => {
  // アニメーション用の共通パルス（明滅）クラス
  const pulseClass = "animate-pulse w-full";

  // ① テーブル型（一覧画面の読み込み用）
  if (variant === 'table') {
    return (
      <div className={`${pulseClass} p-6 space-y-4 bg-white rounded-2xl`}>
        <div className="flex space-x-4 border-b border-slate-100 pb-4">
          {Array.from({ length: 4 }).map((_, i) => (
            <div key={`h-skel-${i}`} className={`h-4 bg-slate-200 rounded-md ${i === 0 ? 'w-1/4' : 'flex-1'}`} />
          ))}
        </div>
        <div className="space-y-6 pt-2">
          {Array.from({ length: rows }).map((_, rowIndex) => (
            <div key={`r-skel-${rowIndex}`} className="flex space-x-4 items-center">
              {Array.from({ length: 4 }).map((_, colIndex) => (
                <div key={`c-skel-${rowIndex}-${colIndex}`} className={`h-5 bg-slate-100 rounded-md ${colIndex === 0 ? 'w-1/4 flex items-center space-x-3' : 'flex-1'}`}>
                  {colIndex === 0 && <div className="h-7 w-7 bg-slate-200 rounded-full shrink-0" />}
                  {colIndex === 0 && <div className="h-4 bg-slate-200 rounded w-2/3" />}
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>
    );
  }

  // ② カード型（ダッシュボードや統計ブロックの読み込み用）
  if (variant === 'card') {
    return (
      <div className={`${pulseClass} grid grid-cols-1 md:grid-cols-3 gap-6`}>
        {Array.from({ length: 3 }).map((_, i) => (
          <div key={`card-skel-${i}`} className="bg-white p-6 rounded-2xl border border-slate-200/60 shadow-sm space-y-4">
            <div className="h-10 w-10 bg-slate-200 rounded-xl" />
            <div className="h-5 bg-slate-200 rounded w-1/2" />
            <div className="space-y-2">
              <div className="h-4 bg-slate-100 rounded w-full" />
              <div className="h-4 bg-slate-100 rounded w-5/6" />
            </div>
          </div>
        ))}
      </div>
    );
  }

  // ③ スピナー型（ボタンの内部や、シンプルに待ち時間を表す用）
  return (
    <div className="flex items-center justify-center p-8 w-full">
      <div className="h-8 w-8 border-4 border-slate-200 border-t-blue-600 rounded-full animate-spin" />
    </div>
  );
};