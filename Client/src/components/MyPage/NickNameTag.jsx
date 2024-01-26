import { useState } from "react";
import { styled } from "styled-components";
import SendNoteButton from "../Common/SendNoteButton.jsx";

const NickName = styled.dd`
  background-color: ${({ $styleCode }) => $styleCode || "#f4eeee"};
  border-radius: ${({ $borderRadius }) => $borderRadius || "8px"};
  color: var(--font-gray);
  font-size: ${({ $fontSize }) => $fontSize || "80%"};
  width: ${({ $width }) => $width || "4.7rem"};
  height: ${({ $height }) => $height || "2rem"};
  flex-shrink: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  cursor: pointer;

  & span {
    color: var(--font-gray);
  }
`;

function NickNameTag({ children, $styleCode }) {
  const [noteOpen, setNoteOpen] = useState(false);

  const handleNote = () => {
    setNoteOpen(!noteOpen);
  };

  return (
    <NickName $styleCode={$styleCode} onClick={handleNote}>
      {children}
      {noteOpen && (
        <SendNoteButton $positionRight="-60%" $positionBottom="-30%">
          쪽지 보내기
        </SendNoteButton>
      )}
    </NickName>
  );
}

export default NickNameTag;
