import Button from "../Common/Button.jsx";

const BandSearchButton = ({ onClick }) => (
  <Button
    size="sm"
    width="3rem"
    height="2rem"
    margin="0px 0px 0px 10px"
    onClick={onClick}
  >
    검색
  </Button>
);

export default BandSearchButton;
