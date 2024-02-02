import PageTitle from "../../components/Common/PageTitle.jsx";
import PageDescription from "../../components/Common/PageDescription.jsx";
import MessageBox from "../../components/Note/NoteMessageList.jsx";
import MessageModal from "../../components/Note/NoteMessageModal.jsx";
function Note() {
  return (
    <>
      <div style={{ textAlign: "center" }}>
        <PageTitle>쪽지함</PageTitle>
        <PageDescription>주고 받은 쪽지를 모아뒀어요!</PageDescription>
      </div>

      <MessageBox />
      <MessageModal />
    </>
  );
}

export default Note;
