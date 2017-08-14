package org.condast.symbiotic.core.transformation;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.symbiotic.def.ITransformation;

public abstract class AbstractTransformation<I,O extends Object> implements ITransformation<I,O> {

	private Collection<I> inputs;
	private O output;
	private String name;
	
	private Collection<ITransformListener<O>> listeners;
	
	protected AbstractTransformation( String name ) {
		inputs = new ArrayList<I>();
		listeners = new ArrayList<ITransformListener<O>>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean addInput( I input ){
		return this.inputs.add( input );
	}

	@Override
	public void removeInput( I input ){
		this.inputs.remove( input );
	}

	@SuppressWarnings("unchecked")
	protected I[] getInput() {
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

	/**
	 * Perform the actual transformation
	 * @param input
	 * @return
	 */
	protected abstract O onTransform( Collection<I> inputs );
	
	@Override
	public O transform() {
		output = onTransform(inputs);
		for( ITransformListener<O> listener: listeners )
			listener.notifyChange( new TransformEvent<O>(this, output ));
		return output;
	}
	
	
}
