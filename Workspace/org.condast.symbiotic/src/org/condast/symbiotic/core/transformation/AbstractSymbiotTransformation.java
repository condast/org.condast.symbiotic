package org.condast.symbiotic.core.transformation;

import org.condast.symbiotic.core.IBehaviour;
import org.condast.symbiotic.core.transformation.AbstractModelTransformation;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.ISymbiotTransformation;

public abstract class AbstractSymbiotTransformation<I,O,M,B extends Object> extends AbstractModelTransformation<I, O, M> implements ISymbiotTransformation<I, O>{

	private ISymbiot symbiot;
	private IBehaviour<I, B> behaviour;

	protected AbstractSymbiotTransformation( String id, ISymbiot symbiot, IBehaviour<I, B> behaviour, M model ) {
		super(id, model );
		this.symbiot = symbiot;
		this.behaviour = behaviour;
	}

	protected ISymbiot getSymbiot() {
		return symbiot;
	}

	@Override
	public void updateStress(ISymbiot symbiot) {
		this.behaviour.updateStress(symbiot);
	}
}