package org.condast.symbiotic.core.process;

import org.condast.symbiotic.core.def.ISymbiot;

/**
 * A process transforms an input into an output. It also generates a stress signal
 * @param <I>
 * @param <O>
 */
public interface IProcess<I extends Object, O extends Object> {

	void setInput( I input );
	
	O getOutput();
	
	/**
	 * Get the normalised output <-1,1>
	 * @return
	 */
	double getNormalisedOutput();

	/**
	 * The symbiot controls the symbiotic behaviour of the process
	 * @return
	 */
	ISymbiot getSymbiot();
	
	double getStress();
	
	/**
	 * Update the output of the process according to the given factor
	 * @return
	 */
	O onUpdate( double factor );	
}
