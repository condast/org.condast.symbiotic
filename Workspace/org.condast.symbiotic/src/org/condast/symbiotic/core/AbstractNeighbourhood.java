package org.condast.symbiotic.core;

import org.condast.symbiotic.core.transformation.Transformation;
import org.condast.symbiotic.def.INeighbourhood;

public abstract class AbstractNeighbourhood<I,O extends Object> extends Transformation<O,I> implements INeighbourhood<I,O> {

	protected AbstractNeighbourhood(String name) {
		super(name);
	}
}
