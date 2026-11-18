package org.condast.symbiotic.ecosystem.organism;

import java.util.HashMap;
import java.util.Map;

import org.condast.symbiotic.core.StressData;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.collection.ISymbiotCollection;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

/**
 * This symbiot is mainly intended for purposes of visualisation, and does not contribute to the activities
 */
public class OrganismSymbiot extends Symbiot implements ISymbiot{

	public static final String S_ORGANISM = "ORGANISM";

	private ISymbiotCollection symbiots;
	private double stress;

	public OrganismSymbiot( ISymbiotCollection symbiots ) {
		super( S_ORGANISM );
		this.symbiots = symbiots;
		this.stress = 0;
	}

	@Override
	public void clearStress() {
		this.symbiots.forEach((s) -> s.clearStress());
	}

	@Override
	public double getStress() {
		return this.symbiots.getAverageStress();
	}

	@Override
	public void setStress(double stress) {
		this.stress = stress;
	}

	@Override
	public double getDeltaStress() {
		return this.stress - this.symbiots.getAverageStress();
	}

	@Override
	public double getOverallStress() {
		return this.symbiots.getAverageStress();
	}

	@Override
	public Map<String, IStressData> getSignals() {
		Map<String, IStressData> results = new HashMap<>();
		this.symbiots.forEach((s) -> results.put(s.getId(), new StressData( s )));
		return results;
	}
}
