package org.condast.symbiotic.core;

public interface ITransformListener<O extends Object> {

	public void notifyChange( TransformEvent<O> event );
}
