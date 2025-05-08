"use client";
import { authAPIs } from "@/utils/apis";
import React, { useEffect, useState } from "react";
import cookie from "react-cookies";

// Các interface cho chi tiết đơn hàng
interface OrderDetail {
    id: number;
    amount: number;
    total_price: number;
    productId: number;
    productName: string;
}

interface LogisticRequest {
    id: number;
    type: string;
    warehouseName: string;
    entryDate: string | null;
    exitDate: string | null;
}

interface OrderExportRequest {
    id: number;
    state: string;
    typeBuy: number | null;
    totalPrice: number;
    createDate: string | null;
    orderById: number;
    orderByName: string;
    orderForId: number;
    orderForName: string;
    transPrice: number;
    transId: number;
    transName: string;
    wareHousePrice: number;
    details: OrderDetail[];
    logisticsRequests: LogisticRequest[] | null;
}

// Interface cho dữ liệu của OrderBill
interface OrderBill {
    id: number;
    name: string;
    address: string;
    type: string;
    orderExportRequestList: OrderExportRequest[] | null;
}

interface OrderBillFormProps {
    orderBill: OrderBill;
}

const OrderBillForm: React.FC<OrderBillFormProps> = ({ orderBill }) => {
    const [activeTab, setActiveTab] = useState<"arrived" | "coming">("arrived");
    const token = cookie.load("access-token");
    const [selectedWarehouse, setSelectedWarehouse] = useState<number | null>(null);
    const [showWarehouseForm, setShowWarehouseForm] = useState(false);
    const [warehouses, setWarehouses] = useState<any[]>([]);
    const filteredRequests =
        orderBill.orderExportRequestList?.filter((request) =>
            (activeTab === "arrived" ? request.state === "arrived" : request.state === "coming")
        ) || [];

    useEffect(() => {
        if (showWarehouseForm) {
            fetchWarehouses();
        }
    }, [showWarehouseForm]);

    const fetchWarehouses = async () => {
        try {
            const response = await authAPIs(token).get("wareHouse");
            setWarehouses(response.data);
        } catch (error) {
            console.error("Error fetching warehouses:", error);
        }
    };

    const confirmExportOrder = async (orderId: number) => {
        if (selectedWarehouse) {
            try {
                let params = {
                    warehouse: selectedWarehouse
                };
                const response = await authAPIs(token).post(`updateLogictis/${orderId}/${orderBill.id}`, params);
                alert(response.data);
                window.location.reload();
            } catch (error) {
                console.error("Error exporting order:", error);
            }
        }
    };

    const handleReceiveOrder = async (oid: number) => {
        try {
            const response = await authAPIs(token).put(`completeOrder/${oid}/${orderBill.id}`);
            alert(response.data);
            window.location.reload();
        } catch (error) {
            console.error("Error receiving order:", error);
            alert("Có lỗi xảy ra khi nhận đơn hàng.");
        }
    };

    const handleToCustomer = async (oid: number) => {
        try {
            const response = await authAPIs(token).put(`updateLogictis/${oid}/${orderBill.id}`);
            alert(response.data);
            window.location.reload();
        } catch (error) {
            console.error("Error receiving order:", error);
            alert("Có lỗi xảy ra khi nhận đơn hàng.");
        }
    };

    const handleExportOrder = () => {
        setSelectedWarehouse(null);
        setShowWarehouseForm(true);
    };

    return (
        <div className="p-6 bg-white shadow-lg rounded-lg">
            <h2 className="text-2xl font-bold mb-4">Order Bill Information</h2>
            <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-4">
                <div><strong>ID:</strong> {orderBill.id}</div>
                <div><strong>tên:</strong> {orderBill.name}</div>
                <div><strong>địa chỉ:</strong> {orderBill.address}</div>
            </div>

            {/* Tabs */}
            <div className="flex border-b mb-4">
                <button
                    className={`px-4 py-2 ${activeTab === "arrived" ? "border-b-2 border-blue-500 font-semibold" : ""}`}
                    onClick={() => setActiveTab("arrived")}
                >
                    Arrived
                </button>
                <button
                    className={`px-4 py-2 ${activeTab === "coming" ? "border-b-2 border-blue-500 font-semibold" : ""}`}
                    onClick={() => setActiveTab("coming")}
                >
                    Coming
                </button>
            </div>

            <h3 className="text-xl font-semibold mb-2">Các đơn hàng xuất:</h3>
            {filteredRequests.length > 0 ? (
                filteredRequests.map((request) => (
                    <div key={request.id} className="border border-gray-300 p-4 rounded-lg mb-4">
                        <p><strong>Trạng thái:</strong> {request.state}</p>
                        <p><strong>Tổng giá:</strong> {request.totalPrice}</p>
                        <h4 className="mt-4 font-semibold">Details:</h4>
                        {request.details.map((detail) => (
                            <div key={detail.id} className="flex border-t border-gray-200 pt-2 items-center">
                                <div className="flex-1"><strong>mã sản phẩm:</strong> {detail.productId}</div>
                                <div className="flex-1"><strong>tên sản phẩm:</strong> {detail.productName}</div>
                                <div className="flex-1"><strong>số lượng:</strong> {detail.amount}</div>
                                <div className="flex-1"><strong>tổng giá:</strong> {detail.total_price}</div>
                            </div>
                        ))}

                        <div className="mt-4">
                            {activeTab === "arrived" ? (
                                <>
                                    <button onClick={handleExportOrder} 
                                        className="bg-green-500 text-white px-4 mr-1 py-2 rounded hover:bg-green-600 transition duration-200">Xuất đơn hàng</button>
                                    <button onClick={() => handleReceiveOrder(request.id)} 
                                        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition duration-200">Xuất đơn hàng cho khách hàng</button>
                                </>
                            ) : (
                                <button
                                    className="bg-orange-500 text-white px-4 py-2 rounded hover:bg-orange-600 transition duration-200"
                                    onClick={() => handleToCustomer(request.id)}
                                >
                                    Nhận đơn hàng
                                </button>
                            )}
                            {showWarehouseForm && (
                                <div className="mt-4 p-4 border border-gray-300 rounded-lg">
                                    <h4 className="text-lg font-semibold mb-2">Chọn kho:</h4>
                                    <select
                                        value={selectedWarehouse || ''}
                                        onChange={(e) => setSelectedWarehouse(Number(e.target.value))}
                                        className="border border-gray-400 rounded p-2 mb-4"
                                    >
                                        <option value="" disabled>Chọn kho...</option>
                                        {warehouses.map(warehouse => (
                                            <option key={warehouse.id} value={warehouse.id}>
                                                {warehouse.name} - {warehouse.address}
                                            </option>
                                        ))}
                                    </select>
                                    <button
                                        onClick={() => confirmExportOrder(request.id)}
                                        className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 transition duration-200"
                                    >
                                        Xác nhận xuất đơn hàng
                                    </button>
                                    <button
                                        onClick={() => setShowWarehouseForm(false)}
                                        className="ml-2 text-gray-700 underline"
                                    >
                                        Hủy
                                    </button>
                                </div>
                            )}
                        </div>
                    </div>
                ))
            ) : (
                <p>No order export requests available for this tab.</p>
            )}
        </div>
    );
};

export default OrderBillForm;
