package com.cero.cm.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

@Slf4j
public class CoordUtils {
	private static final String KMETR = "kilometer";
	private static final String METER = "meter";

	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double meter = distance(lat1, lon1, lat2, lon2, METER);
		double result = 0.0;
		DecimalFormat format = new DecimalFormat("0.00");
		try {
			result = Double.parseDouble(format.format(meter));;
		}catch(Exception e){
			log.error("distance error : lat1 {} , lon1 {}, lat2 {}, lon2 {}",lat1,lon1,lat2,lon2);
		}
		return result;
	}

	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if (lat1 == lat2 && lon1 == lon2) return 0;
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		if (unit == KMETR) {
			dist = dist * 1.609344;
		} else if (unit == METER) {
			dist = dist * 1609.344;
		}

		return (dist);
	}

	// This function converts decimal degrees to radians
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public static void main(String[] args) {
		double dist = distance(37.5340335, 126.9023811, 37.5439414, 126.8822235);
		System.out.println(dist);
	}
}
