package org.condast.symbiotic.core.process;

/**
 * A process transforms an input into an output. It also generates a stress signal
 * @param <I>
 * @param <O>
 */
public interface IProcess<I extends Object, O extends Object> extends IInternal<I>{

	O getOutput();
	
	/**
	 * Get the normalised output <-1,1>
	 * @return
	 */
	double getNormalisedOutput();

	/**
	 * Update the output of the process according to the given factor
	 * @return
	 */
	O onUpdate( double factor );	
}
