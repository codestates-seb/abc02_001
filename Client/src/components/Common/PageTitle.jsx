import { styled } from "styled-components";

const PageTitle = styled.h2`
  color: var(--brand-color);
  font-size: ${({ $fontSize }) => $fontSize || "2rem"};
  font-weight: ${({ $fontWeight }) => $fontWeight || "500"};
  text-align: center;
  margin-bottom: 1rem;
`;

export default PageTitle;
