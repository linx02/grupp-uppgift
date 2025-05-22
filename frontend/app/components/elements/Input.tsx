import Icon from "./Icon";

const Input = () => {
  return (
    <div className="relative w-full">
      <input
        type="text"
        className="bg-white px-4 py-5 rounded-2xl drop-shadow-2xl w-full pr-14"
        placeholder="Sök städer..."
      />
      <button
        type="button"
        className="absolute right-3 top-1/2 -translate-y-1/2 bg-blue-500 rounded-full p-3 flex items-center justify-center cursor-pointer"
        aria-label="Sök"
      >
        <Icon name="search" />
      </button>
    </div>
  );
};

export default Input;
