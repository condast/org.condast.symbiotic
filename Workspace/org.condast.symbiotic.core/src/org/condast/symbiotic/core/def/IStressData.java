package org.condast.symbiotic.core.def;

public interface IStressData {

	public static int MAX_PERCENT = 100;

	//The weights change in smaller steps near the zero value
	public static int DEFAULT_ISOLATION_THRESHOLD = 10; //(percent)

	public static double DEFAULT_NORMALISED_STEP = 0.1d;

	/**
	 * the default initial weight when starting
	 */
	public static double DEFAULT_INITIAL_WEIGHT = 0d;

	/**
	 * the default step for increasing or decreasing the weight of stress signals
	 */
	public static double DEFAULT_WEIGHT_STEP = 0.3d;

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
	
	/**
	 * if true, then the PREVIOUS stress is approximately zero. 
	 * This can be used for fine tuning around the zero value
	 * at the first iteration
	 * @param factor
	 * @return
	 */
	boolean isZero();

	/**
	 * Returns true if the given symbiot is isolated from the target, by the given threshold factor (0..100)
	 */
	boolean isIsolated(ISymbiot symbiot, int threshold);

	/**
	 * Update the stress data. Returns false if everything stays the same. 
	 * @return
	 */
	boolean update();
}