package org.condast.symbiot.core.env;

public class Place<O extends Object> {

	private int x,y;
	
	private O fill;
	
	public Place( int x, int y, O fill ) {
		this.x = x;
		this.y = y;
		this.fill = fill;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public O getFill() {
		return fill;
	}
}
