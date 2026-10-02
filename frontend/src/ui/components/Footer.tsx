import type {FC} from "react";

export const Footer: FC = () => {
    return (
        <footer className="border-t border-slate-100 py-6 text-center text-xs font-medium text-slate-400 tracking-wider uppercase">
            &copy; 2018 - {new Date().getFullYear()} NexusCore Inc. All rights reserved.
        </footer>
    );
};