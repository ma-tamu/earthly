import './App.css'
import {useState} from "react";
import {Footer} from "./components/Footer.tsx";
import {Header} from "./components/Header.tsx";
import {Outlet} from "react-router";

function App() {

  const [lang, setLang] = useState('ja');

  return (
    <div className="min-h-screen bg-white text-slate-800 flex flex-col font-sans antialiased">
      <Header currentLang={lang} onLangChange={setLang}/>

      <main className="flex-1 flex flex-col items-center justify-center px-4 py-16">
        <Outlet/>
      </main>

      <Footer/>
    </div>
  );
}

export default App
