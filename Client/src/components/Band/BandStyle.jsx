import { styled } from "styled-components";
export const SearchSection = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  @media screen and (max-width: 768px) {
    flex-wrap: wrap;
  }
`;

export const SearchForm = styled.form`
  display: flex;
  align-items: center;
  justify-content: center;
`;
export const SearchSchoolInput = styled.input`
  border: 1px solid var(--brand-color);
  margin-left: 10px;
  padding: 5px;
  width: 100px;
  margin-top: 7px;
  text-align: center;
  border-radius: 5px;
`;

export const SearchInput = styled.input`
  border: 1px solid var(--brand-color);
  margin-left: 20px;
  margin-top: 5px;
  padding: 5px;
  width: 50px;
  text-align: center;
  border-radius: 5px;
`;

export const SelectBox = styled.select`
  border: 1px solid var(--brand-color);
  margin-top: 5px;
  margin-left: 1px;
  padding: 5px;
  border-radius: 5px;
`;

export const Table = styled.table`
  width: 100%;
  justify-content: center;
  align-items: center;
  border-collapse: collapse;
`;

export const TableHeader = styled.th`
  border: 1px solid var(--brand-sub-color);
  padding: 8px;
  text-align: center;
  color: #fff;
  background-color: var(--brand-color);
`;

export const TableRow = styled.tr`
  &:nth-child(even) {
    background-color: #f2f2f2;
  }
`;

export const TableCell = styled.td`
  border: 1px solid var(--brand-sub-color);
  height: 62px;
  padding: 8px;
  text-align: center;
`;
