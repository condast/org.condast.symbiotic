package org.condast.symbiotic.core;

public abstract class AbstractModelTransformation<I,O,M extends Object> extends AbstractTransformation<I,O> {

	private M model;
	
	protected AbstractModelTransformation( String name, M model ) {
		super( name );
		this.model = model;
	}

	protected M getModel() {
		return model;
	}
}
