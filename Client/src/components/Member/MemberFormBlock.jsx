import { styled } from "styled-components";

const MemberFormBlock = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: ${({ $marginBottom }) => $marginBottom || "3rem"};
  min-height: ${({ $minHeight }) => $minHeight || "100%"};
`;

export default MemberFormBlock;
