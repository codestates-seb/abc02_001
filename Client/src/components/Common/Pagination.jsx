import { styled } from "styled-components";
import Button from "./Button.jsx";

const PaginationWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
`;

const PageNumber = styled.span`
  font-size: 16px;
  margin: 0 5px;
  font-weight: ${({ isActive }) => (isActive ? "bold" : "normal")};
`;

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
  const handlePrevious = () => {
    if (currentPage > 1) {
      onPageChange(currentPage - 1);
    }
  };

  const handleNext = () => {
    if (currentPage < totalPages) {
      onPageChange(currentPage + 1);
    }
  };
  const renderPageNumbers = () => {
    const numbers = [1];
    for (let i = 1; i <= totalPages; i++) {
      numbers.push(
        <PageNumber
          key={i}
          isActive={i === currentPage}
          onClick={() => onPageChange(i)}
        >
          {i}
        </PageNumber>,
      );
    }
    return numbers;
  };

  return (
    <PaginationWrapper>
      <Button size="sm" width="2rem" margin="10px" onClick={handlePrevious}>
        이전
      </Button>
      {renderPageNumbers()}
      <Button size="sm" width="2rem" margin="10px" onClick={handleNext}>
        다음
      </Button>
    </PaginationWrapper>
  );
};
export default Pagination;
