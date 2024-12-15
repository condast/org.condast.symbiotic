package org.condast.symbiot.core.twodim;

import org.condast.symbiotic.core.enumid.EnumOutputSymbiot;

public class Flagellum2D extends EnumOutputSymbiot<Organism2D.Form, Integer> {

	public static final double DEFAULT_FACTOR_STEP = 0.000001d;
	
	public Flagellum2D( Organism2D.Form form, boolean active) {
		super( form, active);
	}

	@Override
	public boolean enableUpdate(String reference) {
		Organism2D.Form refForm = Organism2D.Form.valueOf(reference);
		boolean retval = false;
		switch( super.getForm() ) {
		case LEFT_FLAGELLUM:
			retval = Organism2D.Form.LEFT_EYE.equals(refForm) || Organism2D.Form.RIGHT_FLAGELLUM.equals(refForm);
			break;
		case RIGHT_FLAGELLUM:
			retval =Organism2D.Form.RIGHT_EYE.equals(refForm) || Organism2D.Form.LEFT_FLAGELLUM.equals(refForm);
			break;
		default:
			break;
		}
		return retval;
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
