import { styled } from "styled-components";

const NickNameTagBig = styled.dd`
  background-color: ${({ $styleCode }) => $styleCode || "#f4eeee"};
  border-radius: 20px;
  color: var(--font-gray);
  font-size: 1.8rem;
  width: 12rem;
  height: 6rem;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export default NickNameTagBig;
