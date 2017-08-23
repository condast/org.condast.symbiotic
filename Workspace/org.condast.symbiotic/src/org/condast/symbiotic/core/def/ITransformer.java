package org.condast.symbiotic.core.def;

import java.util.Collection;
import java.util.Iterator;

/**
 * A transformer takes care of the direct transformation from input to output
 * @author Kees
 *
 * @param <I>
 * @param <O>
 */
public interface ITransformer<I, O extends Object> {

	/**
	 * Allow clearing of the inputs
	 */
	public void clearInputs();

	/**
	 * allow additional operations when adding and removing inputs,
	 * such as initialisation
	 * @param input
	 */
	public boolean addInput( I input );
	public boolean removeInput( I input );
	
	/**
	 * Get the inputs
	 * @return
	 */
	public Collection<I> getInputs();
	
	/**
	 * Transform the given input to an output signal
	 * @param input
	 * @return
	 */
	public O transform( Iterator<I> inputs);

}
