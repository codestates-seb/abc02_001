import { styled } from "styled-components";

const Form = styled.form`
  width: 100%;
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
`;

function MemberForm({ children, onSubmit }) {
  return <Form onSubmit={onSubmit}>{children}</Form>;
}

export default MemberForm;
