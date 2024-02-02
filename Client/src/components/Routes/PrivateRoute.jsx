import { Outlet, Navigate } from "react-router-dom";
import pb from "../../api/pocketbase";
import getNofity from "../../utils/getNotify";

function PrivateRoute() {
  return pb.authStore.model ? (
    <Outlet />
  ) : (
    <Navigate to="/login" {...getNofity("error", "로그인해 주세요.")} />
  );
}

export default PrivateRoute;
