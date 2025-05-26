type BannerProps = {
  imageUrl: string;
  children?: React.ReactNode;
};

const Banner = ({ imageUrl, children }: BannerProps) => {
  return (
    <div
      style={{
        backgroundImage: `url(${imageUrl})`,
      }}
      className="bg-cover bg-center h-96 flex items-center justify-center w-full"
    >
      {children}
    </div>
  );
};

export default Banner;
