// app/components/FooterIndex.tsx
import React from 'react';

const FooterForAgency: React.FC = () => {
  return (
    <footer className="bg-gray-800 text-white py-4">
      <div className="container mx-auto">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div className="text-center">
            <h2 className="text-lg font-semibold">Shopping</h2>
            <img 
              src="https://res.cloudinary.com/dsrf9kurf/image/upload/v1725558310/_d4e5d4c7-e531-447c-afd1-11a415b4fb22_spq7yc.jpg" 
              alt="Shopping Icon" 
              className="w-full h-auto" 
            />
          </div>
          <div>
            <h3 className="text-lg font-semibold">Shop</h3>
            <ul className="list-none space-y-2">
              <li><a href="/products" className="text-white hover:text-gray-400">Products</a></li>
              <li><a href="/sales" className="text-white hover:text-gray-400">Sales</a></li>
              <li><a href="/new-arrivals" className="text-white hover:text-gray-400">New Arrivals</a></li>
            </ul>
          </div>
          <div>
            <h3 className="text-lg font-semibold">Contact</h3>
            <ul className="list-none space-y-2">
              <li><a href="/contact" className="text-white hover:text-gray-400">Contact Us</a></li>
              <li><a href="/support" className="text-white hover:text-gray-400">Support</a></li>
              <li><a href="/faq" className="text-white hover:text-gray-400">FAQ</a></li>
            </ul>
          </div>
          <div>
            <h6 className="text-lg font-semibold">Address</h6>
            <ul className="list-none space-y-2">
              <li>Trường Đại Học Mở Thành Phố Hồ Chí Minh</li>
              <li>Phát Triển Hệ Thống Web, Khoa Công Nghệ Thông Tin</li>
              <li>2021 - 2025</li>
            </ul>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default FooterForAgency;
