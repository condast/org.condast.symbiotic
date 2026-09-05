package org.condast.symbiotic.ui.environment;

public class TrailData {

	private int x,y;
	
	private int birth;

	protected TrailData(int x, int y, int birth) {
		super();
		this.x = x;
		this.y = y;
		this.birth = birth;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getBirth() {
		return birth;
	}
}
