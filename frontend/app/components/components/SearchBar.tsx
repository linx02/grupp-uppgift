"use client";

import Icon from "../elements/Icon";
import { City } from "@/types";
import { useState, useEffect } from "react";
import Link from "next/link";

const SearchBar = ({ cities }: { cities: City[] }) => {
  console.log("Cities in SearchBar:", cities);
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredCities, setFilteredCities] = useState<City[]>([]);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setSearchTerm(value);
    if (value) {
      const filtered = cities.filter((city) =>
        city.name.toLowerCase().includes(value.toLowerCase())
      );
      setFilteredCities(filtered);
      setIsDropdownOpen(true);
    } else {
      setFilteredCities([]);
      setIsDropdownOpen(false);
    }
  };
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      const target = event.target as HTMLElement;
      if (!target.closest(".relative")) {
        setIsDropdownOpen(false);
      }
    };

    document.addEventListener("click", handleClickOutside);
    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, []);

  return (
    <div className="relative w-full">
      <input
        type="text"
        className="bg-white px-4 py-5 rounded-2xl drop-shadow-2xl w-full pr-14"
        placeholder="Sök städer..."
        value={searchTerm}
        onChange={handleSearchChange}
      />
      <button
        type="button"
        className="absolute right-3 top-1/2 -translate-y-1/2 bg-blue-500 rounded-full p-3 flex items-center justify-center cursor-pointer"
        aria-label="Sök"
      >
        <Icon name="search" />
      </button>
      {isDropdownOpen && (
        <div className="absolute top-full left-0 w-full bg-white border border-gray-300 rounded-lg mt-2 z-10">
          <ul className="max-h-60 overflow-y-auto">
            {filteredCities.map((city) => (
              <Link href={`cities/${city.id}`} key={city.id}>
                <li
                  className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                  onClick={() => {
                    setSearchTerm(city.name);
                    setIsDropdownOpen(false);
                  }}
                >
                  {city.name}
                </li>
              </Link>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default SearchBar;
