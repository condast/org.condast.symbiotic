package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.IOrganism.Form;
import org.condast.symbiotic.core.enumid.EnumOutputSymbiot;

public class Flagellum extends EnumOutputSymbiot<IOrganism.Form, Integer> {

	public static final double DEFAULT_FACTOR_STEP = 0.000001d;
	
	public Flagellum( IOrganism.Form form, boolean active) {
		super( form, active);
	}

	@Override
	public boolean enableUpdate(String reference) {
		IOrganism.Form refForm = IOrganism.Form.valueOf(reference);
		boolean retval = false;
		switch( super.getForm() ) {
		case LEFT_FLAGELLUM:
			retval = Form.LEFT_EYE.equals(refForm) || Form.RIGHT_FLAGELLUM.equals(refForm);
			break;
		case RIGHT_FLAGELLUM:
			retval =Form.RIGHT_EYE.equals(refForm) || Form.LEFT_FLAGELLUM.equals(refForm);
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
