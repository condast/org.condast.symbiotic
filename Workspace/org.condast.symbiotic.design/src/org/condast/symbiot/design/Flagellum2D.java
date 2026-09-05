package org.condast.symbiot.design;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.design.organs.FlagellumProcess;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractProcessSymbiot;
import org.condast.symbiotic.core.process.IProcess;

public class Flagellum2D extends AbstractProcessSymbiot<Organism2D.Form, Double, Integer> {

	public Flagellum2D( Organism2D.Form form, boolean active) {
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
		Organism2D.Form refForm = Organism2D.Form.valueOf(reference);
		boolean retval = false;
		switch( super.getForm() ) {
		case LEFT_FLAGELLUM:
			retval = Organism2D.Form.LEFT_EYE.equals(refForm) || Organism2D.Form.RIGHT_FLAGELLUM.equals(refForm);
			break;
		case RIGHT_FLAGELLUM:
			retval =Organism2D.Form.RIGHT_EYE.equals(refForm) || Organism2D.Form.LEFT_FLAGELLUM.equals(refForm);
			break;
		default:
			break;
		}
		return retval;
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