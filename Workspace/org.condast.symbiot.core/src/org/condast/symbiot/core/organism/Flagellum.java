package org.condast.symbiot.core.organism;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.core.test.Organism2D;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractProcessSymbiot;
import org.condast.symbiotic.core.process.IProcess;

public class Flagellum extends AbstractProcessSymbiot<Organism2D.Form, Double, Integer> {

	private boolean oneEye;
	
	public Flagellum( Organism2D.Form form, boolean active) {
		this( form, false, active );
	}
	
	public Flagellum( Organism2D.Form form, boolean oneEye, boolean active) {
		super( form, active);
		this.oneEye = oneEye;
	}

	@Override
	protected IProcess<Double, Integer> createProcess(ISymbiot symbiot) {
		return new FlagellumProcess( symbiot, false, FlagellumProcess.DEFAULT_FACTOR_STEP * 0.01 );
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
			boolean enableEye = oneEye?Organism2D.Form.LEFT_EYE.equals(refForm): Organism2D.Form.RIGHT_EYE.equals(refForm);
			retval = enableEye || Organism2D.Form.LEFT_FLAGELLUM.equals(refForm);
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