package org.condast.symbiotic.ecosystem.neighbourhood;

import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.transformation.ITransformListener;

public interface ILinkedNeighbourhood<I, O extends Object> extends ITransformListener<I> {

	void addTransformation(ITransformation<O, ?> transformation);

	void removeTransformation(ITransformation<O, ?> transformation);

}