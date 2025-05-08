"use client";
import React from 'react';
import Link from 'next/link';
import { useRouter } from 'next/router';
import { useAuth } from '@/context/AuthContext';  

const Header: React.FC = () => {
  const { state, dispatch } = useAuth();  
  const router = useRouter();

  const handleLogout = () => {
    dispatch({ type: 'LOGOUT' });
    router.push("/login"); 
  };

  return (
    <nav className="bg-blue-500 shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between py-3">
          <div>
            <Link href="/employee" passHref legacyBehavior>
              <a className="text-white text-2xl font-bold">Supply Chain Management</a>
            </Link>
          </div>
          <div className="hidden md:flex space-x-4">
            <Link href="/" passHref legacyBehavior>
              <a className="text-white">Home</a>
            </Link>
            <Link href="/profile" passHref legacyBehavior>
              <a className="text-white">Profile</a>
            </Link>
            <Link href="/settings" passHref legacyBehavior>
              <a className="text-white">Settings</a>
            </Link>
          </div>
          <div>
            {state.user === null ? (
              <Link href="/login" passHref legacyBehavior>
                <a className="text-red-500 font-bold">Đăng nhập</a>
              </Link>
            ) : (
              <div className="flex items-center space-x-4">
                <Link href="/profile" passHref legacyBehavior>
                  <a className="text-green-500 flex items-center space-x-2">
                    <img
                      src={state.user.avatar}
                      alt="User Avatar"
                      className="w-6 h-6 rounded-full"
                    />
                    <span>Chào {state.user.username}!</span>
                  </a>
                </Link>
                <button
                  className="bg-red-500 text-white px-4 py-2 rounded-md"
                  onClick={handleLogout}
                >
                  Đăng xuất
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Header;
