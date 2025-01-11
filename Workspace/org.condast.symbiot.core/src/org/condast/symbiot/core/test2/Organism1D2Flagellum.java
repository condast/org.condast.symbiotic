package org.condast.symbiot.core.test2;

import java.util.Map;

import org.condast.symbiot.core.organism.Eye;
import org.condast.symbiot.core.organism.Flagellum;
import org.condast.symbiot.core.test.Organism2D;
import org.condast.symbiot.core.test.Organism2D.Form;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.environment.IEnvironment;
import org.condast.symbiotic.core.organism.AbstractOrganism;
import org.condast.symbiotic.core.organism.IOrganism;

public class Organism1D2Flagellum extends AbstractOrganism<Organism2D.Form>{

	public static final String S_ORGANISM = "ORGANISM";

	public Organism1D2Flagellum() {
		super();
	}

	/**
	 * Design the organism
	 * @param step
	 */
	@Override
	protected void onDesign(Map<Form, ISymbiot> design) {
		Eye<Organism2D.Form> eye = new Eye<Organism2D.Form>( Organism2D.Form.LEFT_EYE, true);
		design.put(Form.LEFT_EYE, eye);
		
		Flagellum leftFlagellum = new Flagellum( Form.LEFT_FLAGELLUM, true);
		leftFlagellum.addInfluence(eye);
		design.put(Form.LEFT_FLAGELLUM, leftFlagellum);
		
		Flagellum rightFlagellum = new Flagellum(Form.RIGHT_FLAGELLUM, true);
		rightFlagellum.addInfluence(eye);
		rightFlagellum.addInfluence(leftFlagellum);
		design.put(Form.RIGHT_FLAGELLUM, rightFlagellum);		
		leftFlagellum.addInfluence(rightFlagellum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public double geDistance( Form form ) {
		Eye<Organism2D.Form> eye = (Eye<Organism2D.Form>) getSymbiot( form );
		return eye.getInput();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean updateInputSymbiots(IEnvironment<IOrganism<Form>> env) {
		Environment1D environment = (Environment1D) env;
		if( environment.noFood())
			return false;

		//Secondly update the eyes to find the nearest food source
		int maxVision = environment.getX()+environment.getBorder().getX();//add a ceiling
		Eye<Organism2D.Form> eye = (Eye<Organism2D.Form>) super.getSymbiot(Form.LEFT_EYE);
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
	protected void updateOutputSymbiots(IEnvironment<IOrganism<Form>> env) {
		//Move the organism,
		Flagellum flagellum = (Flagellum) super.getSymbiot(Organism2D.Form.LEFT_FLAGELLUM);
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
		Flagellum flagellum = (Flagellum) getSymbiot( Organism2D.Form.LEFT_FLAGELLUM);
		builder.append(": (");
		builder.append(flagellum.getOutput());
		builder.append(")");
	}
}