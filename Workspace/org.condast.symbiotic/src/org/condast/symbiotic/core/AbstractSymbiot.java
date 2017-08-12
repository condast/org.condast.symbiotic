package org.condast.symbiotic.core;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.def.IStressListener;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.ITransformation;
import org.condast.symbiotic.def.StressEvent;

public abstract class AbstractSymbiot<M,I,O extends Object> implements ISymbiot{

	private float stress;
	private boolean isActive;

	private Collection<IStressListener> listeners;
	
	//the name of this symbiot
	private String name;
	private int range;

	protected AbstractSymbiot( String name, int range ) {
		this( name, range, true );
	}
	
	protected AbstractSymbiot( String name, int range, boolean active ) {
		this.name = name;
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
	
	/**
	 * Create the transformation for this symbiot
	 * @return
	 */
	protected abstract ITransformation<I,O> createTransformation();

	/* (non-Javadoc)
	 * @see org.condast.symbiotic.core.ISymbiot#getStress()
	 */
	@Override
	public float getStress() {
		return stress;
	}

	@Override
	public void setStress(float stress) {
		this.stress = NumberUtils.clip( 1f, stress);
	}
	
	@Override	
	public void clearStress(){
		this.stress = 0f;
	}
	
	@Override
	public float increaseStress(){
		this.stress = NumberUtils.clip(this.range, this.stress + 1/range);
		return this.stress;
	}

	@Override
	public float decreaseStress(){
		this.stress = NumberUtils.clip(this.range, this.stress - 1/range);
		return this.stress;
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
