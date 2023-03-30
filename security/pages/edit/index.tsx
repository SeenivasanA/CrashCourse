import React, { SyntheticEvent, useState, useEffect } from "react";
import { useRouter } from "next/router";
import bgimage from "../../public/bottle.jpg";
import Image from 'next/image'
import NavigationBar from "@/component/navigatiionBar";

type Props = {
    customer: {
        id: string | string[] | undefined;
    };
  };

const Edit = ({customer} : Props) => {

  const [firstName,setFirstName] = useState('')
  const [lastName,setLastName] = useState('')
  const [email,setEmail] = useState('')
  const [password,setPass] = useState('')
  const [phoneNo,setPhoneNo] = useState('')
  const [address,setAddress] = useState('')
  const router = useRouter();
  const [fetchedData, setFetchedData] = useState()
  const [id,setId]=useState(0)

  let customerId: any;
  if(router.query){
    customerId = router.query.id
    console.log(customerId)
  }
  useEffect(() => {

    
    const fetchData = async () =>{
        if(customerId != undefined){
      try{
        const url = "http://localhost:8080/customers/edit/"+customerId;
          const response =await fetch (url,{
              method: "GET",
              headers: {"content-type":"application/json"}
          });
          const data = await response.json();
          console.log("edit page data" + JSON.stringify(data))
          setFetchedData(data)
          setFirstName(data.firstName)
          setLastName(data.lastName)
          setAddress(data.customerAddress)
          setEmail(data.customerEmail)
          setPhoneNo(data.customerPhone)
          setPass(data.customerPassword)
          setId(data.id)
      }catch(error){
          console.log("error"+ error);
      }
    }
  };

  fetchData();
  }, [customerId]);
  


  const user = {
    username: "Seenivasan",
    email: "Seenivasan@gmail.com",
  };
  
  const submit = async (e: SyntheticEvent) => {
    e.preventDefault();
    console.log("inside submit");
    if(customerId != undefined){
        const url = "http://localhost:8080/customers/";
    const response = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        "id": customerId,
        "firstName" : firstName,
        "customerEmail":email,
        "lastName" : lastName,
        "customerPassword":password,
        "customerPhone": phoneNo,
        "customerAddress":address
      }),
    });

    if(response.ok){
      router.push('/dashboard')
    }
    }
    
  };


  return (
    <>
            <div>
            <div className="blur-[5px] -left-5 -top-5" style={{zIndex:-1,position:"fixed",width:"110vw",height:"110vh"}}>
                <Image src={bgimage} alt="login_image" fill style={{ }}/>
            </div>
            <div className="fixed top-0 w-screen z-10">
                <NavigationBar/>
            </div>
            <div className="p-20 pb-40 overflow-y-auto mt-20">
            
            <div className="grid h-screen place-items-center">
            <div className="bg-gray-900 rounded-lg p-10 opacity-80 w-1/2">
            <div className="flex flex-col justify-center md:justify-start my-auto">
                            <p className="text-center text-gray-200 font-bold text-3xl">Edit</p>
                            <form className="flex flex-col pt-3 md:pt-8">
                                <div className="flex flex-col pt-4">
                                    <label  className="text-3xl pb-5 text-white">FirstName</label>
                                    <input type="text" value={firstName} id="firstname" placeholder="Firstname" className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" onChange={e => setFirstName(e.target.value)}/>
                                </div>
                
                                <div className="flex flex-col pt-4">
                                    <label  className="text-3xl py-5 text-white">LastName</label>
                                    <input type="text" value={lastName} id="lastname" placeholder="Lastname" className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" onChange={e => setLastName(e.target.value)}/>
                                </div>
                                <div className="flex flex-col pt-4">
                                    <label  className="text-3xl pb-5 text-white">UserEmail</label>
                                    <input type="text" value={email} id="email" placeholder="your@email.com" className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" onChange={e => setEmail(e.target.value)}/>
                                </div>
            
                                <div className="flex flex-col pt-4">
                                    <label  className="text-3xl pb-5 text-white">Mobile No.</label>
                                    <input type="number" value={phoneNo} id="number" placeholder="Mobile Number" className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" onChange={e => setPhoneNo(e.target.value)}/>
                                </div>
                
                                <div className="flex flex-col pt-4">
                                    <label  className="text-3xl py-5 text-white">Address</label>
                                    <input type="text" value={address} id="address" placeholder="Address " className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" onChange={e => setAddress(e.target.value)}/>
                                </div>
                              
                                <button type="submit" value={"Submit"} onClick={e => submit(e)} className=" text-lg my-10 text-white bg-gradient-to-r from-cyan-400 via-cyan-500 to-cyan-600 hover:bg-gradient-to-br focus: focus:outline-none focus: dark:focus: font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2">Submit</button>
                            
                            </form>
                    </div>


            </div>
            </div>
            </div>

            </div>


</>
  );
};

export default Edit;
