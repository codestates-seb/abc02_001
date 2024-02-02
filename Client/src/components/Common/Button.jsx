import { styled, css } from "styled-components";

//크기 CSS
const SIZES = {
  sm: css`
    --button-font-size: 0.8rem;
    --button-width: 5rem;
    --button-height: 2.5rem;
    --button-weight: 400;
  `,
  md: css`
    --button-font-size: 1rem;
    --button-width: 10.625rem;
  `,
  lg: css`
    --button-font-size: 1.2rem;
    --button-width: 14rem;
  `,
};

//변형 CSS
const VARIANTS = {
  greybtn: css`
    --button-bg-color: #888888;
    --button-hover-bg-color: #d9d9d9;
  `,
  subbtn: css`
    --button-bg-color: #9bbd8a;
    --button-hover-bg-color: #4f8235;
  `,
  lightbtn: css`
    --button-bg-color: var(--box-gray);
    --button-hover-bg-color: var(--font-lightgray);
    --button-color: #000;
  `,
};

//메인 CSS
const StyledButton = styled.button`
  ${(p) => p.$sizeStyle};
  ${(p) => p.$variantStyle};

  margin: ${({ $margin }) => $margin};
  cursor: pointer;
  width: ${({ $width }) => $width || "var(--button-width, 5rem)"};
  height: ${({ $height }) => $height || "var(--button-height, 3rem)"};
  font-size: var(--button-font-size, 1rem);
  font-weight: var(--button-weight, 500);
  box-shadow:
    0 10px 15px -3px rgb(0 0 0 / 0.1),
    0 4px 6px -4px rgb(0 0 0 / 0.1);
  border-radius: 8px;
  color: var(--button-color, #ffffff);
  background: var(--button-bg-color, var(--brand-color));

  &:active,
  &:hover {
    background: var(--button-hover-bg-color, var(--brand-sub-color));
  }
`;

//버튼 부분
function Button({
  onClick,
  type,
  size,
  variant,
  children,
  margin,
  width,
  height,
}) {
  const sizeStyle = SIZES[size];
  const variantStyle = VARIANTS[variant];

  return (
    <StyledButton
      onClick={onClick}
      type={type}
      $sizeStyle={sizeStyle}
      $variantStyle={variantStyle}
      $margin={margin}
      $width={width}
      $height={height}
    >
      {children}
    </StyledButton>
  );
}

export default Button;
