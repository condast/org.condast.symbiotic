package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiotic.core.enumid.AbstractEnumInputSymbiot;

public class Eye extends AbstractEnumInputSymbiot<IOrganism.Form, Integer> {

	private int maxVision;
	
	public Eye( IOrganism.Form form, float step, boolean active) {
		super( form, step, active);
		this.maxVision = Integer.MAX_VALUE;
		super.setInput( Integer.MAX_VALUE );
	}

	public int getMaxVision() {
		return maxVision;
	}

	public void setMaxVision(int maxVision) {
		this.maxVision = maxVision;
	}
	
	@Override
	protected boolean updateStress(Integer input) {
		float stress = (input == null )?0:  input.floatValue()/maxVision;
		setStress( stress);
		return false;
	}
}
