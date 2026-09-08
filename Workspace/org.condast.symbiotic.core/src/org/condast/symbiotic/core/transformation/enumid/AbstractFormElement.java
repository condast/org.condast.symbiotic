package org.condast.symbiotic.core.transformation.enumid;

import org.condast.symbiotic.core.def.ISymbiot;

public abstract class AbstractFormElement<E extends Enum<E>, S extends ISymbiot>{

	private E form;
	
	private S symbiot; 

	protected AbstractFormElement(E form, S symbiot) {
		this.form = form;
		this.symbiot = symbiot;
	}

	public E getForm() {
		return form;
	}

	public S getSymbiot() {
		return this.symbiot;
	}
}
