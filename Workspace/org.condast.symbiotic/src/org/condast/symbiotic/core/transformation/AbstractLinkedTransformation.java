package org.condast.symbiotic.core.transformation;

import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.def.ITransformer;

public abstract class AbstractLinkedTransformation<I, O extends Object> extends Transformation<I, O> {

	private ITransformation<O,?> outputNode;
	
	public AbstractLinkedTransformation(String name, ITransformation<O,?> outputNode) {
		super(name);
		this.outputNode = outputNode;
	}

	public AbstractLinkedTransformation(String name, ITransformer<I, O> transformer, ITransformation<O,?> outputNode) {
		super(name, transformer);
		this.outputNode = outputNode;
	}

	protected ITransformation<O, ?> getOutNode() {
		return outputNode;
	}

	protected abstract void onOutputBlocked( O output );
	
	@Override
	public void onTransform( O output ) {
		if( outputNode.addInput(output))
			super.onTransform(output);
		else
			onOutputBlocked(output);
	}
}