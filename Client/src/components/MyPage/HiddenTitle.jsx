import { styled } from "styled-components";

const HiddenTitle = styled.h3`
  overflow: hidden;
  position: absolute !important;
  clip: rect(0, 0, 0, 0);
  clip-path: inset(50%);
  width: 1px;
  height: 1px;
  margin: -1px;
`;

export default HiddenTitle;
