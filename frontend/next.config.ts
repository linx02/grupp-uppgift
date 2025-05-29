import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  /* config options here */
  images: {
    domains: ["picsum.photos", "post-imagebucket.s3.amazonaws.com"],
  },
};

export default nextConfig;
