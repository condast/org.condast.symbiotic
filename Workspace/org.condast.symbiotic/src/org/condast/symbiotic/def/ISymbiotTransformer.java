package org.condast.symbiotic.def;

/**
 * A transformation is a base function that transforms a
 * given input to a given output. It can be given an name
 * 
 * @author Kees
 *
 */
public interface ISymbiotTransformer<I,O extends Object> extends ITransformer<I,O>{

	public void updateStress(ISymbiot symbiot);
}