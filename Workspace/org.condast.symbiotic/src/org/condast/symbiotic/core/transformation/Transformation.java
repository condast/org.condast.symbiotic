package org.condast.symbiotic.core.transformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.condast.commons.Utils;
import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.def.ITransformer;

public class Transformation<I,O extends Object> implements ITransformation<I,O> {

	private String name;
	private ITransformer<I,O> transformer; 
	private O output;
	
	private Collection<ITransformListener<O>> listeners;

	protected  Transformation( String name ) {
		this( name, null );
	}
	
	public  Transformation( String name, ITransformer<I,O> transformer ) {
		listeners = new ArrayList<ITransformListener<O>>();
		this.name = name;
		this.transformer = transformer;
	}

	public String getName() {
		return name;
	}

	protected ITransformer<I, O> getTransformer() {
		return transformer;
	}

	protected void setTransformer( ITransformer<I, O> transformer) {
		this.transformer = transformer;
	}

	/**
	 * Allow clearing of the inputs
	 */
	public void clearInputs(){
		this.transformer.clearInputs();
	}

	@Override
	public boolean isInputAllowed() {
		return this.transformer.isInputAllowed();
	}

	@Override
	public boolean addInput( I input ){
		return this.transformer.addInput(input);
	}

	@Override
	public void removeInput( I input ){
		this.transformer.removeInput(input);
	}

	@SuppressWarnings("unchecked")
	public I[] getInput() {
		Collection<I> inputs = transformer.getInputs();
		return (I[]) inputs.toArray( new Object[ inputs.size() ]);
	}

	/**
	 * Get the number of inputs added
	 * @return
	 */
	public int getInputSize(){
		return transformer.getInputs().size();
	}
	
	@Override
	public boolean isEmpty() {
		return transformer.getInputs().isEmpty();
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
	
	protected void onTransform( O output ){
		for( ITransformListener<O> listener: listeners )
			listener.notifyChange( new TransformEvent<O>(this, output ));		
	}
	
	@Override
	public O transform() {
		Collection<I> inputs = transformer.getInputs();
		Iterator<I> iterator =  Utils.assertNull(inputs )? null: inputs.iterator();
		output = transformer.transform( iterator );
		this.onTransform(output);
		return output;
	}
}