import Button from "../elements/Button";

const Nav = () => {
  return (
    <nav className="absolute top-0 w-full px-18">
      <div className="flex items-center justify-between py-8">
        <p className="font-bold text-4xl text-white">Reseguiden.</p>
        <Button>Logga in</Button>
      </div>
    </nav>
  );
};

export default Nav;
