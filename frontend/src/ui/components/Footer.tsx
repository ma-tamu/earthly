import type {FC} from "react";
import {useTranslation} from "react-i18next";

export const Footer: FC = () => {
  const {t} = useTranslation();

  return (
    <footer
      className="border-t border-slate-100 py-6 text-center text-xs font-medium text-slate-400 tracking-wider uppercase">
      &copy; 2018 - {new Date().getFullYear()} {t("page.footer")}
    </footer>
  );
};