package org.condast.symbiotic.core.organism;

public interface IOrganismListener<E extends Enum<E>> {

	public void notifyOrganismChanged( OrganismEvent<E> event);
}
