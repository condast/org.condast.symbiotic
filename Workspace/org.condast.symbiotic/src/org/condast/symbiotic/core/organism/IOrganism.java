package org.condast.symbiotic.core.organism;

import java.util.Collection;

import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.environment.IEnvironment;
import org.condast.symbiotic.core.environment.ILocation;

public interface IOrganism<E extends Enum<E>> extends ILocation {

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

	Collection<ISymbiot> getSymbiots();

	ISymbiot toSymbiot();
	
	public String log();
}