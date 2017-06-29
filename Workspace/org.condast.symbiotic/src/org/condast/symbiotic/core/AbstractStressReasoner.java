package org.condast.symbiotic.core;

import java.util.HashMap;
import java.util.Map;

import org.condast.symbiotic.def.ISymbiot;

public abstract class AbstractStressReasoner implements IStressReasoner {

	private Map<ISymbiot, Float> symbiots;
	
	public AbstractStressReasoner() {
		symbiots = new HashMap<ISymbiot, Float>();
	}

	protected abstract int onSetStrategy( ISymbiot symbiot, float currentStress, float newStress);
	
	@Override
	public int setStrategy(ISymbiot symbiot) {
		if( !symbiot.isActive() ){
			symbiots.remove(symbiot);
			return 0;
		}
		float currentStress = symbiots.get( symbiot );
		int strategy = onSetStrategy(symbiot, currentStress, symbiot.getStress());
		symbiots.put(symbiot, symbiot.getStress());
		return strategy;
	}
}
