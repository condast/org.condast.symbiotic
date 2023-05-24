package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiotic.core.Symbiot;

public class Flagellum extends Symbiot {

	private IOrganism.Form form;

	public Flagellum( IOrganism.Form form, float step, boolean active) {
		super( form.name(), step, active);
		this.form = form;
	}

	public IOrganism.Form getForm() {
		return form;
	}

	@Override
	public double getDeltaStress() {
		double delta = super.getDeltaStress();
		if(Math.abs(delta)< Double.MIN_VALUE) {
			delta = super.getStress();
		}
		return delta;
	}
	
}
