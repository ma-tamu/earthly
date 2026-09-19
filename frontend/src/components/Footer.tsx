import React from 'react';

export const Footer: React.FC = () => {
    return (
        <footer className="border-t border-slate-100 py-6 text-center text-xs font-medium text-slate-400 tracking-wider uppercase">
            &copy; {new Date().getFullYear()} NexusCore Inc. All rights reserved.
        </footer>
    );
};