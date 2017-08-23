package org.condast.symbiotic.core.transformer;

import java.util.logging.Logger;

import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.def.ITransformer;
import org.condast.symbiotic.core.transformation.Transformation;

public class LinkedTransformation<I, O extends Object> extends Transformation<I, O> {

	public static final String S_WRN_NULL_OUTPUT= "The output is null!";

	private ITransformation<O,?> outputNode;
	private Logger logger = Logger.getLogger( this.getClass().getName());


	protected LinkedTransformation(String name, ITransformation<O,?> outputNode) {
		super(name);
		this.outputNode = outputNode;
	}

	public LinkedTransformation( String name, ITransformer<I, O> transformer, ITransformation<O,?> outputNode) {
		super( name, transformer );
		this.outputNode = outputNode;
	}

	/**
	 * This transformation returns null if the linked
	 * node does not accept the transformation
	 * 
	 */
	@Override
	public O transform() {
		if( outputNode == null ){
			logger.warning( S_WRN_NULL_OUTPUT );
			return null;
		}
		O output = super.transform();
		boolean result = outputNode.addInput( output );
		return result? output: null;
	}
}