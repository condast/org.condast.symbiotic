package org.condast.symbiotic.core.transformation;

import java.util.Iterator;

import org.condast.symbiotic.core.IBehaviour;

public abstract class AbstractModelTransformer<M,I,O,B extends Object> extends AbstractBehavedTransformer<I,O,B> implements IModelTransformer<M, I, O> {

	private M model;
	
	protected AbstractModelTransformer( String id, M model, IBehaviour<I,B> behaviour ) {
		super( behaviour );
		this.model = model;
	}

	public M getModel() {
		return model;
	}
	
	protected abstract O onTransform( Iterator<I> inputs);
		
	@Override
	public O transform( Iterator<I> inputs) {
		super.transform(inputs);
		return onTransform(inputs);
	}
}
