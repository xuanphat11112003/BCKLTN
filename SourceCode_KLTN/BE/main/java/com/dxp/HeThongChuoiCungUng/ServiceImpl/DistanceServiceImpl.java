package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Service.DistanceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class DistanceServiceImpl implements DistanceService {
    @Autowired
    RestTemplate restTemplate;

    private static final int EARTH_RADIUS = 6371;
    private final String API_KEY = "5b3ce3597851110001cf62482273f48af2624cd39145a860808c45cd";
    private final String GEOCODING_URL = "https://api.openrouteservice.org/geocode/search?api_key=%s&text=%s";
    private final String DISTANCE_URL = "https://api.openrouteservice.org/v2/matrix/driving-car";

    @Override
    public double calculateDistance(String defaultAddress, String userAddress)  {
        double[] defaultCoordinates = getCoordinates(defaultAddress);
        double[] userCoordinates = getCoordinates(userAddress);
        return getDistanceBetweenCoordinates(defaultCoordinates, userCoordinates);
    }

    @Override
    public double[] getCoordinates(String address) {
        String geocodingUrl = String.format(GEOCODING_URL, API_KEY, address);
        String response = restTemplate.getForObject(geocodingUrl, String.class);
        System.out.println("Phản hồi từ API Geocoding: " + response);
        return parseCoordinatesFromResponse(response);
    }

    private double[] parseCoordinatesFromResponse(String response) {
        double[] coordinates = new double[2];
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            coordinates[0] = jsonNode.path("features").get(0).path("geometry").path("coordinates").get(1).asDouble(); // latitude
            coordinates[1] = jsonNode.path("features").get(0).path("geometry").path("coordinates").get(0).asDouble(); // longitude
        } catch (Exception e) {
            e.printStackTrace();
            coordinates[0] = 0.0;
            coordinates[1] = 0.0;
        }
        return coordinates;
    }

    @Override
    public String getRoute(double[] startCoordinates, double[] endCoordinates) {
        String start = startCoordinates[0] + "," + startCoordinates[1];
        String end = endCoordinates[0] + "," + endCoordinates[1];
        String distanceUrl = String.format(DISTANCE_URL + "?api_key=%s&start=%s&end=%s", API_KEY, start, end);
        return restTemplate.getForObject(distanceUrl, String.class);
    }

    private double getDistanceBetweenCoordinates(double[] startCoordinates, double[] endCoordinates) {
        double lat1 = startCoordinates[0];
        double lon1 = startCoordinates[1];
        double lat2 = endCoordinates[0];
        double lon2 = endCoordinates[1];
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

//    private double parseDistanceFromResponse(String response) {
//        double distance = 0.0;
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(response);
//            distance = jsonNode.path("distances").get(0).get(1).asDouble();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return distance;
//    }
}
