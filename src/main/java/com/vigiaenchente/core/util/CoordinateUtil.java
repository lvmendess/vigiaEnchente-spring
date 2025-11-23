package com.vigiaenchente.core.util;

import com.vigiaenchente.core.domain.valueOptions.Coordinates;

public class CoordinateUtil {

    private CoordinateUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Validates if coordinates are within valid ranges
     */
    public static boolean isValid(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            return false;
        }
        return latitude >= -90 && latitude <= 90
                && longitude >= -180 && longitude <= 180;
    }

    /**
     * Parses location string (format: "lat,lon") to Coordinates
     */
    public static Coordinates parseLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return null;
        }

        try {
            String[] parts = location.split(",");
            if (parts.length != 2) {
                return null;
            }

            double lat = Double.parseDouble(parts[0].trim());
            double lon = Double.parseDouble(parts[1].trim());

            return isValid(lat, lon) ? new Coordinates(lat, lon) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Calculates distance between two coordinates using Haversine formula
     * @return distance in kilometers
     */
    public static double calculateDistance(Coordinates coord1, Coordinates coord2) {
        if (coord1 == null || coord2 == null || !coord1.isValid() || !coord2.isValid()) {
            throw new IllegalArgumentException("Invalid coordinates");
        }

        final int EARTH_RADIUS_KM = 6371;

        double lat1Rad = Math.toRadians(coord1.getLatitude());
        double lat2Rad = Math.toRadians(coord2.getLatitude());
        double deltaLat = Math.toRadians(coord2.getLatitude() - coord1.getLatitude());
        double deltaLon = Math.toRadians(coord2.getLongitude() - coord1.getLongitude());

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}

