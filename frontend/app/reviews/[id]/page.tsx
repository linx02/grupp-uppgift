import { getReviews } from "@/app/api";
import Banner from "@/app/components/components/Banner";
import Icon from "@/app/components/elements/Icon";
import { Review } from "@/types";
import Image from "next/image";
import Link from "next/link";

export default async function Page({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const allReviews = await getReviews();
  console.log("Data fetched for review page:", allReviews);
  console.log("Review ID from params:", id);
  const data: Review = allReviews.find(
    (review: Review) => review.id === Number(id)
  );
  console.log("Data for specific review:", data);
  const similarReviews = allReviews.filter(
    (review: Review) =>
      review.city.id === data?.city.id && review.id !== Number(id)
  );

  if (!data) {
    return (
      <div className="flex justify-center items-center h-screen">
        <h1 className="text-3xl font-bold">Recensionen kunde inte hittas</h1>
      </div>
    );
  }

  return (
    <main>
      <Banner imageUrl={data.imageUrl} />
      <div className="grid grid-cols-3 mx-18 mt-18 gap-18">
        <div className="col-span-2">
          <h1 className="text-5xl font-bold">{data.location}</h1>
          <div className="flex justify-between items-center py-6">
            <div>
              <p>
                {data.date || "Inget datum angivet"} • {data.city.name}
              </p>
            </div>
            <div className="flex items-center gap-2">
              <Icon name="avatar" />
              <p>{data.user.name}</p>
            </div>
          </div>
          <div dangerouslySetInnerHTML={{ __html: data.review }} />
          <div className="flex items-center gap-2 mt-6">
            {Array.from({ length: data.rating }).map((_, i) => (
              <Icon key={i} name="star" />
            ))}
          </div>
        </div>
        <div className="col-span-1 border-[1px] border-neutral-400 rounded-2xl p-6">
          <h2 className="text-2xl font-bold">Utforska {data.city.name}</h2>
          <div className="grid grid-cols-1 gap-4 mt-6">
            {similarReviews.map((review: Review) => (
              <Link
                href={`/reviews/${review.id}`}
                key={review.id}
                className="pb-4 flex space-x-4 items-center hover:drop-shadow-xl transition duration-200 ease-in-out"
              >
                <div className="relative h-24 w-24">
                  <Image
                    alt=""
                    src={review.imageUrl}
                    fill
                    className="rounded-2xl mb-4"
                    objectFit="cover"
                  />
                </div>
                <div>
                  <p className="text-neutral-500">
                    {review.date} • {review.city.name}
                  </p>
                  <p className="font-bold text-xl">{review.location}</p>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </div>
    </main>
  );
}
