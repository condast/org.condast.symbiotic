package org.condast.symbiot.design.organs;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.design.Organism2D;
import org.condast.symbiot.design.Organism2D.Form;

public class Flagellum extends FlagellumSymbiot<Organism2D.Form> {

	private boolean oneEye;
	
	public Flagellum( Organism2D.Form form, boolean active) {
		this( form, false, active );
	}
	
	public Flagellum( Organism2D.Form form, boolean oneEye, boolean active) {
		super( form, active);
		this.oneEye = oneEye;
	}

	@Override
	public boolean enableUpdate(String reference) {
		boolean retval = false;
		if( !Organism2D.Form.isForm(reference) )
			return retval;
		Organism2D.Form refForm = Organism2D.Form.valueOf(reference);
		Form form = Form.valueOf(super.getId());
		switch( form ) {
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

	@Override
	public Integer getOutput() {
		return ( super.getOutput() == null )? 0: super.getOutput();
	}
	
	@Override
	public Integer onUpdate(double factor) {
		double stress = getStress();
		stress += factor;
		stress = NumberUtils.clipRange(-1, 1, stress);
		setStress( stress );
		this.setLearning(true);
		return super.onUpdate(factor);
	}
}