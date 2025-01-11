package org.condast.symbiot.core.test1;

import java.util.Map;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiot.core.organism.Eye;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.environment.IEnvironment;
import org.condast.symbiotic.core.organism.AbstractOrganism;
import org.condast.symbiotic.core.organism.IOrganism;

public class Organism1D extends AbstractOrganism<Organism1D.Form>{

	public enum Form{
		EYE,
		FLAGELLUM;

		@Override
		public String toString() {
			return StringStyler.sentence( name() );
		}
	}

	public Organism1D() {
		super();
	}

	/**
	 * Design the organism
	 * @param step
	 */

	@Override
	protected void onDesign(Map<Form, ISymbiot> design) {
		Eye<Organism1D.Form> eye = new Eye<Organism1D.Form>( Form.EYE, true);
		design.put(Form.EYE, eye);
		
		Flagellum1D flagellum = new Flagellum1D( Form.FLAGELLUM, true);
		flagellum.addInfluence(eye);
		design.put(Form.FLAGELLUM, flagellum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public double geDistance( Form form ) {
		Eye<Organism1D.Form> eye = (Eye<Organism1D.Form>) super.getSymbiot( form );
		return eye.getInput();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean updateInputSymbiots(IEnvironment<IOrganism<Organism1D.Form>> env) {
		Environment1D environment = (Environment1D) env;
		if( environment.noFood())
			return false;

		//Secondly update the eyes to find the nearest food source
		int maxVision = environment.getX()+environment.getBorder().getX();//add a ceiling
		Eye<Organism1D.Form> eye = (Eye<Organism1D.Form>) super.getSymbiot(Form.EYE);
		eye.setLocation(getX(), getY());
		eye.setMaxVision(maxVision);

		//This affects the two eyes
		int distance = environment.getNearestFoodDistance(eye.getX());
		eye.setInput( distance);
		return true;
	}

	/**
	 * The actual movement of the organism
	 * | 8 | 1 | 2 |
	 * | 7 | 0 | 3 |
	 * | 6 | 5 | 4 |
	 * @param angle
	 */
	@Override
	protected void updateOutputSymbiots( IEnvironment<IOrganism<Organism1D.Form>> env) {
		//Move the organism,
		Flagellum1D flagellum = (Flagellum1D) super.getSymbiot(Organism1D.Form.FLAGELLUM);
		int out = flagellum.getOutput();	

		int x = super.getX();
		x += out;
		int minx = -env.getBorder().getX();
		int range =  env.getX() + env.getBorder().getX();
		if(( x < minx ) || ( x > range ))
			return;
		super.setX( x);		
	}
	
	@Override
	protected void logOrganism(StringBuilder builder) {		
		Flagellum1D flagellum = (Flagellum1D) getSymbiot( Organism1D.Form.FLAGELLUM);
		builder.append(": (");
		builder.append(flagellum.getOutput());
		builder.append(")");
	}
}