"use client";
import dynamic from "next/dynamic";
import { useEffect, useState } from "react";
import axios from "axios";
import { authAPIs, endpoints } from "@/utils/apis";
import cookie from "react-cookies";
import "@/pages/agency/component/index.module.css";

const MapComponent = dynamic(() => import('./map'), { ssr: false });

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
  address: string;
  entryDate: string | null;
  exitDate: string | null;
}

interface Order {
  id: number;
  state: string;
  totalPrice: number;
  orderByName: string;
  orderForName: string;
  transPrice: number;
  wareHousePrice: number;
  details: OrderDetail[];
  logicticsRequests: LogisticRequest[];
}

interface OrdersProps {
  orders: Order[];
}

const OrderBillForm: React.FC<OrdersProps> = ({ orders }) => {
  const [selectedState, setSelectedState] = useState<string>('Đang Vận Chuyển');
  const [expandedOrderId, setExpandedOrderId] = useState<number | null>(null);
  const [selectedOrderToViewMap, setSelectedOrderToViewMap] = useState<Order | null>(null);
  const [coordinates, setCoordinates] = useState<[number, number][]>([]);

  useEffect(() => {
    console.log(orders);
  },[])
  const user = cookie.load('user');

  const filteredOrders = orders.filter(order => order.state === selectedState);

  const getLatestLogisticStatus = (logisticRequests: LogisticRequest[] | null | undefined) => {
    if (!logisticRequests || logisticRequests.length === 0) return null;
    return logisticRequests[logisticRequests.length - 1];
  };

  const getOrderWarehouseAddress = (logisticsRequests: LogisticRequest[]) => {
    const lastLogistic = getLatestLogisticStatus(logisticsRequests);
    return lastLogistic?.warehouseName?.trim() ? lastLogistic.address : null;
  };

  const handleShowMap = async (order: Order) => {
    const orderAddress = getOrderWarehouseAddress(order.logicticsRequests);
    const userAddress = user?.address;
    if (userAddress && orderAddress) {
      try {
        const res = await axios.get('http://localhost:8080/chuoicungung/api/getCoordinates', {
          params: { orderAddress, customerAddress: userAddress },
        });

        if (res.data && res.data.length === 2) {
          setCoordinates(res.data);
          setSelectedOrderToViewMap(order);
          console.log(res.data);
        }
      } catch (err) {
        console.error("Lỗi khi lấy tọa độ:", err);
      }
    }
  };

  const handleCloseMap = () => {
    setSelectedOrderToViewMap(null);
    setCoordinates([]);
  };

  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <div className="flex space-x-4 mb-6">
        {['Đang Xử Lý', 'Đang Vận Chuyển', 'Hoàn Thành', 'Đã Hủy'].map(status => (
          <button
            key={status}
            className={`px-4 py-2 rounded-lg transition duration-300 focus:outline-none ${selectedState === status ? 'bg-blue-600 text-white shadow-md' : 'bg-gray-200 text-gray-700 hover:bg-gray-300'}`}
            onClick={() => setSelectedState(status)}
          >
            {status}
          </button>
        ))}
      </div>

      {filteredOrders.length > 0 ? (
        filteredOrders.map((order) => {
          const warehouseAddress = getOrderWarehouseAddress(order.logicticsRequests);
          return (
            <div key={order.id} className="border border-gray-300 p-4 mb-4 rounded-lg shadow-lg bg-white">
              <h2 className="text-xl font-bold text-blue-700">Đơn Hàng #{order.id}</h2>
              <div className="grid grid-cols-3 gap-4 text-gray-600 mt-2">
                <div><strong>Trạng Thái:</strong> {order.state}</div>
                <div><strong>Tổng Giá:</strong> {order.totalPrice.toLocaleString()} VNĐ</div>
                <div><strong>Người Đặt:</strong> {order.orderByName}</div>
                <div><strong>Người Nhận:</strong> {order.orderForName}</div>
                <div><strong>Giá Vận Chuyển:</strong> {order.transPrice.toLocaleString()} VNĐ</div>
                <div><strong>Giá Kho:</strong> {order.wareHousePrice.toLocaleString()} VNĐ</div>
              </div>

              <h3 className="mt-2 font-semibold text-gray-800">Chi Tiết Đơn Hàng:</h3>
              <ul className="list-disc list-inside ml-4 bg-gray-100 p-4 rounded-lg shadow-inner">
                {order.details.slice(0, 3).map((detail) => (
                  <li key={detail.id} className="text-gray-600">
                    {detail.amount} x {detail.productName} - {detail.total_price.toLocaleString()} VNĐ
                  </li>
                ))}
              </ul>

              {order.details.length > 3 && (
                <button
                  className="mt-2 px-4 py-2 bg-blue-600 text-white rounded-lg"
                  onClick={() => setExpandedOrderId(expandedOrderId === order.id ? null : order.id)}
                >
                  {expandedOrderId === order.id ? "Ẩn Chi Tiết" : "Xem Thêm"}
                </button>
              )}

              {expandedOrderId === order.id && (
                <ul className="list-disc list-inside ml-4 mt-4 bg-gray-100 p-4 rounded-lg shadow-inner">
                  {order.details.slice(3).map((detail) => (
                    <li key={detail.id} className="text-gray-600">
                      {detail.amount} x {detail.productName} - {detail.total_price.toLocaleString()} VNĐ
                    </li>
                  ))}
                </ul>
              )}

              {order.state === 'Hoàn Thành' ? (
                <button className="mt-2 px-4 py-2 bg-green-600 text-white font-semibold rounded-lg">Đã Hoàn Thành</button>
              ) : (
                warehouseAddress ? (
                  <button
                    className="mt-2 ml-2 px-4 py-2 bg-blue-600 text-white rounded-lg"
                    onClick={() => handleShowMap(order)}
                  >
                    Xem Bản Đồ
                  </button>
                ) : (
                  <button
                    className="mt-2 ml-2 px-4 py-2 bg-gray-400 text-white rounded-lg cursor-not-allowed"
                    disabled
                  >
                    Không Có Địa Chỉ Kho
                  </button>
                )
              )}
            </div>
          );
        })
      ) : (
        <p className="text-gray-500">Không có đơn hàng nào trong trạng thái này.</p>
      )}

      {/* ✅ MODAL HIỂN THỊ BẢN ĐỒ */}
      {selectedOrderToViewMap && coordinates.length === 2 && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-4xl relative">
            <button
              className="absolute top-2 right-2 text-red-500 font-bold"
              onClick={handleCloseMap}
            >
              X
            </button>
            <h2 className="text-xl font-semibold mb-4">Đường đi từ kho đến người nhận</h2>
            <MapComponent startPoint={coordinates[0]} endPoint={coordinates[1]} />
          </div>
        </div>
      )}
    </div>
  );
};

export default OrderBillForm;
