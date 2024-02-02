import { styled } from "styled-components";
import IntroLayout from "../../layout/IntroLayout.jsx";
import PageDescription from "../../components/Common/PageDescription.jsx";
import PageTitle from "../../components/Common/PageTitle.jsx";
import ButtonStyleLink from "../../components/MyPage/ButtonStyleLink.jsx";
import MemberFormButtonBlock from "../../components/Member/MemberFormButtonBlock.jsx";
import Slick from "../../components/Introduction/Slick.jsx";

const ImageSlcik = styled.div`
  width: 95%;
`;

function Introduction() {
  return (
    <IntroLayout>
      <PageTitle $fontSize="2.8rem" $fontWeight="600">
        Nice to meetU!
      </PageTitle>
      <ImageSlcik>
        <Slick />
      </ImageSlcik>

      <PageDescription>
        설레는 새학기, <br />
        나와 같은 관심사를 가진 친구는 누구일까? <br />
        같은 관심사를 가진 친구와 <br />
        쪽지를 주고 받으며 친해져 보세요!
      </PageDescription>
      <MemberFormButtonBlock $flexDirection="column">
        <ButtonStyleLink to="/login" $width="14rem">
          로그인
        </ButtonStyleLink>
        <ButtonStyleLink
          to="/join"
          $width="14rem"
          $backgroundColor="var(--brand-sub-color)"
        >
          회원가입
        </ButtonStyleLink>
      </MemberFormButtonBlock>
    </IntroLayout>
  );
}

export default Introduction;
