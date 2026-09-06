package org.condast.symbiotic.core.def;

public interface IStressData {

	/**
	 * the default step for increasing or decreasing the weight of stress signals
	 */
	public static double DEFAULT_WEIGHT_STEP = 0.02d;

	//The weights change in smaller steps near the zero value
	public static double DEFAULT_ZERO_ADJUST = 4;

	/**
	 * Get the target symbiot
	 * @return
	 */
	ISymbiot getTarget();

	String getReference();

	/**
	 * clear the stress and weights
	 */
	void clear();
	
	/**
	 * Get the current stress of the symbiot
	 * @return
	 */
	double getStress();

	/**
	 * Get the current stress minus the previous
	 * @return
	 */
	double getDelta();

	/**
	 * Get the current weight 
	 * @return
	 */
	double getWeight();

	//void setWeight( double weight );

	/**
	 * weight minus previous weight
	 * @return
	 */
	double getWeightDelta();
	
	void update();

	/**
	 * if true, then the PREVIOUS stress is approximately zero. 
	 * This can be used for fine tuning around the zero value
	 * at the first iteration
	 * @param factor
	 * @return
	 */
	boolean isZero();

	/**
	 * if true, then the change in stress is larger than the factor. This happens, for instance
	 * at the first iteration
	 * @param factor
	 * @return
	 */
	boolean isJump(double factor);
}