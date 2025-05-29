import Banner from "./components/components/Banner";
import Card from "./components/components/Card";
import SearchBar from "./components/components/SearchBar";
import { getReviews } from "./api";
import { Review, City } from "@/types";
import { getCities } from "./api";
// import Animation from "./components/components/Animation";

export default async function Home() {
  const data = await getReviews();
  const cities: City[] = await getCities();
  console.log("Cities fetched:", cities);

  return (
    <main>
      <Banner imageUrl="/images/banner.png">
        <div className="relative w-full flex flex-col items-center">
          <p className="text-5xl font-bold text-white mt-8 mb-8 text-center">
            Alla städer
          </p>
          <div className="w-full flex justify-center">
            <div className="w-full max-w-lg px-4">
              <SearchBar cities={cities} />
            </div>
          </div>
        </div>
      </Banner>
      <div className="grid grid-cols-3 p-18 gap-8">
        {data.map((review: Review) => (
          <Card
            key={review.id}
            image={review.imageUrl}
            location={review.location}
            city={review.city.name}
            date={review.date || "Inget datum angivet"}
            author={review.user.name}
            link={`/reviews/${review.id}`}
          />
        ))}
      </div>
    </main>
  );
}
