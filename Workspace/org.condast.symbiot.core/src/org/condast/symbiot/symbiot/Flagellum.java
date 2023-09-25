package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.IOrganism.Form;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.EnumOutputSymbiot;

public class Flagellum extends EnumOutputSymbiot<IOrganism.Form, Integer> {

	public static final double DEFAULT_FACTOR_STEP = 0.00001d;
	
	public Flagellum( IOrganism.Form form, float step, boolean active) {
		super( form, step, active);
	}

	@Override
	protected boolean enableSymbiot(ISymbiot reference) {
		IOrganism.Form refForm = IOrganism.Form.valueOf(reference.getId());
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
	public void updateStress() {
		super.updateStress();
		if( getFactor() > DEFAULT_FACTOR_STEP) {
			setOutput(1);
			setStress(DEFAULT_WEIGHT_STEP);
		}else if ( getFactor() < -DEFAULT_FACTOR_STEP) {
			setOutput( -1 );
			setStress(-DEFAULT_WEIGHT_STEP);
		}else {
			setOutput(0);
			setStress(0);
		}
	}	
}
