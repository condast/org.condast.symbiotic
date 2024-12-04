package org.condast.symbiot.symbiot;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.core.IOrganism;
import org.condast.symbiotic.core.enumid.AbstractEnumInputSymbiot;

public class Eye extends AbstractEnumInputSymbiot<IOrganism.Form, Integer> {

	private int x,y;
	private int maxVision;
	private int angle;
	
	
	public Eye( IOrganism.Form form, boolean active) {
		super( form, active);
		this.maxVision = Integer.MAX_VALUE;
		super.setInput( Integer.MAX_VALUE );
	}

	public int getMaxVision() {
		return maxVision;
	}

	public void setMaxVision(int maxVision) {
		this.maxVision = maxVision;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}


	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	/**
	 * Stress is purely based on distance
	 */
	@Override
	protected boolean updateStress(Integer input) {
		double stress = (input == null )?0: Math.abs( input.floatValue()/maxVision);
		stress = NumberUtils.clipRange( -1, 1, stress);
		setStress( stress);
		return false;
	}
}
