package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.Organism;

public class Flagellum extends AbstractSymbiot {

	public static final String S_FLAGELLUM = "Flagellum";
	
	public Flagellum( Organism organism, float step, boolean active) {
		super( S_FLAGELLUM, organism, step, active);
	}

	@Override
	public void update( double distance) {
		// TODO Auto-generated method stub	
	}

	public int update() {
		float stress = getStress();
		if( Math.abs(stress) < Float.MIN_VALUE )
			return 0;
		return( stress < 0 )?-1:1;
	}

}
