package org.condast.symbiotic.core.special;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IStressData;

/**
 * A hidden symbiot does not have I/O , and therefore can be potentially pruned
 * @param <E>
 * @param <I>
 * @param <O>
 */
public class HiddenSymbiot extends AbstractInputSymbiot<Double>{

	public static int DEFAULT_THRESHOLD_PERCENT = 10;

	private boolean hidden; //Hidden symbiots don't have I/O and can be pruned
	private int threshold;
	
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
	}
	
	public boolean isHidden() {
		return hidden;
	}

	public int getThreshold() {
		return threshold;
	}

	/**
	 * Returns true if the symbiot is isolated from the others, by the given threshold factor
	 */
	public boolean isIsolated() {
		return isIsolated( this, this.threshold);
	}

	@Override
	protected double createStress(Double input) {
		boolean isolated = isIsolated(this, threshold );
		double stress =  isolated? getStress() + IStressData.DEFAULT_NORMALISED_STEP: getStress() - IStressData.DEFAULT_NORMALISED_STEP;
		stress = NumberUtils.clipRange(-1,  1, stress );
		setStress( stress );
		return stress;
	}


	/**
	 * The stress of a hidden symbiot is based on its isolation
	 */
	@Override
	public void update() {
		super.update();
		if( !this.hidden)
			return;
		
	}
}