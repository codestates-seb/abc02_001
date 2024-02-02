import { styled } from "styled-components";
import { Link } from "react-router-dom";

const ButtonStyleLink = styled(Link)`
  display: block;
  width: ${({ $width }) => $width || "7rem"};
  height: 3rem;
  font-weight: 500;
  color: var(--font-white);
  background-color: ${({ $backgroundColor }) =>
    $backgroundColor || "var(--brand-color)"};
  box-shadow:
    0 10px 15px -3px rgb(0 0 0 / 0.1),
    0 4px 6px -4px rgb(0 0 0 / 0.1);
  border-radius: 8px;
  text-align: center;
  line-height: 3rem;

  &:hover {
    background-color: ${({ $backgroundColor }) =>
      $backgroundColor === "var(--brand-sub-color)"
        ? "var(--brand-color)"
        : "var(--brand-sub-color)"};
  }
`;

export default ButtonStyleLink;
