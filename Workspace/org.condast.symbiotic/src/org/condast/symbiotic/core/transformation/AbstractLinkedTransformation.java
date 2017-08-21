package org.condast.symbiotic.core.transformation;

import java.util.logging.Logger;

import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.def.ITransformer;

public abstract class AbstractLinkedTransformation<I, O extends Object> extends Transformation<I, O> {

	public static final String S_WRN_NULL_OUTPUT= "The output is null!";
	
	private ITransformation<O,?> outputNode;
	private Logger logger = Logger.getLogger( this.getClass().getName());
	
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
		if( this.outputNode == null ){
			logger.warning( S_WRN_NULL_OUTPUT );
			return;
		}
		if( outputNode.addInput(output))
			super.onTransform(output);
		else
			onOutputBlocked(output);
	}
}