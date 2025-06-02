"use client";

import Link from "next/link";
import { register } from "../api";
import { useRouter } from "next/navigation";

export default function LoginPage() {
  const router = useRouter();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    try {
      console.log("Submitting registration form", formData);
      const data = await register(formData);

      const token = data.token;

      if (token) {
        localStorage.setItem("token", token);
        router.push("/");
      } else {
        console.error("Registration failed: No token received");
        // Show error message
      }
    } catch (error) {
      console.error("Registration failed:", error);
      // Show error message
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md w-96">
        <h2 className="text-2xl font-bold mb-6 text-center">Registrera</h2>
        <form className="rounded-xl" onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-2" htmlFor="email">
              E-post
            </label>
            <input
              type="text"
              id="email"
              name="email"
              className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <div className="mb-6">
            <label
              className="block text-sm font-medium mb-2"
              htmlFor="password"
            >
              Lösenord
            </label>
            <input
              type="password"
              id="password"
              name="password"
              className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition duration-200"
          >
            Registrera
          </button>
          <p className="text-center mt-4">
            Har du redan ett konto?{" "}
            <Link className="text-blue-600" href="/login">
              Logga in
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}
