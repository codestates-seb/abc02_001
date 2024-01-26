import { styled } from "styled-components";
import { Link } from "react-router-dom";
import logoImage from "../assets/siteLogo.png";

const StyledHeader = styled.header`
  border-bottom: 1px solid var(--font-lightgray);
  padding: 0rem 2rem;
  height: 4rem;
  display: flex;
  justify-content: space-between;
`;

const HeaderTitle = styled.h1`
  width: 10rem;
  height: 4rem;

  .header-title-link {
    background-color: pink;
    display: block;
    width: 100%;
    height: 100%;
    background: no-repeat center/100% url(${logoImage});
  }
`;

function Header({ children }) {
  return (
    <StyledHeader>
      <HeaderTitle>
        <Link to="/" aria-label="미츄" className="header-title-link" />
      </HeaderTitle>
      {children}
    </StyledHeader>
  );
}

export default Header;
