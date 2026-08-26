package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.IOrganism.Form;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;

public class Flagellum implements ISymbiot{ //EnumOutputSymbiot<IOrganism.Form, Integer> {

	public static final double DEFAULT_FACTOR_STEP = 0.00001d;
	
	public Flagellum( IOrganism.Form form, float step, boolean active) {
		//super( form, step, active);
	}

	protected boolean enableSymbiot(ISymbiot reference) {
		IOrganism.Form refForm = IOrganism.Form.valueOf(reference.getId());
		boolean retval = false;
		/*
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
		*/
		return retval;
	}

	public void updateStress() {
		/*
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
		*/
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getStress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addStressListener(IStressListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStressListener(IStressListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearStress() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float increaseStress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float decreaseStress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStress(float stress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IStressData getStressData(ISymbiot symbiot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getOverallStress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOverallWeight() {
		// TODO Auto-generated method stub
		return 0;
	}	
}
