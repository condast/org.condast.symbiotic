package org.condast.symbiotic.core.transformation;

import java.util.Collection;
import java.util.Iterator;

import org.condast.symbiotic.core.IBehaviour;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.def.ISymbiotTransformer;
import org.condast.symbiotic.core.def.ITransformer;

public abstract class AbstractBehavedTransformer<I,O,B extends Object> implements ISymbiotTransformer<I, O>{

	private IBehaviour<I, B> behaviour;
	private  ITransformer<I,O> transformer;

	protected AbstractBehavedTransformer( IBehaviour<I, B> behaviour ) {
		this( null, behaviour );
	}
	
	protected AbstractBehavedTransformer( ITransformer<I,O> transformer, IBehaviour<I, B> behaviour ) {
		this.transformer = transformer;
		this.behaviour = behaviour;
	}

	@Override
	public void clearInputs() {
		transformer.clearInputs();
	}

	@Override
	public boolean addInput(I input) {
		return true;
	}

	@Override
	public boolean removeInput(I input) {
		return true;
	}

	@Override
	public Collection<I> getInputs() {
		return transformer.getInputs();
	}

	public IBehaviour<I, B> getBehaviour() {
		return behaviour;
	}

	/**
	 * Create a new stress level based on the 
	 * @param symbiot
	 */
	protected abstract void onUpdateStress( Iterator<I> inputs, ISymbiot symbiot );
	
	
	@Override
	public O transform( Iterator<I> inputs) {
		onUpdateStress(inputs, this.behaviour.getOwner());
		if( this.transformer == null )
			return null;
		return transformer.transform( inputs );
	}

	/**
	 * If a symbiot has a changed stress level, then pass this to the behaviour
	 */
	@Override
	public void updateStress(ISymbiot symbiot) {
		this.behaviour.updateStress(symbiot);
	}
}