package org.condast.symbiot.design.organs;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiot.design.Organism2D;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractInternalSymbiot;
import org.condast.symbiotic.core.process.AbstractInternal;
import org.condast.symbiotic.core.process.IInternal;

public class Stomach extends AbstractInternalSymbiot<Integer> {

	public static final int FULL_STOMACH = 100;
	
	public Stomach( Organism2D.Form form, boolean active) {
		this( form, FULL_STOMACH, active );
	}
	
	public Stomach( Organism2D.Form form, int filled, boolean active) {
		super( form.name(), active);
	}

	@Override
	protected IInternal<Integer> createInternal(ISymbiot symbiot) {
		return new Internal( symbiot );
	}

	public void reset() {
		Internal internal = (Internal) super.getInternal();
		internal.reset();
	}

	/**
	 * The stress of the stomach is equal to the factor
	 */
	@Override
	public void update() {
		Internal internal = (Internal) super.getInternal();
		internal.update(super.getFactor());
		double stress = getStress();
		double factor = getFactor();
		stress += factor;
		stress = NumberUtils.clipRange(-1, 1, stress);
		setStress( stress );
		super.update();
	}
	
	private class Internal extends AbstractInternal<Integer>{

		protected Internal(ISymbiot symbiot) {
			this(symbiot, FULL_STOMACH);
		}

		protected Internal(ISymbiot symbiot, int filled) {
			super(symbiot, filled);
		}

		public void reset() {
			super.setInput(FULL_STOMACH);
		}
		
		@Override
		public Integer getInput() {
			Integer input =  super.getInput();
			return (input == null)?0: input;
		}

		@Override
		protected double normalisedInput( Integer input ) {
			return Math.abs( super.getInput()/FULL_STOMACH);
		}
		
		public Integer update(double factor) {
			int input =  (super.getInput()== null)?0: super.getInput();
			int hunger =  NumberUtils.clipRange(0, FULL_STOMACH, input);
			super.setInput(hunger-1);
			return super.getInput();
		}
	}
}