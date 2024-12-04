package org.condast.symbiotic.core.def;

public interface IBehaviour {

	/**
	 * the default step for increasing or decreasing the weight of stress signals
	 */
	public static double DEFAULT_WEIGHT_STEP = 0.1d;

	double getWeightStep();
	
	public void updateSymbiot( ISymbiot symbiot );
}
