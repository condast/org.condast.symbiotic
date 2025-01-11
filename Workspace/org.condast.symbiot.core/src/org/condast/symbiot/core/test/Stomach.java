package org.condast.symbiot.core.test;

import org.condast.symbiotic.core.Symbiot;

public class Stomach extends Symbiot {

	private Organism2D.Form form;
	
	public Stomach( Organism2D.Form form, boolean active) {
		super( form.name(), active);
	}

	public Organism2D.Form getForm() {
		return form;
	}

	public void update( boolean hasfood) {
		if( hasfood )
			clearStress();
	}
}
