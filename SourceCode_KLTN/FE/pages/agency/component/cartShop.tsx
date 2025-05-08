"use client"; 
import { startTransition, useEffect, useState } from "react"; 
import cookie from "react-cookies"; 
import { useRouter } from "next/router"; 
import { useMyCart } from "@/context/CartContext";
import { FaPlus, FaMinus, FaTimes } from 'react-icons/fa';

const CartShop: React.FC = () => {
    const { cartCounter, setDispatch } = useMyCart(); 
    const [cart, setCart] = useState<Record<string, any> | null>(cookie.load("cart") || null); 
    const [isClient, setIsClient] = useState(false); 
    const router = useRouter();

    const handleRemove = (id: string) => {
        const updatedCart = { ...cart }; 
        delete updatedCart[id]; 
        setCart(updatedCart); 
    
        const newTotal = Object.values(updatedCart).reduce((acc, item) => acc + item.quantity, 0); 
    
        if (newTotal === 0) {
            cookie.remove("cart", { path: '/' }); 
            setDispatch({ type: "update", payload: 0 }); 
        } else {
            cookie.save("cart", updatedCart, { path: '/' }); 
            setDispatch({ type: "update", payload: newTotal }); 
        }
    };

    const handleQuantityChange = (id: string, quantity: number) => {
        if (quantity < 1) {
            handleRemove(id);
            return;
        }

        const updatedCart = { ...cart, [id]: { ...cart[id], quantity } };
        setCart(updatedCart);
        cookie.save("cart", updatedCart);
        
        const newTotal = Object.values(updatedCart).reduce((acc, item) => acc + item.quantity, 0);
        setDispatch({ type: "update", payload: newTotal });
    };

    const increaseQuantity = (id: string) => {
        const updatedCart = { ...cart };
        updatedCart[id].quantity += 1;
        handleQuantityChange(id, updatedCart[id].quantity);
    };

    const decreaseQuantity = (id: string) => {
        const updatedCart = { ...cart };
        if (updatedCart[id].quantity > 1) {
            updatedCart[id].quantity -= 1;
            handleQuantityChange(id, updatedCart[id].quantity);
        } else {
            handleRemove(id);
        }
    };

    useEffect(() => {
        startTransition(() => {
            setIsClient(true); 
            const loadedCart = cookie.load("cart") || null; 
            setCart(loadedCart);
        });
    }, []);

    if (!isClient) return null;

    const handleCheckout = () => {
        router.push("checkout");
    };

    if (cart === null || Object.keys(cart).length === 0) {
        return <div className="text-center py-8 text-yellow-500">KHÔNG có sản phẩm trong giỏ!</div>;
    }

    return (
        <div className="container mx-auto p-6">
            <h1 className="text-center text-2xl text-blue-500 font-bold mb-6">GIỎ HÀNG</h1>
            <table className="min-w-full bg-white border border-gray-300">
                <thead>
                    <tr className="bg-gray-100">
                        <th className="p-3 text-left">Hình ảnh</th>
                        <th className="p-3 text-left">Tên sản phẩm</th>
                        <th className="p-3 text-left">Giá</th>
                        <th className="p-3 text-left">Số lượng</th>
                        <th className="p-3"></th>
                    </tr>
                </thead>
                <tbody>
                    {Object.values(cart).map((c: any) => (
                        <tr key={c.id} className="border-b">
                            <td className="p-3">
                                <img src={c.img} alt={c.name} className="w-24 h-auto object-cover rounded-lg" />
                            </td>
                            <td className="p-3">
                                <div className="font-semibold">{c.name}</div>
                            </td>
                            <td className="p-3 text-green-600 font-bold">{c.price} VNĐ</td>
                            <td className="p-3 flex items-center">
                                <button
                                    className="bg-red-500 text-white p-1 rounded-l hover:bg-red-600 transition duration-300 flex items-center"
                                    onClick={() => decreaseQuantity(c.id)}
                                >
                                     <FaMinus />
                                </button>
                                <input
                                    type="number"
                                    value={c.quantity}
                                    min="1"
                                    className="w-16 text-center border border-gray-300 mx-2"
                                    onChange={(e) => handleQuantityChange(c.id, Number(e.target.value))}
                                />
                                <button
                                    className="bg-green-500 text-white p-1 rounded-r hover:bg-green-600 transition duration-300 flex items-center"
                                    onClick={() => increaseQuantity(c.id)}
                                >
                                     <FaPlus />
                                </button>
                            </td>
                            <td className="p-3 text-right">
                                <button
                                    className="bg-red-500 text-white p-2 rounded-full hover:bg-red-600 transition duration-300"
                                    onClick={() => handleRemove(c.id)}
                                >
                                    <FaTimes />
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="text-right mt-6">
                <button
                    className="bg-green-500 text-white py-2 px-6 rounded-lg hover:bg-green-600 transition duration-300"
                    onClick={handleCheckout}
                >
                    Thanh Toán
                </button>
            </div>
        </div>
    );
};

export default CartShop;
