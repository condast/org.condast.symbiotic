package org.condast.symbiotic.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;

public abstract class AbstractBehaviour implements IBehaviour {

	public static final int DEFAULT_RANGE = 10;
	
	private Map<String, StressData> symbiots;
	private boolean includeOwner;
	private ISymbiot owner;
	private int range;

	protected AbstractBehaviour() {
		this( DEFAULT_RANGE, false );
	}
	
	protected AbstractBehaviour( int range, boolean includeOwner ) {
		symbiots = new HashMap<>();
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
	 * @param reference
	 * @return
	 */
	protected IStressData getStressData( String reference ){
		return this.owner.getStressData(reference);
	}
	
	protected abstract void onUpdate( String reference, StressData currentStress );
	
	@Override
	public boolean updateStress(ISymbiot symbiot) {
		if( !symbiot.isActive() ){
			symbiots.remove(symbiot.getId());
			return false;
		}else if( !this.includeOwner && ( this.owner.equals( symbiot )))
			return false;
		
		StressData stress = symbiots.get( symbiot.getId() ); 
		onUpdate( symbiot.getId(), stress);
		symbiots.put(symbiot.getId(), stress);
		return true;
	}

	protected abstract int onUpdateValue( String reference, int current, boolean revert );

	@Override
	public int calculate( boolean revert ) {
		Iterator<Map.Entry<String, StressData>> iterator = symbiots.entrySet().iterator();
		int retval = 0;
		while( iterator.hasNext()){
			Map.Entry<String, StressData> entry = iterator.next();
			retval += onUpdateValue( entry.getKey(), retval, revert );
		}
		return retval;
	}

}
