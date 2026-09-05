package org.condast.symbiot.design.organs;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractProcessSymbiot;
import org.condast.symbiotic.core.process.AbstractProcess;
import org.condast.symbiotic.core.process.IProcess;

public class Eye<E extends Enum<E>> extends AbstractProcessSymbiot<E, Integer, Double> {

	private int x,y;
	private int maxVision;
	private int angle;
	
	public Eye( E form, boolean active) {
		super( form, active);
		this.maxVision = Integer.MAX_VALUE;
	}

	@Override
	protected IProcess<Integer, Double> createProcess(ISymbiot symbiot) {
		return new Process( symbiot );
	}

	public int getInput() {
		Process process = (Eye<E>.Process) getProcess();
		Integer inp = process.getInput();
		return (inp == null )?0: inp;
	}
	
	@Override
	public void setInput(Integer input) {
		getProcess().setInput(input);
	}

	@Override
	public ISymbiot getSymbiot() {
		return this;
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
	public void update() {
		double stress = getProcess().getStress();
		stress = NumberUtils.clipRange( -1, 1, stress);
		setStress( stress);
		super.update();
	}

	private class Process extends AbstractProcess<Integer, Double>{

		protected Process(ISymbiot symbiot) {
			super(symbiot);
		}
	
		@Override
		public Integer getInput() {
			return super.getInput();
		}


		@Override
		public double getStress() {
			return normalisedInput();
		}

		@Override
		protected double normalisedInput() {
			Integer input = super.getInput();
			return (input == null )?0: Math.abs( input.doubleValue()/maxVision);
		}

		/**
		 * No specific output
		 */
		@Override
		protected Double transformOutput(double output) {
			return 0d;
		}
	}
}
