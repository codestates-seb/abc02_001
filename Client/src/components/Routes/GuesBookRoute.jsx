import pb from "../../api/pocketbase";
import { Navigate, Outlet } from "react-router-dom";

function GuestBookRoute() {
  const band = pb.authStore.model.band;

  return !band ? <Navigate to="/band" /> : <Outlet />;
}

export default GuestBookRoute;
