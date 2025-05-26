import Banner from "./components/components/Banner";
import Card from "./components/components/Card";
import Input from "./components/elements/Input";
import { getReviews } from "./api/api";
import { Review } from "@/types";
// import Animation from "./components/components/Animation";

export default async function Home({
  searchParams,
}: {
  searchParams: { [key: string]: string | undefined };
}) {
  const { page } = await searchParams;
  const data = await getReviews((page && parseInt(page)) || 0);

  return (
    <main>
      <Banner imageUrl="/images/banner.png">
        <div className="relative w-full flex justify-center items-center">
          <p className="text-5xl font-bold text-white">Alla städer</p>
          <div className="absolute bottom-[-21vh] w-[40vw]">
            <Input />
          </div>
        </div>
      </Banner>
      <div className="grid grid-cols-3 p-18 gap-8">
        {data.map((review: Review) => (
          <Card
            key={review.id}
            image={review.image}
            location={review.location}
            city={review.city}
            date={review.date}
            author={review.author}
            link={`/reviews/${review.id}`}
          />
        ))}
      </div>
    </main>
  );
}
