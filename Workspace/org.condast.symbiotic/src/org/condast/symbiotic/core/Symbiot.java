package org.condast.symbiotic.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.def.StressEvent;

public class Symbiot<I,O extends Object> implements ISymbiot, Comparable<ISymbiot>{

	public static final String S_ERR_NO_ID = "A symbiot must have a valid id!";

	/**
	 * Listeners to a change in the stress levels
	 */
	private Collection<IStressListener> listeners;
	
	//the name of this symbiot
	private String id;
	private float stress;
	private boolean isActive;
	private IBehaviour<I,O> behaviour;
	
	private Map<ISymbiot, IStressData> signals;
		
	public Symbiot( String id, IBehaviour<I,O> behaviour ) {
		this( id, behaviour, true );
	}
	
	public Symbiot( String id, IBehaviour<I,O> behaviour, boolean active ) {
		if( id == null )
			throw new NullPointerException( S_ERR_NO_ID);
		this.id = id;
		this.isActive = active;
		this.behaviour = behaviour;
		this.behaviour.setOwner(this);
		this.signals = new HashMap<ISymbiot, IStressData>();
		listeners = new ArrayList<IStressListener>();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}

	@Override
	public void addStressListener(IStressListener listener) {
		this.listeners.add(listener );
	}

	@Override
	public void removeStressListener(IStressListener listener) {
		this.listeners.remove( listener );
	}
	
	protected synchronized void notifyStressChanged(){
		for( IStressListener listener: listeners )
			listener.notifyStressChanged( new StressEvent (this));
	}
		
	/* (non-Javadoc)
	 * @see org.condast.symbiotic.core.ISymbiot#getStress()
	 */
	@Override
	public float getStress() {
		return stress;
	}
	
	@Override
	public void setStress(float stress) {
		this.stress = stress;
		try{
			this.notifyStressChanged();
		}
		catch( Exception ex ){
			ex.printStackTrace();
		}
	}

	@Override	
	public void clearStress(){
		this.stress = 0f;
	}
	
	@Override
	public float increaseStress(){
		if(!isActive )
			return 0f;
		int range = behaviour.getRange();
		this.stress = NumberUtils.clip(1f, this.stress + 1f/range);
		setStress(stress);
		return this.stress;
	}

	@Override
	public float decreaseStress(){
		if(!isActive )
			return 0f;
		int range = behaviour.getRange();
		this.stress = NumberUtils.clip(1f, this.stress - 1/range);
		setStress(stress);
		return this.stress;
	}
	
	/**
	 * allow dynamically changing the behaviour
	 * @param behaviour
	 */
	protected void setBehaviour(IBehaviour<I, O> behaviour) {
		this.behaviour = behaviour;
	}
	
	@Override
	public IStressData getStressData( ISymbiot symbiot ){
		IStressData data = this.signals.get(symbiot );
		if( data == null ){
			data = new StressData(symbiot);
			signals.put( symbiot, data);
		}
		return data;
	}
	
	/**
	 * Get the overall stress <-1,1>
	 * @return
	*/
	@Override
	public float getOverallStress(){
		float overall = 0f;
		for( IStressData sd: this.signals.values() ){
			overall += sd.getCurrentStress();
		}
		return ( overall/this.signals.size() );
	}

	/**
	 * Get the overall weight <-1,1>
	 * @return
	*/
	@Override
	public float getOverallWeight(){
		float overall = 0f;
		for( IStressData sd: this.signals.values() ){
			overall += sd.getWeight();
		}
		return overall/signals.size();
	}


	@Override
	public void updateStressLevels(ISymbiot symbiot) {
		this.behaviour.updateStress(symbiot);
	}

	@Override
	public int compareTo(ISymbiot arg0) {
		return this.id.compareTo(arg0.getId());
	}	
	
	private class StressData implements IStressData{
		
		private float weight;
		private float currentStress;
		private ISymbiot symbiot;
		
		private StressData(ISymbiot symbiot) {
			this( symbiot, 0f, 0f );
		}
		
		private StressData(ISymbiot symbiot, float weight, float currentStress) {
			super();
			this.weight = weight;
			this.currentStress = currentStress;
			this.symbiot = symbiot;
		}

		@Override
		public float getWeight() {
			return weight;
		}
		
		@Override
		public float getCurrentStress() {
			return currentStress;
		}
		
		/**
		 * Get the delta between the new stress and the currently stored stress
		 * @return
		 */
		@Override
		public float getDelta(){
			return this.symbiot.getStress() - this.currentStress;
		}

		@Override
		public void setData( float weight ){
			this.currentStress = symbiot.getStress();
			this.weight = NumberUtils.clip(1f, weight );
		}
	}
}