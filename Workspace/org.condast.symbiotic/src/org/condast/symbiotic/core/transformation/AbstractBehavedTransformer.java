package org.condast.symbiotic.core.transformation;

import java.util.Iterator;

import org.condast.symbiotic.core.IBehaviour;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.def.ISymbiotTransformer;

public abstract class AbstractBehavedTransformer<I,O,B extends Object> extends AbstractTransformer<I,O> implements ISymbiotTransformer<I, O>{

	private IBehaviour<I, B> behaviour;

	protected AbstractBehavedTransformer( IBehaviour<I, B> behaviour ) {
		this.behaviour = behaviour;
	}

	public IBehaviour<I, B> getBehaviour() {
		return behaviour;
	}

	/**
	 * Create a new stress level based on the 
	 * @param symbiot
	 */
	protected abstract void onUpdateStress( Iterator<I> inputs, ISymbiot symbiot );
	
	protected abstract O onTransform( Iterator<I> inputs );
	
	@Override
	public O transform( Iterator<I> inputs) {
		onUpdateStress(inputs, this.behaviour.getOwner());
		return onTransform( inputs );
	}

	/**
	 * If a symbiot has a changed stress level, then pass this to the behaviour
	 */
	@Override
	public void updateStress(ISymbiot symbiot) {
		this.behaviour.updateStress(symbiot);
	}
}