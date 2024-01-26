import pb from "../../api/pocketbase";
import { Navigate, Outlet } from "react-router-dom";

function BandRoute() {
  const band = pb.authStore.model.band;

  return band ? <Navigate to="/guestbook" /> : <Outlet />;
}

export default BandRoute;
