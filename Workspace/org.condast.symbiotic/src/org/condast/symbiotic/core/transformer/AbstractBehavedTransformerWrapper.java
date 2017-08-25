package org.condast.symbiotic.core.transformer;

import java.util.Iterator;

import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.def.ISymbiotTransformer;
import org.condast.symbiotic.core.def.ITransformer;

public abstract class AbstractBehavedTransformerWrapper<I,O,B extends Object> extends TransformerWrapper<I,O> implements ISymbiotTransformer<I, O>{

	private IBehaviour<I, B> behaviour;

	protected AbstractBehavedTransformerWrapper( ITransformer<I,O> transformer, IBehaviour<I, B> behaviour ) {
		super( transformer );
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
	
	protected abstract O onTransform( Iterator<I> inputs, O output );
	
	@Override
	public O transform( Iterator<I> inputs) {
		O retval = onTransform( inputs, super.transform(inputs ));
		onUpdateStress(inputs, this.behaviour.getOwner());
		return retval;
	}

	/**
	 * If a symbiot has a changed stress level, then pass this to the behaviour
	 */
	@Override
	public void updateStress(ISymbiot symbiot) {
		this.behaviour.updateStress(symbiot);
	}
}