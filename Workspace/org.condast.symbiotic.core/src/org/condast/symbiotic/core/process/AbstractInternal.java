package org.condast.symbiotic.core.process;

import org.condast.symbiotic.core.def.ISymbiot;

public abstract class AbstractInternal<I extends Object> implements IInternal<I> {

	private I input;
	
	private ISymbiot symbiot;
	
	protected AbstractInternal(ISymbiot symbiot) {
		this( symbiot, null );
	}
	
	protected AbstractInternal(ISymbiot symbiot, I input) {
		super();
		this.input = input;
		this.symbiot = symbiot;
	}

	@Override
	public I getInput() {
		return input;
	}

	@Override
	public void setInput(I input) {
		this.input = input;
	}

	@Override
	public ISymbiot getSymbiot() {
		return symbiot;
	}

	/**
	 * Transform the input to a double <-1,1>
	 * @param output
	 * @return
	 */
	protected abstract double normalisedInput( I input);

	protected double onCalculateStress(ISymbiot symbiot, I input) {
		double stress = normalisedInput( input );
		symbiot.setStress(stress);
		return stress;
	}

	@Override
	public double getStress() {
		return this.onCalculateStress(symbiot, input);
	}
}