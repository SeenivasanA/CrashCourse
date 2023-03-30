import React, { SyntheticEvent, useState, useEffect } from "react";
import { useRouter } from "next/router";
import bgimage from "../../public/bottle.jpg";
import Image from "next/image";
import NavigationBar from "@/component/navigatiionBar";

const Create = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPass] = useState("");
  const [phoneNo, setPhoneNo] = useState("");
  const [address, setAddress] = useState("");
  const router = useRouter();
  const [fetchedData, setFetchedData] = useState();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch("http://localhost:8080/customers", {
          method: "GET",
          headers: { "content-type": "application/json" },
        });
        const data = await response.json();
        console.log("data" + JSON.stringify(data));
        setFetchedData(data);
      } catch (error) {
        console.log("error" + error);
      }
    };

    fetchData();
  }, []);

  const user = {
    username: "Seenivasan",
    email: "Seenivasan@gmail.com",
  };

  const submit = async (e: SyntheticEvent) => {
    e.preventDefault();
    console.log("inside submit");
    const response = await fetch("http://localhost:8080/customers", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        firstName: firstName,
        customerEmail: email,
        lastName: lastName,
        customerPassword: password,
        customerPhone: phoneNo,
        customerAddress: address,
      }),
    });

    if (response.ok) {
      router.push("/dashboard");
    }
  };

  return (
    <>
      <div>
        <div
          className="blur-[5px] -left-5 -top-5"
          style={{
            zIndex: -1,
            position: "fixed",
            width: "110vw",
            height: "110vh",
          }}
        >
          <Image src={bgimage} alt="login_image" fill style={{}} />
        </div>
        <div className="fixed top-0 w-screen z-10">
          <NavigationBar />
        </div>
        <div className="p-20 pb-40 overflow-y-auto mt-20">
          <div className="grid h-screen place-items-center ">
            <div className="bg-gray-900 rounded-lg p-10 opacity-80 w-1/2">
              <div className="flex flex-col justify-center md:justify-start my-auto">
                <p className="text-center text-gray-200 font-bold text-3xl">
                  Create a new Customer Account
                </p>
                <form className="flex flex-col pt-3 md:pt-8">
                  <div className="flex flex-col pt-4">
                    <label className="text-3xl pb-5 text-white">
                      FirstName
                    </label>
                    <input
                      type="text"
                      id="firstname"
                      placeholder="Firstname"
                      className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline"
                      onChange={(e) => setFirstName(e.target.value)}
                    />
                  </div>

                  <div className="flex flex-col pt-4">
                    <label className="text-3xl py-5 text-white">LastName</label>
                    <input
                      type="text"
                      id="lastname"
                      placeholder="Lastname"
                      className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline"
                      onChange={(e) => setLastName(e.target.value)}
                    />
                  </div>
                  <div className="flex flex-col pt-4">
                    <label className="text-3xl pb-5 text-white">
                      UserEmail
                    </label>
                    <input
                      type="text"
                      id="email"
                      placeholder="your@email.com"
                      className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline"
                      onChange={(e) => setEmail(e.target.value)}
                    />
                  </div>

                  <div className="flex flex-col pt-4">
                    <label className="text-3xl py-5 text-white">Password</label>
                    <input
                      type="password"
                      id="password"
                      placeholder="Password"
                      className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline"
                      onChange={(e) => setPass(e.target.value)}
                    />
                  </div>
                  <div className="flex flex-col pt-4">
                    <label className="text-3xl pb-5 text-white">
                      Mobile No.
                    </label>
                    <input
                      type="number"
                      id="number"
                      placeholder="Mobile Number"
                      className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline"
                      onChange={(e) => setPhoneNo(e.target.value)}
                    />
                  </div>

                  <div className="flex flex-col pt-4">
                    <label className="text-3xl py-5 text-white">Address</label>
                    <input
                      type="text"
                      id="address"
                      placeholder="Address "
                      className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline"
                      onChange={(e) => setAddress(e.target.value)}
                    />
                  </div>

                  <button
                    type="submit"
                    value={"Submit"}
                    onClick={(e) => submit(e)}
                    className=" text-lg my-10 text-white bg-gradient-to-r from-cyan-400 via-cyan-500 to-cyan-600 hover:bg-gradient-to-br focus: focus:outline-none focus: dark:focus: font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2"
                  >
                    Submit
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Create;
