package org.condast.symbiotic.core;

import org.condast.symbiotic.def.ISymbiot;

public abstract class AbstractSymbiotTransformation<I,O,M extends Object> extends AbstractTransformation<I,O,M> {

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
