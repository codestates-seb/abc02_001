import Button from "../Common/Button.jsx";

//개설요청 버튼///
export function BandRequestButton({ onClick }) {
  return (
    <Button size="md" onClick={onClick}>
      개설요청
    </Button>
  );
}
//가입하기 버튼//
export function BandJoinButton({ onClick }) {
  return (
    <Button size="md" onClick={onClick}>
      가입하기
    </Button>
  );
}

//비밀번호 입력창//

export function BandPasswordInput({ value, onChange, onSubmit }) {
  return (
    <div>
      <input
        type="password"
        value={value}
        onChange={onChange}
        maxLength={4}
        placeholder="비밀번호 4자리 입력"
      />
      <Button size="sm" onClick={onSubmit}>
        등록
      </Button>
    </div>
  );
}
