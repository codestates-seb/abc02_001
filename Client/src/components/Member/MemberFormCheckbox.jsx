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
  position: relative;
  width: 22%;
`;

const CheckboxLabel = styled.label`
  display: inline-block;
  width: 100%;
  height: 30px;
  border: 2px solid var(--brand-color);
  border-radius: 8px;
  background-color: ${(p) => (p.$isChecked ? "var(--brand-color)" : "white ")};
  color: ${(p) => (p.$isChecked ? "white " : "var(--brand-color)")};
  font-size: 90%;
  font-weight: 500;
  line-height: 190%;
  text-align: center;

  &:hover {
    color: white;
    background-color: var(--brand-color);
  }
`;

const CheckboxInput = styled.input`
  position: absolute;
  top: 20%;
  left: 40%;
  opacity: 0;
`;

const interests = [
  { id: 1, kr: "음악", en: "MUSIC" },
  { id: 2, kr: "영화", en: "MOVIE" },
  { id: 3, kr: "운동", en: "EXERCISE" },
  { id: 4, kr: "여행", en: "TRAVEL" },
  { id: 5, kr: "게임", en: "GAME" },
  { id: 6, kr: "음식", en: "FOOD" },
  { id: 7, kr: "봉사활동", en: "VOLUNTEER" },
  { id: 8, kr: "미술", en: "ART" },
  { id: 9, kr: "독서", en: "READING" },
  { id: 10, kr: "패션", en: "FASHION" },
  { id: 11, kr: "코딩", en: "CODING" },
  { id: 12, kr: "요리", en: "COOKING" },
  { id: 13, kr: "댄스", en: "DANCE" },
  { id: 14, kr: "사진", en: "PHOTO" },
  { id: 15, kr: "영상", en: "MEDIA" },
  { id: 16, kr: "공부", en: "STUDY" },
];

function MemberFormCheckbox({ checkedList, onChange }) {
  return (
    <Fieldset>
      <Legend>관심사</Legend>
      <InputDescription>관심사를 1 ~ 3개 선택해주세요.</InputDescription>
      <Ul>
        {interests.map((item) => {
          return (
            <Li key={item.id}>
              <CheckboxLabel
                htmlFor={item.en}
                $isChecked={checkedList?.includes(item.en)}
              >
                {item.kr}
                <CheckboxInput
                  type="checkbox"
                  id={item.en}
                  name="interests"
                  value={item.en}
                  onChange={onChange}
                />
              </CheckboxLabel>
            </Li>
          );
        })}
      </Ul>
    </Fieldset>
  );
}

export default MemberFormCheckbox;
