package org.condast.symbiotic.core;

import java.util.HashMap;
import java.util.Map;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.def.ISymbiot;

public class DefaultBehaviour<I extends Object> extends AbstractBehaviour<I,Integer> {

	private Map<ISymbiot, Float> weights;
	
	public DefaultBehaviour( int range ) {
		this( range, false );
	}

	public DefaultBehaviour( int range, boolean includeOwner) {
		super( range, includeOwner);
		weights = new HashMap<ISymbiot, Float>();
	}

	@Override
	protected float onUpdate(ISymbiot symbiot, float currentStress) {
		float weight = weights.get( symbiot );
		float retval = ( symbiot.getStress() - currentStress )/getRange();
		retval = NumberUtils.clip(1f, retval );
		return weight;
	}

	@Override
	protected Integer onUpdateValue(ISymbiot symbiot, Integer current) {
		float weight = weights.get( symbiot );
		return ( int )( weight * getRange() );
	}


}
