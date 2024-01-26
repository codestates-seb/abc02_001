import { useEffect, useState } from "react";
import pb from "../../api/pocketbase.js";
import MemberLayout from "../../layout/MemberLayout.jsx";
import PageTitle from "../../components/Common/PageTitle.jsx";
import MyPageSection from "../../components/MyPage/MyPageSection.jsx";
import MyPageProfile from "../../components/MyPage/MyPageProfile.jsx";
import MyPageChangeInfo from "../../components/MyPage/MyPageChangeInfo.jsx";
import DivisionLine from "../../components/MyPage/DivisionLine.jsx";
import MyPageNoBand from "../../components/MyPage/MyPageNoBand.jsx";
import MyPageRecommend from "../../components/MyPage/MyPageRecommend.jsx";
import MyPageFriendList from "../../components/MyPage/MyPageFriendList.jsx";
import getNofity from "../../utils/getNotify";
import Spinner from "../../components/Common/Spinner.jsx";

function MyPage() {
  const [userInfo, setUserInfo] = useState(pb.authStore.model);
  const [openColorPalette, setOpenColoPalette] = useState(false);
  const [mbtiFriends, setMbtiFriends] = useState(null);
  const [firstInterests, setFirstInterests] = useState({
    interest: userInfo.interests[0],
    friendsList: null,
  });
  const [secondInterests, setSecondInterests] = useState({
    interest: userInfo.interests[1],
    friendsList: null,
  });
  const [thirdInterests, setThirdInterests] = useState({
    interest: userInfo.interests[2],
    friendsList: null,
  });
  const [isLoading, setIsLoading] = useState(false);

  async function postColor() {
    const userId = pb.authStore.model.id;
    const data = { styleCode: userInfo.styleCode };
    await pb.collection("users").update(userId, data);
    await pb.collection("users").authRefresh();
  }

  function translateInterest(en) {
    switch (en) {
      case "MUSIC":
        return "음악";
      case "MOVIE":
        return "영화";
      case "EXERCISE":
        return "운동";
      case "TRAVEL":
        return "여행";
      case "GAME":
        return "게임";
      case "FOOD":
        return "음식";
      case "VOLUNTEER":
        return "봉사활동";
      case "ART":
        return "미술";
      case "READING":
        return "독서";
      case "FASHION":
        return "패션";
      case "CODING":
        return "코딩";
      case "COOKING":
        return "요리";
      case "DANCE":
        return "춤";
      case "PHOTO":
        return "사진";
      case "MEDIA":
        return "영상";
      case "STUDY":
        return "공부";
    }
  }

  function handleColorChange(e) {
    e.preventDefault();
    if (!openColorPalette) {
      setOpenColoPalette(!openColorPalette);
      return;
    }
    postColor();
    getNofity("success", "색상이 변경되었습니다.");
    setOpenColoPalette(!openColorPalette);
  }

  function handleStyleCode(e) {
    const { value } = e.target;
    setUserInfo({ ...userInfo, styleCode: value });
  }

  async function getFriendsList(filter, data) {
    const params = `band = "${userInfo.band}" && ${filter} ~ "${data}" && id!="${userInfo.id}"`;
    return await pb
      .collection("users")
      .getList(1, 50, {
        filter: params,
      })
      .then((res) => res.items)
      .catch((error) => console.log(error));
  }

  useEffect(() => {
    async function setList() {
      setIsLoading(true);
      try {
        // mbti 친구 요청
        const mbtiRes = await getFriendsList("mbti", userInfo.mbti);
        setMbtiFriends(mbtiRes);

        // 첫번째 관심사 친구 요청
        const firstInt = await getFriendsList(
          "interests",
          firstInterests.interest,
        );
        setFirstInterests({ ...firstInterests, friendsList: firstInt });

        // 두번째 관심사 친구 요청
        if (secondInterests.interest) {
          const secondInt = await getFriendsList(
            "interests",
            secondInterests.interest,
          );
          setSecondInterests({ ...secondInterests, friendsList: secondInt });
        }

        // 세번째 관심사 친구 요청
        if (thirdInterests.interest) {
          const thirdInt = await getFriendsList(
            "interests",
            thirdInterests.interest,
          );
          setThirdInterests({ ...thirdInterests, friendsList: thirdInt });
        }
      } catch (error) {
        throw new Error(error);
      } finally {
        setIsLoading(false);
      }
    }
    setList();
  }, []);

  return (
    <MemberLayout>
      <PageTitle>마이페이지</PageTitle>
      {/* 프로필 */}
      <MyPageSection sectionTite="프로필">
        <MyPageProfile userInfo={userInfo} />
      </MyPageSection>
      {/* 회원정보 수정, 색상 변경 */}
      <MyPageSection sectionTite="회원정보 수정" height="7rem">
        <MyPageChangeInfo
          selectedColor={userInfo.styleCode}
          openColorPalette={openColorPalette}
          handleColorChange={handleColorChange}
          handleStyleCode={handleStyleCode}
        />
      </MyPageSection>
      {/* 구분선 */}
      <DivisionLine />

      {/* 친구 추천 */}
      {userInfo.band === "" ? (
        <MyPageNoBand />
      ) : (
        <MyPageSection sectionTite="친구 추천" height="38rem">
          <MyPageRecommend>
            <MyPageFriendList interest="MBTI" friendsList={mbtiFriends} />
            <MyPageFriendList
              interest={translateInterest(firstInterests.interest)}
              friendsList={firstInterests.friendsList}
            />
            {secondInterests.interest && (
              <MyPageFriendList
                interest={translateInterest(secondInterests.interest)}
                friendsList={secondInterests.friendsList}
              />
            )}
            {thirdInterests.interest && (
              <MyPageFriendList
                interest={translateInterest(thirdInterests.interest)}
                friendsList={thirdInterests.friendsList}
              />
            )}
          </MyPageRecommend>
        </MyPageSection>
      )}
      <Spinner isOpen={isLoading} />
    </MemberLayout>
  );
}

export default MyPage;
