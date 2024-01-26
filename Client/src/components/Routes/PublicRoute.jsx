import { Navigate, Outlet } from "react-router-dom";
import pb from "../../api/pocketbase";

function PublicRoute() {
  return pb.authStore.model ? <Navigate to="band" /> : <Outlet />;
}

export default PublicRoute;
