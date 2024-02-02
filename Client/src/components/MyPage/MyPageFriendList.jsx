import { styled } from "styled-components";
import NickNameTag from "./NickNameTag.jsx";
import RecommendTitle from "./RecommendTitle.jsx";
import NoFriends from "./NoFriends.jsx";

const FriendWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-wrap: nowrap;
  overflow-x: scroll;
  overflow-y: hidden;
  gap: 5px;
  margin-top: 1rem;
  margin-bottom: 4rem;
  padding-bottom: 10px;

  &::-webkit-scrollbar {
    background: none;
  }
`;

function MyPageFriendList({ interest, friendsList }) {
  const friendsListLength = friendsList?.length;
  return (
    <>
      {/* 추천 소제목 */}
      <RecommendTitle interest={interest} />
      {!friendsListLength ? (
        <NoFriends interest={interest} />
      ) : (
        <FriendWrapper>
          {friendsList?.map((friend) => {
            return (
              <NickNameTag key={friend.id} $styleCode={friend.styleCode}>
                {friend.nickname}
              </NickNameTag>
            );
          })}
        </FriendWrapper>
      )}
    </>
  );
}

export default MyPageFriendList;
