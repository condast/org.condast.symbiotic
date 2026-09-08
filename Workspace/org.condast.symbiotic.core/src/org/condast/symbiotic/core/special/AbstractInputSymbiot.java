package org.condast.symbiotic.core.special;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.IInputSymbiot;

/**
 * an input symbiot has generates a stress signal based on an input signal, but does not necessarily produce an output
 * @param <I>
 */
public abstract class AbstractInputSymbiot<I extends Object> extends Symbiot implements IInputSymbiot<I>{

	private I input;
	
	protected AbstractInputSymbiot( String formId ) {
		this( formId, true);
	}

	protected AbstractInputSymbiot( String formId, boolean active) {
		super( formId, active);
		this.input = null;
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
	public void update() {
		double stress = (input == null )? 0d: NumberUtils.clipRange(-1, 1, createStress( input));
		setStress( stress );
		super.update();
	}
	
	@Override
	public String toString() {
		return ( this.input == null )? "NULL": this.input.toString();
	}

}