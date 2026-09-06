package org.condast.symbiotic.core.collection;

import java.util.Collection;
import java.util.Map;

import org.condast.symbiotic.core.def.ISymbiot;

public interface ISymbiotCollection extends Collection<ISymbiot>{

	/**
	 * reset the internal counter of the collection
	 */
	void reset();

	/**
	 * get the update counter
	 * @return
	 */
	long getCounter();

	/**
	 * returns the symbiot with the given identifier
	 * @param identifier
	 * @return
	 */
	ISymbiot get(String identifier);

	/**
	 * clear the symbiots
	 */
	void clear();

	/**
	 * Get the cumulated stress from the symbiots
	 * @return
	 */
	Map<String, Double> getCumultatedStress();

	boolean add(ISymbiot symbiot);
	/**
	 *Get the overall stress levels of all the symbiots in the collection
	 * @return
	 */
	Map<String, Map<String, Double>> getStress();
	
	/**
	 * Update the cell based on the other symbiots
	 * @param symbiots
	 */
	void updateSymbiots();

	/**
	 *Get the average stress levels of all the symbiots in the collection
	 * @return
	 */
	double getAverageStress();
}