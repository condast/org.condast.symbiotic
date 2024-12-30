package org.condast.symbiot.core.onedim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiot.core.organism.Eye;
import org.condast.symbiotic.core.StressData;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.collection.ISymbiotCollection;
import org.condast.symbiotic.core.collection.SymbiotCollection;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.environment.IEnvironment;
import org.condast.symbiotic.core.environment.Location;
import org.condast.symbiotic.core.organism.IOrganism;
import org.condast.symbiotic.core.organism.IOrganismListener;
import org.condast.symbiotic.core.organism.OrganismEvent;

public class Organism1D extends Location implements IOrganism<Organism1D.Form>{

	public static final String S_ORGANISM = "ORGANISM";

	public enum Form{
		EYE,
		FLAGELLUM;

		@Override
		public String toString() {
			return StringStyler.sentence( name() );
		}
	}

	private Map<Form, ISymbiot> design;

	private ISymbiotCollection symbiots;

	private Collection<IOrganismListener<Organism1D.Form>> listeners;

	public Organism1D() {
		super();
		symbiots = new SymbiotCollection();
		design();
		this.listeners = new ArrayList<>();
	}

	/**
	 * Design the organism
	 * @param step
	 */
	protected void design() {
		Eye<Organism1D.Form> eye = new Eye<Organism1D.Form>( Form.EYE, true);
		symbiots.add(eye);
		design = new HashMap<>();
		design.put(Form.EYE, eye);
		
		Flagellum1D flagellum = new Flagellum1D( Form.FLAGELLUM, true);
		flagellum.addInfluence(eye);
		symbiots.add(flagellum);
		design.put(Form.FLAGELLUM, flagellum);
	}

	@Override
	public ISymbiot getSymbiot( Form form) {
		return symbiots.get(form.name());
	}

	@Override
	public Collection<ISymbiot> getSymbiots() {
		return symbiots;
	}

	@Override
	public void addListener( IOrganismListener<Organism1D.Form> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener( IOrganismListener<Organism1D.Form> listener) {
		this.listeners.remove(listener);
	}

	protected void notifyListeners( OrganismEvent<Organism1D.Form> event ) {
		this.listeners.forEach( l->l.notifyOrganismChanged(event));
	}

	@SuppressWarnings("unchecked")
	@Override
	public double geDistance( Form form ) {
		Eye<Organism1D.Form> eye = (Eye<Organism1D.Form>) design.get( form );
		return eye.getInput();
	}

	/**
	 * The actual movement of the organism
	 * | 8 | 1 | 2 |
	 * | 7 | 0 | 3 |
	 * | 6 | 5 | 4 |
	 * @param angle
	 */
	protected void move( int out, IEnvironment<IOrganism<Organism1D.Form>> env ) {
		int x = super.getX();
		x += out;
		int minx = -env.getBorder().getX();
		int range =  env.getX() + env.getBorder().getX();
		if(( x < minx ) || ( x > range ))
			return;
		super.setX( x);
	}

	@Override
	public ISymbiot toSymbiot() {
		return new OrganismSymbiot( this.symbiots );
	}


	@SuppressWarnings("unchecked")
	@Override
	public void update( IEnvironment<IOrganism<Organism1D.Form>> env ) {
		Environment1D environment = (Environment1D) env;
		if( environment.noFood())
			return;

		//Secondly update the eyes to find the nearest food source
		int maxVision = environment.getX()+environment.getBorder().getX();//add a ceiling
		Eye<Organism1D.Form> eye = (Eye<Organism1D.Form>) design.get(Form.EYE);
		eye.setLocation(getX(), getY());
		eye.setMaxVision(maxVision);

		//This affects the two eyes
		int distance = environment.getNearestFoodDistance(eye.getX());
		eye.setInput( distance);

		//Then update the stress signals
		this.symbiots.updateSymbiots();

		//Move the organism,
		Flagellum1D flagellum = (Flagellum1D) design.get(Organism1D.Form.FLAGELLUM);
		int out = flagellum.getOutput();	
		move( out, environment );
				
		notifyListeners( new OrganismEvent<Organism1D.Form>(this));
	}

	/**
	 * This symbiot is mainly intended for purposes of visualisation, and does not contribute to the activities
	 */
	private static class OrganismSymbiot extends Symbiot{

		private ISymbiotCollection symbiots;
		private double stress;

		public OrganismSymbiot( ISymbiotCollection organism ) {
			super( S_ORGANISM );
			this.symbiots = organism;
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

	@Override
	public String log() {
		StringBuilder builder = new StringBuilder();	
		
		Flagellum1D flagellum = (Flagellum1D) getSymbiot( Organism1D.Form.FLAGELLUM);
		builder.append(": (");
		builder.append(flagellum.getOutput());
		builder.append(")");
		return builder.toString();
	}
}