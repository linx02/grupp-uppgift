import { Review } from "@/types";

const env = process.env.NODE_ENV;
// const isDev = env === "development";
const isDev = false;
const baseUrl = "http://localhost:8080";

// const devData: { reviews: Review[] } = {
//   reviews: [
//     {
//       id: 1,
//       location: "Universeum",
//       city: "Göteborg",
//       review:
//         "Universeum är en fantastisk plats att besöka. Det är en av de mest populära turistattraktionerna i Göteborg och erbjuder en unik inblick i naturvetenskap och teknik.",
//       rating: 5,
//       author: "John Doe",
//       image: "https://picsum.photos/1920/1080?random=1",
//       date: "2023-10-01",
//     },
//     {
//       id: 2,
//       location: "Vasa Museum",
//       city: "Stockholm",
//       review:
//         "Vasa museum är ett måste för historieintresserade. Här kan du se det välbevarade skeppet Vasa och lära dig om dess fascinerande historia.",
//       rating: 4,
//       author: "Jane Doe",
//       image: "https://picsum.photos/1920/1080?random=2",
//       date: "2023-10-01",
//     },
//     {
//       id: 3,
//       location: "Skansen",
//       city: "Stockholm",
//       review:
//         "Skansen är världens äldsta friluftsmuseum och en fantastisk plats för hela familjen med djur, kultur och vacker utsikt.",
//       rating: 3,
//       author: "John Smith",
//       image: "https://picsum.photos/1920/1080?random=3",
//       date: "2023-10-01",
//     },
//     {
//       id: 4,
//       location: "Liseberg",
//       city: "Göteborg",
//       review:
//         "Liseberg är en av Europas bästa nöjesparker med roliga attraktioner och vacker parkmiljö.",
//       rating: 5,
//       author: "Jane Smith",
//       image: "https://picsum.photos/1920/1080?random=4",
//       date: "2023-10-01",
//     },
//     {
//       id: 5,
//       location: "Abisko Nationalpark",
//       city: "Abisko",
//       review:
//         "Abisko Nationalpark erbjuder fantastisk natur och är en av de bästa platserna i Sverige för att se norrsken.",
//       rating: 5,
//       author: "Anna Svensson",
//       image: "https://picsum.photos/1920/1080?random=5",
//       date: "2023-10-01",
//     },
//     {
//       id: 6,
//       location: "Turning Torso",
//       city: "Malmö",
//       review:
//         "Turning Torso är en imponerande byggnad och ett landmärke i Malmö. Utsikten från toppen är fantastisk.",
//       rating: 4,
//       author: "Erik Larsson",
//       image: "https://picsum.photos/1920/1080?random=6",
//       date: "2023-10-01",
//     },
//   ],
// };

const GET = async (url: string) => {
  const res = await fetch(url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!res.ok) {
    console.error("Error fetching data:", res.status, res.statusText);
    throw new Error("Failed to fetch data");
  }
  return res.json();
};

const POST = async (url: string, data: any) => {
  const res = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    throw new Error("Failed to post data");
  }
  return res.json();
};

export const getReviews = async () => {
  // if (isDev) {
  //   return devData.reviews;
  // }
  const res = await GET(`${baseUrl}/posts`);
  return res;
};

export const getReview = async (id: string) => {
  // if (isDev) {
  //   return devData.reviews.find((review) => review.id === parseInt(id));
  // }
  const res = await GET(`${baseUrl}/posts/${id}`);
  return res;
};

export const createReview = async (data: FormData) => {
  const res = await POST(`${baseUrl}/posts`, data);
  return res;
};

export const getCities = async () => {
  // if (isDev) {
  //   return devData.reviews
  //     .map((review) => review.city)
  //     .filter((value, index, self) => self.indexOf(value) === index);
  // }
  const res = await GET(`${baseUrl}/cities`);
  console.log("Cities fetched:", res);
  return res;
};

// export const getReviewsByCity = async (city: string) => {
//   if (isDev) {
//     return devData.reviews.filter((review) => review.city === city);
//   }
//   const res = await GET(`${baseUrl}/reviews/city/${city}`);
//   return res;
// };

export const register = async (data: FormData) => {
  const body = {
    email: data.get("email"),
    password: data.get("password"),
  };
  const res = await POST(`${baseUrl}/api/auth/register`, body);
  return res;
};

export const login = async (data: FormData) => {
  const body = {
    email: data.get("email"),
    password: data.get("password"),
  };
  const res = await POST(`${baseUrl}/api/auth/login`, body);
  return res;
};

export const validateToken = async (token: string) => {
  const res = await fetch(`${baseUrl}/api/auth/validate`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error("Token validation failed");
  }
  return res.json();
};
