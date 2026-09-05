package org.condast.symbiot.design.organs;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.design.Organism2D;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractProcessSymbiot;
import org.condast.symbiotic.core.process.AbstractProcess;
import org.condast.symbiotic.core.process.IProcess;

public class Stomach extends AbstractProcessSymbiot<Organism2D.Form, Integer, Integer> {

	public static final int FULL_STOMACH = 100;
	
	public Stomach( Organism2D.Form form, boolean active) {
		this( form, FULL_STOMACH, active );
	}
	
	public Stomach( Organism2D.Form form, int filled, boolean active) {
		super( form, active);
	}

	@Override
	protected IProcess<Integer, Integer> createProcess(ISymbiot symbiot) {
		return new Process( symbiot );
	}

	public void reset() {
		Process process = (Process) super.getProcess();
		process.reset();
	}

	/**
	 * The stress of the stomach is equal to the factor
	 */
	@Override
	public void update() {
		double stress = getStress();
		double factor = getFactor();
		stress += factor;
		stress = NumberUtils.clipRange(-1, 1, stress);
		setStress( stress );
		super.update();
	}
	
	private class Process extends AbstractProcess<Integer, Integer>{

		protected Process(ISymbiot symbiot) {
			this(symbiot, FULL_STOMACH);
		}

		protected Process(ISymbiot symbiot, int filled) {
			super(symbiot, filled);
		}

		public void reset() {
			super.setInput(FULL_STOMACH);
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
			return Math.abs( super.getInput()/FULL_STOMACH);
		}
		
		@Override
		public Integer onUpdate(double factor) {
			int input =  (super.getInput()== null)?0: super.getInput();
			int hunger =  NumberUtils.clipRange(0, FULL_STOMACH, input);
			super.setInput(hunger-1);
			return super.onUpdate(-factor);
		}

		/**
		 * No specific output
		 */
		@Override
		protected Integer transformOutput(double output) {
			return 0;
		}
	}
}