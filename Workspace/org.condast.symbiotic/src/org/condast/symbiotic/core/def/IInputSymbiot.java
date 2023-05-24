package org.condast.symbiotic.core.def;

public interface IInputSymbiot<I extends Object> extends ISymbiot{

	/**
	 * Get the input 
	 * @param symbiot
	 * @return
	 */
	I getInput();

	/**
	 * Set the input
	 * @param symbiot
	 * @return
	 */
	void setInput( I input );
}