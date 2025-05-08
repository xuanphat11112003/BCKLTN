"use client";
import React, { useState } from "react";
import Link from "next/link";
import ParticlesComponent from "./Partile.js";
import styles from "@/pages/login/login.module.css"; 

import { api, authAPIs, endpoints } from "@/utils/apis.js";

import { useAuth } from "@/context/AuthContext.jsx";
import { useRouter } from "next/router.js";
import cookie from "react-cookies"

const Login: React.FC = () => {
  const router = useRouter();
  const {dispatch} = useAuth();


  const onFinish = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const values = Object.fromEntries(formData.entries());

    const { username, password } = values;
    console.log(values);
    try {
      let url = endpoints.login;
      console.log(url);
      const res = await api.post(url, values);
      if (res.data) {
        const { accessToken } = res.data;
        cookie.save("access-token", `Bearer ${accessToken}`);
        console.log("Đăng nhập thành công ", res.data);
      }
    } catch (error) {
      console.error("Đăng nhập thất bại:", error);
      alert("Đăng nhập thất bại! Vui lòng kiểm tra thông tin.");
    }

    let baseUrl = endpoints.currentUser;
    
    try {
      const responseUser = await authAPIs(cookie.load("access-token")).post(baseUrl);
      dispatch({ type: "LOGIN", payload: responseUser.data });
      const role = responseUser.data.role; 
      cookie.save("user", responseUser.data); 
      if (role === "ROLE_AGENCY") {
        router.push("/agency");
      } else if (role === "ROLE_EMPLOYEE") {
        router.push("/employee");
      }
    } catch (error) {
      console.error(error);
      alert("không đủ quyền truy cập")
    } 
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <ParticlesComponent className={styles.particles} />

      <div className="w-full max-w-md mx-auto z-10">
        <fieldset className="bg-white p-6 rounded-lg shadow-md">
          <div className="text-center mb-4">
            <h1 className="text-2xl font-bold">Đăng nhập</h1>
          </div>
          <form onSubmit={onFinish} autoComplete="off">
            <div className="mb-4">
              <label htmlFor="username" className="block text-sm font-medium text-gray-700">Username</label>
              <input
                type="text"
                name="username"
                id="username"
                placeholder="Username"
                required
                className="border rounded-lg p-2 w-full"
              />
            </div>

            <div className="mb-4">
              <label htmlFor="password" className="block text-sm font-medium text-gray-700">Mật khẩu</label>
              <input
                type="password"
                name="password"
                id="password"
                placeholder="Password"
                required
                className="border rounded-lg p-2 w-full"
              />
            </div>

            <div className="mb-4">
              <button
                type="submit"
                className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
              >
                Đăng nhập
              </button>
            </div>
          </form>

          <Link href="/" className="text-blue-500 hover:underline">
            Quay lại trang chủ
          </Link>

          <div className="mt-4">
            <hr className="my-2" />
            <div className="text-center">
              Chưa có tài khoản?{" "}
              <Link href="/auth/register" className="text-blue-500 hover:underline">Đăng ký tại đây</Link>
            </div>
          </div>
        </fieldset>
      </div>
    </div>
  );
};

export default Login;
