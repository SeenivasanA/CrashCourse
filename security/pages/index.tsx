import Head from "next/head";
import { Inter } from "next/font/google";
import Router, { useRouter } from "next/router";
import { useEffect } from "react";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const router = useRouter();

  useEffect(() => {
    router.push("/dashboard");
  }, []);

  return (
    <>
      <Head>
        <title>E Commerse App</title>
      </Head>
      <div></div>
    </>
  );
}
