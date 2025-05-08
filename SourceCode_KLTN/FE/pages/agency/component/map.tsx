"use client";
import { useEffect, useRef, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup, Polyline } from "react-leaflet";
import L from "leaflet";
import { LatLngExpression } from "leaflet";
import axios from "axios";
import "leaflet/dist/leaflet.css";

// Hàm lấy dữ liệu GeoJSON từ API OpenRouteService
const getRouteGeoJSON = async (start: [number, number], end: [number, number]) => {
  const apiKey = "5b3ce3597851110001cf62482273f48af2624cd39145a860808c45cd";  
  const startCoord = `${start[1]},${start[0]}`; 
  const endCoord = `${end[1]},${end[0]}`;

  const url = `https://api.openrouteservice.org/v2/directions/driving-car?api_key=${apiKey}&start=${startCoord}&end=${endCoord}`;
  console.log(url);
  try {
    const res = await axios.get(url);
    return res.data;
  } catch (err) {
    console.error("Lỗi khi lấy đường đi từ OpenRouteService:", err);
    alert("Không thể lấy đường đi từ OpenRouteService!");
    throw err;
  }
};

interface MapProps {
  startPoint: [number, number];
  endPoint: [number, number];
}

const MapComponent = ({ startPoint, endPoint }: MapProps) => {
  const [routeData, setRouteData] = useState<any>(null);

  // Fetch route khi các điểm thay đổi
  useEffect(() => {
    const drawRoute = async () => {
      try {
        const data = await getRouteGeoJSON(startPoint, endPoint);
        setRouteData(data);  // Lưu dữ liệu lộ trình vào state
      } catch (err) {
        console.error("Không thể lấy route:", err);
      }
    };

    drawRoute();
  }, [startPoint, endPoint]);

  // Vị trí của các marker
  const positionStart: LatLngExpression = [startPoint[0], startPoint[1]];  
  const positionEnd: LatLngExpression = [endPoint[0], endPoint[1]];  

  return (
    <MapContainer center={positionStart} zoom={13} style={{ height: "500px", width: "100%" }}>
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
      
      {/* Marker cho Kho Gửi */}
      <Marker position={positionStart}>
        <Popup>Kho Gửi</Popup>
      </Marker>

      {/* Marker cho Người Nhận */}
      <Marker position={positionEnd}>
        <Popup>Người Nhận</Popup>
      </Marker>

      {/* Vẽ tuyến đường giữa 2 điểm */}
      {routeData && routeData.features && routeData.features[0].geometry.coordinates && (
        <Polyline
          positions={routeData.features[0].geometry.coordinates.map((coord: number[]) => [coord[1], coord[0]])}  // Chuyển đổi tọa độ từ [longitude, latitude] thành [latitude, longitude]
          color="blue"
          weight={4}
        />
      )}
    </MapContainer>
  );
};

export default MapComponent;
