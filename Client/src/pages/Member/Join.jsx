// 마크업 컴포넌트
import MemberLayout from "../../layout/MemberLayout.jsx";
import PageTitle from "../../components/Common/PageTitle.jsx";
import MemberForm from "../../components/Member/MemberForm.jsx";
import MemberFormInput from "../../components/Member/MemeberFormInput.jsx";
import MemberFormBlock from "../../components/Member/MemberFormBlock.jsx";
import MemberErrorText from "../../components/Member/MemberErrorText.jsx";
import PageDescription from "../../components/Common/PageDescription.jsx";
import MemberFormRadio from "../../components/Member/MemberFormRadio.jsx";
import MemberFormCheckbox from "../../components/Member/MemberFormCheckbox.jsx";
import MemberFormButtonBlock from "../../components/Member/MemberFormButtonBlock.jsx";
import Button from "../../components/Common/Button.jsx";
import Spinner from "../../components/Common/Spinner.jsx";
// 훅, 유틸
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  EmailReg,
  PasswordReg,
  NicknameReg,
} from "../../utils/inputValidation.js";

// 라이브러리
import { Mobile } from "../../layout/MediaQuery.jsx";
import getNofity from "../../utils/getNotify.js";
import pb from "../../api/pocketbase.js";

function Join() {
  const navigate = useNavigate();

  // 전제 입력값 상태
  const [formState, setFormState] = useState({
    email: "",
    emailVisibility: true,
    password: "",
    passwordConfirm: "",
    nickname: "",
    mbti: "",
    interests: [],
    styleCode: "#f4eeee",
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

  // 로딩 스피너 상태
  const [isLoading, setIsLoading] = useState(false);

  // 이메일 입력
  const handleEmail = (e) => {
    const { value } = e.target;
    const deleteSpaceEmail = value.trim();

    if (!EmailReg(deleteSpaceEmail) || deleteSpaceEmail === "") {
      setEmailError({
        ...emailError,
        isError: true,
        errorMessage: "이메일 형식으로 입력해주세요.",
      });
    } else {
      setEmailError({
        ...emailError,
        isError: false,
        errorMessage: "이메일 형식으로 입력해주세요.",
      });
      setFormState({ ...formState, email: deleteSpaceEmail });
    }
  };

  // 이메일 중복 검사
  const chekckEmailDupe = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const response = await pb.collection("users").getList(1, 1, {
        filter: `(email='${formState.email}')`,
      });
      if (response.items.length > 0) {
        setEmailError({
          ...emailError,
          isError: true,
          errorMessage: "이미 가입된 이메일입니다.",
        });
        getNofity("error", "이미 가입된 이메일입니다.");
      } else {
        setEmailError({
          ...emailError,
          isError: false,
          errorMessage: "사용 가능한 이메일입니다.",
        });
        getNofity("success", "사용 가능한 이메일입니다.");
      }
      setIsLoading(false);
    } catch (error) {
      console.log(error);
      setIsLoading(false);
    }
  };

  // 비밀번호 입력
  const handlePassword = (e) => {
    const { value } = e.target;
    const deletedSpacePassword = value.trim();
    if (!PasswordReg(deletedSpacePassword)) {
      setPasswordError({ ...passwordError, isError: true });
      return;
    }
    setPasswordError({ ...passwordError, isError: false });
    setFormState({ ...formState, password: deletedSpacePassword });
  };

  // 비밀번호확인 입력
  const handlePasswordConfirm = (e) => {
    const { value } = e.target;
    const deletedSpaceValue = value.trim();
    if (formState.password !== deletedSpaceValue) {
      setPasswordConfirmError({ ...passwordConfirmError, isError: true });
      return;
    }
    setPasswordConfirmError({ ...passwordConfirmError, isError: false });
    setFormState({
      ...formState,
      passwordConfirm: deletedSpaceValue,
    });
  };

  // 닉네임 입력
  const handleNickname = (e) => {
    const { value } = e.target;
    const deletedSpaceValue = value.trim();
    if (!NicknameReg(deletedSpaceValue)) {
      SetNicknameError({ ...nicknameError, isError: true });
      return;
    }
    SetNicknameError({ ...nicknameError, isError: false });
    setFormState({ ...formState, nickname: deletedSpaceValue });
  };

  // 닉네임 중복 검사
  const checkNicknameDupe = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const response = await pb.collection("users").getList(1, 1, {
        filter: `(nickname='${formState.nickname}')`,
      });
      if (response.items.length > 0) {
        SetNicknameError({
          ...nicknameError,
          isError: true,
          errorMessage: "이미 가입된 닉네임입니다.",
        });
        getNofity("error", "이미 가입된 닉네임입니다.");
      } else {
        SetNicknameError({
          ...nicknameError,
          isError: false,
          errorMessage: "사용 가능한 닉네임입니다.",
        });
        getNofity("success", "사용 가능한 닉네임입니다.");
      }
      setIsLoading(false);
    } catch (error) {
      console.log(error);
      setIsLoading(false);
    }
  };

  // MBTI 선택
  const handleMbti = (e) => {
    const { name, value } = e.target;
    setFormState({ ...formState, [name]: value });
  };

  // 관심사 선택
  const handleIntersts = (e) => {
    const { value } = e.target;
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
    setFormState({ ...formState, interests: interestArray });
  };

  const handleJoin = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    await pb
      .collection("users")
      .create(formState)
      .then(() => {
        getNofity("success", "회원가입이 완료되었습니다.");
        navigate("/login");
      })
      .catch((error) => {
        console.log(error);
      })
      .finally(() => setIsLoading(false));
  };

  return (
    <MemberLayout>
      <PageTitle>회원가입</PageTitle>
      <PageDescription>
        닉네임과 이메일로 미츄를 시작하세요! <br />
        MBTI와 관심사를 설정하시면
        <Mobile>
          <br />
        </Mobile>
        친구를 추천해 드립니다.
      </PageDescription>
      <MemberForm onSubmit={handleJoin}>
        {/* 이메일 */}
        <MemberFormBlock>
          <MemberFormInput
            type="email"
            name="email"
            label="이메일"
            defaultValue={formState.email}
            placeholder="example@email.com"
            duplicationCheck={true}
            onChange={handleEmail}
            chekckEmailDupe={chekckEmailDupe}
          />
          <MemberErrorText $isError={emailError.isError}>
            {emailError.errorMessage}
          </MemberErrorText>
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
            onChange={handlePassword}
          />
          <MemberErrorText $isError={passwordError.isError}>
            {passwordError.errorMessage}
          </MemberErrorText>

          {/* 비밀번호 확인*/}
          <MemberFormInput
            type="password"
            name="passwordConfirm"
            label="비밀번호확인"
            defaultValue={formState.passwordConfirm}
            placeholder="비밀번호 확인"
            duplicationCheck={false}
            onChange={handlePasswordConfirm}
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
            onChange={handleNickname}
            checkNicknameDupe={checkNicknameDupe}
          />
          <MemberErrorText $isError={nicknameError.isError}>
            {nicknameError.errorMessage}
          </MemberErrorText>
        </MemberFormBlock>
        {/* MBTI */}
        <MemberFormBlock>
          <MemberFormRadio isChecked={formState.mbti} onChange={handleMbti} />
        </MemberFormBlock>
        {/* 관심사 */}
        <MemberFormBlock>
          <MemberFormCheckbox
            checkedList={formState.interests}
            onChange={handleIntersts}
          />
        </MemberFormBlock>
        {/* 회원가입 버튼 */}
        <MemberFormButtonBlock name="회원가입" onClick={handleJoin}>
          <Button type="submit" size="lg" onClick={handleJoin}>
            회원가입
          </Button>
        </MemberFormButtonBlock>
      </MemberForm>
      {/* 로딩 스피너 */}
      <Spinner isOpen={isLoading} />
    </MemberLayout>
  );
}

export default Join;
