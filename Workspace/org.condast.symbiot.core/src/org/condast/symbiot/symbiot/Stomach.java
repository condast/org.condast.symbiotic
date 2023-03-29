package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;

public class Stomach extends AbstractSymbioticEntity<Double> {

	private IOrganism.Form form;
	
	public Stomach( IOrganism.Form form, IOrganism organism, float step, boolean active) {
		super( form.name(), organism, step, active);
	}

	public IOrganism.Form getForm() {
		return form;
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
