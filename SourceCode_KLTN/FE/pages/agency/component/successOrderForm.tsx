"use client";
import React, { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import { useRouter } from "next/router";
import cookie from "react-cookies";
import { authAPIs, endpoints } from "@/utils/apis";
import { FaCheckCircle, FaTimesCircle } from "react-icons/fa";
import { useMyCart } from "@/context/CartContext";

const SuccessOrderForm: React.FC = () => {
    const router = useRouter();
    const [order, setOrder] = useState<Record<string, any> | null>(null);
    const [isPaymentValid, setIsPaymentValid] = useState(false);
    const [isClient, setIsClient] = useState(false);
    const { cartCounter, setDispatch } = useMyCart(); 

    useEffect(() => {
        setIsClient(true);
        const { vnp_TransactionStatus, vnp_SecureHash } = router.query;
        const orderFromCookie = cookie.load("order");
        console.log(orderFromCookie);
        if (vnp_TransactionStatus === "00" && vnp_SecureHash && orderFromCookie) {
            setIsPaymentValid(true);
            setOrder(orderFromCookie);
            createOrder(orderFromCookie);
        } else {
            setIsPaymentValid(true);
        }
    }, [router.query, router]);

    const createOrder = async (order: any) => {
        const url = `${endpoints['exportorder']}`;
        try {
            const response = await authAPIs(cookie.load("access-token")).post(url, order);
            console.log("Order created successfully:", response.data);
            cookie.remove("order", { path: '/' });
            cookie.remove("cart", { path: '/' }); 
        } catch (error) {
            console.error("Error creating order:", error);
        }finally{
            setDispatch({ type: "update", payload: 0 });
        }
    };

    const goToHomePage = () => {
        router.push("/agency");
    };

    if (!isClient) return null;

    return (
        <div className="flex flex-col items-center justify-center min-h-screen text-center bg-gray-100 p-6">
            {isPaymentValid ? (
                <>
                    <div className="mt-8">
                        <FaCheckCircle className="text-green-500 text-6xl" />
                    </div>
                    <h1 className="text-green-600 text-4xl font-bold">Cảm ơn bạn đã thanh toán thành công!</h1>
                    <p className="mt-4 text-lg text-gray-700">Đơn hàng của bạn đã được xử lý và chúng tôi sẽ giao hàng sớm nhất.</p>
                    {order && (
                        <div className="mt-4 bg-white shadow-md rounded-lg p-6">
                            <h2 className="text-xl font-semibold">Thông tin hóa đơn</h2>
                            <p className="mt-2">Cảm ơn bạn đã đặt đơn hàng !</p>
                            <p className="mt-2"><strong>Tổng số tiền:</strong> {order.totalPrice} VNĐ</p>
                            <p className="mt-2">
                                <strong>Ngày đặt hàng:</strong> {new Date(order.date || Date.now()).toLocaleDateString()}
                            </p>
                        </div>
                    )}
                    <button 
                        onClick={goToHomePage} 
                        className="mt-6 px-6 py-3 bg-blue-500 text-white font-semibold rounded-lg shadow-md hover:bg-blue-600 transition duration-300 ease-in-out transform hover:-translate-y-1 hover:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75"
                    >
                        Quay lại Trang chủ
                    </button>
                    
                </>
            ) : (
                <>
                     <div className="mt-8">
                        <FaTimesCircle className="text-red-500 text-6xl" />
                    </div>
                    <h1 className="text-red-600 text-4xl font-bold">Thanh toán không hợp lệ hoặc đã gặp lỗi.</h1>
                    <Button variant="primary" onClick={goToHomePage} className="mt-6 px-6 py-3 bg-blue-500 text-white font-semibold rounded-lg shadow-md hover:bg-blue-600 transition duration-300 ease-in-out transform hover:-translate-y-1 hover:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75"
                    >
                        Quay lại Trang chủ
                    </Button>
                   
                </>
            )}
        </div>
    );
};

export default SuccessOrderForm;
