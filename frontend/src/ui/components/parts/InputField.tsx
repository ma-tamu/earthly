import {type FC, type InputHTMLAttributes, type ReactNode} from 'react';

type InputFieldProps = InputHTMLAttributes<HTMLInputElement> & {
  label: string;
  error?: string;
  icon?: ReactNode;
};

export const InputField: FC<InputFieldProps> = ({label, error, icon, id, ...props}) => {
  return (
    <div>
      <label htmlFor={id} className="block text-xs font-semibold uppercase tracking-wider text-slate-500 mb-2">
        {label}
      </label>
      <div className="relative">
        {icon &&
            <span
                className={`absolute inset-y-0 left-0 flex items-center pl-3.5 ${error ? 'text-red-400' : 'text-slate-400'}`}>
            {icon}
            </span>
        }
        <input
          id={id}
          className={`w-full pr-4 py-3 bg-slate-50/50 border text-sm rounded-xl focus:outline-none placeholder-slate-400 transition-all
            ${icon ? 'pl-11' : 'pl-4'} /* アイコンの有無で左側の余白（Padding Left）を動的に切り替え */
            ${error
            ? 'border-red-400 focus:ring-4 focus:ring-red-500/10'
            : 'border-slate-200 focus:ring-4 focus:ring-blue-500/10'
          }`}
          {...props}
        />
      </div>
      {error && (
        <p className="mt-1.5 text-xs text-red-500 flex items-center font-medium">
          <svg className="w-3.5 h-3.5 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor"
               strokeWidth={2.5}>
            <path strokeLinecap="round" strokeLinejoin="round"
                  d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
          </svg>
          {error}
        </p>
      )}
    </div>
  );
};