import { Link } from "react-router-dom";
import { styled } from "styled-components";

const QuestionBlock = styled.div`
  text-align: center;
  font-size: 90%;
  margin-bottom: ${({ $marginBottom }) => $marginBottom};

  @media screen and (min-width: 768px) {
    font-size: 1rem;
  }

  & > a {
    color: #76bacf;
    text-decoration: underline;

    &:visited {
      color: var(--brand-color);
    }
  }
`;

const QuestionSpan = styled.span`
  color: var(--font-gray);
  margin-right: 5px;
`;

function MemberQuestionBlock({ question, linkText, link, marginBottom }) {
  return (
    <QuestionBlock $marginBottom={marginBottom}>
      <QuestionSpan>{question}</QuestionSpan>
      <Link to={link}>{linkText}</Link>
    </QuestionBlock>
  );
}

export default MemberQuestionBlock;
