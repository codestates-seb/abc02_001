import Header from "./Header.jsx";
import Navigation from "./Navigation.jsx";
import Main from "./Main.jsx";
import Footer from "./Footer.jsx";
import { Outlet } from "react-router-dom";

function PrivateLayout() {
  return (
    <>
      <Header>
        <Navigation />
      </Header>
      <Main>
        <Outlet />
      </Main>
      <Footer />
    </>
  );
}

export default PrivateLayout;
