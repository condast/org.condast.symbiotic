package org.condast.symbiotic.core;

import org.condast.symbiotic.def.ISymbiot;

public abstract class AbstractSymbiotTransformation<I,O extends Object> extends AbstractTransformation<I,O> {

	private ISymbiot symbiot;
	
	protected AbstractSymbiotTransformation( ISymbiot symbiot) {
		super( symbiot.getName() );
		this.symbiot = symbiot;
	}

	protected ISymbiot getSymbiot() {
		return symbiot;
	}
	
	/**
	 * Perform the actual transformation
	 * @param input
	 * @return
	 */
	protected abstract O onTransform( I input );
}
