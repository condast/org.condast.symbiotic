package org.condast.symbiotic.def;

/**
 * A transformation is a base function that transforms a
 * given input to a given output. It can be given an name
 * 
 * @author Kees
 *
 */
public interface ISymbiotTransformation<I,O extends Object> extends ITransformation<I,O>{

	void updateStress(ISymbiot symbiot);
}