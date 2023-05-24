package org.condast.symbiotic.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;

public abstract class AbstractBehaviour implements IBehaviour {

	public static final int DEFAULT_RANGE = 10;
	
	private Map<ISymbiot, Float> symbiots;
	private boolean includeOwner;
	private ISymbiot owner;
	private int range;

	protected AbstractBehaviour() {
		this( DEFAULT_RANGE, false );
	}
	
	protected AbstractBehaviour( int range, boolean includeOwner ) {
		symbiots = new HashMap<ISymbiot, Float>();
		this.range = range;
		this.includeOwner = includeOwner;
	}

	/**
	 * Get the id of the symbiot
	 * @return
	 */
	@Override
	public String getId(){
		return owner.getId();
	}
	
	@Override
	public void addStressListener(IStressListener listener) {
		this.owner.addStressListener(listener );
	}

	@Override
	public void removeStressListener(IStressListener listener) {
		this.owner.removeStressListener( listener );
	}

	@Override
	public int getRange() {
		return range;
	}

	@Override
	public ISymbiot getOwner() {
		return owner;
	}

	@Override
	public void setOwner(ISymbiot owner) {
		this.owner = owner;
	}

	/**
	 * Get the overall stress
	 * @return
	 */
	public float getOverallStress(){
		return (float) this.owner.getOverallStress();
	}

	/**
	 * Get the stress data of the owner for the given symbiot
	 * @param symbiot
	 * @return
	 */
	protected IStressData getStressData( ISymbiot symbiot ){
		return this.owner.getStressData(symbiot);
	}
	
	protected abstract float onUpdate( ISymbiot symbiot, float currentStress );
	
	@Override
	public boolean updateStress(ISymbiot symbiot) {
		if( !symbiot.isActive() ){
			symbiots.remove(symbiot);
			return false;
		}else if( !this.includeOwner && ( this.owner.equals( symbiot ))){
			return false;
		}
		
		Float stress = NumberUtils.assertNull( symbiots.get( symbiot )); 
		symbiots.put(symbiot, onUpdate( symbiot, stress));
		return true;
	}

	protected abstract int onUpdateValue( ISymbiot symbiot, int current, boolean revert );

	@Override
	public int calculate( boolean revert ) {
		Iterator<Map.Entry<ISymbiot, Float>> iterator = symbiots.entrySet().iterator();
		int retval = 0;
		while( iterator.hasNext()){
			Map.Entry<ISymbiot, Float> entry = iterator.next();
			retval += onUpdateValue( entry.getKey(), retval, revert );
		}
		return retval;
	}

}
