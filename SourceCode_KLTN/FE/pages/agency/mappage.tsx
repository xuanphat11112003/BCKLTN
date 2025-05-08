// MapPage.tsx
import dynamic from 'next/dynamic';

const MapComponent = dynamic(() => import('./component/map'), { ssr: false });

const MapPage = () => {
  const startPoint = [21.0285, 105.8542]; // Hà Nội
  const endPoint = [10.762622, 106.660172]; // TP. Hồ Chí Minh

  return (
    <div>
      <h1>Leaflet Map with Routing</h1>
      <MapComponent startPoint={startPoint} endPoint={endPoint} />
    </div>
  );
};

export default MapPage;
