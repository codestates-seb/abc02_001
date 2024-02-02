import PageTitle from "../../components/Common/PageTitle.jsx";
import PageDescription from "../../components/Common/PageDescription.jsx";
import Pagination from "../../components/Common/Pagination.jsx";

import Gestcomment from "../../components/GestBook/GB_Comment.jsx";
import TextInput from "../../components/GestBook/GB_TextBox.jsx";
import Button from "../../components/Common/Button.jsx";
import { styled } from "styled-components";
import { useState, useEffect } from "react";

import getNofity from "../../utils/getNotify.js";

import pb from "../../api/pocketbase.js";
import Spinner from "../../components/Common/Spinner.jsx";
//스타일//
const TextContainSt = styled.section`
  margin-top: 10px;
  width: 100%;
  border: 2px solid var(--box-gray);
  background: #ebf1e6;
  text-align: end;
`;
const GBViewStyle = styled.ul`
  margin-top: 10px;
  padding: 10px;
  border: 1px solid var(--font-lightgray);
  width: 100%;
  height: auto;
`;

function GuestBook() {
  const [text, setText] = useState("");
  const [comments, setComments] = useState([]);
  const [page, setPage] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const loginUser = pb.authStore.model.id;
  const loginBand = pb.authStore.model.band;
  const loginName = pb.authStore.model.nickname;

  //전체 방명록 불러오기
  useEffect(() => {
    setIsLoading(true);
    pb.collection("comment")
      .getList(1, 20, {
        filter: `(band="${loginBand}")`,
        sort: "-created",
        expand: "userId",
      })
      .then((response) => {
        // console.log(response);
        setComments(response.items);
      })
      .catch((error) => console.error(error))
      .finally(() => setIsLoading(false));
  }, []);

  //방명록 등록
  const ComHandleSumbmit = async (event) => {
    event.preventDefault();
    const data = {
      userId: loginUser,
      contexts: text,
      band: loginBand,
    };
    try {
      const res = await pb.collection("comment").create(data);
      res.expand = { userId: { nickname: loginName } };
      setComments((prev) => [res, ...prev]);
      setText("");
      getNofity("success", "글이 등록되었습니다.");
    } catch (error) {
      console.error("글을 등록하는 도중 오류가 발생했습니다:", error);
    }
  };

  const ComHandleChange = (event) => {
    setText(event.target.value);
  };

  //삭제기능
  const handleDelete = async (id) => {
    // 선택된 방명록 아이디를 가져온다.
    // 가져온 아이디에 해댕하는 글을 지운다
    // 가저온 아이디 이외의 글은 다시 방명록에 넣는다.
    try {
      await pb.collection("comment").delete(id);
      setComments(comments.filter((comment) => comment.id !== id));
      getNofity("success", "삭제되었습니다!");
    } catch (error) {
      console.error("삭제 오류발생:", error);
    }
  };

  return (
    <>
      <div style={{ textAlign: "center" }}>
        <PageTitle>방명록</PageTitle>
        <PageDescription> 짧은 글로 반 친구들과 소통해보세요! </PageDescription>
      </div>
      <section>
        {/* 방명록 작성부분 */}
        <TextContainSt>
          <form onSubmit={ComHandleSumbmit}>
            <TextInput
              border="1px solid var(--brand-color)"
              maxLength="200"
              onChange={ComHandleChange}
              value={text}
              width="100%"
              placeholder="글을 적어주세요!"
            />
            <Button
              type="submit"
              margin="2px 5px 0px 0px"
              onClick={ComHandleSumbmit}
            >
              등록
            </Button>
          </form>
        </TextContainSt>
      </section>
      <section>
        {/*  방명록 조회 부분 */}
        <GBViewStyle>
          {comments.map((comment) => {
            return (
              <Gestcomment
                key={comment.id}
                id={comment.id}
                nickName={comment.expand.userId.nickname}
                contexts={comment.contexts}
                created={comment.created}
                handleDelete={handleDelete}
                isWrite={comment.userId === loginUser}
                comment={comment}
              />
            );
          })}
        </GBViewStyle>
        <Pagination
          page={page}
          totalItems={comments.length}
          onClick={setPage}
        />
      </section>
      <Spinner isOpen={isLoading} />
    </>
  );
}

export default GuestBook;
