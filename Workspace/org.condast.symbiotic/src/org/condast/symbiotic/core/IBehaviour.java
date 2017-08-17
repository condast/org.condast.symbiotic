package org.condast.symbiotic.core;

import org.condast.symbiotic.core.def.ISymbiot;

public interface IBehaviour<I,O extends Object> {

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
	 * Calculate an output based on the stress levels. 
	 * Provide the input in case this is required
	 * @return
	 */
	public O calculate( I input );
}
