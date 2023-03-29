package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;

public class Eye extends AbstractSymbioticEntity<Double> {

	private IOrganism.Form form;
	
	public Eye( IOrganism.Form form, IOrganism organism, float step, boolean active) {
		super( form.name(), organism, step, active);
		super.setData( Double.MAX_VALUE );
		this.form = form;
	}

	public IOrganism.Form getForm() {
		return form;
	}

	@Override
	public void update( double distance) {
		double dist = super.getData();
		//if( distance > dist)
		//	increaseStress();
		//else
		//	decreaseStress();
		setStress((float)distance/100);
		setData(distance);
	}	
	
	
}
