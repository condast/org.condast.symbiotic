package org.condast.symbiotic.design.test1;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.special.AbstractOutputSymbiot;

public class Flagellum1D extends AbstractOutputSymbiot<Integer> {

	public Flagellum1D( Organism1D.Form form, boolean active) {
		super( form.name(), active);
	}

	/**
	@Override
	public boolean enableUpdate(String reference) {
		Organism1D.Form refForm = Organism1D.Form.valueOf(reference);
		return Organism1D.Form.EYE.equals(refForm);
	}


	/**
	 * The stress of the flagellum is equal to the factor
	 */
	@Override
	public void update() {
		double factor = getFactor();
		factor = NumberUtils.clipRange(-1, 1, factor);
		setStress( factor );
		super.update();
	}

	@Override
	public double getNormalisedOutput() {
		return super.getFactor();
	}

	@Override
	public Integer onUpdate(double factor) {
		Integer output = (int) NumberUtils.clipRange(-1, 1, factor);
		return output;
	}
}
