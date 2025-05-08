"use client";
import { authAPIs, endpoints } from '@/utils/apis';
import path from 'path';
import React, { useState } from 'react';
import cookie from "react-cookies"

interface CartItem {
    id: number; 
    name: string; 
    price: number; 
    quantity: number; 
    img: string; 
}

interface CostData {
    trans: number; 
    cost1: number; 
    cost2: number;
    warehouseCost: number;
    cart: Record<string, CartItem>; 
}

interface CheckoutFormProps {
    Cost: CostData | null; 
}

const CheckoutForm: React.FC<CheckoutFormProps> = ({ Cost }) => {
    const [shippingMethod, setShippingMethod] = useState<'fast' | 'normal'>('normal'); 
    const [totalShippingCost, setTotalShippingCost] = useState<number>(Cost ? Cost.cost2 : 0); 

    if (!Cost) {
        return <div className="text-red-500 text-center text-lg">Không có dữ liệu chi phí!</div>;
    }

    const calculateTotalCost = (cartItems: Record<string, CartItem>) => {
        return Object.values(cartItems).reduce((total, item) => total + item.price * item.quantity, 0);
    };

    const totalCost = calculateTotalCost(Cost.cart);

    const handleShippingChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const method = event.target.value;
        setShippingMethod(method as 'fast' | 'normal');
        setTotalShippingCost(method === 'fast' ? Cost.cost1 : Cost.cost2);
    };

    const grandTotal = totalCost + totalShippingCost + Cost.warehouseCost; 

    const handlePayment = async () => {
        if (Cost.cart === null) return;
        const transId = shippingMethod === 'fast' ? 1 : 2;
        const orderPayload = {
            totalPrice: grandTotal,
            transId:  transId, 
            transPrice: totalShippingCost,
            wareHousePrice: Cost.warehouseCost,
            details: Object.values(Cost.cart).map(item => ({
                amount: item.quantity,
                productId: item.id,
                total_price: item.price
            })),
        };
        console.log(orderPayload);
        let totalAmount = grandTotal;
        console.log(totalAmount);
        let url1 = `${endpoints['vnPay']}?amount=${totalAmount}`;
        let token = cookie.load("access-token");
        try {
            let respone = await authAPIs(token).get(url1);
            let {url} = respone.data;
            console.log(url);
            window.location.href = url;
            
        } catch (error) {
            console.info(error);
        }finally{
            cookie.save("order", orderPayload, {path: '/'});
        }
    };

    return (
        <div className="w-[80%] mx-auto p-6 bg-white rounded-lg shadow-lg">
            <h2 className="text-2xl font-bold mb-4 text-center">Thanh toán</h2>

            <div className="bg-gray-100 p-4 rounded-md mb-4">
                <h3 className="font-bold">Sản phẩm</h3>
                {Object.values(Cost.cart).map((item) => (
                    <div key={item.id} className="flex justify-between mt-5 items-center">    
                        <img src={item.img} alt={item.name} className="w-16 h-16 mr-4" />
                        <div className="flex flex-col justify-between w-full">
                            <div><span className="font-bold">{item.name}</span></div>
                            <div className="flex justify-between w-full">
                                
                                <span>đ{item.price.toLocaleString()}</span>
                                <span>x {item.quantity.toLocaleString()}</span>
                            </div>
                        </div>
                    </div>
                ))}
                <div className="flex mt-2 ml-[70%] ">
                    <span className="font-bold mr-5">Tổng tiền trong giỏ hàng:</span>
                    <span>đ{totalCost.toLocaleString()}</span>
                </div>
            </div>

            <div className="mb-4">
                <label className="flex items-center">
                    <input 
                        type="checkbox" 
                        className="mr-2" 
                    />
                    Bảo hiểm bảo vệ người tiêu dùng (đ6.999)
                </label>
                <p className="text-sm text-gray-600">Giúp bảo vệ bạn khỏi các nguy hiểm, thiệt hại gây ra bởi sản phẩm được bảo hiểm trong quá trình sử dụng.</p>
            </div>

            <div className="mb-4">
                <h3 className="font-bold">Phương thức vận chuyển</h3>
                <label className="flex items-center mt-2">
                    <input 
                        type="radio" 
                        value="normal" 
                        checked={shippingMethod === 'normal'} 
                        onChange={handleShippingChange} 
                        className="mr-2"
                    />
                    Hàng Công Kênh (đ{Cost.cost2}) 
                </label>
                <label className="flex items-center mt-2">
                    <input 
                        type="radio" 
                        value="fast" 
                        checked={shippingMethod === 'fast'} 
                        onChange={handleShippingChange} 
                        className="mr-2"
                    />
                    Vận chuyển nhanh (đ{Cost.cost1})
                </label>
            </div>

            <div className="mb-4">
                <span className="font-semibold">Tổng số tiền (1 sản phẩm): </span>
                <span>đ{totalCost.toLocaleString()}</span>
            </div>

            <div className="mb-4">
                <span className="font-semibold">Tổng thanh toán: </span>
                <span className="text-xl font-bold text-red-600">đ{grandTotal.toLocaleString()}</span>
            </div>

            <button onClick={handlePayment} className="w-full bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600 transition">Đặt hàng</button>
        </div>
    );
};

export default CheckoutForm;
