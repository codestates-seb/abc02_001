import { useState } from "react";
import { styled, css } from "styled-components";
import Button from "../Common/Button.jsx";

const mobileStyle = css`
  @media (max-width: 768px) {
    width: 100%;
    box-sizing: border-box;
    padding: 10px;
  }
`;

// 스타일 컴포넌트 //
const MsgTitle = styled.h2`
  font-weight: 700;
  color: var(--brand-color);
  margin-left: 10px;
`;
const MsgTextarea = styled.textarea`
  font-size: 0.7rem;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 369px;
  height: 161px;
  margin-bottom: 20px;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid var(--box-gray);
  overflow: auto;
  white-space: normal;
  resize: none;
  ${mobileStyle}
`;
const ModalContainer = styled.div`
  background-color: white;
  border-radius: 8px;
  border: 1px solid var(--box-gray);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 400px;
  ${mobileStyle}
`;

const MsgSection = styled.section`
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const MsgDataList = styled.dl`
  position: relative;
  display: grid;
  grid-template-columns: 1fr 3fr;
  gap: 10px;
  margin-bottom: 3px;
  padding: 3px;
  border-bottom: 1px solid var(--font-lightgray);
  font-size: 0.8rem;

  dt {
    font-weight: bold;
  }
  @media (max-width: 768px) {
    grid-template-columns: 1.5fr 2fr;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  text-align: center;
  gap: 10px;
`;

// 컴포넌트//
const MessageModal = ({ isOpen, onClose, onSend }) => {
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setMessage(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSend(message);
    setMessage("");
    onClose();
  };

  //if (!isOpen) return null;
  return (
    <>
      <ModalContainer>
        <MsgTitle>쪽지</MsgTitle>
        <section>
          <MsgDataList>
            <dt>오늘 보낸 쪽지</dt>
            <dd>0/50</dd>
            <dt>받는 친구</dt>
            <dd>생크림맛우유</dd>
          </MsgDataList>
        </section>
        <MsgSection>
          <form onSubmit={handleSubmit}>
            <MsgTextarea
              placeholder="보낼 말을 적어주세요!"
              value={message}
              onChange={handleChange}
            />
            <ButtonContainer>
              <Button size="sm" type="submit">
                보내기
              </Button>
              <Button size="sm" variant="lightbtn" onClick={onClose}>
                닫기
              </Button>
            </ButtonContainer>
          </form>
        </MsgSection>
      </ModalContainer>
    </>
  );
};

export default MessageModal;
