import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";

import { lazy } from "react";

const LandingRoute = lazy(() => import("./components/Routes/LandingRoute.jsx"));
const PublicRoute = lazy(() => import("./components/Routes/PublicRoute.jsx"));
const PrivateRoute = lazy(() => import("./components/Routes/PrivateRoute.jsx"));
const BandRoute = lazy(() => import("./components/Routes/BandRoute.jsx"));
const GuestBookRoute = lazy(
  () => import("./components/Routes/GuesBookRoute.jsx"),
);

const PageLayout = lazy(() => import("./layout/PageLayout.jsx"));
const PrivateLayout = lazy(() => import("./layout/PrivateLayout.jsx"));
const Introduction = lazy(
  () => import("./pages/Introduction/Introduction.jsx"),
);
const Join = lazy(() => import("./pages/Member/Join.jsx"));
const Login = lazy(() => import("./pages/Member/Login.jsx"));
// const Note = lazy(() => import("./pages/Note/Note.jsx"));
const Band = lazy(() => import("./pages/Band/Band.jsx"));
const GuestBook = lazy(() => import("./pages/GuestBook/GuestBook.jsx"));
const MyPage = lazy(() => import("./pages/MyPage/MyPage.jsx"));
const Membership = lazy(() => import("./pages/Member/Membership.jsx"));

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/" element={<LandingRoute />} />
      <Route element={<PublicRoute />}>
        <Route element={<PageLayout />}>
          <Route path="introduction" element={<Introduction />} />
          <Route path="join" element={<Join />} />
          <Route path="login" element={<Login />} />
        </Route>
      </Route>
      <Route element={<PrivateRoute />}>
        <Route element={<PrivateLayout />}>
          <Route element={<BandRoute />}>
            <Route path="band" element={<Band />} />
          </Route>
          <Route element={<GuestBookRoute />}>
            <Route path="guestbook" element={<GuestBook />} />
          </Route>
          {/* <Route path="note" element={<Note />} /> */}
          <Route path="mypage" element={<MyPage />} />
          <Route path="membership" element={<Membership />} />
        </Route>
      </Route>
    </>,
  ),
);

export default router;
