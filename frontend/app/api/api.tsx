const baseUrl = "http://localhost:8080/api";

export const getReviews = async (page: number) => {
  const res = await fetch(`${baseUrl}/reviews?page=${page}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  const data = await res.json();
  if (!res.ok) {
    throw new Error(data.message);
  }
  return data;
};
export const getReview = async (id: string) => {};
export const createReview = async (data: FormData) => {};
