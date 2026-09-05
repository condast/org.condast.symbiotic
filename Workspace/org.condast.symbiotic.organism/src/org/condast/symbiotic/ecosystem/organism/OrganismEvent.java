package org.condast.symbiotic.ecosystem.organism;

import java.util.EventObject;

public class OrganismEvent<E extends Enum<E>> extends EventObject {
	private static final long serialVersionUID = 1L;
	
	public OrganismEvent(IOrganism<E> source) {
		super(source);
	}

	@SuppressWarnings("unchecked")
	public IOrganism<E> getOrganism() {
		return (IOrganism<E>) super.getSource();
	}
}
