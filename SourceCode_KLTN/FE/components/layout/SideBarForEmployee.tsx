// src/components/Sidebar.tsx
import React from "react";
import Link from "next/link";

const Sidebar: React.FC = () => {
    return (
        <div className={`absolute top-16 left-0 h-[100%] w-64 bg-blue-100 border-r border-gray-300`} style={{ zIndex: -5 }}>
            <nav className={`flex flex-col h-full p-4`}>
                <Link href="/orderimport" className="nav-link custom-nav-link py-2 px-4 hover:bg-blue-200 rounded">Mua Hàng</Link>
                <Link href="/orderimport/add" className="nav-link custom-nav-link py-2 px-4 hover:bg-blue-200 rounded">Tạo Đơn Hàng</Link>
                <Link href="#manage-products" className="nav-link custom-nav-link py-2 px-4 hover:bg-blue-200 rounded">Quản Lý Sản Phẩm</Link>
                <Link href="/material" className="nav-link custom-nav-link py-2 px-4 hover:bg-blue-200 rounded">Xem Vật Liệu</Link>
                <Link href="#suppliers" className="nav-link custom-nav-link py-2 px-4 hover:bg-blue-200 rounded">Nhà Cung Cấp</Link>
                <Link href="#inventory-management" className="nav-link custom-nav-link py-2 px-4 hover:bg-blue-200 rounded">Quản Lý Tồn Kho</Link>
                <Link href="#shipping" className="nav-link custom-nav-link py-2 px-4 hover:bg-blue-200 rounded">Vận Chuyển</Link>
            </nav>
        </div>
    );
}

export default Sidebar;
