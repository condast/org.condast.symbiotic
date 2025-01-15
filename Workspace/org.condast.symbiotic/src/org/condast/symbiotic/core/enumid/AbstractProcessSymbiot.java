package org.condast.symbiotic.core.enumid;

import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.process.IProcess;

public abstract class AbstractProcessSymbiot<E extends Enum<E>, I extends Object, O extends Object> extends Symbiot implements IProcess<I,O>{

	private E form;
	
	private IProcess<I,O> process; 

	protected AbstractProcessSymbiot(E form) {
		super(form.name());
		this.form = form;
		this.process = this.createProcess( this );
	}

	public AbstractProcessSymbiot( E form, boolean active) {
		super( form.name(), active);
		this.form = form;
		this.process = this.createProcess( this );
	}

	protected abstract IProcess<I,O> createProcess( ISymbiot symbiot);
	
	public E getForm() {
		return form;
	}

	@Override
	public ISymbiot getSymbiot() {
		return this;
	}

	@Override
	public void setInput( I input) {
		process.setInput(input);
	}

	protected IProcess<I, O> getProcess() {
		return process;
	}

	@Override
	public double getNormalisedOutput() {
		return this.process.getNormalisedOutput();
	}

	@Override
	public O getOutput() {
		return this.process.getOutput();
	}

	@Override
	public O updateOutput( double factor ) {
		return this.process.updateOutput( factor );
	}

	@Override
	public void update() {
		super.update();
		this.updateOutput( getFactor() );
	}
}
