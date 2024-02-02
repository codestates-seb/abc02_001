import { SearchForm, SearchInput } from "./BandStyle.jsx";

const GradeSearchForm = ({ gradeInput, setGradeInput }) => (
  <SearchForm>
    <SearchInput
      type="search"
      id="grade"
      value={gradeInput}
      onChange={(e) => setGradeInput(e.target.value)}
    />
    <label htmlFor="grade"> 학년 </label>
  </SearchForm>
);

export default GradeSearchForm;
