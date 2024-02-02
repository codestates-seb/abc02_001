import ReactModal from "react-modal";
import { ReactComponent as SpinnerImage } from "../../assets/loading-spin.svg";

function Spinner({ isOpen = true, message = "잠시만 기다려주세요." }) {
  return (
    <>
      <ReactModal
        isOpen={isOpen}
        contentLabel="잠시만 기다려주세요."
        ariaHideApp={false}
        style={{
          overlay: {
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          },
          content: {
            position: "static",
            border: "1px solid lightgray",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            width: "20rem",
            height: "20rem",
            backgroundColor: "white",
            fontSize: "1.2rem",
          },
        }}
      >
        <SpinnerImage />
        <span>{message}</span>
      </ReactModal>
    </>
  );
}

export default Spinner;
