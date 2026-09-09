package org.condast.symbiotic.design.organs;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.special.AbstractInputSymbiot;
import org.condast.symbiotic.design.Organism2D;

public class Stomach extends AbstractInputSymbiot<Integer> {

	public static final int FULL_STOMACH = 100;
	
	public Stomach( Organism2D.Form form, boolean active) {
		this( form, FULL_STOMACH, active );
	}
	
	public Stomach( Organism2D.Form form, int filled, boolean active) {
		super( form.name(), active);
	}

	public void reset() {
		super.setInput(FULL_STOMACH);
	}

	@Override
	protected double createStress(Integer input) {
		int satisfied =  NumberUtils.clipRange(0, FULL_STOMACH, input);
		super.setInput(satisfied-1);
		double stress = (FULL_STOMACH - satisfied)/FULL_STOMACH;
		return stress;
	}
}