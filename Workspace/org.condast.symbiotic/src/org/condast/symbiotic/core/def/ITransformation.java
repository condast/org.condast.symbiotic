package org.condast.symbiotic.core.def;

import org.condast.symbiotic.core.transformation.ITransformListener;

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
	public String getName();

	/**
	 * Transform the given input to an output signal
	 * @param input
	 * @return
	 */
	public O transform();

	/**
	 * Allow clearing of the inputs
	 */
	public void clearInputs();
		
	/**
	 * Add an input. Returns true if this was done succesfully
	 * @param input
	 * @return
	 */
	public boolean addInput(I input);

	public void removeInput(I input);
	
	/**
	 * Get the input
	 * @return
	 */
	public I[] getInput();
	
	/**
	 * Get the number of inputs added
	 * @return
	 */
	public int getInputSize();
	
	/**
	 * Retuens true if the inputs are mepty
	 * @return
	 */
	public boolean isEmpty();
	
	public O getOutput();

	void addTransformationListener(ITransformListener<O> listener);

	void removeTransformationListener(ITransformListener<O> listener);

	boolean isInputAllowed();

}