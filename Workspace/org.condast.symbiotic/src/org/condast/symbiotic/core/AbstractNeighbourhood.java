package org.condast.symbiotic.core;

import org.condast.symbiotic.def.INeighbourhood;

public abstract class AbstractNeighbourhood<I,O,M extends Object> extends AbstractTransformation<O,I,M> implements INeighbourhood<I,O> {

	protected AbstractNeighbourhood(String name) {
		super(name);
	}
}
