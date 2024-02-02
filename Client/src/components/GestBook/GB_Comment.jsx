import { styled } from "styled-components";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useState } from "react";
import SendNoteButton from "../Common/SendNoteButton.jsx";

const CommentEntry = styled.li`
  /* 방명록 항목에 대한 스타일 */
  border-bottom: 2px solid var(--box-gray);
  width: 95%;
  padding: 3px;
  margin-left: 20px;
  margin-right: 20px;
  margin-top: 3px;
  display: grid;
  grid-template-columns: auto 1fr auto auto;
  grid-template-areas: "name comment button date";

  @media screen and (max-width: 768px) {
    grid-template-areas:
      "name name date date"
      "comment comment coment button";
  }
`;

const CommentNameStyle = styled.strong`
  /* 방명록 이름에 대한 스타일 */
  font-weight: 700;
  font-size: 1rem;
  grid-area: name;
  position: relative;
`;

const CommentStyle = styled.p`
  /* 방명록 내용에 대한 스타일 */
  font-size: 1rem;
  padding-left: 1rem;
  grid-area: comment;
`;

const CommentDateStyle = styled.time`
  /* 방명록 날짜에 대한 스타일 */
  font-size: 0.6rem;
  padding-left: 10px;
  opacity: 0.5;
  color: var(--font-gray);
  text-align: right;
  grid-area: date;
`;
const ButtonStyle = styled.div`
  /* 수정 및 삭제 버튼에 대한 스타일 */
  grid-area: button;
  display: flex;
  align-items: center;
`;

const DeleteButton = styled.button`
  /* 삭제 버튼에 대한 스타일 */
  font-size: 0.6rem;
  color: var(--font-red);
  opacity: 0.5;
  margin-left: 10px;
`;

function GuestComment({
  comment,
  id,
  nickName,
  contexts,
  created,
  handleDelete,
  isWrite,
}) {
  const [sendButtonOpen, setSendButtonOpen] = useState(false);
  const handelSendButton = () => setSendButtonOpen(!sendButtonOpen);

  const parsedDate = new Date(created).toLocaleDateString("ko-kr");

  return (
    <>
      <CommentEntry key={id}>
        <CommentNameStyle onClick={handelSendButton}>
          {nickName}
          {sendButtonOpen && (
            <SendNoteButton $positionBottom="5px" $positionLeft="50px">
              쪽지 보내기
            </SendNoteButton>
          )}
        </CommentNameStyle>
        <CommentStyle> {contexts}</CommentStyle>
        {isWrite && (
          <ButtonStyle>
            <DeleteButton
              onClick={() => {
                handleDelete(comment.id);
              }}
            >
              x
            </DeleteButton>
          </ButtonStyle>
        )}
        <CommentDateStyle>{parsedDate}</CommentDateStyle>
      </CommentEntry>
      <ToastContainer />
    </>
  );
}

export default GuestComment;
