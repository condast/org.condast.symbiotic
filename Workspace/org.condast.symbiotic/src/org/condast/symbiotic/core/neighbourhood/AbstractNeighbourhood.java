package org.condast.symbiotic.core.neighbourhood;

import org.condast.symbiotic.core.def.INeighbourhood;
import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.transformation.Transformation;

public abstract class AbstractNeighbourhood<I,O extends Object> extends Transformation<O,I> implements INeighbourhood<I,O> {

	private ITransformation<O,I> inNode, outNode;
	
	protected AbstractNeighbourhood(String name, ITransformation<O,I> inNode, ITransformation<O,I> outNode) {
		super(name);
		this.inNode = inNode;
		this.outNode = outNode;
	}
	
	protected ITransformation<O,I> getInNode(){
		return inNode;
	}

	protected ITransformation<O,I> getOutNode(){
		return outNode;
	}

}
