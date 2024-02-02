import { styled } from "styled-components";

const MemberErrorText = styled.span`
  color: ${({ $isError }) =>
    $isError ? `var(--font-red)` : `var(--font-gray)`};
  font-size: 0.75rem;
`;

export default MemberErrorText;
