type HeaderProps = {
    currentLang: string;
    onLangChange: (lang: string) => void;
};


export const Header: React.FC<HeaderProps> = ({currentLang, onLangChange}) => {
    return (
        <header className="border-b border-slate-100 px-6 py-4 flex items-center justify-between sticky top-0 bg-white/80 backdrop-blur-md z-50">
            <div className="flex items-center space-x-3">
                <div className="h-9 w-9 bg-blue-600 rounded-xl flex items-center justify-center shadow-lg shadow-blue-500/20">
                    <svg className="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2.5}>
                        <path strokeLinecap="round" strokeLinejoin="round" d="M13 10V3L4 14h7v7l9-11h-7z" />
                    </svg>
                </div>
                <span className="text-xl font-bold tracking-tight bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
          Earthly
        </span>
            </div>

            <div className="relative">
                <select
                    value={currentLang}
                    onChange={(e) => onLangChange(e.target.value)}
                    className="appearance-none bg-slate-50 border border-slate-200 text-sm rounded-lg pl-3 pr-8 py-2 text-slate-600 cursor-pointer focus:outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20 transition-all font-medium"
                >
                    <option value="ja">日本語 (JA)</option>
                    <option value="en">English (EN)</option>
                </select>
                <div className="absolute inset-y-0 right-2.5 flex items-center pointer-events-none text-slate-400">
                    <svg className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                        <path strokeLinecap="round" strokeLinejoin="round" d="M19 9l-7 7-7-7" />
                    </svg>
                </div>
            </div>
        </header>
    );
}