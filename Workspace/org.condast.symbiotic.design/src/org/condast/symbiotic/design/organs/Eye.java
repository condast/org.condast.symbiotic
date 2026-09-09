package org.condast.symbiotic.design.organs;

import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.special.AbstractInputSymbiot;

public class Eye<E extends Enum<E>> extends AbstractInputSymbiot<Integer> {

	private int x,y;
	private int maxVision;
	private int angle;
	
	public Eye( E form, boolean active) {
		super( form.name(), active);
		this.maxVision = Integer.MAX_VALUE;
	}

	public Integer getInput() {
		Integer inp = super.getInput();
		return (inp == null )?0: inp;
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

	@Override
	protected boolean enableUpdate(IStressData data) {
		setLearning( Math.abs( getStress()) > 0.1);
		return super.enableUpdate(data);
	}

	@Override
	protected double createStress(Integer input) {
		return (input == null )?0: Math.abs( input.doubleValue()/maxVision);
	}
}
