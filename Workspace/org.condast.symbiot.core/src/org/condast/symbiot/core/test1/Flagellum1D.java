package org.condast.symbiot.core.test1;

import org.condast.symbiotic.core.enumid.EnumOutputSymbiot;

public class Flagellum1D extends EnumOutputSymbiot<Organism1D.Form, Integer> {

	public static final double DEFAULT_FACTOR_STEP = 0.000001d;
	
	public Flagellum1D( Organism1D.Form form, boolean active) {
		super( form, active);
	}

	@Override
	public boolean enableUpdate(String reference) {
		Organism1D.Form refForm = Organism1D.Form.valueOf(reference);
		return Organism1D.Form.EYE.equals(refForm);
	}

	@Override
	public void update() {
		super.update();
		if( getFactor() > DEFAULT_FACTOR_STEP) {
			setOutput(1);
			setStress(0.01);
		}else if ( getFactor() < -DEFAULT_FACTOR_STEP) {
			setOutput( -1 );
			setStress(0.01);
		}else
			setOutput(0);	
	}	
}
