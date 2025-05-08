import React from 'react';

const LoadingOverlay: React.FC = () => (
  <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    {/* Bạn có thể thêm biểu tượng loading ở đây nếu muốn */}
    <div className="loader"></div>
    <style jsx>{`
      .loader {
        border: 8px solid rgba(255, 255, 255, 0.3);
        border-top: 8px solid white;
        border-radius: 50%;
        width: 50px;
        height: 50px;
        animation: spin 1s linear infinite;
      }

      @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
      }
    `}</style>
  </div>
);

export default LoadingOverlay;
