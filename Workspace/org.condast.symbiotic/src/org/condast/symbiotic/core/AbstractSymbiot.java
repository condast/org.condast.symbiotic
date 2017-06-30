package org.condast.symbiotic.core;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.commons.range.IntRange;
import org.condast.symbiotic.def.IStressListener;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.StressEvent;

public abstract class AbstractSymbiot<M extends Object> implements ISymbiot<M>{

	private float stress;
	private boolean isActive;

	private Collection<IStressListener> listeners;

	/**
	 * Ther model that drives the symbiot
	 */
	private M model;
	
	//the name of this symbiot
	private String name;
	private IntRange range;

	protected AbstractSymbiot( M model, String name, int maxStrategy ) {
		this( model, name, new IntRange(0, maxStrategy), true );
	}

	protected AbstractSymbiot( M model, String name, IntRange range ) {
		this( model, name, range, true );
	}
	
	protected AbstractSymbiot( M model, String name, IntRange range, boolean active ) {
		this.name = name;
		this.model = model;
		this.range = range;
		listeners = new ArrayList<IStressListener>();
		this.isActive = active;
	}

	/* (non-Javadoc)
	 * @see org.condast.symbiotic.core.ISymbiot#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public M getModel() {
		return model;
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
			listener.notifyStressChanged( new StressEvent(this));
	}

	/* (non-Javadoc)
	 * @see org.condast.symbiotic.core.ISymbiot#getStress()
	 */
	@Override
	public float getStress() {
		return stress;
	}

	@Override
	public IntRange getRange() {
		return range;
	}

	protected abstract void onSetStrategy(int strategy);

	@Override
	public void setStrategy(int strategy) {
		if(!isActive )
			return;
		this.onSetStrategy( strategy );
		
	}	
}
