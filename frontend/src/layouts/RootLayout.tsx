import {useState} from "react";
import {Outlet} from "react-router";
import {FiChevronDown, FiZap} from "react-icons/fi";

export function RootLayout() {

  const [lang, setLang] = useState('ja');

  return (
    <div className="min-h-screen bg-white text-slate-800 flex flex-col font-sans antialiased selection:bg-blue-500 selection:text-white">
      <header className="border-b border-slate-100 px-6 py-4 flex items-center justify-between sticky top-0 bg-white/80 backdrop-blur-md z-50">
        <div className="flex items-center space-x-3">
          <div className="h-9 w-9 bg-blue-600 rounded-xl flex items-center justify-center shadow-lg shadow-blue-500/20">
            <FiZap className="w-5 h-5 text-white" />
          </div>
          <span className="text-xl font-bold tracking-tight bg-linear-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
            NexusCore
          </span>
        </div>

        <div className="relative group">
          <select
            value={lang}
            onChange={(e) => setLang(e.target.value)}
            className="appearance-none bg-slate-50 border border-slate-200 text-sm rounded-lg pl-3 pr-8 py-2 text-slate-600 cursor-pointer focus:outline-none focus:ring-2 focus:ring-blue-500/20 transition-all font-medium"
          >
            <option value="ja">日本語 (JA)</option>
            <option value="en">English (EN)</option>
          </select>
          <div className="absolute inset-y-0 right-2.5 flex items-center pointer-events-none text-slate-400">
            <FiChevronDown className="w-4 h-4" />
          </div>
        </div>
      </header>

      <main className="flex-1 flex flex-col items-center justify-center px-4 py-16">
        <Outlet />
      </main>

      <footer className="border-t border-slate-100 py-6 text-center text-xs font-medium text-slate-400 tracking-wider uppercase">
        &copy; {new Date().getFullYear()} NexusCore Inc. All rights reserved.
      </footer>
    </div>
  );
}