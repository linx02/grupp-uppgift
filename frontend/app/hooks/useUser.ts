import React, { useState, useEffect } from "react";

export const useUser = () => {
  const [user, setUser] = useState<any | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    console.log("Token from localStorage:", token);

    const getUser = async () => {
      try {
        const response = await fetch(
          "http://localhost:8080/api/auth/validate",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          console.error("Token validation failed:", response.statusText);
          localStorage.removeItem("token");
          setUser(null);
          return;
        }

        const userData = await response.json();
        userData.token = localStorage.getItem("token");
        if (userData) {
          setUser(userData);
        } else {
          localStorage.removeItem("token");
          setUser(null);
        }
      } catch (error) {
        console.error("Error validating token:", error);
        localStorage.removeItem("token");
        setUser(null);
      }
    };

    getUser();
  }, []);

  return user;
};
