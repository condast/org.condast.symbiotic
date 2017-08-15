package org.condast.symbiotic.core.transformation;

import org.condast.symbiotic.core.IBehaviour;
import org.condast.symbiotic.core.transformation.AbstractModelTransformation;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.ISymbiotTransformation;

public abstract class AbstractBehavedTransformation<I,O,M,B extends Object> extends AbstractModelTransformation<I, O, M> implements ISymbiotTransformation<I, O>{

	private IBehaviour<I, B> behaviour;

	protected AbstractBehavedTransformation( String id, IBehaviour<I, B> behaviour, M model ) {
		super(id, model );
		this.behaviour = behaviour;
	}

	protected IBehaviour<I, B> getBehaviour() {
		return behaviour;
	}

	@Override
	public void updateStress(ISymbiot symbiot) {
		this.behaviour.updateStress(symbiot);
	}
}