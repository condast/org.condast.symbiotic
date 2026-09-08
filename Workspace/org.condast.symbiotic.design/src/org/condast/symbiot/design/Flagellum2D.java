package org.condast.symbiot.design;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.design.Organism2D.Form;
import org.condast.symbiotic.core.special.AbstractOutputSymbiot;

public class Flagellum2D extends AbstractOutputSymbiot<Integer> {

	public Flagellum2D( Organism2D.Form form, boolean active) {
		super( form.name(), active);
	}

	@Override
	public boolean enableUpdate(String reference) {
		Organism2D.Form refForm = Organism2D.Form.valueOf(reference);
		boolean retval = false;
		Form form = Form.valueOf(super.getId());
		switch( form ) {
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

	@Override
	public double getNormalisedOutput() {
		return super.getOutput();
	}

	@Override
	public Integer onUpdate(double factor) {
		factor = NumberUtils.clipRange(-1, 1, factor);
		setStress( factor );
		return (factor < -0.5)?-1:(factor >0.5)?1:0;
	}
}