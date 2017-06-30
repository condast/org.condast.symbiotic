package org.condast.symbiotic.def;

import org.condast.commons.range.IntRange;

public interface ISymbiot<M extends Object>{

	/**
	 * A descriptive name for this symbiot
	 * @return
	 */
	public String getName();
	
	/**
	 * Get the model for this symbiot
	 * @return
	 */
	public M getModel();
	
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
	 * The symbiot offers a number of possibilities to influence
	 * the behavious of the symbiot. Broadly speaking this  can 
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
	public IntRange getRange();
	
	public void addStressListener( IStressListener listener );
	public void removeStressListener( IStressListener listener );
	
}