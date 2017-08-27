package org.condast.symbiotic.core.def;

public interface ISymbiot{
		
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
	
	public void addStressListener( IStressListener listener );
	public void removeStressListener( IStressListener listener );

	void clearStress();

	float increaseStress();

	float decreaseStress();

	String getId();

	void setStress(float stress);

	/**
	 * Get the overall stress
	 * @param symbiot
	 * @return
	 */
	public IStressData getStressData(ISymbiot symbiot);

	/**
	 * Get the overall stress
	 * @param symbiot
	 * @return
	 */
	float getOverallStress();

	/**
	 * Get the overall weight
	 * @param symbiot
	 * @return
	 */
	float getOverallWeight();	
}