package org.condast.symbiot.core;

import java.util.Collection;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiot.core.env.Environment;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.environment.ILocation;

public interface IOrganism extends ILocation {

	public enum Form{
		LEFT_EYE,
		RIGHT_EYE,
		LEFT_FLAGELLUM,
		RIGHT_FLAGELLUM,
		ANGLE,
		STOMACH;

		@Override
		public String toString() {
			return StringStyler.sentence( name() );
		}
	}

	/**
	 * Get the symbiot with the given form
	 * @param form
	 * @return
	 */
	ISymbiot getSymbiot(Form form);

	void addListener(IOrganismListener listener);

	void removeListener(IOrganismListener listener);

	void update(Environment environment);

	double geDistance(Form form);

	Collection<ISymbiot> getSymbiots();

	ISymbiot toSymbiot();
}