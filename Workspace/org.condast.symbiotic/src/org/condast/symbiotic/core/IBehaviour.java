package org.condast.symbiotic.core;

import org.condast.symbiotic.def.ISymbiot;

public interface IBehaviour<I,O extends Object> {

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

	void setOwner(ISymbiot owner);
}
