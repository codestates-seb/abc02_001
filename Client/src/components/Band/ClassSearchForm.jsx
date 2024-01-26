import { SearchForm, SearchInput } from "./BandStyle.jsx";

const ClassSearchForm = ({ classInput, setClassInput }) => (
  <SearchForm>
    <SearchInput
      type="search"
      id="banNum"
      value={classInput}
      onChange={(e) => setClassInput(e.target.value)}
    />
    <label htmlFor="banNum"> 반 </label>
  </SearchForm>
);

export default ClassSearchForm;
