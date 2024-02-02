import { styled } from "styled-components";

const Paragraph = styled.p`
  color: var(--font-gray);
  margin-top: 1rem;
  margin-bottom: 5.8rem;
  font-size: 0.9rem;

  @media screen and (min-width: 370px) {
    font-size: 1rem;
  }
`;

function NoFriends({ interest }) {
  return (
    <Paragraph>
      나와 같은 {interest === "MBTI" ? interest : "관심사"}를 가진 친구가
      없습니다.
    </Paragraph>
  );
}

export default NoFriends;
