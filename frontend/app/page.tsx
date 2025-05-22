import Banner from "./components/components/Banner";
import Card from "./components/components/Card";
import Input from "./components/elements/Input";
import { getReviews } from "./api/api";

export default async function Home() {
  const data = await getReviews(0);

  return (
    <main>
      <Banner>
        <div className="relative w-full flex justify-center items-center">
          <p className="text-5xl font-bold text-white">Alla städer</p>
          <div className="absolute bottom-[-21vh] w-[40vw]">
            <Input />
          </div>
        </div>
      </Banner>
      <div className="grid grid-cols-3 p-18 gap-8">
        <Card
          image="https://picsum.photos/500/500"
          location="Universeum"
          city="Göteborg"
          date="2023-10-01"
          author="John Doe"
          link="/stockholm"
        />
        <Card
          image="https://picsum.photos/500/500"
          location="Universeum"
          city="Göteborg"
          date="2023-10-01"
          author="John Doe"
          link="/stockholm"
        />
        <Card
          image="https://picsum.photos/500/500"
          location="Universeum"
          city="Göteborg"
          date="2023-10-01"
          author="John Doe"
          link="/stockholm"
        />
        <Card
          image="https://picsum.photos/500/500"
          location="Universeum"
          city="Göteborg"
          date="2023-10-01"
          author="John Doe"
          link="/stockholm"
        />
        <Card
          image="https://picsum.photos/500/500"
          location="Universeum"
          city="Göteborg"
          date="2023-10-01"
          author="John Doe"
          link="/stockholm"
        />
        <Card
          image="https://picsum.photos/500/500"
          location="Universeum"
          city="Göteborg"
          date="2023-10-01"
          author="John Doe"
          link="/stockholm"
        />
      </div>
    </main>
  );
}
