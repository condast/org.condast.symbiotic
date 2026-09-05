package org.condast.symbiotic.ecosystem.organism;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.condast.symbiotic.core.StressData;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.collection.ISymbiotCollection;
import org.condast.symbiotic.core.collection.SymbiotCollection;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.growth.DefaultGrowth;
import org.condast.symbiotic.core.growth.IGrowth;
import org.condast.symbiotic.ecosystem.environment.IEnvironment;
import org.condast.symbiotic.ecosystem.environment.Location;

public abstract class AbstractOrganism<E extends Enum<E>> extends Location implements IOrganism<E>{

	public static final String S_ORGANISM = "ORGANISM";

	private Map<E, ISymbiot> design;

	private ISymbiotCollection symbiots;

	private Collection<IOrganismListener<E>> listeners;

	protected AbstractOrganism() {
		this(  new SymbiotCollection());
	}

	protected AbstractOrganism( ISymbiotCollection symbiots ) {
		super();
		this.symbiots = symbiots;
		this.listeners = new ArrayList<>();
		design();
		symbiots.forEach( s -> {
			design.values().forEach( d -> {
				onAddInfluence(s, d);
			});
		});
	}

	/**
	 * design the organism;
	 * @param design
	 */
	protected abstract void onDesign( Map<E, ISymbiot> design );

	/**
	 * design the organism;
	 * @param design
	 */
	protected void onAddSymbiots( ISymbiotCollection symbiots ) {
		/**
		 * Default NOTHING
		 */
	}

	/**
	 * design the organism;
	 * @param design
	 */
	protected void onAddInfluence( ISymbiot source, ISymbiot target ) {
		if(!target.equals(source))
			source.addInfluence(target);
		
	}

	/**
	 * Design the organism
	 * @param step
	 */
	protected void design() {
		design = new HashMap<>();
		onDesign(design);
		design.values().forEach( s -> symbiots.add(s));
		this.onAddSymbiots(symbiots);
	}

	@Override
	public void clear() {
		this.symbiots.forEach( s -> s.clear());
	}

	@Override
	public void setLocation( int x, int y ) {
		super.setLocation(x, y);
	}

	protected ISymbiotCollection getSymbiotCollection() {
		return this.symbiots;
	}
	
	@Override
	public Collection<ISymbiot> getSymbiots() {
		return symbiots;
	}

	@Override
	public ISymbiot getSymbiot( E form) {
		return symbiots.get(form.name());
	}

	@Override
	public void addListener( IOrganismListener<E> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener( IOrganismListener<E> listener) {
		this.listeners.remove(listener);
	}

	protected void notifyListeners( OrganismEvent<E> event ) {
		this.listeners.forEach( l->l.notifyOrganismChanged(event));
	}

	@Override
	public ISymbiot toSymbiot() {
		return new OrganismSymbiot( this.symbiots );
	}

	/**
	 * First update the symbiots that sense the environment. If the method returns false, then nothing was changed
	 */
	protected abstract boolean updateInputSymbiots( IEnvironment<IOrganism<E>> env);

	/**
	 * Then update the symbiots that change on the stress signals
	 */
	protected abstract void updateOutputSymbiots( IEnvironment<IOrganism<E>> env);

	@Override
	public void update( IEnvironment<IOrganism<E>> env ) {
		boolean result = this.updateInputSymbiots( env );
		if( !result)
			return;
		this.symbiots.updateSymbiots();
		this.updateOutputSymbiots( env );
		
		//IGrowth growth = new DefaultGrowth();
		//growth.prune(symbiots);
		notifyListeners( new OrganismEvent<E>(this));
	}

	/**
	 * This symbiot is mainly intended for purposes of visualisation, and does not contribute to the activities
	 */
	private static class OrganismSymbiot extends Symbiot{

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

	protected abstract void logOrganism( StringBuilder builder );
	
	@Override
	public String log() {
		StringBuilder builder = new StringBuilder();			
		logOrganism(builder);
		return builder.toString();
	}
}