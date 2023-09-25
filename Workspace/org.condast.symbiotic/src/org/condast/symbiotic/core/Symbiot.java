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
	private boolean isActive;
	private double weightStep; //The increase or decrease of stress per cycle
	
	private Map<ISymbiot, IStressData> signals;

	public Symbiot( String id) {
		this( id, DEFAULT_WEIGHT_STEP, true );
	}

	public Symbiot( String id, double weightStep ) {
		this( id, weightStep, true );
	}
	
	public Symbiot( String id, double weightStep, boolean active ) {
		if( id == null )
			throw new NullPointerException( S_ERR_NO_ID);
		this.id = id;
		this.isActive = active;
		this.stress = 0; 
		this.oldStress = 0;
		this.weightStep = weightStep;
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

	protected double getWeightStep() {
		return weightStep;
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
	public double getDeltaStress( boolean strict ) {
		double delta = this.oldStress - stress; 
		if(!strict && Math.abs(delta)<Double.MIN_VALUE && Math.abs(stress)>Double.MIN_VALUE)
			delta = DEFAULT_WEIGHT_STEP;	
		return delta;
	}

	@Override
	public void setStress(double stress) {
		if( Math.abs(stress) > 1d)
			throw new NumberFormatException( S_ERR_INVALID_STRESS + stress);
		this.oldStress = this.stress;
		this.stress = stress;
		this.notifySymbiotChanged(new StressEvent (this));
	}

	@Override	
	public void clearStress(){
		this.stress = 0f;
	}

	@Override
	public void addInfluence( ISymbiot symbiot ) {
		this.signals.put(symbiot, new StressData( symbiot ));
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
	
	protected boolean enableSymbiot(ISymbiot reference) {
		return !reference.equals( this);
	}

	@Override
	public void updateStress() {
		this.signals.forEach((s,d)-> updateStress( s,d ));
	}

	protected void updateStress(ISymbiot reference, IStressData dt ) {
		if(!enableSymbiot(reference))
			return;
		IStressData data = getStressData(reference);//ensures that stressData is not null
		double refStress = reference.getDeltaStress( false);
		double weight = data.getWeight();
		//stressDelta >=0 is good, because this means that the stress is decreasing
		if( refStress > 0 )
			weight -= this.weightStep*data.getCurrentStress();
		else if( refStress < 0)
			weight += this.weightStep*data.getCurrentStress();
		weight = NumberUtils.clipRange(-1, 1, weight);
		data.setWeight(weight);		
		data.update();	
		this.notifySymbiotChanged(new StressEvent (this));
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
			overall += sd.getCurrentStress();
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
		if(( signals == null ) || signals.isEmpty())
			return 0d;
		double result = 0;

		Iterator<IStressData> iterator = signals.values().iterator();
		while( iterator.hasNext() ) {
			IStressData source = iterator.next();
			double stress = Math.abs(source.getDelta())<Double.MIN_VALUE?-0.01d: source.getDelta();
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
	public Map<ISymbiot, IStressData> getSignals() {
		return signals;
	}

	@Override
	public int compareTo(ISymbiot arg0) {
		return this.id.compareTo(arg0.getId());
	}	
}