package org.condast.symbiotic.core;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class DefaultBehaviour extends AbstractBehaviour {

	private float overall;
	
	public DefaultBehaviour( int range ) {
		this( range, false );
	}

	public DefaultBehaviour( int range, boolean includeOwner) {
		super( range, includeOwner);
		this.overall = 0f;
	}

	@Override
	protected float onUpdate(ISymbiot symbiot, float currentStress) {
		IStressData sd = getStressData(symbiot);
		float weight = NumberUtils.assertNull( sd.getWeight());
		float retval = ( symbiot.getStress() - currentStress )/getRange();
		retval = NumberUtils.clip( 1f, retval );
		overall = getOverallStress();
		return weight;
	}

	@Override
	protected int onUpdateValue( ISymbiot symbiot, int current, boolean revert ) {
		IStressData sd = getStressData(symbiot);
		float weight = NumberUtils.assertNull( sd.getWeight());
		return ( int )( weight * getRange() );
	}
	
	/**
	 * Get the delta
	 * @return
	 */
	protected float getDelta(){
		return getOverallStress() - overall;
	}
	
	/**
	 * Get the overall stress
	 * @return
	 */
	public float getOverallStress(){
		return super.getOwner().getOverallStress();
	}

	@Override
	public int getValue() {
		float total = super.getOwner().getOverallWeight();
		return (int) (total * this.getRange());
	}
	
	
}
