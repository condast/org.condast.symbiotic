package org.condast.symbiot.design.test1;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.design.organism.FlagellumProcess;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractProcessSymbiot;
import org.condast.symbiotic.core.process.IProcess;

public class Flagellum1D extends AbstractProcessSymbiot<Organism1D.Form, Double, Integer> {

	public Flagellum1D( Organism1D.Form form, boolean active) {
		super( form, active);
	}

	/**
	 * In this case the output needs to be able to switch from positive to negative quickly, so we don't add the factor to it
	 * instead the factor is the normalised output
	 */
	@Override
	protected IProcess<Double, Integer> createProcess(ISymbiot symbiot) {
		return new FlagellumProcess( symbiot, false );
	}

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
}
