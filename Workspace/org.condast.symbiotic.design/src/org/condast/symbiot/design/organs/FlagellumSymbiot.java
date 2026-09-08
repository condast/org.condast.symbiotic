package org.condast.symbiot.design.organs;

import org.condast.symbiotic.core.special.AbstractOutputSymbiot;

public class FlagellumSymbiot<E extends Enum<E>> extends AbstractOutputSymbiot<Integer>{

	public static final double DEFAULT_FACTOR_STEP = 0.00001d;
	
	private double step;

	public FlagellumSymbiot(E form) {
		this(form, false);
	}

	public FlagellumSymbiot(E form, boolean active) {
		this(form, active, DEFAULT_FACTOR_STEP);
	}

	public FlagellumSymbiot(E form, boolean active, double step) {
		super(form.name(), active);
		this.step = step;
	}
	
	@Override
	public double getNormalisedOutput() {
		Integer output = super.getOutput();
		return ( output == null )? 0: output;
	}

	@Override
	public Integer onUpdate(double factor) {
		if( factor > step)
			return 1;
		return ( factor < -step)? -1: 0;
	}
}
