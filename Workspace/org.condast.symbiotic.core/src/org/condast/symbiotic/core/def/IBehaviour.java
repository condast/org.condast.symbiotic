package org.condast.symbiotic.core.def;

public interface IBehaviour extends ISymbiot {


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

	void addStressListener(IStressListener listener);

	String getId();

	char[] getRange();

	int getValue();

	ISymbiot getOwner();

	int calculate(boolean equals);
}
