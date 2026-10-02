import type {FC} from "react";
import {useSearchParams} from "react-router";
import {FiChevronLeft, FiChevronRight} from "react-icons/fi";

type PaginationProps = {
  currentPage: number;
  totalPages: number;
  totalCount: number;
  limit: number;
};

export const Pagination: FC<PaginationProps> = ({currentPage, totalPages, totalCount, limit,}) => {

  const [searchParams, setSearchParams] = useSearchParams();

  const handlePageChange = (newPage: number) => {
    if (newPage < 1 || newPage > totalPages) return;
    const newParams = new URLSearchParams(searchParams);
    newParams.set('page', newPage.toString());
    setSearchParams(newParams);
  };


  const renderPageNumbers = () => {
    const pages = [];
    for (let i = 1; i <= totalPages; i++) {
      pages.push(
        <button
          key={i}
          onClick={() => handlePageChange(i)}
          className={`h-9 w-9 text-xs font-bold rounded-lg transition-all border
            ${currentPage === i
            ? 'bg-blue-600 border-blue-600 text-white shadow-sm'
            : 'bg-white border-slate-200 text-slate-600 hover:bg-slate-50'
          }`}
        >
          {i}
        </button>
      );
    }
    return pages;
  };

  if (totalCount === 0) return null;

  const fromItem = (currentPage - 1) * limit + 1;
  const toItem = Math.min(currentPage * limit, totalCount);

  return (
    <div className="px-6 py-4 border-t border-slate-100 flex items-center justify-between bg-slate-50/30">
      <div className="text-xs font-medium text-slate-400">
        全 <span className="text-slate-700">{totalCount}</span> 件中 {fromItem}〜{toItem} 件を表示
      </div>
      <div className="flex items-center space-x-1.5">
        <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}
                className="p-2 text-slate-500 hover:bg-white border border-transparent hover:border-slate-200 rounded-lg disabled:opacity-40 transition-all">
          <FiChevronLeft className="w-4 h-4"/>
        </button>
        {renderPageNumbers()}
        <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}
                className="p-2 text-slate-500 hover:bg-white border border-transparent hover:border-slate-200 rounded-lg disabled:opacity-40 transition-all">
          <FiChevronRight className="w-4 h-4"/>
        </button>
      </div>
    </div>
  );
}
