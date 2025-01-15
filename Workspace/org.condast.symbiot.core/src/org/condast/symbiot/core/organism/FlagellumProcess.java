package org.condast.symbiot.core.organism;

import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.process.AbstractProcess;

public class FlagellumProcess extends AbstractProcess<Double, Integer>{

	public static final double DEFAULT_FACTOR_STEP = 0.00001d;
	

	public FlagellumProcess(ISymbiot symbiot) {
		this(symbiot, false);
	}

	public FlagellumProcess(ISymbiot symbiot, boolean addFactor) {
		super(symbiot, addFactor);
	}

	@Override
	public double getStress() {
		return 0;
	}

	@Override
	protected double normalisedInput() {
		return super.getInput();
	}

	@Override
	protected Integer transformOutput(double output) {
		if( output > DEFAULT_FACTOR_STEP)
			return 1;
		return ( output < -DEFAULT_FACTOR_STEP)? -1: 0;
	}
}
