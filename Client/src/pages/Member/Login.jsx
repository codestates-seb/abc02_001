import MemberLayout from "../../layout/MemberLayout.jsx";
import PageTitle from "../../components/Common/PageTitle.jsx";
import PageDescription from "../../components/Common/PageDescription.jsx";
import MemberForm from "../../components/Member/MemberForm.jsx";
import MemberFormBlock from "../../components/Member/MemberFormBlock.jsx";
import MemberFormInput from "../../components/Member/MemeberFormInput.jsx";
import MemberFormButtonBlock from "../../components/Member/MemberFormButtonBlock.jsx";
import Button from "../../components/Common/Button.jsx";
import MemberQuestionBlock from "../../components/Member/MemberQuestionBlock.jsx";
import Spinner from "../../components/Common/Spinner.jsx";
import { useState } from "react";
import getNofity from "../../utils/getNotify";
import pb from "../../api/pocketbase.js";
import { useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();
  const [formState, setFormState] = useState({ identity: "", password: "" });
  const [isLoading, setIsLoading] = useState(false);

  const handleInput = (e) => {
    const { name, value } = e.target;
    setFormState({ ...formState, [name]: value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      setIsLoading(true);
      await pb
        .collection("users")
        .authWithPassword(formState.identity, formState.password);
      getNofity("success", "로그인에 성공하였습니다.");
      setIsLoading(false);
      navigate("/band");
    } catch (error) {
      console.log(error);
      getNofity("error", "로그인에 실패하였습니다.");
      setIsLoading(false);
    }
  };

  return (
    <MemberLayout>
      <PageTitle>로그인</PageTitle>
      <PageDescription>
        로그인을 하시려면 <br /> 이메일과 비밀번호를 입력해주세요.
      </PageDescription>
      <MemberForm onSubmit={handleLogin}>
        {/* 이메일 */}
        <MemberFormBlock $minHeight="7rem" $marginBottom="1rem">
          <MemberFormInput
            type="email"
            name="identity"
            label="이메일"
            defaultValue={formState.identity}
            placeholder="example@email.com"
            onChange={handleInput}
          />
        </MemberFormBlock>

        {/* 비밀번호 */}
        <MemberFormBlock $minHeight="8rem" $marginBottom="4rem">
          <MemberFormInput
            type="password"
            name="password"
            label="비밀번호"
            defaultValue={formState.password}
            placeholder="비밀번호"
            duplicationCheck={false}
            onChange={handleInput}
          />
        </MemberFormBlock>

        {/* 로그인 버튼 */}
        <MemberFormButtonBlock $marginBottom="4rem">
          <Button type="submit" size="lg" onClick={handleLogin}>
            로그인
          </Button>
        </MemberFormButtonBlock>

        {/* 비밀번호 찾기 링크 */}
        <MemberQuestionBlock
          question="비밀번호를 잊어버리셨나요?"
          linkText="비밀번호 찾기"
          link="#"
        />
        {/* 회원가입 하기 링크 */}
        <MemberQuestionBlock
          question="계정이 없으신가요?"
          linkText="회원가입 하기"
          link="/join"
          marginBottom="8rem"
        />
      </MemberForm>
      <Spinner isOpen={isLoading} />
    </MemberLayout>
  );
}

export default Login;
