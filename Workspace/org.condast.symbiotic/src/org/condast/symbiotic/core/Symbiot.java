package org.condast.symbiotic.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.condast.commons.Utils;
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
	private boolean isActive;
	
	private Map<String, IStressData> signals;

	public Symbiot( String id ) {
		this( id, true );
	}
	
	public Symbiot( String id, boolean active ) {
		if( id == null )
			throw new NullPointerException( S_ERR_NO_ID);
		this.id = id;
		this.isActive = active;
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
	public boolean isActive() {
		return isActive;
	}
	
	@Override
	public void clear() {
		this.clearStress();
		this.signals.values().forEach( s->s.clear());	
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
	public void addInfluence( ISymbiot reference ) {
		this.signals.put(reference.getId(), new StressData( reference ));
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
		signals.values().forEach(d-> d.update());
		this.notifySymbiotChanged(new StressEvent (this));
	}

	@Override
	public int compareTo(ISymbiot arg0) {
		return this.id.compareTo(arg0.getId());
	}	
}