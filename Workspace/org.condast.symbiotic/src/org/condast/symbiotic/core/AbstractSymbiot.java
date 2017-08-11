package org.condast.symbiotic.core;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.symbiotic.def.IStressListener;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.ITransformation;
import org.condast.symbiotic.def.StressEvent;

public abstract class AbstractSymbiot<I,O,M extends Object> implements ISymbiot{

	private float stress;
	private boolean isActive;

	private Collection<IStressListener> listeners;

	/**
	 * The model that drives the symbiot
	 */
	private ITransformation<I,O> transform;
	
	//the name of this symbiot
	private String name;
	private int range;

	protected AbstractSymbiot( ITransformation<I,O> transformation, String name, int range ) {
		this( transformation, name, range, true );
	}
	
	protected AbstractSymbiot( ITransformation<I,O> transformation, String name, int range, boolean active ) {
		this.name = name;
		this.transform = transformation;
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

	protected ITransformation<I,O> getTransformation(){
		return this.transform;
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
	public int getRange() {
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
