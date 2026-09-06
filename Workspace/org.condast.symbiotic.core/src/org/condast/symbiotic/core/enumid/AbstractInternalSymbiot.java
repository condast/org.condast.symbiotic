package org.condast.symbiotic.core.enumid;

import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.process.IInternal;

public abstract class AbstractInternalSymbiot<I extends Object> extends Symbiot implements IInternal<I>{

	private IInternal<I> internal; 

	protected AbstractInternalSymbiot(String name) {
		super(name);
		this.internal = this.createInternal( this );
	}

	public AbstractInternalSymbiot( String name, boolean active) {
		super( name, active);
		this.internal = this.createInternal( this );
	}

	protected abstract IInternal<I> createInternal( ISymbiot symbiot);
	
	protected IInternal<I> getInternal() {
		return internal;
	}

	@Override
	public ISymbiot getSymbiot() {
		return this;
	}

	@Override
	public I getInput() {
		return internal.getInput();
	}

	@Override
	public void setInput( I input) {
		internal.setInput(input);
	}

	@Override
	public void update() {
		internal.getStress();
		super.update();
	}
}
