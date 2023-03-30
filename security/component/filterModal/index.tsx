import React, { Fragment, SyntheticEvent, useState } from "react";
import { useRouter } from "next/router";

const FilterModal = () => {
  const router = useRouter();

  function dashboard(data: any) {
    router.push({
      pathname: "/dashboard",
      query: { username: data.username, email: data.email },
    });
  }

  return (
    <div className="fixed inset-0 bg-black opacity-50 backdrop-blur-sm">
        hipjijnponpo;n
    </div>
  );
};

export default FilterModal;
