import { styled } from "styled-components";

const StyleCodeUl = styled.ul`
  display: flex;
  justify-content: center;
  gap: 3%;
  width: 100%;
  position: absolute;
  bottom: -160%;
  right: 0%;
  border: 1px solid var(--font-lightgray);
  border-radius: 8px;
  box-shadow:
    0 10px 15px -3px rgb(0 0 0 / 0.1),
    0 4px 6px -4px rgb(0 0 0 / 0.1);
`;

const StyleCodeLi = styled.li`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0.5rem 0;
`;

const StyleCodeInput = styled.input`
  appearance: none;
  width: 1.5rem;
  height: 1.5rem;
  border-radius: 50%;
  border: 1px solid var(--font-lightgray);
  background-color: white;
  position: relative;
  transition: all 0.2s ease-in-out;

  &::after {
    display: block;
    content: "";
    width: 1.1rem;
    height: 1.1rem;
    border-radius: 50%;
    border: 1px solid var(--font-lightgray);
    background-color: ${({ value }) => value};
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    transition: all 0.2s ease-in-out;
  }

  &:hover,
  &:focus {
    cursor: pointer;
  }

  &:hover,
  &:checked {
    outline: none;
    border-width: 3px;
  }

  &:hover::after,
  &:checked::after {
    border-width: 3px;
    width: 0.9rem;
    height: 0.9rem;
  }
`;

const StyleCodeLabel = styled.label`
  color: var(--font-gray);
  font-weight: 300;
  font-size: 0.8rem;
`;

const colorList = [
  { styleCode: "#f4eeee", colorName: "갈색" },
  { styleCode: "#faebdd", colorName: "주황" },
  { styleCode: "#fbf3db", colorName: "노랑" },
  { styleCode: "#edf3ec", colorName: "초록" },
  { styleCode: "#e7f3f8", colorName: "파랑" },
  { styleCode: "#f6f3f9", colorName: "보라" },
  { styleCode: "#faf1f5", colorName: "분홍" },
  { styleCode: "#fdebec", colorName: "빨강" },
];

function StyleCodePalette({ selectedColor, handleStyleCode }) {
  return (
    <StyleCodeUl>
      {colorList.map((item) => {
        return (
          <StyleCodeLi key={item.styleCode}>
            <StyleCodeInput
              type="radio"
              id={item.styleCode}
              name={item.styleCode}
              value={item.styleCode}
              checked={selectedColor === item.styleCode}
              onChange={handleStyleCode}
            />
            <StyleCodeLabel htmlFor={item.styleCode}>
              {item.colorName}
            </StyleCodeLabel>
          </StyleCodeLi>
        );
      })}
    </StyleCodeUl>
  );
}

export default StyleCodePalette;
