package org.condast.symbiotic.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.def.IStressListener;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.ITransformation;
import org.condast.symbiotic.def.StressEvent;

public abstract class AbstractSymbiot<M,I,O extends Object> implements ISymbiot<I,O>{

	private float stress;
	private boolean isActive;

	private Collection<IStressListener> listeners;
	
	//the name of this symbiot
	private String name;
	private int range;
	
	private ITransformation<I,O> transformation;
	
	private Map<ISymbiot<?,?>, Float> levels;

	protected AbstractSymbiot( String name, int range ) {
		this( name, range, true );
	}
	
	protected AbstractSymbiot( String name, int range, boolean active ) {
		this.name = name;
		this.range = range;
		this.isActive = active;
		this.levels = new HashMap<ISymbiot<?,?>, Float>();
		listeners = new ArrayList<IStressListener>();
		this.transformation = this.createTransformation();
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

	/**
	 * Create the transformation for this symbiot
	 * @return
	 */
	protected abstract ITransformation<I,O> createTransformation();

	@Override
	public ITransformation<I, O> getTransformation() {
		return transformation;
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
	
	protected abstract float onChangeLevel( ISymbiot<?,?> symbiot, float current );
	
	@Override
	public void updateLevel( ISymbiot<?,?> symbiot ){
		float current = levels.get( symbiot );
		levels.put( symbiot, onChangeLevel( symbiot, current));
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
		this.stress = NumberUtils.clip(this.range, this.stress + 1/range);
		return this.stress;
	}

	@Override
	public float decreaseStress(){
		if(!isActive )
			return 0f;
		this.stress = NumberUtils.clip(this.range, this.stress - 1/range);
		return this.stress;
	}

	@Override
	public int getRange() {
		return range;
	}
}