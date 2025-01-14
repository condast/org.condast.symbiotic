package org.condast.symbiotic.core.def;

public interface IBehaviour {

	/**
	 * the default step for increasing or decreasing the weight of stress signals
	 */
	public static double DEFAULT_WEIGHT_STEP = 0.8d;

	/**
	 * If the behaviours is inclusive, then the stress of all the symbiots are 
	 * used for all calculations. This by definition has complexity of O(n^2) 
	 * @return
	 */
	boolean isInclusive();

	double getWeightStep();
	
	/**
	 * Get the normalised total stress of all the symbiots, because the behaviour may use this metric 
	 * @param stress
	 */
	void setStress(double stress);

	public void updateWeights( ISymbiot symbiot );
}
