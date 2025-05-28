"use client";

import Button from "../elements/Button";
import Link from "next/link";

import { useState, useEffect } from "react";

import { validateToken } from "@/app/api/api";

const Nav = () => {
  const [user, setUser] = useState<any | null>(null);
  useEffect(() => {
    const token = localStorage.getItem("token");
    console.log("Token from localStorage:", token);

    const getUser = async () => {
      const user = await validateToken(token || "");
      console.log("User from validateToken:", user);
      if (user) {
        setUser(user);
        console.log("User is authenticated:", !!user);
      } else {
        localStorage.removeItem("token");
        setUser(null);
      }
    };
    getUser();
  }, [user]);

  return (
    <nav className="absolute top-0 w-full px-18">
      <div className="flex items-center justify-between py-8">
        <Link href="/" className="font-bold text-4xl text-white">
          Reseguiden.
        </Link>
        <div className="flex items-center gap-6">
          {!!user && <p className="font-bold text-white">{user.email}</p>}
          <Link
            href={!!user ? "/create" : "/login"}
            className="flex items-center"
          >
            <Button>{!!user ? "Skapa inlägg" : "Logga in"}</Button>
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Nav;
