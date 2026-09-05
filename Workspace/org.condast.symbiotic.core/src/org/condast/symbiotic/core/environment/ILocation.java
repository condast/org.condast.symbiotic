package org.condast.symbiotic.core.environment;

public interface ILocation {

	int getX();

	int getY();

	int[] getLocation();

	boolean equals(int x, int y);

}