package org.condast.symbiotic.core.filter;

import java.util.Iterator;

public interface ITransformFilter<I, O extends Object> {

	/**
	 * Returns true if the input is accepted
	 * @param input
	 * @return
	 */
	public boolean accept( I input );
	
	/**
	 * Returns true if the conditions for transformation are met 
	 * @param inputs
	 * @return
	 */
	public boolean acceptTransform( Iterator<I> inputs );
}
