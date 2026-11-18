package org.condast.symbiotic.ecosystem.organism;

import java.util.Collection;

import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.ecosystem.environment.IEnvironment;
import org.condast.symbiotic.ecosystem.environment.ILocation;

public interface IOrganism<E extends Enum<E>> extends ILocation {

	/**
	 * Clear the data of the current organism, so that it 
	 * restarts with the current design
	 */
	public void clear();
	
	/**
	 * Get the symbiot with the given form
	 * @param form
	 * @return
	 */
	ISymbiot getSymbiot(E form);

	void addListener(IOrganismListener<E> listener);

	void removeListener(IOrganismListener<E> listener);

	void update(IEnvironment<IOrganism<E>> environment);

	double geDistance(E form);

	/**
	 * Set the location of the organism
	 * @param x
	 * @param y
	 */
	public void setLocation( int x, int y );

	Collection<ISymbiot> getSymbiots();

	ISymbiot toSymbiot();
	
	public String log();
}