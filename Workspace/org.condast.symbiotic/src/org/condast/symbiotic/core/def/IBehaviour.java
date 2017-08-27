package org.condast.symbiotic.core.def;

public interface IBehaviour {

	/**
	 * Get the id of the owner (symbiot)
	 * @return
	 */
	public String getId();

	/**
	 * Pass the stress listener of the owner
	 * @param listener
	 */
	public void addStressListener(IStressListener listener);
	public void removeStressListener(IStressListener listener);

	/**
	 * Get or set the symbiot that owns this behaviour
	 * @return
	 */
	public ISymbiot getOwner();
	public void setOwner(ISymbiot owner);

	/**
	 * By default, a possibility is included to add a range
	 * to the behaviour. This range transforms a float value <-1, 1> to
	 * an integer <-range, range>
	 * @return
	 */
	public int getRange();
		
	/**
	 * Update the stress level of the given input symbiot. 
	 * Returns true if the update went successfully
	 * @param symbiot
	 */
	public boolean updateStress( ISymbiot symbiot );
	
	/**
	 * Calculate an integer based on the stress levels,  
	 * This output should fall within the range of the behaviour
	 * @param revert: if there is an external reason to revert the 
	 * current action pattenr
	 * @return
	 */
	public int calculate( boolean revert );
	
	public int getValue();
}
