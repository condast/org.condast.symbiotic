package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.Organism;
import org.condast.symbiotic.core.Symbiot;

public abstract class AbstractSymbiot extends Symbiot {

	private Organism organism;
	
	protected AbstractSymbiot( String id, Organism organism, float step, boolean active) {
		super( id, step, active);
		this.organism = organism;
	}

	public Organism getOrganism() {
		return organism;
	}

	/**
	 * update based on the nearest food item
	 * @param food
	 */
	public abstract void update( double distance );
	
}
