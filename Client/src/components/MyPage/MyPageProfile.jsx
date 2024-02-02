import { styled } from "styled-components";
import HiddenTitle from "../../components/MyPage/HiddenTitle.jsx";
import NickNameTagBig from "./NickNameTagBig.jsx";

const Dl = styled.dl`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  color: var(--font-gray);
`;

function MyPageProfile({ userInfo }) {
  return (
    <Dl>
      <HiddenTitle as="dt">닉네임</HiddenTitle>
      <NickNameTagBig $styleCode={userInfo.styleCode}>
        {userInfo.nickname}
      </NickNameTagBig>
      <HiddenTitle as="dt">이메일</HiddenTitle>
      <dd>{userInfo.email}</dd>
    </Dl>
  );
}

export default MyPageProfile;
