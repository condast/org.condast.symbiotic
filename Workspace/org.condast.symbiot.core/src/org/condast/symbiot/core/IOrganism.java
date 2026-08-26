package org.condast.symbiot.core;

import java.util.Collection;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiot.core.Organism.Angle;
import org.condast.symbiot.core.env.Environment;
import org.condast.symbiotic.core.def.ISymbiot;

public interface IOrganism extends ILocation {

	public enum Form{
		LEFT_EYE,
		RIGHT_EYE,
		LEFT_FLAGELLUM,
		RIGHT_FLAGELLUM,
		STOMACH;

		@Override
		public String toString() {
			return StringStyler.sentence( name() );
		}
	}

	void addListener(IOrganismListener listener);

	void removeListener(IOrganismListener listener);

	void update(Environment environment);

	double geDistance(Form form);

	Collection<ISymbiot> getSymbiots();

	Angle getAngle();

	ISymbiot toSymbiot();

}