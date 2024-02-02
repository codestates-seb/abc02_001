import { toast } from "react-toastify";

//  react-toastify 라이브러리로 toast를 띄우는 함수
// type 종류 : info, success, warning, error, default
const getNofity = (type = "info", message = "메세지를 입력해주세요.") =>
  toast[type](message, {
    position: "top-center",
    autoClose: 5000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "colored",
  });

export default getNofity;
