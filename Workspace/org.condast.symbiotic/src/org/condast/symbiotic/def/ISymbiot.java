package org.condast.symbiotic.def;

public interface ISymbiot{

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
	 * Set the stress
	 * @param stress
	 */
	public void setStress(float stress);

	/**
	 * The symbiot offers a number of possibilities to influence
	 * the behaviour. Broadly speaking this  can 
	 * be done in two ways:
	 * - analogous: an integer range
	 * - discrete: every value is a code for a certain behaviour 
	 * @param strategy
	 */
	public void setStrategy( int strategy );
	
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
}