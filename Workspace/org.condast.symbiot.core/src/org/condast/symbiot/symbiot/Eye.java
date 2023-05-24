package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiotic.core.AbstractInputSymbiot;

public class Eye extends AbstractInputSymbiot<Integer> {

	private IOrganism.Form form;
	private int maxVision;
	
	
	public Eye( IOrganism.Form form, float step, boolean active) {
		super( form.name(), step, active);
		super.setInput( Integer.MAX_VALUE );
		this.form = form;
	}

	public int getMaxVision() {
		return maxVision;
	}

	public void setMaxVision(int maxVision) {
		this.maxVision = maxVision;
	}

	public IOrganism.Form getForm() {
		return form;
	}
	
	@Override
	protected boolean updateStress(Integer input) {
		float stress = (input == null )?0:  input.floatValue()/maxVision;
		setStress( stress);
		return false;
	}	
}
