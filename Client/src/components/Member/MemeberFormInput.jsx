import { useId } from "react";
import { styled } from "styled-components";
import Button from "../../components/Common/Button.jsx";
import InputDescription from "./InputDescription.jsx";

const TextInputLabel = styled.label`
  color: var(--brand-color);
  font-size: 1.1rem;
  font-weight: 500;
  display: inline-block;
`;

const TextInput = styled.input`
  background-color: var(--box-gray);
  width: 100%;
  height: 3rem;
  border-radius: 6px;
  padding: 1rem;

  &::placeholder {
    color: var(--font-gray);
  }
`;

const Text = styled.span`
  color: var(--font-black);
`;

function MemberFormInput({
  type = "text",
  name,
  label,
  defaultValue,
  placeholder,
  description,
  duplicationCheck,
  onChange,
  emailText,
  chekckEmailDupe,
  checkNicknameDupe,
}) {
  const id = useId();

  return (
    <>
      <TextInputLabel htmlFor={id}>{label}</TextInputLabel>
      {description && <InputDescription>{description}</InputDescription>}
      <div
        style={{
          display: "flex",
          alignItems: "center",
          gap: "10px",
        }}
      >
        {emailText ? (
          <Text>{emailText}</Text>
        ) : (
          <TextInput
            type={type}
            name={name}
            id={id}
            defaultValue={defaultValue}
            placeholder={placeholder}
            autoComplete={type === "password" ? "off" : null}
            onChange={onChange}
          />
        )}
        {duplicationCheck && (
          <Button
            type="submit"
            size="sm"
            onClick={name === "email" ? chekckEmailDupe : checkNicknameDupe}
          >
            중복검사
          </Button>
        )}
      </div>
    </>
  );
}

export default MemberFormInput;
