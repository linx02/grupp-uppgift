"use client";

import Button from "../elements/Button";
import Link from "next/link";
import { useUser } from "@/app/hooks/useUser";

const Nav = () => {
  const user = useUser();

  return (
    <nav className="absolute top-0 w-full px-18">
      <div className="flex items-center justify-between py-8">
        <Link href="/" className="font-bold text-4xl text-white">
          Reseguiden.
        </Link>
        <div className="flex items-center gap-6">
          {!!user && <p className="font-bold text-white">{user.email}</p>}
          <Link
            href={!!user ? "/reviews/create" : "/login"}
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
