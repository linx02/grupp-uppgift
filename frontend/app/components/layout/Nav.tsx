import Button from "../elements/Button";
import Link from "next/link";

const Nav = () => {
  return (
    <nav className="absolute top-0 w-full px-18">
      <div className="flex items-center justify-between py-8">
        <Link href="/" className="font-bold text-4xl text-white">
          Reseguiden.
        </Link>
        <Button>Logga in</Button>
      </div>
    </nav>
  );
};

export default Nav;
