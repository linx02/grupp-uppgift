import { getCities, getReviews } from "@/app/api";
import { Review, City } from "@/types";
import Banner from "@/app/components/components/Banner";
import Card from "@/app/components/components/Card";

export default async function CityPage({
  params,
}: {
  params: Promise<{ cityId: string }>;
}) {
  const { cityId } = await params;
  const reviews = await getReviews();
  const cities: City[] = await getCities();

  const city = cities.find((c) => c.id === parseInt(cityId));

  if (!city) {
    return <div className="text-center p-8">Staden hittades inte.</div>;
  }

  const cityReviews = reviews.filter(
    (review: Review) => review.city.id === city.id
  );

  return (
    <main>
      <Banner imageUrl="/images/banner.png">
        <div className="relative w-full flex justify-center items-center">
          <p className="text-5xl font-bold text-white">{city.name}</p>
        </div>
      </Banner>
      <div className="grid grid-cols-3 p-18 gap-8">
        {cityReviews.map((review: Review) => (
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
