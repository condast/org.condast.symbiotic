package org.condast.symbiotic.def;

/**
 * A transformation is a base function that transforms a
 * given input to a given output. It can be given an name
 * 
 * @author Kees
 *
 */
public interface ITransformation<I,O extends Object> {

	/**
	 * Get the name of the transformation
	 * @return
	 */
	String getName();

	/**
	 * Transform the given input to an output signal
	 * @param input
	 * @return
	 */
	public O transform();

	public void addInput(I input);

	public void removeInput(I input);
	
	public O getOutput();

}