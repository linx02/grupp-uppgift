"use client";

import { useState, useEffect } from "react";
import { useUser } from "@/app/hooks/useUser";
import { createReview } from "@/app/api";
import { City } from "@/types";

export default function CreateReviewPage() {
  const user = useUser();

  const [location, setLocation] = useState("");
  const [city, setCity] = useState<City | null>(null);
  const [review, setReview] = useState("");
  const [rating, setRating] = useState(1);
  const [image, setImage] = useState<File | null>(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  // Borde hämta städer från API men jag är för lat för att implementera det nu
  const cities: City[] = [
    { id: 1, name: "Stockholm" },
    { id: 2, name: "Göteborg" },
    { id: 3, name: "Malmö" },
    { id: 4, name: "Uppsala" },
  ];

  if (!user) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-100">
        <div className="bg-white p-8 rounded shadow-md w-96">
          <h2 className="text-2xl font-bold mb-6 text-center">
            Logga in för att skapa en recension
          </h2>
          <p className="text-center">
            Du måste vara inloggad för att skapa en recension.
            <a className="text-blue-600" href="/login">
              {" "}
              Logga in här
            </a>
            .
          </p>
        </div>
      </div>
    );
  }

  const isValidForm = () => {
    if (!location || !city || !review || rating < 1 || rating > 5) {
      setError("Alla fält måste fyllas i korrekt.");
      return false;
    }
    if (image && !image.type.startsWith("image/")) {
      setError("Endast bildfiler är tillåtna.");
      return false;
    }
    setError("");
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isValidForm()) {
      console.error("Formuläret är inte giltigt.");
      return;
    }

    try {
      const formData = new FormData();
      formData.append("location", location);
      if (city) {
        formData.append("cityId", city.id.toString());
      }
      formData.append("review", review);
      formData.append("rating", rating.toString());
      if (image) {
        formData.append("imageFile", image);
      }

      const req = new Request("http://localhost:8080/posts", {
        method: "POST",
        body: formData,
        headers: new Headers({
          Authorization: `Bearer ${user.token}`,
          // DO NOT add 'Content-Type' here — browser will do it with correct boundary
        }),
      });

      const response = await fetch(req);

      if (response.ok) {
        setSuccess("Recension skapad!");
        setLocation("");
        setCity(null);
        setReview("");
        setRating(1);
        setImage(null);
      } else {
        setError("Ett fel uppstod vid skapandet av recensionen.");
      }
    } catch (error) {
      console.error(error);
      setError("Ett fel uppstod vid skapandet av recensionen.");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="rounded-xl">
      <div className="flex items-center justify-center h-screen bg-gray-100">
        <div className="bg-white p-8 rounded shadow-md w-96">
          <h2 className="text-2xl font-bold mb-6 text-center">
            Skapa en recension
          </h2>
          <div className="mb-4">
            <label
              className="block text-sm font-medium mb-2"
              htmlFor="location"
            >
              Plats
            </label>
            <input
              type="text"
              id="location"
              className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-2" htmlFor="city">
              Stad
            </label>
            <select
              id="city"
              className="..."
              value={city?.id || ""}
              onChange={(e) =>
                setCity(
                  cities.find((c) => c.id === Number(e.target.value)) || null
                )
              }
              required
            >
              <option value="">Välj stad</option>
              {cities.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-2" htmlFor="review">
              Recension
            </label>
            <textarea
              id="review"
              rows={4}
              value={review}
              onChange={(e) => setReview(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            ></textarea>
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-2" htmlFor="rating">
              Betyg (1 till 5)
            </label>
            <input
              type="number"
              id="rating"
              min={1}
              max={5}
              className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={rating}
              onChange={(e) => setRating(Number(e.target.value))}
              required
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-2" htmlFor="image">
              Ladda upp bild
            </label>
            <input
              type="file"
              id="image"
              onChange={(e) => {
                const file = e.target.files?.[0] || null;
                setImage(file);
              }}
              accept="image/*"
              className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 mt-4 cursor-pointer rounded hover:bg-blue-700 transition duration-200"
          >
            Skapa Recension
          </button>
        </div>
      </div>
    </form>
  );
}
