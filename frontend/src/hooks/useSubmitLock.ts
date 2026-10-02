import {useState} from "react";

export function useSubmitLock<T extends (...args: any[]) => Promise<any>>(asyncFn: T) {
  const [isPending, setIsPending] = useState(false);

  const execute = async (...args: Parameters<T>): Promise<ReturnType<T> | undefined> => {

    if (isPending) {
      // 💡 ロック中は連打されても無視して即終了
      return;
    }

    setIsPending(true);
    try {
      return await asyncFn(...args);
    } finally {
      setIsPending(false);
    }
  };

  return {isPending, execute};
}