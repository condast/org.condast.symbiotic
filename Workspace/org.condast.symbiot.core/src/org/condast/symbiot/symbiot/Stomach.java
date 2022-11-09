package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.Organism;

public class Stomach extends AbstractSymbiot {

	public static final String S_STOMACH = "Stomach";
	
	public Stomach( Organism organism, float step, boolean active) {
		super( S_STOMACH, organism, step, active);
	}

	public void useEnergy() {
		increaseStress();
	}
	
	@Override
	public void update( double distance) {
		// TODO Auto-generated method stub
		
	}

	public void update( boolean hasfood) {
		if( hasfood )
			clearStress();
		else
			increaseStress();
	}
}
