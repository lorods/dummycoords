package io.github.lorods.dummycoords.instantiable;

import java.io.Serializable;
import java.util.Random;

public class Pair implements Serializable {
	private static final long serialVersionUID = 1L;
	double lat, longit;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLongit() {
		return longit;
	}

	public void setLongit(double longit) {
		this.longit = longit;
	}

	public Pair() {
		Random gen = new Random();
		lat = gen.nextDouble() * 180 - 90;
		longit = gen.nextDouble() * 360 - 180;
		// refer to https://wiki.openstreetmap.org/wiki/Node#Structure and
		// https://wiki.openstreetmap.org/wiki/Precision_of_coordinates#Conversion_to_decimal
		// for the specs behind this
	}

	public Pair(double longit, double lat) {
		this.lat = lat;
		this.longit = longit;
	}
}
