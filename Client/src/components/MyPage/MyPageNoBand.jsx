import { Link } from "react-router-dom";
import { styled } from "styled-components";

const NoBand = styled.p`
  width: 100%;
  padding-top: 3rem;
  background-color: "aliceblue";
  color: var(--font-gray);
  text-align: center;

  & a {
    display: inline-block;
    color: var(--brand-color);
    text-decoration: underline;
  }
`;

function MyPageNoBand() {
  return (
    <NoBand>
      가입한 반이 없습니다. <br />
      친구 추천을 받으시려면 <br />
      <Link to="/band">반에 가입</Link>해 주세요.
    </NoBand>
  );
}

export default MyPageNoBand;
