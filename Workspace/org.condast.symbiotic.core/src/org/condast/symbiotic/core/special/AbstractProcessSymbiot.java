package org.condast.symbiotic.core.special;

import org.condast.symbiotic.core.def.IOutputSymbiot;

public abstract class AbstractProcessSymbiot<E extends Enum<E>, I extends Object, O extends Object> extends AbstractInputSymbiot<I> implements IOutputSymbiot<O>{

	private O output;
	
	protected AbstractProcessSymbiot(String form) {
		this(form, true );
	}

	protected AbstractProcessSymbiot( String form, boolean active) {
		super( form, active);
		this.output = null;
	}
	
	@Override
	public O getOutput() {
		return output;
	}

	@Override
	public void update() {
		super.update();
		this.onUpdate( getFactor() );
	}
}
