package org.condast.symbiotic.core.def;

/**
 * An Internal entity has a symbiot and produces a stress signal based on a given input
 * @param <I>
 * @param <O>
 */
public interface IInputSymbiot<I extends Object> {

	/**
	 * Get the input value 
	 * @return
	 */
	I getInput();

	void setInput( I input );
	
	double getStress();
}