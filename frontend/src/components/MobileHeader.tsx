import {type FC} from 'react';
import {FiMenu} from 'react-icons/fi';

type MobileHeaderProps = {
  onMenuOpen: () => void;
};

export const MobileHeader: FC<MobileHeaderProps> = ({onMenuOpen}) => {
  return (
    <header
      className="h-16 bg-white border-b border-slate-200 px-4 flex items-center justify-between sticky top-0 z-30 md:hidden">
      <div className="flex items-center space-x-3">
        <button
          onClick={onMenuOpen}
          className="p-2 text-slate-500 hover:bg-slate-50 rounded-lg"
          aria-label="メニューを開く"
        >
          <FiMenu className="w-6 h-6"/>
        </button>
        <span className="text-lg font-bold text-slate-900">Earthly</span>
      </div>
    </header>
  );
};