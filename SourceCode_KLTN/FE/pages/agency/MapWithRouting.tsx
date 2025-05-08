"use client";

import { useEffect } from "react";
import dynamic from "next/dynamic";
import L from "leaflet";
import "leaflet-routing-machine";
import "leaflet/dist/leaflet.css";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css";

// Dynamically import react-leaflet components to prevent SSR errors
const MapContainer = dynamic(() => import("react-leaflet").then(mod => mod.MapContainer), { ssr: false });
const TileLayer = dynamic(() => import("react-leaflet").then(mod => mod.TileLayer), { ssr: false });
const Marker = dynamic(() => import("react-leaflet").then(mod => mod.Marker), { ssr: false });
const Popup = dynamic(() => import("react-leaflet").then(mod => mod.Popup), { ssr: false });

interface MapWithRoutingProps {
  from: [number, number];
  to: [number, number];
}

const RoutingMachine = ({ from, to }: MapWithRoutingProps) => {
  useEffect(() => {
    const map = L.map('routing-map').setView(from, 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);

    L.Routing.control({
      waypoints: [L.latLng(...from), L.latLng(...to)],
      routeWhileDragging: false,
      show: false,
    }).addTo(map);

    return () => {
      map.remove();
    };
  }, [from, to]);

  return <div id="routing-map" style={{ height: "500px", width: "100%" }} />;
};

const MapWithRouting = ({ from, to }: MapWithRoutingProps) => {
  return (
    <div className="w-full h-[500px]">
      <RoutingMachine from={from} to={to} />
    </div>
  );
};

export default MapWithRouting;
