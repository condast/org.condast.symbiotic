package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.IOrganism.Form;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.EnumOutputSymbiot;

public class Flagellum extends EnumOutputSymbiot<IOrganism.Form, Integer> {

	public Flagellum( IOrganism.Form form, float step, boolean active) {
		super( form, step, active);
	}

	@Override
	protected boolean enableSymbiot(ISymbiot reference) {
		IOrganism.Form refForm = IOrganism.Form.valueOf(reference.getId());
		boolean retval = false;
		switch( super.getForm() ) {
		case LEFT_FLAGELLUM:
			retval = Form.LEFT_EYE.equals(refForm);
			break;
		case RIGHT_FLAGELLUM:
			retval =Form.RIGHT_EYE.equals(refForm);
			break;
		default:
			break;
		}
		return retval;
	}

	@Override
	public void updateStress() {
		super.updateStress();
		if( getOverallWeight() > Double.MIN_VALUE)
			setOutput(1);
		else if ( getOverallWeight() < -Double.MIN_VALUE)
			setOutput( -1 );
		else
			setOutput(0);
	}	
}
