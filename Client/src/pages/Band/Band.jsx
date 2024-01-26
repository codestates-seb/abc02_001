import pb from "../../api/pocketbase.js";

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import PageDescription from "../../components/Common/PageDescription.jsx";
import PageTitle from "../../components/Common/PageTitle.jsx";

import getNotify from "../../utils/getNotify";
import Spinner from "../../components/Common/Spinner.jsx";

//반검색
import {
  SearchSection,
  TableCell,
  TableRow,
  TableHeader,
  Table,
} from "../../components/Band/BandStyle.jsx";
import SchoolSearchForm from "../../components/Band/SchoolSearchForm.jsx";
import GradeSearchForm from "../../components/Band/GradeSearchForm.jsx";
import ClassSearchForm from "../../components/Band/ClassSearchForm.jsx";
import SchoolTypeSelect from "../../components/Band/SchoolTypeSelect.jsx";
import SearchButton from "../../components/Band/BandSearchButton.jsx";

//반생성가입
import {
  BandRequestButton,
  BandJoinButton,
} from "../../components/Band/BandFunction.jsx";

function Band() {
  //반 검색 상태관리//
  const [schoolInput, setSchoolInput] = useState("");
  const [gradeInput, setGradeInput] = useState("");
  const [classInput, setClassInput] = useState("");
  const [schoolType, setSchoolType] = useState("초등학교");
  const [isVisible, setIsVisible] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  //반 가입 상태관리//
  const [isBand, setIsBand] = useState(false);
  const navigate = useNavigate();
  const loginUser = pb.authStore.model.id;

  const handleSearchClick = async (event) => {
    event.preventDefault();
    setIsVisible(true);
    setIsLoading(true);
    try {
      // 서버로부터 데이터를 받아온다.
      const response = await pb.collection("band").getList(1, 1, {
        filter: `(school= "${schoolInput}" &&
        schoolCode= "${schoolType}" &&
        grade= ${gradeInput} &&
        bandNumber= ${classInput})`,
      });
      if (response.items.length === 0) {
        // `isBand` 상태를 업데이트합니다.
        setIsBand(false);
        getNotify("error", "해당하는 반이 없습니다.");
        setIsLoading(false);
      } else if (response.items.length > 0) {
        setIsBand(true);
        setIsLoading(false);
      }
    } catch (error) {
      console.error("검색 오류", error);
      getNotify("error", "검색오류가 발생했습니다");
      setIsLoading(false);
    }
  };
  const data = {
    school: `${schoolInput}`,
    schoolCode: `${schoolType}`,
    grade: gradeInput,
    bandNumber: classInput,
  };
  async function handleRequestClick(event) {
    event.preventDefault();
    setIsLoading(true);
    try {
      await pb.collection("band").create(data);
      setIsBand(true);
      getNotify("success", "개설 요청이 완료되었습니다.");
      setIsLoading(false);
    } catch (error) {
      console.error("개설 요청 중 오류 발생:", error);
      getNotify("error", "개설 요청 중 오류가 발생하였습니다.");
      setIsLoading(false);
    }
  }

  async function handleJoinClick(event) {
    event.preventDefault();
    setIsLoading(true);
    try {
      const response = await pb.collection("band").getList(1, 1, {
        filter: `(school= "${schoolInput}" &&
        schoolCode= "${schoolType}" &&
        grade= ${gradeInput} &&
        bandNumber= ${classInput})`,
      });
      const bandUp = response.items[0].id;
      const updateJoin = { band: `${bandUp}` };
      await pb.collection("users").update(loginUser, updateJoin);
      getNotify("success", () => (
        <div>
          반 가입이 완료되었습니다. <br /> 반의 방명록으로 이동합니다.
        </div>
      ));
      await pb.collection("users").authRefresh();
      setIsLoading(false);
      navigate("/guestbook");
    } catch (error) {
      console.error("가입요청 중 오류 발생:", error);
      getNotify("error", "가입에 실패하였습니다.");
      setIsLoading(false);
    }
  }

  return (
    <>
      <section style={{ textAlign: "center" }}>
        <PageTitle>반 검색</PageTitle>
        <PageDescription>우리 반을 찾아보세요!</PageDescription>
        <SearchSection onSubmit={handleSearchClick}>
          <SchoolSearchForm
            schoolInput={schoolInput}
            setSchoolInput={setSchoolInput}
          />
          <SchoolTypeSelect
            schoolType={schoolType}
            setSchoolType={setSchoolType}
          />
          <div style={{ display: "flex" }}>
            <GradeSearchForm
              gradeInput={gradeInput}
              setGradeInput={setGradeInput}
            />
            <ClassSearchForm
              classInput={classInput}
              setClassInput={setClassInput}
            />
            <SearchButton type="submit" onClick={handleSearchClick} />
          </div>
        </SearchSection>
      </section>
      <section style={{ marginTop: "20px" }}>
        <PageTitle>반 목록</PageTitle>
        <Table>
          <thead>
            <TableRow>
              <TableHeader>검색결과</TableHeader>
              <TableHeader>개설상태</TableHeader>
            </TableRow>
          </thead>
          <tbody>
            <TableRow>
              <TableCell>
                {isVisible &&
                  `${schoolInput}${schoolType} ${gradeInput}학년 ${classInput}반`}
              </TableCell>
              <TableCell>
                {isVisible && (
                  <>
                    {isBand ? (
                      <BandJoinButton onClick={handleJoinClick}>
                        반 가입하기
                      </BandJoinButton>
                    ) : (
                      <BandRequestButton onClick={handleRequestClick}>
                        반 개설하기
                      </BandRequestButton>
                    )}
                  </>
                )}
              </TableCell>
            </TableRow>
          </tbody>
        </Table>
      </section>
      <Spinner isOpen={isLoading} />
    </>
  );
}

export default Band;
