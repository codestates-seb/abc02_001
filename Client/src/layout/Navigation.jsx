import { styled } from "styled-components";
import { useState } from "react";
import { useLocation, Link, useNavigate } from "react-router-dom";
import hambergerIcon from "../assets/nav-hamberger.svg";
import xmarkIcon from "../assets/x-mark.svg";
import pb from "../api/pocketbase";
import getNofity from "../utils/getNotify";

const Nav = styled.nav`
  @media screen and (max-width: 767px) {
    position: relative;
    align-self: center;
  }
`;

const NavButton = styled.button`
  display: none;

  /* 모바일 버전 */
  @media screen and (max-width: 767px) {
    display: block;
    width: 1.6rem;
    height: 2rem;
    background: no-repeat center/80%;
    background-image: url(${hambergerIcon});

    &.openNav {
      background-image: url(${xmarkIcon});
    }
  }
`;

const NavList = styled.ul`
  height: 100%;
  display: flex;
  align-items: center;
  gap: 1.5rem;
  background-color: white;
  z-index: 1;

  /* 모바일 버전 */
  @media screen and (max-width: 767px) {
    display: none;
    width: 13rem;
    height: auto;
    border: 1px solid var(--font-lightgray);
    padding: 1rem;
    font-size: 1.3rem;

    /* 모바일 버전 위치 */
    position: absolute;
    right: 0rem;
    top: 3rem;

    &.openNav {
      display: block;
    }
  }
`;

const navItems = [
  { id: 1, link: "/band", menuName: "우리 반" },
  // { id: 2, link: "/note", menuName: "쪽지함" },
  { id: 3, link: "/mypage", menuName: "마이페이지" },
];

const NavItem = styled.li`
  color: var(--font-gray);
  cursor: pointer;

  /* 마우스 hover 또는 선택 시 */
  &:hover,
  &:focus-within,
  &.seleted-nav {
    color: var(--brand-color);
    text-decoration: underline;
    font-weight: 500;
  }
`;

function Navigation() {
  const [openNav, setOpenNav] = useState(false);
  const { pathname } = useLocation();
  const navigation = useNavigate();

  const openNavList = () => setOpenNav(!openNav);
  const handleLogout = () => {
    pb.authStore.clear();
    getNofity("success", "로그아웃되었습니다.");
    navigation("introduction");
  };

  return (
    <Nav>
      <NavButton
        onClick={openNavList}
        className={openNav && "openNav"}
        aria-label="네비게이션"
      />
      <NavList className={openNav && "openNav"}>
        {navItems.map((item) => (
          <NavItem
            key={item.id}
            className={item.link === pathname ? "seleted-nav" : ""}
            onClick={openNavList}
          >
            <Link to={item.link}>{item.menuName}</Link>
          </NavItem>
        ))}
        <NavItem onClick={handleLogout}>로그아웃</NavItem>
      </NavList>
    </Nav>
  );
}

export default Navigation;
