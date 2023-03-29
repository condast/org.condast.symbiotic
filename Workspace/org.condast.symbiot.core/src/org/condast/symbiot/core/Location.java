package org.condast.symbiot.core;

public class Location implements ILocation {

	private int x, y;
	
	protected Location() {
		this( 0,0);
	}
	
	protected Location( int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	@Override
	public int[] getLocation() {
		int[] result = new int[2];
		result[0] = x;
		result[1] = y;
		return result;
	}
	
	public void setLocation( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		int hash = 7;
	    hash = 31 * hash + x;
	    hash = 31 * hash + y;
	    return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Location ))
			return super.equals(obj);
		Location loc = (Location) obj;
		boolean result = (( loc.x - x ) == 0 ) && (( loc.y - y)==0);
		return result;
	}

	public boolean equals( int x, int y ) {
		return (( this.x - x) == 0 ) && (( this.y - y) ==0);
	}
	
	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + "]";
	}
}
