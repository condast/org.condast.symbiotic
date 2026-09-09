package org.condast.symbiotic.design.organs;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.special.AbstractOutputSymbiot;
import org.condast.symbiotic.design.Organism2D;

public class Flagellum extends AbstractOutputSymbiot<Integer> {

	public static final double DEFAULT_FACTOR_STEP = 0.00001d;
	
	private double step;
	//private boolean oneEye;
	
	public Flagellum( Organism2D.Form form, boolean active) {
		this( form, false, DEFAULT_FACTOR_STEP, active );
	}
	
	public Flagellum( Organism2D.Form form, boolean oneEye, double step, boolean active) {
		super( form.name(), active);
		//this.oneEye = oneEye;
		this.step = step;
	}

	@Override
	public boolean enableUpdate( IStressData data ) {
		boolean retval = super.enableUpdate(data);
	/*	
		if(!retval )
			return retval;
		if( !Organism2D.Form.isForm( data.getReference()) )
			return retval;
		Organism2D.Form refForm = Organism2D.Form.valueOf( data.getReference());
		Form form = Form.valueOf(super.getId());
		switch( form ) {
		case LEFT_FLAGELLUM:
			retval = Organism2D.Form.LEFT_EYE.equals(refForm) || Organism2D.Form.RIGHT_FLAGELLUM.equals(refForm);
			break;
		case RIGHT_FLAGELLUM:
			boolean enableEye = oneEye?Organism2D.Form.LEFT_EYE.equals(refForm): Organism2D.Form.RIGHT_EYE.equals(refForm);
			retval = enableEye || Organism2D.Form.LEFT_FLAGELLUM.equals(refForm);
			break;
		default:
			break;
		}
		*/
		return retval;
	}
	
	@Override
	public Integer getOutput() {
		return ( super.getOutput() == null )? 0: super.getOutput();
	}
	
	@Override
	protected double onUpdateStress(double currentStress, double factor) {
		return NumberUtils.clipRange(-1, 1, currentStress + factor );
	}

	@Override
	public double getNormalisedOutput() {
		Integer output = super.getOutput();
		return ( output == null )? 0: output;
	}
	
	@Override
	public Integer onUpdate(double factor) {
		this.setLearning(true);
		if( factor > step)
			return 1;
		return ( factor < -step)? -1: 0;
	}
}