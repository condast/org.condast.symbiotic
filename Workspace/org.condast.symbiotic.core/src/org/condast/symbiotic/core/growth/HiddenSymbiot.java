package org.condast.symbiotic.core.growth;

import java.util.Collection;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

/**
 * A hidden symbiot does not have I/O , and therefore can be potentially pruned
 * @param <E>
 * @param <I>
 * @param <O>
 */
public class HiddenSymbiot extends Symbiot{

	public static int DEFAULT_THRESHOLD_PERCENT = 10;

	private boolean hidden; //Hidden symbiots don't have I/O and can be pruned
	private int threshold;
	
	private double isolation;

	public HiddenSymbiot( String form) {
		this( form, true, DEFAULT_THRESHOLD_PERCENT);
	}

	public HiddenSymbiot( String formId, int threshold) {
		this( formId, true, threshold);
	}

	public HiddenSymbiot( String formId, boolean hidden, int threshold) {
		this( formId, hidden, threshold, true);
	}

	public HiddenSymbiot( String formId, boolean hidden, int threshold, boolean active) {
		super( formId, active);
		this.hidden = hidden;
		this.threshold = NumberUtils.clipRange(100, threshold);
		this.isolation = 0;
	}

	public boolean isHidden() {
		return hidden;
	}

	public int getThreshold() {
		return threshold;
	}

	public void reset() {
		this.isolation = 0;
	}

	/**
	 * Returns true if the symbiot is isolated from the others, by the given threshold factor
	 */
	public boolean isIsolated() {
		return isIsolated( this, this.threshold);
	}

	/**
	 * The stress of a hidden symbiot is based on its isolation
	 */
	@Override
	public void update() {
		super.update();
		if( !this.hidden)
			return;
		
		this.isolation = getIsolationRatio(this, this.threshold );
		super.setStress( this.isolation);
	}
	
	/**
	 * Returns true if the symbiot is isolated from the others, by the given threshold factor
	 */
	public static boolean isIsolated( ISymbiot symbiot, int threshold) {
		Collection<IStressData> signals = symbiot.getSignals().values();
		if(( signals == null ) || signals.isEmpty())
			return true;

		double th = threshold/100d;
		for( IStressData source: signals ) {
			ISymbiot target = source.getTarget();
			IStressData tsd = target.getSignals().get( symbiot.getId());
			double stress = Math.abs( tsd.getStress());
			if( stress >= th)
				return false;
		}
		return true;
	}

	/**
	 * Get the ratio of the isolation between symbiots, based on the given threshold 
	 */
	public static double getIsolationRatio( ISymbiot symbiot, int threshold) {
		int count = 0;
		Collection<IStressData> signals = symbiot.getSignals().values();
		if(( signals == null ) || signals.isEmpty())
			return count;

		double th = threshold/100d;
		for( IStressData source: signals ) {
			ISymbiot target = source.getTarget();
			IStressData tsd = target.getSignals().get( symbiot.getId());
			if( tsd == null )
				continue;
			double stress = Math.abs( tsd.getStress());
			if( stress < th)
				count++;
		}
		return ((double)count/signals.size());
	}

}