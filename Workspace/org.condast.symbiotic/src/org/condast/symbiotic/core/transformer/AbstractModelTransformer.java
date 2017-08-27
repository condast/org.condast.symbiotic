package org.condast.symbiotic.core.transformer;

import org.condast.symbiotic.core.def.IBehaviour;

public abstract class AbstractModelTransformer<M,I,O,B extends Object> extends AbstractBehavedTransformer<I,O> implements IModelTransformer<M, I, O> {

	private M model;
	
	protected AbstractModelTransformer( M model, IBehaviour behaviour ) {
		super( behaviour );
		this.model = model;
	}

	public M getModel() {
		return model;
	}
}
