package org.condast.symbiot.core.test1;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.core.organism.FlagellumProcess;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractProcessSymbiot;
import org.condast.symbiotic.core.process.IProcess;

public class Flagellum1D extends AbstractProcessSymbiot<Organism1D.Form, Double, Integer> {

	public Flagellum1D( Organism1D.Form form, boolean active) {
		super( form, active);
	}

	@Override
	protected IProcess<Double, Integer> createProcess(ISymbiot symbiot) {
		return new FlagellumProcess( symbiot );
	}

	@Override
	public void setInput(Double input) {
		super.getProcess().setInput(input);
	}


	@Override
	public ISymbiot getSymbiot() {
		return this;
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
