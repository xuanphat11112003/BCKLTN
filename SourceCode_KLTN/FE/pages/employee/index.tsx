import React from "react";
import Link from "next/link";
import EmployeeChat from "@/components/layout/ChatOfEmployee";



const Home = () => {
  const cards = [
    { title: "Cập Nhật Đơn Hàng Sản Xuất", text: "Xem các công thức vật liệu sản xuất sản phẩm.", link: "/employee/orderManufacture", image: "https://brademar.com/wp-content/uploads/2022/10/Danh-muc-san-pham-cua-Samsung.jpg" },
    { title: "Cập Nhật Vận Chuyển", text: "Kiểm tra và quản lý hàng tồn kho.", link: "/employee/orderBill", image: "https://simerp.io/wp-content/uploads/2021/04/quan-ly-hang-ton-kho.png" }
  ];

  const chunkArray = (array: any[], chunkSize: number) => {
    const result = [];
    for (let i = 0; i < array.length; i += chunkSize) {
      result.push(array.slice(i, i + chunkSize));
    }
    return result;
  };

  const groupedCards = chunkArray(cards, 3);

  return (
    <div className="container mx-auto mt-8">
      <h2 className="text-3xl font-bold text-center mb-8">Chọn Chức Năng Quản Lý</h2>
      <div className="carousel">
        {groupedCards.map((group, index) => (
          <div key={index} className="carousel-item">
            <div className="flex justify-center space-x-8">
              {group.map((card, idx) => (
                <div key={idx} className="w-1/3 p-4">
                  <div className="bg-white shadow-lg rounded-lg overflow-hidden">
                    <img
                      src={card.image}
                      alt={card.title}
                      className="w-full h-48 object-cover"
                    />
                    <div className="p-4">
                      <h3 className="text-lg font-semibold">{card.title}</h3>
                      <p className="text-gray-600">{card.text}</p>
                      <Link href={card.link} className="mt-4 inline-block bg-blue-500 text-white py-2 px-4 rounded">
                        Xem Thêm
                      </Link>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>

      {/* Khung thông tin */}
      <div className="mt-8 p-4 bg-gray-100 rounded-lg shadow-md">
        <h3 className="text-xl font-bold mb-2">Thông Tin Hữu Ích</h3>
        <p className="text-gray-700">Đây là nơi bạn có thể tìm thấy các thông tin quan trọng về quy trình sản xuất và vận chuyển hàng hóa.</p>
      </div>

      {/* Khung chatbot */}
      <div className="mt-8 p-4 bg-white rounded-lg shadow-md">
        <h3 className="text-xl font-bold mb-2">Chatbot Hỗ Trợ</h3>
        <div className="border rounded-lg p-4">
          <EmployeeChat/>
        </div>
      </div>
    </div>
  );
}

export default Home;
