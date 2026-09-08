package org.condast.symbiotic.core.special;

import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.IOutputSymbiot;

public abstract class AbstractOutputSymbiot<O extends Object> extends Symbiot implements IOutputSymbiot<O>{

	private O output;
	
	protected AbstractOutputSymbiot(String form) {
		this(form, true );
	}

	protected AbstractOutputSymbiot(String form, boolean active) {
		super( form, active);
		this.output = null;
	}
	
	@Override
	public O getOutput() {
		return output;
	}

	public abstract O onUpdate( double factor );

	@Override
	public void update() {
		super.update();
		this.output = this.onUpdate( getFactor() );
	}
	
	@Override
	public String toString() {
		return ( this.output == null )? "NULL": this.output.toString();
	}	
}
