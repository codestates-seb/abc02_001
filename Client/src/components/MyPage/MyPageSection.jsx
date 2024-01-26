import { styled } from "styled-components";
import HiddenTitle from "../../components/MyPage/HiddenTitle.jsx";

const Section = styled.section`
  text-align: center;
  height: ${({ $height }) => $height};
`;

function MyPageSection({ children, sectionTite, height }) {
  return (
    <Section $height={height}>
      <HiddenTitle>{sectionTite}</HiddenTitle>
      {children}
    </Section>
  );
}

export default MyPageSection;
