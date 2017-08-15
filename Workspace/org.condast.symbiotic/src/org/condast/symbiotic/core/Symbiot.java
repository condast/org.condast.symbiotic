package org.condast.symbiotic.core;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.def.IStressListener;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.StressEvent;

public class Symbiot<I,O extends Object> implements ISymbiot{

	/**
	 * Listeners to a change in the stress levels
	 */
	private Collection<IStressListener> listeners;
	
	//the name of this symbiot
	private float stress;
	private boolean isActive;
	private IBehaviour<I,O> behaviour;
		
	public Symbiot( IBehaviour<I,O> behaviour ) {
		this( behaviour, true );
	}
	
	public Symbiot( IBehaviour<I,O> behaviour, boolean active ) {
		this.isActive = active;
		this.behaviour = behaviour;
		this.behaviour.setOwner(this);
		listeners = new ArrayList<IStressListener>();
	}

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
	
	protected void notifyStressChanged(){
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
	public void clearStress(){
		this.stress = 0f;
	}
	
	@Override
	public float increaseStress(){
		if(!isActive )
			return 0f;
		int range = behaviour.getRange();
		this.stress = NumberUtils.clip(1f, this.stress + 1/range);
		this.notifyStressChanged();
		return this.stress;
	}

	@Override
	public float decreaseStress(){
		if(!isActive )
			return 0f;
		int range = behaviour.getRange();
		this.stress = NumberUtils.clip(1f, this.stress - 1/range);
		this.notifyStressChanged();
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
	public void updateStressLevels(ISymbiot symbiot) {
		this.behaviour.updateStress(symbiot);
	}
}