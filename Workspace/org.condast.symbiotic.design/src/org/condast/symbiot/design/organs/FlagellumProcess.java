package org.condast.symbiot.design.organs;

import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.process.AbstractProcess;

public class FlagellumProcess extends AbstractProcess<Double, Integer>{

	public static final double DEFAULT_FACTOR_STEP = 0.00001d;
	
	private double step;
	
	public FlagellumProcess(ISymbiot symbiot) {
		this(symbiot, false);
	}

	public FlagellumProcess(ISymbiot symbiot, boolean addFactor) {
		this(symbiot, addFactor, DEFAULT_FACTOR_STEP);
	}

	public FlagellumProcess(ISymbiot symbiot, boolean addFactor, double step) {
		super(symbiot, addFactor);
		this.step = step;
	}

	@Override
	public double getStress() {
		return 0;
	}

	@Override
	protected double normalisedInput( Double input) {
		return super.getInput();
	}

	@Override
	protected Integer transformOutput(double output) {
		if( output > step)
			return 1;
		return ( output < -step)? -1: 0;
	}

	@Override
	protected double onCalculateStress(ISymbiot symbiot, Double input) {
		// TODO Auto-generated method stub
		return 0;
	}
}
