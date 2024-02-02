import { styled } from "styled-components";
import { ReactComponent as GithubIcon } from "../assets/github.svg";

const StyledFooter = styled.footer`
  background-color: var(--brand-color);
  color: var(--font-white);
  height: 8rem;

  /* footer 하단 고정 */
  position: relative;
  transform: translateY(-100%);

  padding: 2rem 4rem 2rem 4rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 1.2rem;

  .copyright {
    font-weight: 200;
  }
`;

const SytledLink = styled.a`
  position: relative;

  .github-icon {
    position: relative;
    top: 0.1rem;
    margin-right: 0.2rem;
  }
`;

function Footer() {
  return (
    <StyledFooter>
      <p className="copyright">&copy; 아띠 2024</p>
      <SytledLink href="https://github.com/codestates-seb/abc02_001">
        <GithubIcon className="github-icon" fill="white" />
        GitHub
      </SytledLink>
    </StyledFooter>
  );
}

export default Footer;
