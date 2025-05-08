package com.dxp.HeThongChuoiCungUng.Service;

import java.net.MalformedURLException;

public interface DistanceService {
    double[] getCoordinates(String address);
    public double calculateDistance(String defaultAddress, String userAddress) throws MalformedURLException;
    public String getRoute(double[] startCoordinates, double[] endCoordinates);

}
