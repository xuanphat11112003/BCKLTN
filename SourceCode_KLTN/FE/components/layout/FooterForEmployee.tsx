// src/layout/Footer.tsx
import React from 'react';

const FooterForEmployee: React.FC = () => {
  return (
    <footer className="bg-gray-900 text-white py-6 shadow-md w-full mt-auto">
      <div className="container mx-auto text-center">
        <h2 className="text-xl font-semibold mb-2">Supply Chain Management System</h2>
        <p className="text-sm mb-4">Dedicated to our Employees</p>
        <div className="flex justify-center space-x-4 mb-4">
          <a href="/about" className="hover:text-gray-300 transition duration-300">About Us</a>
          <a href="/privacy" className="hover:text-gray-300 transition duration-300">Privacy Policy</a>
          <a href="/terms" className="hover:text-gray-300 transition duration-300">Terms of Service</a>
        </div>
        <p className="text-xs">
          © 2024 Supply Chain Management System. All rights reserved.
        </p>
      </div>
    </footer>
  );
}

export default FooterForEmployee;
