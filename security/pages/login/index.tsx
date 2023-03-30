import React, { SyntheticEvent, useState } from "react";
import bgimage from "../../public/bottle.jpg";
import Image from 'next/image'
import { useRouter } from "next/router";

const Login = () =>{

    const [email,setEmail] = useState('')
    const [password,setPass] = useState('')
    const router = useRouter();

    const submit = async (e: SyntheticEvent) => {
        e.preventDefault();
        console.log("inside submit");
        const response = await fetch("http://localhost:8080/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            "username":email,
            "password": password,
          }),
        });
      
        if (response.ok) {
        //   const data = await response.json();
        console.log(" auth ---" +JSON.stringify(response.body))
        //   sessionStorage.setItem("jwt", data.jwt);
        //   sessionStorage.setItem("username", data.username);
        //   sessionStorage.setItem("email", data.useremail);
        //   await   router.push({pathname: '/dashboard'});
        } else {
          console.log("Login failed");
        }
      };
      
      
     
    return(
        <div>
        <div className="blur-[5px] -left-5 -top-5" style={{zIndex:-1,position:"fixed",width:"110vw",height:"110vh"}}>
            <Image src={bgimage} alt="login_image" fill style={{ }}/>
        </div>
        
         
        <div className="grid h-screen place-items-center">
        <div className="card opacity-80 w-1/2 h-3/4">
        

        <div className="flex flex-col justify-center md:justify-start my-auto pt-8 md:pt-0 px-8 md:px-24 lg:px-32">
                <p className="text-center text-3xl font-bold">Welcome</p>
                <form className="flex flex-col pt-3 md:pt-8">
                    <div className="flex flex-col pt-4">
                        <label  className="text-3xl pb-5 text-white">Email</label>
                        <input type="email" id="email" placeholder="your@email.com" className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" onChange={e => setEmail(e.target.value)}/>
                    </div>
    
                    <div className="flex flex-col pt-4">
                        <label  className="text-3xl py-5 text-white">Password</label>
                        <input type="password" id="password" placeholder="Password" className="text-2xl shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" onChange={e => setPass(e.target.value)}/>
                    </div>
                  
                    <button type="submit" value={"Log In"} onClick={e => submit(e)} className="my-10 text-white bg-gradient-to-r from-cyan-400 via-cyan-500 to-cyan-600 hover:bg-gradient-to-br focus: focus:outline-none focus: dark:focus: font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2">Log In</button>
                
                </form>
                <div className="text-center text-2xl pt-12 pb-12 text-white">
                    <p>Don't have an account? <a href="register.html" className="underline font-semibold">Register here.</a></p>
                </div>
        </div>


        </div>
        </div>

        </div>
    )
}

export default Login;