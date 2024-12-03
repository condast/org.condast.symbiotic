package org.condast.symbiotic.core;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IStressData;

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
	protected void onUpdate( String reference, StressData currentStress) {
		double retval = currentStress.getDelta()/getRange();
		retval = (float) NumberUtils.clip( 1f, retval );
		overall = getOverallStress();
	}

	@Override
	protected int onUpdateValue( String reference, int current, boolean revert ) {
		IStressData sd = getStressData(reference);
		float weight = NumberUtils.assertNull( (float) sd.getWeight());
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
		return (float) super.getOwner().getOverallStress();
	}

	@Override
	public int getValue() {
		float total = (float) super.getOwner().getOverallWeight();
		return (int) (total * this.getRange());
	}
}
