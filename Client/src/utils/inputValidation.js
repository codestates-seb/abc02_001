// 이메일 유효성 검사
export function EmailReg(email) {
  const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
  return emailRegex.test(String(email));
}

// 비밀번호 유효성 검사
export function PasswordReg(text) {
  const passwordReg =
    /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*])[a-zA-Z0-9~!@#$%^&*]{8,20}$/;
  return passwordReg.test(String(text));
}

// 닉네임 유효성 검사
export function NicknameReg(text) {
  const nicknameReg = /^[가-힣a-zA-Z0-9]{2,6}$/;
  return nicknameReg.test(String(text));
}
