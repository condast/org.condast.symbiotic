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

	/**
	 * Update the stress level for the given symbiot
	 * @param symbiot
	 */
	public void updateStressLevels(ISymbiot symbiot);

	String getId();	
}