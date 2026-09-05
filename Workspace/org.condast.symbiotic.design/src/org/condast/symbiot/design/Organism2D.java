package org.condast.symbiot.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiot.design.organs.Eye;
import org.condast.symbiot.design.organs.Flagellum;
import org.condast.symbiot.design.organs.Stomach;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.collection.ISymbiotCollection;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.ecosystem.environment.IEnvironment;
import org.condast.symbiotic.ecosystem.organism.AbstractOrganism;
import org.condast.symbiotic.ecosystem.organism.IOrganism;

public class Organism2D extends AbstractOrganism<Organism2D.Form>{

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

	public Organism2D() {
		super();
	}

	/**
	 * Design the organism
	 * @param step
	 */
	@Override
	protected void onDesign(Map<Form, ISymbiot> design) {
		Eye<Organism2D.Form> leftEye = new Eye<>( Form.LEFT_EYE, true);
		design.put(Form.LEFT_EYE, leftEye);
		
		Eye<Organism2D.Form> rightEye = new Eye<>( Form.RIGHT_EYE, true);
		design.put(Form.RIGHT_EYE, rightEye);

		AngleControl angleControl = new AngleControl( Form.ANGLE);
		design.put(Form.ANGLE, angleControl);

		Flagellum leftFlagellum = new Flagellum( Form.LEFT_FLAGELLUM, true);
		design.put(Form.LEFT_FLAGELLUM, leftFlagellum);
		
		Flagellum rightFlagellum = new Flagellum(Form.RIGHT_FLAGELLUM, true);
		design.put(Form.RIGHT_FLAGELLUM, rightFlagellum);
		
		Stomach stomach = new Stomach( Form.STOMACH, true );
		design.put( Form.STOMACH, stomach);
	}
	
	@Override
	protected void onAddSymbiots(ISymbiotCollection symbiots) {
		for( int i = 0; i< 10; i++ ) {
			ISymbiot newSymbiot = new Symbiot( String.valueOf(i), true ); 
			List<ISymbiot> list = new ArrayList<>(symbiots);
			double weight = 1d;
			double delta = weight/(list.size()+1);
			for( int j=list.size()-1; j>=0; j--) {
				ISymbiot target = list.get(j);
				newSymbiot.addInfluence(target, weight );
				weight -= delta;
			}
			symbiots.add( newSymbiot );
		}
		super.onAddSymbiots(symbiots);
	}

	@SuppressWarnings("unchecked")
	@Override
	public double geDistance( Form form ) {
		Eye<Organism2D.Form> eye = (Eye<Organism2D.Form>) super.getSymbiot( form );
		return eye.getInput();
	}

	/**
	 * The actual movement of the organism
	 * | 8 | 1 | 2 |
	 * | 7 | 0 | 3 |
	 * | 6 | 5 | 4 |
	 * @param angle
	 */
	protected void move( AngleControl.Angle angle ) {
		int x = super.getX();
		int y=  super.getY();
		switch( angle ) {
		case NORTH:
			y-=1;
			break;
		case NORTH_EAST:
			x+=1;
			y-=1;
			break;
		case EAST:
			x+=1;
			break;
		case SOUTH_EAST:
			x+=1;
			y+=1;
			break;
		case SOUTH:
			y+=1;
			break;
		case SOUTH_WEST:
			x-=1;
			y+=1;
			break;
		case WEST:
			x-=1;
			break;
		case NORTH_WEST:
			x-=1;
			y-=1;
			break;
		default:
			break;
		}
		super.setX( x);
		super.setY(y);
	}

	/**
	 * Update the location of the eyes, based on the angle
	 * @param angle
	 */
	@SuppressWarnings("unchecked")
	protected void updateEyes() {
		Eye<Organism2D.Form> leftEye = (Eye<Organism2D.Form>) super.getSymbiot(Form.LEFT_EYE);
		Eye<Organism2D.Form> rightEye = (Eye<Organism2D.Form>) super.getSymbiot(Form.RIGHT_EYE);

		int x= getX();
		int y= getY();
		int offset = 1;		

		AngleControl angleControl = (AngleControl) super.getSymbiot( Organism2D.Form.ANGLE);
		switch( angleControl.getAngle() ) {
		case NORTH:
			leftEye.setLocation(x-offset, y-offset);
			rightEye.setLocation(x+offset, y-offset);
			break;
		case NORTH_EAST:
			leftEye.setLocation(x+offset, y-offset);
			rightEye.setLocation(x-offset, y-offset);
			break;
		case EAST:
			leftEye.setLocation(x+offset, y-offset);
			rightEye.setLocation(x-offset, y+offset);
			break;
		case SOUTH_EAST:
			leftEye.setLocation(x+offset, y-offset);
			rightEye.setLocation(x-offset, y+offset);
			break;
		case SOUTH:
			leftEye.setLocation(x-offset, y+offset);
			rightEye.setLocation(x+offset, y+offset);
			break;
		case SOUTH_WEST:
			leftEye.setLocation(x+offset, y-offset);
			rightEye.setLocation(x-offset, y+offset);
			break;
		case WEST:
			leftEye.setLocation(x-offset, y+offset);
			rightEye.setLocation(x-offset, y-offset);
			break;
		case NORTH_WEST:
			leftEye.setLocation(x+offset, y-offset);
			rightEye.setLocation(x+offset, y+offset);
			break;
		default://default NORTH facing, without movement
			leftEye.setLocation(x-offset, y);
			rightEye.setLocation(x+offset, y);
			break;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean updateInputSymbiots(IEnvironment<IOrganism<Form>> env) {
		Environment environment = (Environment) env;
		if( environment.noFood())
			return false;

		//First set the eyes to reflect the current angle
		this.updateEyes();
		
		//Secondly update the eyes to find the nearest food source
		int maxVision = environment.getDiagonal()+5;//add a ceiling
		Eye<Organism2D.Form> leftEye = (Eye<Organism2D.Form>) super.getSymbiot(Form.LEFT_EYE);
		Eye<Organism2D.Form> rightEye = (Eye<Organism2D.Form>) super.getSymbiot(Form.RIGHT_EYE);
		leftEye.setMaxVision(maxVision);
		rightEye.setMaxVision(maxVision);

		Stomach stomach = (Stomach) super.getSymbiot(Form.STOMACH);

		//First find the angle to the nearest food source
		int angle =  environment.getNearestFoodAngle(getX(), getY());

		//This affects the two eyes
		int distance = environment.getNearestFoodDistance(leftEye.getX(), leftEye.getY());
		leftEye.setInput( distance);
		leftEye.setAngle(angle);
		if( distance < 2)
			stomach.reset();

		distance = environment.getNearestFoodDistance(rightEye.getX(), rightEye.getY());
		rightEye.setInput(distance);
		rightEye.setAngle(angle);
		if( distance < 2)
			stomach.reset();
		return true;
	}

	@Override
	protected void updateOutputSymbiots(IEnvironment<IOrganism<Form>> env) {
		//Move the organism,
		Flagellum leftFlagellum = (Flagellum) super.getSymbiot(Form.LEFT_FLAGELLUM);
		Flagellum rightFlagellum = (Flagellum) super.getSymbiot(Form.RIGHT_FLAGELLUM);
		int outLeft = leftFlagellum.getOutput();
		int outRight = rightFlagellum.getOutput();		
		
		//Calculate the angle of movement
		AngleControl angleControl = (AngleControl) super.getSymbiot( Organism2D.Form.ANGLE);
		AngleControl.Angle movement =  angleControl.update(outLeft, outRight);
		move( movement );			
	}

	@Override
	protected void logOrganism(StringBuilder builder) {
		Flagellum leftFlagellum = (Flagellum)getSymbiot( Organism2D.Form.LEFT_FLAGELLUM);
		Flagellum rightFlagellum = (Flagellum) getSymbiot( Organism2D.Form.RIGHT_FLAGELLUM);
		builder.append(": (");
		builder.append(leftFlagellum.getOutput());
		builder.append(",");
		builder.append(rightFlagellum.getOutput());
		builder.append(")");
	}
}