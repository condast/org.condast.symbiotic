package org.condast.symbiotic.core.enumid;

import org.condast.symbiotic.core.process.IProcess;

public abstract class AbstractProcessSymbiot<E extends Enum<E>, I extends Object, O extends Object> extends AbstractInternalSymbiot<I> implements IProcess<I,O>{

	private E form;
	
	private IProcess<I,O> process; 

	@SuppressWarnings("unchecked")
	protected AbstractProcessSymbiot(E form) {
		super(form.name());
		this.form = form;
		this.process = (IProcess<I, O>) this.createInternal( this );
	}

	@SuppressWarnings("unchecked")
	public AbstractProcessSymbiot( E form, boolean active) {
		super( form.name(), active);
		this.form = form;
		this.process = (IProcess<I, O>) this.createInternal( this );
	}
	
	public E getForm() {
		return form;
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
	public O onUpdate( double factor ) {
		return this.process.onUpdate( factor );
	}

	@Override
	public void update() {
		super.update();
		this.onUpdate( getFactor() );
	}
}
