"use client";
import React, { useState } from "react";
import { api } from "@/src/utils/api"; 
import { authAPIs } from "@/utils/apis";
import cookie from "react-cookies";

export interface Product {
    id: number;
    quantity: number;
    productId: number;
    productName: string;
}

export interface OrdersProps {
    id: number;
    create_date: string | null;
    transaction: string;
    active: boolean;
    orderById: number;
    orderByName: string;
    wareHouseId: number;
    wareHouseName: string;
    details: Product[];
}

interface OrderManufactureProps {
    initialOrders: OrdersProps[];
}

const OrderManufactureForm: React.FC<OrderManufactureProps> = ({ initialOrders }) => {
    const [activeTab, setActiveTab] = useState("received"); 
    const [orders, setOrders] = useState(initialOrders); 


    const filteredOrders = {
        received: orders.filter(order => order.transaction === "Đã Nhận"),
        inProgress: orders.filter(order => order.transaction === "Đang Thực Hiện"),
        completed: orders.filter(order => order.transaction === "Đã Hoàn Tất")
    };


    const handleUpdateOrder = async (orderId: number) => {
        try {

            await  await authAPIs(cookie.load("access-token")).put(`orderManufacture/${orderId}`);

            setOrders(prevOrders =>
                prevOrders.map(order =>
                    order.id === orderId ? { ...order, transaction: "Đang Thực Hiện" } : order
                )
            );

            alert("Order status updated successfully");
        } catch (error) {
            console.error("Error updating order status:", error);
            alert("Failed to update order status");
        }
    };


    const handleAddProductStock = async (orderId: number) => {
        try {

            await authAPIs(cookie.load("access-token")).post(`productStock/${orderId}`);

            alert("Product stock added successfully");
        } catch (error) {
            console.error("Error adding product stock:", error);
            alert("Failed to add product stock");
        }
    };

    return (
        <div className="space-y-4">
            <div className="flex space-x-4 mb-4">
                <button
                    className={`px-4 py-2 rounded ${activeTab === "received" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
                    onClick={() => setActiveTab("received")}
                >
                    Đã Nhận
                </button>
                <button
                    className={`px-4 py-2 rounded ${activeTab === "inProgress" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
                    onClick={() => setActiveTab("inProgress")}
                >
                    Đang Thực Hiện
                </button>
                <button
                    className={`px-4 py-2 rounded ${activeTab === "completed" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
                    onClick={() => setActiveTab("completed")}
                >
                    Đã Hoàn Tất
                </button>
            </div>

            <div className="space-y-4">
                {filteredOrders[activeTab].map(order => (
                    <div key={order.id} className="border border-gray-300 p-4 rounded-lg shadow-md">
                        <h3 className="text-lg font-semibold">Order ID: {order.id}</h3>
                        <p className="text-gray-600">Create Date: {new Date(order.create_date || "").toLocaleString()}</p>
                        <p className="text-gray-600">Transaction Status: {order.transaction}</p>
                        <p className="text-gray-600">Active: {order.active ? "Yes" : "No"}</p>
                        <p className="text-gray-600">Ordered By: {order.orderByName} (ID: {order.orderById})</p>
                        <p className="text-gray-600">Warehouse: {order.wareHouseName} (ID: {order.wareHouseId})</p>

                        <h4 className="mt-4 text-md font-semibold">Details:</h4>
                        <div className="flex flex-wrap space-x-4 mt-2">
                            {order.details.map(detail => (
                                <div key={detail.id} className="flex-1 border-l border-gray-300 pl-4">
                                    <p className="text-gray-800">Tên sản phẩm: {detail.productName}</p>
                                    <p className="text-gray-800">Số lượng cần sản xuất: {detail.quantity}</p>
                                </div>
                            ))}
                        </div>

                        {order.transaction === "Đã Nhận" && (
                            <div className="mt-4 flex space-x-2">
                                <button
                                    className="bg-green-500 text-white px-4 py-2 rounded"
                                    onClick={() => handleUpdateOrder(order.id)}
                                >
                                    Cập nhật Đơn Hàng
                                </button>
                            </div>
                        )}
                        {order.transaction === "Đang Thực Hiện" && (
                            <div className="mt-4 flex space-x-2">
                                <button
                                    className="bg-yellow-500 text-white px-4 py-2 rounded"
                                    onClick={() => handleAddProductStock(order.id)}
                                >
                                    Thêm Kho Sản Phẩm
                                </button>
                            </div>
                        )}
                        {order.transaction === "Đã Hoàn Tất" && (
                            <p className="mt-4 text-gray-600">Đã hoàn tất</p>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default OrderManufactureForm;
