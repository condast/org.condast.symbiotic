package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.Organism;

public class Eye extends AbstractSymbiot {

	public static final String S_EYE = "Eye";
	
	private double distance;
	
	public Eye( Organism organism, float step, boolean active) {
		super( S_EYE, organism, step, active);
		this.distance = Double.MAX_VALUE;
	}

	@Override
	public void update( double distance) {
		if( distance > this.distance)
			increaseStress();
		else
			decreaseStress();
		this.distance = distance;
	}	
}
