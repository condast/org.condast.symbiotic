package org.condast.symbiot.design.organs;

import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractInternalSymbiot;
import org.condast.symbiotic.core.process.AbstractInternal;
import org.condast.symbiotic.core.process.IInternal;

public class Eye<E extends Enum<E>> extends AbstractInternalSymbiot<Integer> {

	private int x,y;
	private int maxVision;
	private int angle;
	
	public Eye( E form, boolean active) {
		super( form.name(), active);
		this.maxVision = Integer.MAX_VALUE;
	}

	@Override
	protected IInternal<Integer> createInternal(ISymbiot symbiot) {
		return new Internal( symbiot );
	}

	public Integer getInput() {
		Internal internal = (Eye<E>.Internal) getInternal();
		Integer inp = internal.getInput();
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

	private class Internal extends AbstractInternal<Integer>{

		protected Internal(ISymbiot symbiot) {
			super(symbiot);
		}
	
		@Override
		protected double normalisedInput( Integer input ) {
			return (input == null )?0: Math.abs( input.doubleValue()/maxVision);
		}
	}
}
