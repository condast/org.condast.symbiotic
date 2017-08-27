package org.condast.symbiotic.core.ecosystem;

import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.transformation.ITransformListener;

public interface ILinkedNeighbourhood<I, O extends Object> extends ITransformListener<I> {

	void addTransformation(ITransformation<O, ?> transformation);

	void removeTransformation(ITransformation<O, ?> transformation);

}