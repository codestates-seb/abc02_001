import { useState } from "react";
import MemberLayout from "../../layout/MemberLayout.jsx";
import PageTitle from "../../components/Common/PageTitle.jsx";
import MemberForm from "../../components/Member/MemberForm.jsx";
import MemberFormInput from "../../components/Member/MemeberFormInput.jsx";
import MemberFormBlock from "../../components/Member/MemberFormBlock.jsx";
import MemberErrorText from "../../components/Member/MemberErrorText.jsx";
import MemberFormRadio from "../../components/Member/MemberFormRadio.jsx";
import MemberFormCheckbox from "../../components/Member/MemberFormCheckbox.jsx";
import MemberFormButtonBlock from "../../components/Member/MemberFormButtonBlock.jsx";
import Button from "../../components/Common/Button.jsx";
import getNofity from "../../utils/getNotify.js";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function Membership() {
  const [formState, setFormState] = useState({
    email: "name@email.com",
    password: "",
    passwordConfirm: "",
    nickname: "",
    mbti: "",
    interests: ["음악", "영화", "운동"],
  });

  // 에러 메세지 상태
  const [emailError, setEmailError] = useState({
    isError: false,
    errorMessage: "이메일 형식으로 입력해주세요.",
  });
  const [passwordError, setPasswordError] = useState({
    isError: false,
    errorMessage: "영문, 숫자, 특수문자(~!@#$%^&*) 조합 8~20 자리",
  });
  const [passwordConfirmError, setPasswordConfirmError] = useState({
    isError: false,
    errorMessage: "비밀번호가 일치하지 않습니다.",
  });
  const [nicknameError, SetNicknameError] = useState({
    isError: false,
    errorMessage: "한글, 영문, 숫자 조합 2~6자리(특수문자 불가)",
  });

  // 관심사 선택
  const handleIntersts = (value) => {
    const interestSet = new Set([...formState.interests]);
    let interestArray = [];

    if (interestSet.has(value)) {
      interestSet.delete(value);
    } else if (interestSet.size >= 3) {
      getNofity("error", "관심사는 3개 까지만 선택 가능합니다.");
    } else {
      interestSet.add(value);
    }
    interestArray = [...interestSet];
    return interestArray;
  };

  // input 입력
  const handelInput = (e) => {
    const { name, value } = e.target;

    if (name === "interests") {
      const addedInterests = handleIntersts(value);
      setFormState({ ...formState, [name]: addedInterests });
    } else {
      setFormState({ ...formState, [name]: value });
    }
  };

  // 에러 핸들링
  const handleDebounceInput = () => {};
  const handleJoin = (e) => e.preventDefault();

  return (
    <MemberLayout>
      <PageTitle>회원정보 수정</PageTitle>
      <MemberForm onSubmit={handleJoin}>
        {/* 이메일 */}
        <MemberFormBlock>
          <MemberFormInput
            type="email"
            name="email"
            label="이메일"
            emailText={formState.email}
          />
        </MemberFormBlock>
        {/* 비밀번호 */}
        <MemberFormBlock $minHeight="15.7rem" $marginBottom="0rem">
          <MemberFormInput
            type="password"
            name="password"
            label="비밀번호"
            defaultValue={formState.password}
            placeholder="비밀번호"
            duplicationCheck={false}
            onChange={handelInput}
          />
          <MemberErrorText $isError={passwordError.isError}>
            {passwordError.errorMessage}
          </MemberErrorText>

          {/* 비밀번호 확인*/}
          <MemberFormInput
            name="passwordConfirm"
            label="비밀번호확인"
            defaultValue={formState.passwordConfirm}
            placeholder="비밀번호 확인"
            duplicationCheck={false}
            onChange={handelInput}
          />
          {passwordConfirmError.isError && (
            <MemberErrorText $isError={passwordConfirmError.isError}>
              {passwordConfirmError.errorMessage}
            </MemberErrorText>
          )}
        </MemberFormBlock>
        {/* 닉네임*/}
        <MemberFormBlock>
          <MemberFormInput
            name="nickname"
            label="닉네임"
            defaultValue={formState.nickname}
            placeholder="닉네임"
            duplicationCheck={true}
            onChange={handelInput}
          />
          <MemberErrorText $isError={nicknameError.isError}>
            {nicknameError.errorMessage}
          </MemberErrorText>
        </MemberFormBlock>
        {/* MBTI */}
        <MemberFormBlock>
          <MemberFormRadio isChecked={formState.mbti} onChange={handelInput} />
        </MemberFormBlock>
        {/* 관심사 */}
        <MemberFormBlock $marginBottom="8rem">
          <MemberFormCheckbox
            checkedList={formState.interests}
            onChange={handelInput}
          />
        </MemberFormBlock>

        {/* 버튼 */}
        <MemberFormButtonBlock>
          <Button type="submit" size="md">
            회원정보 수정
          </Button>
          <Button type="button" size="md" variant="greybtn">
            회원 탈퇴
          </Button>
        </MemberFormButtonBlock>
      </MemberForm>
      <ToastContainer />
    </MemberLayout>
  );
}

export default Membership;
