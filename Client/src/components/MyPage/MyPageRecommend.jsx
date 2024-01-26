import { styled } from "styled-components";

const MyPageRecommend = styled.dl`
  display: flex;
  flex-direction: column;
  align-items: baseline;

  & span {
    color: var(--brand-color);
  }
`;

export default MyPageRecommend;
