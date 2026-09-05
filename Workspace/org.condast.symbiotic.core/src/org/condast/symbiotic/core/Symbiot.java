package org.condast.symbiotic.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.condast.commons.Utils;
import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.def.StressEvent;

public class Symbiot implements ISymbiot, Comparable<ISymbiot>{

	public static final String S_ERR_NO_ID = "A symbiot must have a valid id!";
	public static final String S_ERR_INVALID_STRESS = "The stress is outside the boundaries <-1,1>: ";

	/**
	 * Listeners to a change in the stress levels
	 */
	private Collection<IStressListener> listeners;
	
	//the name of this symbiot
	private String id;
	private double oldStress, stress;
	private boolean active;
	private boolean hidden; //Hidden symbiots don't have I/O and can be pruned
	
	private Map<String, IStressData> signals;

	public Symbiot( String id ) {
		this( id, false, true );
	}

	public Symbiot( String id, boolean hidden ) {
		this( id, hidden, true );
	}

	public Symbiot( String id, boolean hidden, boolean active ) {
		if( id == null )
			throw new NullPointerException( S_ERR_NO_ID);
		this.id = id;
		this.active = active;
		this.hidden = hidden;
		this.stress = 0; 
		this.oldStress = 0;
		this.signals = new HashMap<String, IStressData>();
		listeners = new ArrayList<IStressListener>();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean isHidden() {
		return hidden;
	}

	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public void clear() {
		this.clearStress();
		this.signals.values().forEach( s->s.clear());	
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void addStressListener(IStressListener listener) {
		this.listeners.add(listener );
	}

	@Override
	public void removeStressListener(IStressListener listener) {
		this.listeners.remove( listener );
	}
	
	protected synchronized void notifySymbiotChanged( StressEvent event ){
		for( IStressListener listener: listeners )
			listener.notifyStressChanged( event );
	}
		
	/* (non-Javadoc)
	 * @see org.condast.symbiotic.core.ISymbiot#getStress()
	 */
	@Override
	public double getStress() {
		return stress;
	}

	/* (non-Javadoc)
	 * @see org.condast.symbiotic.core.ISymbiot#getStress()
	 */
	@Override
	public double getDeltaStress( ) {
		return this.oldStress - stress; 
	}

	@Override
	public void setStress(double stress) {
		if( Math.abs(stress) > 1d)
			throw new NumberFormatException( S_ERR_INVALID_STRESS + stress);
		this.oldStress = this.stress;
		this.stress = stress;
	}

	@Override	
	public void clearStress(){
		this.stress = 0f;
	}

	@Override
	public void addInfluence( ISymbiot target ) {
		this.signals.put(target.getId(), new StressData( target ));
	}

	@Override
	public void addInfluence( ISymbiot target, double initWeight ) {
		double weight = (initWeight < 0)?-NumberUtils.clip(0, Math.abs(initWeight)): NumberUtils.clip(0, Math.abs(initWeight));
		this.signals.put(target.getId(), new StressData( target, (float) weight ));
	}

	@Override
	public void removeInfluence(ISymbiot target) {
		this.signals.remove(target.getId());
	}

	@Override
	public IStressData getStressData( String reference ){
		IStressData data = this.signals.get(reference );
		return data;
	}
	
	/**
	 * Get the overall stress <-1,1>
	 * @return
	*/
	@Override
	public double getOverallStress(){
		float overall = 0f;
		if( Utils.assertNull(this.signals))
			return overall;
		for( IStressData sd: this.signals.values() )
			overall += sd.getStress();
		return ( overall/this.signals.size() );
	}

	/**
	 * Get the overall weight <-1,1>
	 * @return
	*/
	@Override
	public double getOverallWeight(){
		double overall = 0f;
		if( Utils.assertNull(this.signals))
			return overall;
		for( IStressData sd: this.signals.values() )
			overall += sd.getWeight();
		return overall/signals.size();
	}

	/**
	 * Get the output of the symbiot. This is defined as sigma( w.s)
	 */
	@Override
	public double getFactor() {
		double result = 0;
		if(( signals == null ) || signals.isEmpty())
			return result;

		Iterator<IStressData> iterator = signals.values().iterator();
		while( iterator.hasNext() ) {
			IStressData source = iterator.next();
			double stress = source.getStress();
			result += stress*source.getWeight();
		}
		result/=signals.size();
		return result;
	}

	/**
	 * Returns true if the symbiot is isolated from the others, by the given threshold factor
	 */
	@Override
	public boolean isIsolated( double threshold) {
		if(( signals == null ) || signals.isEmpty())
			return true;

		double th = NumberUtils.clip(1d, Math.abs( threshold ));
		Iterator<IStressData> iterator = signals.values().iterator();
		while( iterator.hasNext() ) {
			IStressData source = iterator.next();
			ISymbiot target = source.getTarget();
			IStressData tsd = target.getSignals().get( this.getId());
			double stress = Math.abs( tsd.getStress());
			if( stress >= th)
				return false;
		}
		return true;
	}

	/**
	 * Get the signals that the symbiot uses for stress strategies. This is mainly used by the
	 * symbiot collection
	 * @return
	 */
	@Override
	public Map<String, IStressData> getSignals() {
		return signals;
	}
	
	@Override
	public boolean enableUpdate(String reference) {
		return !this.id.equals(reference);
	}

	@Override
	public void update() {
		if(!this.active)
			return;
		signals.values().forEach(d-> d.update());
		this.notifySymbiotChanged(new StressEvent (this));
	}

	@Override
	public int compareTo(ISymbiot arg0) {
		return this.id.compareTo(arg0.getId());
	}	
	
}