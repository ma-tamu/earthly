import {createBrowserRouter} from "react-router";
import App from "../App.tsx";
import {Login} from "../pages/Login.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    Component: App,
    children:[]
  },
  {
    path: "login",
    Component: Login,
  }
]);

export default router;