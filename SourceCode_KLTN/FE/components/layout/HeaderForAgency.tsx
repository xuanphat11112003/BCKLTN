"use client";
import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { useAuth } from "@/context/AuthContext"; 
import { useMyCart } from "@/context/CartContext";
import { Badge } from "react-bootstrap";
import { FaShoppingCart, FaUserCircle, FaSignOutAlt, FaClipboardList } from "react-icons/fa";

const HeaderForAgency: React.FC = () => {
    const nav = useRouter();
    const { cartCounter, setDispatch } = useMyCart();
    const cartItems = cartCounter.cartItems;
    const { state, dispatch } = useAuth(); 
    const { user } = state; 
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [scrolled, setScrolled] = useState(false);

    useEffect(() => {
        const handleScroll = () => {
            setScrolled(window.scrollY > 50);
        };

        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, []);
    
    useEffect(() => {
        if (cartCounter) {
            setDispatch(cartCounter.cartItems);
        }
    }, [cartCounter]);

    const toggleDropdown = () => {
        setDropdownOpen(!dropdownOpen);
    };

    const handleLogout = () => {
        dispatch({ type: "LOGOUT" });
        nav.push("/login");
    };

    return (
        <header className={`sticky top-0 flex justify-between z-10 items-center py-4 px-6 transition-colors duration-300 ease-in-out ${scrolled ? 'bg-gray-800 text-white shadow-md' : 'bg-gray-300 text-gray-800 bg-opacity-50'}`}>
            <div className="flex items-center">
                {user === null ? (
                    <a href="/login" className="btn bg-blue-600 text-white mx-2 hover:bg-blue-700 transition">Login</a>
                ) : (
                    <div className="relative">
                        <button
                            className="flex items-center bg-gray-800 text-white rounded-md px-3 py-2 mx-2 transition hover:bg-gray-700"
                            onClick={toggleDropdown}
                        >
                            <img
                                src={user.avatar}
                                alt="Profile"
                                className="rounded-full w-10 h-10 mr-2"
                            />
                            <span className="font-semibold">{user.username}</span>
                        </button>
                        {dropdownOpen && (
                            <ul className="absolute right-0 mt-2 w-48 bg-white text-gray-800 rounded-md shadow-lg">
                                <li>
                                    <a href="/profile" className="flex items-center px-4 py-2 hover:bg-gray-200">
                                        <FaUserCircle className="mr-2" /> Profile
                                    </a>
                                </li>
                                <li>
                                    <a href="/agency/orderBill" className="flex items-center px-4 py-2 hover:bg-gray-200">
                                        <FaClipboardList className="mr-2" /> Đơn hàng
                                    </a>
                                </li>
                                <li>
                                    <button className="flex items-center px-4 py-2 text-left w-full hover:bg-gray-200" onClick={handleLogout}>
                                        <FaSignOutAlt className="mr-2" /> Logout
                                    </button>
                                </li>
                            </ul>
                        )}
                    </div>
                )}
                <a href="/agency/cart" className="relative flex items-center bg-transparent border border-gray-300  px-3 py-2 rounded-md transition hover:bg-gray-100">
                    <FaShoppingCart className="text-xl text-blue" />
                    <Badge className="absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 bg-red-500 w-1/2 text-center rounded-full">{cartItems}</Badge>
                </a>
            </div>
            <div className="text-center mb-4">
                <h1 className="text-3xl font-bold">Welcome to SO4 Store!</h1>
            </div>
            <div className="flex space-x-2">
                <a href="/" className="btn bg-transparent border border-gray-300  px-3 py-2 rounded-md transition hover:bg-gray-100">Home</a>
                <a href="/contact" className="btn bg-transparent border border-gray-300  px-3 py-2 rounded-md transition hover:bg-gray-100">Contact Us</a>
            </div>
        </header>
    );
};

export default HeaderForAgency;
