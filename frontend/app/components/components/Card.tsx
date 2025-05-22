import Image from "next/image";
import Link from "next/link";

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
      <Image
        alt=""
        src={image}
        width={500}
        height={500}
        className="rounded-2xl pb-2"
      />
      <div className="flex flex-col gap-2">
        <p className="text-neutral-500">
          {date} • {city}
        </p>
        <p className="font-bold text-2xl">{location}</p>
        <div className="border-b-[1px] border-neutral-400" />
        <p className="text-neutral-500">{author}</p>
      </div>
    </Link>
  );
};

export default Card;
