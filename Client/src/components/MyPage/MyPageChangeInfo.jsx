import { styled } from "styled-components";
import Button from "../Common/Button.jsx";
import StyleCodePalette from "./StyleCodePalette.jsx";
import ButtonStyleLink from "./ButtonStyleLink.jsx";

const Ul = styled.ul`
  display: flex;
  justify-content: center;
  gap: 1rem;
  position: relative;
`;

function MyPageChangeInfo({
  openColorPalette,
  handleColorChange,
  selectedColor,
  handleStyleCode,
}) {
  return (
    <Ul>
      <li>
        <ButtonStyleLink to="/membership">회원정보 수정</ButtonStyleLink>
      </li>
      <li>
        <form>
          <Button
            variant="subbtn"
            type="submit"
            width="7rem"
            onClick={handleColorChange}
          >
            {openColorPalette ? "변경 완료" : "색상 변경"}
          </Button>
          {openColorPalette && (
            <StyleCodePalette
              handleStyleCode={handleStyleCode}
              selectedColor={selectedColor}
            />
          )}
        </form>
      </li>
    </Ul>
  );
}

export default MyPageChangeInfo;
