package org.condast.symbiotic.core.def;

public interface IStressData {

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

	/**
	 * weight minus previous weight
	 * @return
	 */
	double getWeightDelta();

	void setWeight( double weight );
	
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