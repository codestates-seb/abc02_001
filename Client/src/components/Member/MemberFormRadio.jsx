import { styled } from "styled-components";
import InputDescription from "./InputDescription.jsx";

const Fieldset = styled.fieldset`
  border: none;
`;

const Legend = styled.legend`
  color: var(--brand-color);
  font-size: 1.1rem;
  font-weight: 500;
  display: inline-block;
`;

const Ul = styled.ul`
  max-width: 22.5rem;
  margin-top: 10px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 10px;
`;

const Li = styled.li`
  border: 2px solid var(--brand-color);
  border-radius: 8px;
  background-color: ${(p) => (p.$isChecked ? "var(--brand-color)" : "white ")};
  color: ${(p) => (p.$isChecked ? "white " : "var(--brand-color)")};
  font-size: 90%;
  font-weight: 500;
  position: relative;
  width: 22%;
  height: 30px;
  text-align: center;

  &:focus-within,
  &:hover {
    color: white;
    background-color: var(--brand-color);
  }
`;

const RadioLabel = styled.label`
  line-height: 180%;
`;

const RadioInput = styled.input`
  position: absolute;
  top: 20%;
  left: 40%;
  opacity: 0;
`;

const mbti = [
  { id: 1, type: "ISTJ" },
  { id: 2, type: "ISFJ" },
  { id: 3, type: "INFJ" },
  { id: 4, type: "INTJ" },
  { id: 5, type: "ISTP" },
  { id: 6, type: "ISFP" },
  { id: 7, type: "INFP" },
  { id: 8, type: "INTP" },
  { id: 9, type: "ESTP" },
  { id: 10, type: "ESFP" },
  { id: 11, type: "ENFP" },
  { id: 12, type: "ENTP" },
  { id: 13, type: "ESTJ" },
  { id: 14, type: "ESFJ" },
  { id: 15, type: "ENFJ" },
  { id: 16, type: "ENTJ" },
];

function MemberFormRadio({ isChecked, onChange }) {
  return (
    <Fieldset>
      <Legend>MBTI</Legend>
      <InputDescription>MBTI를 선택해주세요.</InputDescription>
      <Ul>
        {mbti.map((item) => {
          return (
            <Li
              key={item.id}
              onChange={onChange}
              $isChecked={isChecked === item.type}
            >
              <RadioLabel htmlFor={item.type}>{item.type}</RadioLabel>
              <RadioInput
                type="radio"
                id={item.type}
                name="mbti"
                value={item.type}
              />
            </Li>
          );
        })}
      </Ul>
    </Fieldset>
  );
}

export default MemberFormRadio;
