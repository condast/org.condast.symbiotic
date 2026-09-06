package org.condast.symbiotic.core.process;

import org.condast.symbiotic.core.def.ISymbiot;

/**
 * An Internal entity has a symbiot and produces a stress signal based on a given input
 * @param <I>
 * @param <O>
 */
public interface IInternal<I extends Object> {

	/**
	 * Get the input value 
	 * @return
	 */
	I getInput();

	void setInput( I input );
	
	/**
	 * The symbiot controls the symbiotic behaviour of the process
	 * @return
	 */
	ISymbiot getSymbiot();
	
	double getStress();
}