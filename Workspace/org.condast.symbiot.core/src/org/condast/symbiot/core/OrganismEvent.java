package org.condast.symbiot.core;

import java.util.EventObject;

public class OrganismEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	public OrganismEvent(Organism source) {
		super(source);
	}

	public Organism getOrganism() {
		return (Organism) super.getSource();
	}
}
