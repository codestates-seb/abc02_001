import { SearchForm, SearchSchoolInput } from "./BandStyle.jsx";

const SchoolSearchForm = ({ schoolInput, setSchoolInput }) => (
  <SearchForm>
    <label htmlFor="school">학교명 </label>
    <SearchSchoolInput
      type="search"
      id="school"
      value={schoolInput}
      onChange={(e) => setSchoolInput(e.target.value)}
    />
  </SearchForm>
);

export default SchoolSearchForm;
