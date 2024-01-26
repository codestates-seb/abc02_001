import { SelectBox } from "./BandStyle.jsx";

const SchoolTypeSelect = ({ schoolType, setSchoolType }) => (
  <SelectBox value={schoolType} onChange={(e) => setSchoolType(e.target.value)}>
    <option value="초등학교"> 초등학교 </option>
    <option value="중학교">중학교</option>
    <option value="고등학교">고등학교</option>
  </SelectBox>
);

export default SchoolTypeSelect;
