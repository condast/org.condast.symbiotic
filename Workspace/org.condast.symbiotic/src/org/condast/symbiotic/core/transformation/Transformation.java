package org.condast.symbiotic.core.transformation;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.def.ITransformer;

public class Transformation<I,O extends Object> implements ITransformation<I,O> {

	private Collection<I> inputs;
	private O output;
	private String name;
	private ITransformer<I,O> transformer; 
	
	private Collection<ITransformListener<O>> listeners;

	protected  Transformation( String name ) {
		this( name, null );
	}
	
	public  Transformation( String name, ITransformer<I,O> transformer ) {
		inputs = new ArrayList<I>();
		listeners = new ArrayList<ITransformListener<O>>();
		this.name = name;
		this.transformer = transformer;
	}

	public String getName() {
		return name;
	}

	protected void setTransformer( ITransformer<I, O> transformer) {
		this.transformer = transformer;
	}

	@Override
	public boolean addInput( I input ){
		this.transformer.addInput(input);
		return this.inputs.add( input );
	}

	@Override
	public void removeInput( I input ){
		this.transformer.removeInput(input);
		this.inputs.remove( input );
	}

	@SuppressWarnings("unchecked")
	public I[] getInput() {
		return (I[]) inputs.toArray( new Object[ inputs.size() ]);
	}

	/**
	 * Get the number of inputs added
	 * @return
	 */
	public int getInputSize(){
		return inputs.size();
	}
	
	@Override
	public O getOutput() {
		return output;
	}

	/**
	 * Add a transformation listener that listens to changes in the output 
	 * @param listener
	*/
	@Override
	public void addTransformationListener( ITransformListener<O> listener ){
		this.listeners.add( listener );
	}

	/**
	 * Remove a transformation listener that listens to changes in the output 
	 * @param listener
	*/
	@Override
	public void removeTransformationListener( ITransformListener<O> listener ){
		this.listeners.remove( listener );
	}
	
	@Override
	public O transform() {
		output = transformer.transform(inputs.iterator());
		for( ITransformListener<O> listener: listeners )
			listener.notifyChange( new TransformEvent<O>(this, output ));
		return output;
	}
	
	
}
