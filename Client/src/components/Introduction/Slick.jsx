import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { styled } from "styled-components";
import SlickPage1 from "../../assets/slickpage1.gif";
import SlickPage2 from "../../assets/slickpage2.gif";
import SlickPage3 from "../../assets/slickpage3.gif";
import SlickPage4 from "../../assets/slickpage4.gif";

const Image = styled.img`
  width: 100%;
  position: relative;
`;

const ArrowButton = styled.button`
  position: absolute;
  z-index: 1;
  font-size: 1rem;
  color: var(--brand-color);
  background-color: var(--brand-color);
  border-radius: 40px;
  top: 50%;

  &.left {
    left: -1rem;
  }
  &.right {
    right: -1rem;
  }
`;

export default function Slick() {
  const settings = {
    dots: false,
    infinite: true,
    autoplay: true,
    autoplaySpeed: 5000,
    speed: 3000,
    slidesToShow: 1,
    slidesToScroll: 1,
    nextArrow: <NextArrow />,
    prevArrow: <PrevArrow />,
  };

  return (
    <div>
      <Slider {...settings}>
        <div>
          <Image src={SlickPage1}></Image>
        </div>
        <div>
          <Image src={SlickPage2}></Image>
        </div>
        <div>
          <Image src={SlickPage3}></Image>
        </div>
        <div>
          <Image src={SlickPage4}></Image>
        </div>
      </Slider>
    </div>
  );
}

const NextArrow = ({ onClick }) => {
  return (
    <ArrowButton className="right" onClick={onClick} type="button">
      o
    </ArrowButton>
  );
};

const PrevArrow = ({ onClick }) => {
  return (
    <ArrowButton className="left" onClick={onClick} type="button">
      o
    </ArrowButton>
  );
};
