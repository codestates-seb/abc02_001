import { RouterProvider } from "react-router-dom";
import router from "./routes";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Suspense } from "react";
import Spinner from "./components/Common/Spinner.jsx";

function App() {
  return (
    <Suspense fallback={<Spinner />}>
      <RouterProvider router={router} />
      {/* 토스트 알림 */}
      <ToastContainer />
    </Suspense>
  );
}

export default App;
