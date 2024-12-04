package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiotic.core.Symbiot;

public class Stomach extends Symbiot {

	private IOrganism.Form form;
	
	public Stomach( IOrganism.Form form, boolean active) {
		super( form.name(), active);
	}

	public IOrganism.Form getForm() {
		return form;
	}

	public void update( boolean hasfood) {
		if( hasfood )
			clearStress();
	}
}
