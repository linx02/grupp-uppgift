import Image from "next/image";
import Link from "next/link";
import Icon from "../elements/Icon";

type CardProps = {
  image: string;
  location: string;
  city: string;
  date: string;
  author: string;
  link: string;
};

const Card = ({ image, location, city, date, author, link }: CardProps) => {
  return (
    <Link
      href={link}
      className="rounded-2xl p-6 border-[1px] border-neutral-400 cursor-pointer hover:shadow-xl transition duration-200 ease-in-out"
    >
      <div className="h-82 w-full relative">
        <Image
          alt=""
          src={image}
          fill
          className="rounded-2xl pb-2"
          style={{ objectFit: "cover" }}
        />
      </div>
      <div className="flex flex-col gap-2">
        <p className="text-neutral-500">
          {date} • {city}
        </p>
        <p className="font-bold text-2xl">{location}</p>
        <div className="border-b-[1px] border-neutral-400" />
        <div className="flex items-center gap-2 pt-2">
          <Icon name="avatar" />
          <p className="text-neutral-500">{author}</p>
        </div>
      </div>
    </Link>
  );
};

export default Card;
