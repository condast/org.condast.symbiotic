package org.condast.symbiotic.ecosystem.organism;

public interface IOrganismListener<E extends Enum<E>> {

	public void notifyOrganismChanged( OrganismEvent<E> event);
}
