package org.condast.symbiotic.core.enumid;

import org.condast.symbiotic.core.Symbiot;

public class EnumSymbiot<E extends Enum<E>> extends Symbiot {

	private E form;

	protected EnumSymbiot(E form) {
		super(form.name());
		this.form = form;
	}

	protected EnumSymbiot(E form, float step) {
		super(form.name(), step);
		this.form = form;
	}

	public EnumSymbiot( E form, float step, boolean active) {
		super( form.name(), step, active);
		this.form = form;
	}

	public E getForm() {
		return form;
	}
}
