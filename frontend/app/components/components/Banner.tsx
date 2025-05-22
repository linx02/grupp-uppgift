type BannerProps = {
  imageUrl?: string;
  children?: React.ReactNode;
};

const Banner = ({ imageUrl, children }: BannerProps) => {
  return (
    <div
      style={{
        backgroundImage: `url(${
          imageUrl || "https://picsum.photos/1920/1080"
        })`,
      }}
      className="bg-cover bg-center h-96 flex items-center justify-center"
    >
      {children}
    </div>
  );
};

export default Banner;
