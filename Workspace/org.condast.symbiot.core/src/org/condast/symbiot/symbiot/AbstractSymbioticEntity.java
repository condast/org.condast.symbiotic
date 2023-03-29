package org.condast.symbiot.symbiot;

import java.util.Collection;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.ISymbiot;

public abstract class AbstractSymbioticEntity<D extends Object> extends Symbiot implements ISymbioticEntity<D> {

	private IOrganism organism;
	
	private D data;
	
	protected AbstractSymbioticEntity( String id, IOrganism organism, float step, boolean active) {
		super( id, step, active);
		this.organism = organism;
	}

	@Override
	public D getData() {
		return data;
	}

	protected void setData(D data) {
		this.data = data;
	}


	protected IOrganism getOrganism() {
		return organism;
	}

	/**
	 * update based on the nearest food item
	 * @param food
	 */
	public abstract void update( double distance );
	
	/**
	 * Update the cell based on the other symbiots
	 * @param symbiots
	 */
	@Override
	public void update( Collection<ISymbiot> symbiots ) {
		/* DEFAULT NOTHING */
	}
	
}
