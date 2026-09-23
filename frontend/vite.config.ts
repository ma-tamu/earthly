import react from '@vitejs/plugin-react'
import {defineConfig} from 'vite'
import tailwindcss from '@tailwindcss/vite'
import * as dns from "node:dns";

dns.setDefaultResultOrder('ipv4first')

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        react(),
        tailwindcss(),
    ],
});
