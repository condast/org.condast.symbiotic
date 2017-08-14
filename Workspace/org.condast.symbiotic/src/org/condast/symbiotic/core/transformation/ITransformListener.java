package org.condast.symbiotic.core.transformation;

public interface ITransformListener<O extends Object> {

	public void notifyChange( TransformEvent<O> event );
}
