package org.condast.symbiotic.core.special;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.StressData;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.IInputSymbiot;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

/**
 * an input symbiot has generates a stress signal based on an input signal, but does not necessarily produce an output
 * @param <I>
 */
public abstract class AbstractInputSymbiot<I extends Object> extends Symbiot implements IInputSymbiot<I>{

	public static final String S_INPUT_SOURCE = "Input Source: ";
	
	private I input;
	
	protected AbstractInputSymbiot( String formId ) {
		this( formId, true);
	}

	protected AbstractInputSymbiot( String formId, boolean active) {
		super( formId, active);
		this.input = null;
		this.addInfluence( new InputStressData( this ));
	}

	/**
	 * An internal symbiot creates a stress signal based on its input
	 * @param symbiot
	 * @return
	 */
	protected abstract double createStress(I input);
	
	@Override
	public I getInput() {
		return input;
	}

	@Override
	public void setInput( I input) {
		this.input = input;
	}

	@Override
	protected double onUpdateStress(double currentStress, double factor) {
		return (input == null )? 0d: NumberUtils.clipRange(-1, 1, createStress( input));
	}

	@Override
	public String toString() {
		return ( this.input == null )? "NULL": this.input.toString();
	}

	private class InputStressData extends StressData {

		public InputStressData( ISymbiot source) {
			super( source, IStressData.DEFAULT_WEIGHT_STEP, IStressData.DEFAULT_WEIGHT_STEP );
		}

		@Override
		public String getReference() {
			return S_INPUT_SOURCE + super.getReference();
		}
		
		@Override
		public void clear() {
			super.clear();
			super.setWeight(DEFAULT_WEIGHT_STEP);
		}

		@Override
		public double getStress() {
			double stress = createStress(input);
			return stress;
		}	
	}
}