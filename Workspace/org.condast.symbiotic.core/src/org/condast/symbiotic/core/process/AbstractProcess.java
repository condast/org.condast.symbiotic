package org.condast.symbiotic.core.process;

import org.condast.symbiotic.core.def.ISymbiot;

public abstract class AbstractProcess<I, O extends Object> implements IProcess<I, O> {

	private I input;
	private double output;
	
	private ISymbiot symbiot;
	
	/**
	 * The default behaviour of a process is to add a factor to the output
	 * before transforming it. It is also possible to only use the factor
	 */
	private boolean addFactor;

	protected AbstractProcess(ISymbiot symbiot) {
		this( symbiot, true );
	}
	
	protected AbstractProcess(ISymbiot symbiot, boolean addFactor) {
		this( symbiot, null, addFactor);
	}

	protected AbstractProcess(ISymbiot symbiot, I input) {
		this( symbiot, null, true);
	}

	protected AbstractProcess(ISymbiot symbiot, I input, boolean addFactor) {
		super();
		this.addFactor = addFactor;
		this.input = input;
		this.output = 0;
		this.symbiot = symbiot;
	}

	protected boolean isAddFactor() {
		return addFactor;
	}

	protected void setAddFactor(boolean addFactor) {
		this.addFactor = addFactor;
	}

	/**
	 * Transform the input to a double <-1,1>
	 * @param output
	 * @return
	 */
	protected abstract double normalisedInput();

	protected I getInput() {
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

	@Override
	public O getOutput() {
		return transformOutput(output);
	}

	@Override
	public double getNormalisedOutput() {
		return output;
	}

	protected void setNormalisedOutput(double output) {
		this.output = output;
	}

	/**
	 * Transform the output <-1,1> to the desired output
	 * @param output
	 * @return
	 */
	protected abstract O transformOutput( double output );
	
	/**
	 * The default behaviour is to omit the factor
	 */
	@Override
	public O onUpdate( double factor ) {
		double update = addFactor? output+factor: factor;
		setNormalisedOutput(update);
		return this.transformOutput(update );
	}
}