import { styled } from "styled-components";

const Dt = styled.dt`
  font-weight: 500;
`;

function RecommendTitle({ interest }) {
  return interest === "MBTI" ? (
    <Dt>
      나와 같은 <span>{interest}</span> 친구
    </Dt>
  ) : (
    <Dt>
      <span>{interest}</span>에 관심 있는 친구
    </Dt>
  );
}

export default RecommendTitle;
