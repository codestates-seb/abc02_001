import { styled } from "styled-components";

const Main = styled.main`
  // 모바일(기본) 레이아웃
  width: 100%;
  min-height: 100%;
  padding: 3rem;
  padding-bottom: 11rem;
  margin-left: auto;
  margin-right: auto;

  // 데스크탑 레이아웃
  @media screen and (min-width: 1024px) {
    max-width: 840px;
  }
`;

export default Main;
