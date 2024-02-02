import { styled } from "styled-components";

const SendNoteButton = styled.button`
  position: absolute;
  left: ${({ $positionLeft }) => $positionLeft};
  right: ${({ $positionRight }) => $positionRight};
  bottom: ${({ $positionBottom }) => $positionBottom};
  display: block;
  z-index: 1;
  background-color: white;
  border: 1px solid var(--font-gray);
  padding: 4px;
  border-radius: 5px;
  cursor: pointer;
  font-weight: 400;
  font-size: 0.8rem;
  color: var(--font-gray);

  &:hover {
    color: var(--brand-color);
    text-decoration: underline;
  }
`;

export default SendNoteButton;
