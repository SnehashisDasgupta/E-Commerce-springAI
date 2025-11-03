import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import { AppProvider } from "./Context/Context.jsx";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'react-toastify/dist/ReactToastify.css';
import "./index.css"; // <- make sure this path matches your CSS file

/* Ensure body has saved theme BEFORE React mounts to avoid flash */
const savedTheme = localStorage.getItem("theme") || "light-theme";
document.body.className = savedTheme;


ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <AppProvider>
      <App />
    </AppProvider>
  </React.StrictMode>
);