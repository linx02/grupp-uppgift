interface City {
  id: number;
  name: string;
}

interface User {
  id: number;
  name: string;
  email: string;
}

export interface Review {
  id: string | number;
  location: string;
  review: string;
  rating: number;
  user: User;
  imageUrl: string;
  date: string;
  city: City;
}
