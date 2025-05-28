"use client";

import { useState, useEffect } from "react";
import { createReview } from "@/app/api/api";

export default async function CreateReviewPage() {
  if (!document.cookie.includes("user_id")) {
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

  const [location, setLocation] = useState("");
  const [city, setCity] = useState("");
  const [review, setReview] = useState("");
  const [rating, setRating] = useState(1);
  const [image, setImage] = useState<File | null>(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

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
    if (!isValidForm()) return;

    try {
      const formData = new FormData();
      formData.append("location", location);
      formData.append("city", city);
      formData.append("review", review);
      formData.append("rating", rating.toString());
      if (image) {
        formData.append("image", image);
      }

      const response = await createReview(formData);
      if (response.success) {
        setSuccess("Recension skapad framgångsrikt!");
        setLocation("");
        setCity("");
        setReview("");
        setRating(1);
        setImage(null);
      } else {
        setError(
          response.message || "Ett fel uppstod vid skapandet av recensionen."
        );
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
            <input
              type="text"
              id="city"
              className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={city}
              onChange={(e) => setCity(e.target.value)}
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-2" htmlFor="review">
              Recension
            </label>
            <textarea
              id="review"
              rows={4}
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
              accept="image/*"
              className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition duration-200"
          >
            Skapa Recension
          </button>
        </div>
      </div>
    </form>
  );
}
