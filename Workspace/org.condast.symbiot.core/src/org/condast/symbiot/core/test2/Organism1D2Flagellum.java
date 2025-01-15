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
	 * The actual movement of the organism
	 * | -1 | -1 |  Left |
	 * | x  | |x |   0   |
	 * | 1  | 1  | Right |
	 * @param leftOut, rightOut
	 */
	@Override
	protected void updateOutputSymbiots(IEnvironment<IOrganism<Form>> env) {
		Flagellum leftFlagellum = (Flagellum) super.getSymbiot(Organism2D.Form.LEFT_FLAGELLUM);
		int leftOut = leftFlagellum.getOutput();	

		Flagellum rightFlagellum = (Flagellum) super.getSymbiot(Organism2D.Form.RIGHT_FLAGELLUM);
		int rightOut = rightFlagellum.getOutput();	

		int x = super.getX();
		if(( leftOut * rightOut ) <= 0 )
			return;
		if( leftOut > 0 )
			x += 1;
		else if( leftOut < 0 )
			x-= 1;
		super.setX( x );
	}


	@Override
	protected void logOrganism(StringBuilder builder) {		
		Flagellum flagellum = (Flagellum) getSymbiot( Organism2D.Form.LEFT_FLAGELLUM);
		builder.append(": (");
		builder.append(flagellum.getOutput());
		builder.append(")");
	}
}