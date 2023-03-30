import React, { useState, useEffect } from "react";
import { useRouter } from "next/router";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCaretDown,
  faCaretUp,
  faCheck,
  faChevronDown,
  faChevronLeft,
  faChevronRight,
  faChevronUp,
  faClose,
  faFilter,
  faPenToSquare,
  faTrash,
} from "@fortawesome/free-solid-svg-icons";
import Modal from "@mui/material/Modal";
import Head from "next/head";

const Create = () => {
  const router = useRouter();
  const [refresh, setRefresh] = useState(true);
  const [fetchedData, setFetchedData] = useState();
  const [searchList, setSearchList] = useState();
  const [showModel, setShowModel] = useState(false);
  const [totalPage, setTotalpage] = useState(0);
  const [currentPage, setCurrentPage] = useState();
  const [data, setData] = useState();
  const [query, setQuery] = useState("");
  const [sortName, setSortName] = useState(true);
  const [sortAddress, setSortAddress] = useState(true);
  const [sortStatus, setSortStatus] = useState(true);
  const [isOpen, setIsOpen] = useState(false);
  const [isSearch, setIsSearch] = useState(false);
  const [isSort, setIsSort] = useState(false);
  const [isFilter, setIsFilter] = useState(false);
  const [searchTerm, setSearchTerm] = useState();
  const [sortBy, setSortBy] = useState();
  const [direct, setDirect] = useState(false);
  const [check1, setCheck1] = useState(false);
  const [check2, setCheck2] = useState(false);
  const [showAddressDrop, setShowAddressDrop] = useState(false);
  const [selectedAddress, setSelectedAddress] = useState([]);
  const [statusQuery, setStatusQuery] = useState(null);

  const user = {
    username: "router.query.username",
    email: "router.query.email",
  };

  function dashboard(data: any) {
    router.push("/dashboard");
  }

  function navigateEdit(customer: any) {
    router.push({
      pathname: "/edit",
      query: { id: customer },
    });
  }
  const navigateDelete = async (customer: any) => {
    const url = "http://localhost:8080/customers/" + customer;
    console.log("inside submit");
    const response = await fetch(url, {
      method: "DELETE",
      headers: { "Content-Type": "application/json" },
    });

    if (response.ok) {
      setRefresh(!refresh);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch("http://localhost:8080/customers/0", {
          method: "GET",
          headers: {
            "content-type": "application/json",
            Authorization:
              "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1NUVURFTlQifV0sImlhdCI6MTY4MDA5MjE0NywiZXhwIjoxNjgwODkyMjAwfQ.PuxVy3ByYnHNJdE-4zTgFeDTS9yX2lQKyBGu1Tc5zZ0N68ie0Cu0TpEnEF1EGxZS5vf12KsPRiApPC79Pz5V4Q",
          },
        });
        const data = await response.json();
        setData(data);
        setCurrentPage(data.number);
        setTotalpage(data.totalPages);
        setFetchedData(data.content);
      } catch (error) {
        console.log("error" + error);
      }
    };

    fetchData();
  }, [refresh]);

  function navigateCreate() {
    router.push("/create");
  }

  function navigateDashboard() {
    router.push("/dashboard");
  }

  const search = async (event: any) => {
    try {
      let url = "";
      setSearchTerm(event);
      setIsSearch(true);
      console.log("issearch --" + isSearch);
      if (event) {
        url = "http://localhost:8080/customers/search/0/" + event;

        const response = await fetch(url, {
          method: "GET",
          headers: { "content-type": "application/json" },
        });
        const data = await response.json();
        setData(data);
        setCurrentPage(data.number);
        setTotalpage(data.totalPages);
        setFetchedData(data.content);
      } else {
        setIsSearch(false);
        url = "http://localhost:8080/customers/0";

        const response = await fetch(url, {
          method: "GET",
          headers: { "content-type": "application/json" },
        });
        const data = await response.json();
        setData(data);
        setCurrentPage(data.number);
        setTotalpage(data.totalPages);
        setFetchedData(data.content);
        console.log("search list -----\n" + JSON.stringify(searchList));
      }
    } catch (error) {
      console.log("error" + error);
    }
  };

  function nextPage(value: any): void {
    const fetchData = async () => {
      try {
        value += 1;
        let url = "http://localhost:8080/customers/" + value;
        if (isSearch) {
          console.log("inside is search ----");
          url =
            "http://localhost:8080/customers/search/" +
            value +
            "/" +
            searchTerm;
        } else if (isFilter) {
          console.log("inside is filter ----");
          url = "http://localhost:8080/customers/filter/" + value + "/" + query;
        } else if (isSort && direct) {
          url =
            "http://localhost:8080/customers/sort/" +
            sortBy +
            "/" +
            value +
            "/asc";
        } else if(isSort){
          url =
            "http://localhost:8080/customers/sort/" +
            sortBy +
            "/" +
            value +
            "/dec";
        }
        if (value < totalPage) {
          const response = await fetch(url, {
            method: "GET",
            headers: { "content-type": "application/json" },
          });
          const data = await response.json();
          setData(data);
          setCurrentPage(data.number);
          setTotalpage(data.totalPages);
          setFetchedData(data.content);
        }
      } catch (error) {
        console.log("error" + error);
      }
    };

    fetchData();
  }

  function previousPage(value: any): void {
    const fetchData = async () => {
      try {
        value -= 1;
        let url = "http://localhost:8080/customers/" + value;
        if (isSearch) {
          console.log("inside is search ----");
          url =
            "http://localhost:8080/customers/search/" +
            value +
            "/" +
            searchTerm;
        } else if (isFilter) {
          console.log("inside is filter ----");
          url = "http://localhost:8080/customers/filter/" + value + "/" + query;
        } else if (isSort && direct) {
          url =
            "http://localhost:8080/customers/sort/" +
            sortBy +
            "/" +
            value +
            "/asc";
        } else if(isSort){
          url =
            "http://localhost:8080/customers/sort/" +
            sortBy +
            "/" +
            value +
            "/dec";
        }

        if (value >= 0) {
          const response = await fetch(url, {
            method: "GET",
            headers: { "content-type": "application/json" },
          });
          const data = await response.json();
          setData(data);
          setCurrentPage(data.number);
          setTotalpage(data.totalPages);
          setFetchedData(data.content);
        }
      } catch (error) {
        console.log("error" + error);
      }
    };

    fetchData();
  }

  function sort(field: any, direct: any): void {
    const fetchData = async () => {
      try {
        setIsSort(true);
        setSortBy(field);
        if (direct) {
          setDirect(true);
          const url =
            "http://localhost:8080/customers/sort/" + field + "/0/asc";
          const response = await fetch(url, {
            method: "GET",
            headers: { "content-type": "application/json" },
          });
          const data = await response.json();
          setData(data);
          setCurrentPage(data.number);
          setTotalpage(data.totalPages);
          setFetchedData(data.content);
          if (field == "name") {
            setSortName(!sortName);
          } else if (field == "status") {
            setSortStatus(!sortStatus);
          } else {
            setSortAddress(!sortAddress);
          }
        } else {
          setDirect(false);
          const url =
            "http://localhost:8080/customers/sort/" + field + "/0/dec";
          const response = await fetch(url, {
            method: "GET",
            headers: { "content-type": "application/json" },
          });
          const data = await response.json();
          setData(data);
          setCurrentPage(data.number);
          setTotalpage(data.totalPages);
          setFetchedData(data.content);
          if (field == "name") {
            setSortName(!sortName);
          } else if (field == "status") {
            setSortStatus(!sortStatus);
          } else {
            setSortAddress(!sortAddress);
          }
        }
      } catch (error) {
        console.log("error" + error);
      }
    };

    fetchData();
  }

  function settingAddress(value: string) {
    setSelectedAddress((prevSelectedAddress) => {
      if (prevSelectedAddress.includes(value)) {
        return prevSelectedAddress.filter((item) => item !== value);
      } else {
        return [...prevSelectedAddress, value];
      }
    });
  }

  function reset() {
    setSelectedAddress([]);
    setCheck1(false);
    setCheck2(false);
    setStatusQuery(null);
    setShowAddressDrop(false);
    setIsFilter(false);
    setRefresh(!refresh)
  }

  const filter = async () => {
    setShowAddressDrop(false);
    setIsFilter(true);
    let quering: string = "query=";

    if (statusQuery && statusQuery != null) {
      quering += "isActive@true";
    } else if (!statusQuery && statusQuery != null) {
      quering += "isActive@false";
    } else {
      quering += "isActive@null";
    }

    const addressQuery =
      selectedAddress.length > 0
        ? "isAddress@" + selectedAddress.join("%23")
        : "";
    if (addressQuery) {
      quering += "," + addressQuery;
    }

    console.log(quering);
    setQuery(quering);
          console.log("query--"+quering)
          const url = "http://localhost:8080/customers/filter/0/"+quering 
          const response = await fetch(url, {
            method: "GET",
            headers: {
              "content-type": "application/json",
            },
          });
          const data = await response.json();
          setData(data);
          setCurrentPage(data.number);
          setTotalpage(data.totalPages);
          setFetchedData(data.content);
          console.log(JSON.stringify(data));
    
  }

  return (
    <>
      <Head>
        <title>E Commerce App</title>
        <script src="https://unpkg.com/@themesberg/flowbite@latest/dist/flowbite.bundle.js"></script>
      </Head>
      <Modal
        open={isOpen}
        onClose={() => {
          setIsOpen(!isOpen);
        }}
      >
        <div className="w-1/3">
          <div className="fixed inset-0 bg-white z-50 w-1/2 h-screen outline-none translate-x-full">
            <div className="flex text-2xl p-5 font-bold">
              <FontAwesomeIcon
                icon={faClose}
                className="cursor-pointer pr-20"
                onClick={() => setIsOpen(false)}
              />
              <div className="pb-1">Filter</div>
            </div>

            <div className="flex flex-col justify-between h-5/6">
              <div className="w-full p-10">
                <div className="text-2xl">By Status</div>

                <div>
                  <label className="inline-flex items-center m-10 cursor-pointer">
                    <input
                      type="radio"
                      className="form-radio text-indigo-600 cursor-pointer"
                      name="radio-color"
                      value="true"
                      checked={statusQuery === true}
                      onChange={() => setStatusQuery(true)}
                    />
                    <span className="ml-2 text-gray-700">Active</span>
                  </label>

                  <label className="inline-flex items-center cursor-pointer">
                    <input
                      type="radio"
                      className="form-radio text-indigo-600 cursor-pointer"
                      name="radio-color"
                      value="false"
                      checked={statusQuery === false}
                      onChange={() => setStatusQuery(false)}
                    />
                    <span className="ml-2 text-gray-700">InActive</span>
                  </label>
                </div>
                <div>
                  <div className="text-2xl pb-10">By Address</div>

                  {showAddressDrop ? (
                    <button
                      className="bg-blue-300 p-3 rounded-md w-full flex justify-between"
                      onClick={() => setShowAddressDrop(!showAddressDrop)}
                    >
                      {" "}
                      {selectedAddress.length > 0
                        ? selectedAddress.join(", ")
                        : "Select Address"}{" "}
                      <FontAwesomeIcon
                        icon={faChevronUp}
                        className="pl-3 pt-1"
                      />{" "}
                    </button>
                  ) : (
                    <button
                      className="bg-blue-300 p-3 rounded-md w-full flex justify-between"
                      onClick={() => setShowAddressDrop(!showAddressDrop)}
                    >
                      {" "}
                      {selectedAddress.length > 0
                        ? selectedAddress.join(", ")
                        : "Select Address"}
                      <FontAwesomeIcon
                        icon={faChevronDown}
                        className="pl-3 pt-1"
                      />{" "}
                    </button>
                  )}

                  {showAddressDrop && (
                    <div className="flex justify-center p-1 w-full">
                      <div className="z-50 absolute bg-gray-200 w-11/12 rounded-md p-3">
                        {check1 ? (
                          <div
                            className="p-2 bg-gray-300 rounded-md cursor-pointer m-1 flex flex-row justify-between"
                            onClick={() => {
                              settingAddress("Bangalore"), setCheck1(!check1);
                            }}
                          >
                            {" "}
                            Bangalore{" "}
                            <FontAwesomeIcon
                              icon={faCheck}
                              className="pl-3 pt-1 text-green-600"
                            />{" "}
                          </div>
                        ) : (
                          <div
                            className="p-2 hover:bg-gray-300 hover:rounded-md cursor-pointer m-1"
                            onClick={() => {
                              settingAddress("Bangalore"), setCheck1(!check1);
                            }}
                          >
                            {" "}
                            Bangalore{" "}
                          </div>
                        )}
                        {check2 ? (
                          <div
                            className="p-2 bg-gray-300 rounded-md cursor-pointer m-1 flex flex-row justify-between"
                            onClick={() => {
                              settingAddress("Chennai"), setCheck2(!check2);
                            }}
                          >
                            {" "}
                            Chennai{" "}
                            <FontAwesomeIcon
                              icon={faCheck}
                              className="pl-3 pt-1 text-green-600"
                            />{" "}
                          </div>
                        ) : (
                          <div
                            className="p-2 hover:bg-gray-300 hover:rounded-md cursor-pointer m-1"
                            onClick={() => {
                              settingAddress("Chennai"), setCheck2(!check2);
                            }}
                          >
                            {" "}
                            Chennai{" "}
                          </div>
                        )}
                      </div>
                    </div>
                  )}
                </div>
              </div>
              <div className="flex justify-end items-end pr-12">
                <button
                  className="rounded-md border border-gray-500 p-2 px-3 m-3"
                  onClick={() => setIsOpen(false)}
                >
                  Cancel
                </button>
                <button
                  className="rounded-md border border-gray-500 p-2 px-3 m-3"
                  onClick={() => {
                    reset();
                  }}
                >
                  Reset
                </button>
                <button
                  className="rounded-md bg-gray-400 p-2 px-5 m-3"
                  onClick={() => {
                    filter();
                  }}
                >
                  {" "}
                  Filter
                </button>
              </div>
            </div>
          </div>
        </div>
      </Modal>

      <div className="w-full h-screen">
        <div>
          <div className="flex flex-col bg-teal-50 h-screen overflow-x-hidden fixed">
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
                <div
                  className="px-5 text-gray-300 text-xl  hover:bg-gray-700 py-2 transform transition duration-200 ease-in-out hover:scale-105 hover:rounded hover:text-cyan-500 cursor-pointer"
                  onClick={() => setIsOpen(true)}
                >
                  Filter
                </div>
              </div>
            </div>

            <div className="bg-teal-50 p-14 pb-4 pt-20 w-full">
              <div className="flex flex-row justify-between w-full">
                <div className="w-2/3">
                  <div className="pb-20 ">
                    <div>
                      <div className="relative flex items-center w-full h-12 shadow-md rounded-lg focus-within:shadow-xl bg-white overflow-hidden">
                        <div className="grid place-items-center h-full w-12 bg-gray-600 text-gray-300">
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            className="h-6 w-6"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                          >
                            <path
                              strokeLinecap="round"
                              strokeLinejoin="round"
                              strokeWidth="2"
                              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                            />
                          </svg>
                        </div>

                        <input
                          className="peer h-full w-full outline-none text-md pl-4 text-gray-700 "
                          type="text"
                          id="search"
                          placeholder="Search something.."
                          onChange={(e) => search(e.target.value)}
                        />
                      </div>
                    </div>
                  </div>
                </div>

                <div className="flex flex-row pt-2 pr-5">
                  <div className="text-2xl px-5 py-3  text-gray-600">
                    <FontAwesomeIcon
                      icon={faFilter}
                      className="hover:text-sky-600 cursor-pointer"
                      onClick={() => setIsOpen(true)}
                    />
                  </div>
                  <div className="text-2xl pt-3 pl-5">
                    {" "}
                    <FontAwesomeIcon
                      icon={faChevronLeft}
                      className="hover:text-sky-600 cursor-pointer"
                      onClick={() =>
                        previousPage(currentPage)
                      }
                    />{" "}
                  </div>
                  <div className="px-5 pt-3 ">
                    {" "}
                    {currentPage + 1} of {totalPage}
                  </div>
                  <div className="text-2xl pt-3 ">
                    {" "}
                    <FontAwesomeIcon
                      icon={faChevronRight}
                      className="hover:text-sky-600 cursor-pointer"
                      onClick={() => nextPage(currentPage)}
                    />{" "}
                  </div>
                </div>
              </div>

              <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table className="w-full text-sm text-left text-gray-500 bg-red-100 dark:text-gray-400">
                  <thead className="text-lg text-gray-200 uppercase bg-gray-50">
                    <tr className="bg-gray-600">
                      <th scope="col" className="px-6 py-3">
                        Customer
                        {sortName ? (
                          <FontAwesomeIcon
                            icon={faCaretDown}
                            onClick={() => sort("name", sortName)}
                            className="pl-3 hover:text-sky-600 cursor-pointer"
                          />
                        ) : (
                          <FontAwesomeIcon
                            icon={faCaretUp}
                            onClick={() => sort("name", sortName)}
                            className="pl-3 hover:text-sky-600 cursor-pointer"
                          />
                        )}
                      </th>
                      <th scope="col" className="px-6 py-3">
                        Mobile Number
                      </th>
                      <th scope="col" className="px-6 py-3">
                        Address
                        {sortAddress ? (
                          <FontAwesomeIcon
                            icon={faCaretDown}
                            onClick={() => sort("Address", sortAddress)}
                            className="pl-3 hover:text-sky-600 cursor-pointer"
                          />
                        ) : (
                          <FontAwesomeIcon
                            icon={faCaretUp}
                            onClick={() => sort("Address", sortAddress)}
                            className="pl-3 hover:text-sky-600 cursor-pointer"
                          />
                        )}{" "}
                      </th>
                      <th scope="col" className="px-6 py-3">
                        Status
                        {sortStatus ? (
                          <FontAwesomeIcon
                            icon={faCaretDown}
                            onClick={() => sort("status", sortStatus)}
                            className="pl-3 hover:text-sky-600 cursor-pointer"
                          />
                        ) : (
                          <FontAwesomeIcon
                            icon={faCaretUp}
                            onClick={() => sort("status", sortStatus)}
                            className="pl-3 hover:text-sky-600 cursor-pointer"
                          />
                        )}{" "}
                      </th>
                      <th scope="col" className="px-6 py-3">
                        <span className="sr-only">Edit</span>
                      </th>
                    </tr>
                  </thead>
                  <tbody className="text-lg">
                    {fetchedData &&
                      fetchedData?.map((item) => (
                        <tr
                          key={item.id}
                          className="border-b bg-gray-100 border-gray-300 hover:bg-gray-300"
                        >
                          <th
                            scope="row"
                            className="px-6 py-4 font-medium whitespace-nowrap text-black"
                          >
                            {item.firstName}, {item.lastName}
                            <div className="text-md text-gray-400 font-medium pt-2">
                              {item.customerEmail}
                            </div>
                          </th>
                          <td className="px-6 py-4">{item.customerPhone}</td>
                          <td className="px-6 py-4">{item.customerAddress}</td>
                          <td className="px-6 py-4">
                            {item.status ? (
                              <div className="text-green-600">Active</div>
                            ) : (
                              <div className="text-red-400">Inactive</div>
                            )}
                          </td>
                          <td className="px-6 py-4 text-right">
                            <div className="flex flex-row ">
                              <div
                                onClick={() => navigateEdit(item.id)}
                                className="font-medium text-blue-400 pr-10 hover:text-blue-600 cursor-pointer"
                              >
                                <FontAwesomeIcon icon={faPenToSquare} />
                              </div>
                              <div
                                onClick={() => navigateDelete(item.id)}
                                className="font-medium text-red-400 hover:text-red-600 cursor-pointer"
                              >
                                <FontAwesomeIcon icon={faTrash} />
                              </div>
                            </div>
                          </td>
                        </tr>
                      ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Create;
