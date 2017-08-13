package org.condast.symbiotic.def;

public interface ISymbiot<I,O extends Object>{

	/**
	 * A descriptive name for this symbiot
	 * @return
	 */
	public String getName();
		
	/**
	 * Returns true if this symbiot is active. If not, the
	 * symbiot will not be included in the calculations
	 * and the strategy is not updated
	 * @return
	 */
	public boolean isActive();

	/**
	 * A symbiot emits a stress signal
	 * @return
	 */
	public float getStress();
	
	/**
	 * Get the range of the strategiess
	 * @return
	 */
	public int getRange();
	
	public void addStressListener( IStressListener listener );
	public void removeStressListener( IStressListener listener );

	void clearStress();

	float increaseStress();

	float decreaseStress();

	ITransformation<I, O> getTransformation();

	/**
	 * Update the stress level for the given symbiot
	 * @param symbiot
	 */
	public void updateLevel(ISymbiot<?, ?> symbiot);	
}