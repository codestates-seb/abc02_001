//import { useState, useEffect } from "react";
import { styled } from "styled-components";
import { ReactComponent as MsgIcon } from "../../assets/message.svg";
import { ReactComponent as OpenMsgIcon } from "../../assets/openMsg.svg";

// MessageList 스타일 //
const MessageListContainer = styled.section`
  border: 1px solid var(--box-gray);
  padding: 0.5rem;
  width: 100%;
`;

const MessageItem = styled.ul`
  background-color: #f7f7f7;
  padding: 10px;
  margin-bottom: 4px;
  border-bottom: 1px solid var(--font-lightgray);
  display: flex;
  flex-direction: row;
`;
const MsgNameStyle = styled.li`
  font-weight: 700;
  font-size: 0.8rem;
`;

const MsgStyle = styled.li`
  font-size: 0.8rem;
  padding-left: 1rem;
`;

const MsgDateStyle = styled.li`
  font-size: 0.6rem;
  padding-left: 10px;
  opacity: 0.5;
  color: var(--font-gray);
  text-align: end;
`;

//MessageBox 스타일//
const MessageLayout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 5px;
  border: 2px solid var(--brand-color);
  margin-top: 10px;
`;

const MessageSection = styled.section`
  margin-bottom: 20px;
  width: 100%;
`;
const MessageSelect = styled.select`
  padding: 5px;
  text-align: center;
  border-radius: 5px;
  border: 1px solid var(--brand-color);
  margin-bottom: 5px;
  width: 20%;
  box-sizing: border-box;

  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

//아이콘 컴포넌트 //
const MessageRead = ({ unread }) => {
  return unread ? <MsgIcon /> : <OpenMsgIcon />;
};

// 쪽지목록 //
const MessageList = ({ type }) => {
  //   const [messages, setMessages] = useState([]);

  //   useEffect(() => {
  //     // 서버에서 쪽지 데이터를 불러옵니다.
  //     // fetchMessages(type).then(data => setMessages(data));
  //   }, [type]);

  return (
    <MessageListContainer>
      <MessageItem>
        <MessageRead />
        <MsgNameStyle>우유맛생크림</MsgNameStyle>
        <MsgStyle>쪽지 내용</MsgStyle>
        <MsgDateStyle>날짜</MsgDateStyle>
      </MessageItem>
    </MessageListContainer>
  );
};

const MessageBox = () => {
  return (
    <MessageLayout>
      <MessageSection>
        <ul>
          <li>
            <MessageSelect>
              <option value="받은쪽지함">받은쪽지함</option>
              <option value="보낸쪽지함">보낸쪽지함</option>
            </MessageSelect>
          </li>
        </ul>
      </MessageSection>
      <MessageList />
    </MessageLayout>
  );
};

export default MessageBox;
