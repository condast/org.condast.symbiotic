package org.condast.symbiotic.core.def;

public interface IOutputSymbiot<O extends Object> extends ISymbiot{

	/**
	 * Get the input 
	 * @param symbiot
	 * @return
	 */
	O getOutput();
}