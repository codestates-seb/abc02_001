import { Navigate } from "react-router-dom";
import pb from "../../api/pocketbase";

function LandingRoute() {
  return pb.authStore.model ? (
    <Navigate to="band" replace />
  ) : (
    <Navigate to="introduction" replace />
  );
}

export default LandingRoute;
