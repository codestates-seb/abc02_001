import { styled } from "styled-components";

const Layout = styled.div`
  max-width: 440px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

function MemberLayout({ children }) {
  return <Layout>{children}</Layout>;
}

export default MemberLayout;
