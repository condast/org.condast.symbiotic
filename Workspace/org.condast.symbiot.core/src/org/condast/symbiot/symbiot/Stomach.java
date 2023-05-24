package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiotic.core.Symbiot;

public class Stomach extends Symbiot {

	private IOrganism.Form form;
	
	public Stomach( IOrganism.Form form, float step, boolean active) {
		super( form.name(), step, active);
	}

	public IOrganism.Form getForm() {
		return form;
	}

	public void useEnergy() {
		increaseStress();
	}
	
	public void update( boolean hasfood) {
		if( hasfood )
			clearStress();
		else
			increaseStress();
	}
}
