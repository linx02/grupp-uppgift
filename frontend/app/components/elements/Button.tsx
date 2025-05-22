type ButtonProps = {
  children: React.ReactNode;
  onClick?: () => void;
  className?: string;
};

const Button = ({ children, onClick, className }: ButtonProps) => {
  return (
    <button
      className="bg-blue-500 text-white rounded-2xl p-4 font-semibold hover:cursor-pointer hover:bg-blue-600 hover:drop-shadow-xl transition duration-200 ease-in-out"
      onClick={onClick}
    >
      {children}
    </button>
  );
};

export default Button;
