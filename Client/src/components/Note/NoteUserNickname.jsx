import { useState } from "react";
import MessageModal from "./NoteMessageModal.jsx";

const UserNickname = ({ nickname }) => {
  const [modalOpen, setModalOpen] = useState(false);

  const handleReplyClick = () => {
    setModalOpen(true);
  };

  const handleModalClose = () => {
    setModalOpen(false);
  };

  const handleSend = (message) => {
    console.log(`Message sent to ${nickname}: ${message}`);
    // 여기서 메시지 전송 로직을 추가합니다.
  };

  return (
    <div>
      {/* <div onClick={handleReplyClick}>{nickname}</div> */}
      <MessageModal
        isOpen={modalOpen}
        onClose={handleModalClose}
        onSend={handleSend}
      />
    </div>
  );
};

export default UserNickname;
