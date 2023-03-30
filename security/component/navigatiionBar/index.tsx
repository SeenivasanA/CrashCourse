import React, { SyntheticEvent, useState } from "react";
import { useRouter } from "next/router";

const NavigationBar = () => {
  const router = useRouter();

  function dashboard(data: any) {
    router.push({
      pathname: "/dashboard",
      query: { username: data.username, email: data.email },
    });
  }

  function navigateCreate() {
    router.push("/create");
  }

  function navigateDashboard() {
    router.push("/dashboard");
  }

  return (
    <div className="flex flex-row items-center w-screen bg-gray-800">
      <div className="text-2xl px-10 font-bold text-gray-300 cursor-pointer">
        E Commerce App
      </div>
      <div className="flex flex-row justify-center items-center">
        <div
          className="px-5 py-2 text-gray-300 text-xl hover:bg-gray-700 transform transition duration-200 ease-in-out hover:scale-105 hover:rounded hover:text-cyan-500 cursor-pointer"
          onClick={() => navigateDashboard()}
        >
          Home
        </div>
        <div className="border-l border m-4 h-8"></div>
        <div
          className="px-5 text-gray-300 text-xl  hover:bg-gray-700 py-2 transform transition duration-200 ease-in-out hover:scale-105 hover:rounded hover:text-cyan-500 cursor-pointer"
          onClick={() => navigateCreate()}
        >
          Create
        </div>
        <div className="border-l border m-4 h-8"></div>
        <div className="px-5 text-gray-300 text-xl  hover:bg-gray-700 py-2 transform transition duration-200 ease-in-out hover:scale-105 hover:rounded hover:text-cyan-500 cursor-pointer">
          Filter
        </div>
      </div>
    </div>
  );
};

export default NavigationBar;
