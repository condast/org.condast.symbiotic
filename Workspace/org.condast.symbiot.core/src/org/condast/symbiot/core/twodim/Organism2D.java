package org.condast.symbiot.core.twodim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiot.core.organism.Eye;
import org.condast.symbiot.core.organism.Flagellum;
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

public class Organism2D extends Location implements IOrganism<Organism2D.Form>{

	public static final String S_ORGANISM = "ORGANISM";

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

	private Map<Form, ISymbiot> design;

	private ISymbiotCollection symbiots;

	private Collection<IOrganismListener<Organism2D.Form>> listeners;

	public Organism2D() {
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
		Eye<Organism2D.Form> leftEye = new Eye<>( Form.LEFT_EYE, true);
		symbiots.add(leftEye);
		design = new HashMap<>();
		design.put(Form.LEFT_EYE, leftEye);
		
		Eye<Organism2D.Form> rightEye = new Eye<>( Form.RIGHT_EYE, true);
		symbiots.add(rightEye);
		design.put(Form.RIGHT_EYE, rightEye);

		AngleControl angleControl = new AngleControl( Form.ANGLE);
		symbiots.add(angleControl);
		design.put(Form.ANGLE, angleControl);

		Flagellum leftFlagellum = new Flagellum( Form.LEFT_FLAGELLUM, true);
		leftFlagellum.addInfluence(leftEye);
		leftFlagellum.addInfluence(rightEye);
		symbiots.add(leftFlagellum);
		design.put(Form.LEFT_FLAGELLUM, leftFlagellum);
		
		Flagellum rightFlagellum = new Flagellum(Form.RIGHT_FLAGELLUM, true);
		rightFlagellum.addInfluence(leftEye);
		rightFlagellum.addInfluence(rightEye);
		rightFlagellum.addInfluence(leftFlagellum);
		symbiots.add(  rightFlagellum);
		design.put(Form.RIGHT_FLAGELLUM, rightFlagellum);		

		leftFlagellum.addInfluence(rightFlagellum);
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
	public void addListener( IOrganismListener<Organism2D.Form> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener( IOrganismListener<Organism2D.Form> listener) {
		this.listeners.remove(listener);
	}

	protected void notifyListeners( OrganismEvent<Organism2D.Form> event ) {
		this.listeners.forEach( l->l.notifyOrganismChanged(event));
	}

	@SuppressWarnings("unchecked")
	@Override
	public double geDistance( Form form ) {
		Eye<Organism2D.Form> eye = (Eye<Organism2D.Form>) design.get( form );
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

	@Override
	public ISymbiot toSymbiot() {
		return new OrganismSymbiot( this.symbiots );
	}

	/**
	 * Update the location of the eyes, based on the angle
	 * @param angle
	 */
	@SuppressWarnings("unchecked")
	protected void updateEyes() {
		Eye<Organism2D.Form> leftEye = (Eye<Organism2D.Form>) design.get(Form.LEFT_EYE);
		Eye<Organism2D.Form> rightEye = (Eye<Organism2D.Form>) design.get(Form.RIGHT_EYE);

		int x= getX();
		int y= getY();
		int offset = 1;		

		AngleControl angleControl = (AngleControl) this.design.get( Organism2D.Form.ANGLE);
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
	public void update( IEnvironment<IOrganism<Organism2D.Form>> env ) {
		Environment<Organism2D.Form> environment = (Environment<Form>) env;
		if( environment.noFood())
			return;

		//First set the eyes to reflect the current angle
		this.updateEyes();
		
		//Secondly update the eyes to find the nearest food source
		int maxVision = environment.getDiagonal()+5;//add a ceiling
		Eye<Organism2D.Form> leftEye = (Eye<Organism2D.Form>) design.get(Form.LEFT_EYE);
		Eye<Organism2D.Form> rightEye = (Eye<Organism2D.Form>) design.get(Form.RIGHT_EYE);
		leftEye.setMaxVision(maxVision);
		rightEye.setMaxVision(maxVision);

		//First find the angle to the nearest food source
		int angle =  environment.getNearestFoodAngle(getX(), getY());

		//This affects the two eyes
		int distance = environment.getNearestFoodDistance(leftEye.getX(), leftEye.getY());
		leftEye.setInput( distance);
		leftEye.setAngle(angle);

		distance = environment.getNearestFoodDistance(rightEye.getX(), rightEye.getY());
		rightEye.setInput(distance);
		rightEye.setAngle(angle);

		//Then update the stress signals
		this.symbiots.updateSymbiots();

		//Move the organism,
		Flagellum leftFlagellum = (Flagellum) design.get(Form.LEFT_FLAGELLUM);
		Flagellum rightFlagellum = (Flagellum) design.get(Form.RIGHT_FLAGELLUM);
		int outLeft = leftFlagellum.getOutput();
		int outRight = rightFlagellum.getOutput();		
		
		//Calculate the angle of movement
		AngleControl angleControl = (AngleControl) this.design.get( Organism2D.Form.ANGLE);
		AngleControl.Angle movement =  angleControl.update(outLeft, outRight);
		move( movement );
				
		notifyListeners( new OrganismEvent<Organism2D.Form>(this));
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
		Flagellum leftFlagellum = (Flagellum)getSymbiot( Organism2D.Form.LEFT_FLAGELLUM);
		Flagellum rightFlagellum = (Flagellum) getSymbiot( Organism2D.Form.RIGHT_FLAGELLUM);
		builder.append(": (");
		builder.append(leftFlagellum.getOutput());
		builder.append(",");
		builder.append(rightFlagellum.getOutput());
		builder.append(")");
		return builder.toString();
	}
}