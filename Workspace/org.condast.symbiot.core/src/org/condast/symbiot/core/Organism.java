package org.condast.symbiot.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiot.core.env.Environment;
import org.condast.symbiot.symbiot.Eye;
import org.condast.symbiot.symbiot.Flagellum;
import org.condast.symbiotic.core.StressData;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.collection.ISymbiotCollection;
import org.condast.symbiotic.core.collection.SymbiotCollection;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class Organism extends Location implements IOrganism{

	public static final String S_ORGANISM = "ORGANISM";

	public enum Angle{
		ZERO(0),
		NORTH(1),
		NORTH_EAST(2),
		EAST(3),
		SOUTH_EAST(4),
		SOUTH(5),
		SOUTH_WEST(6),
		WEST(7),
		NORTH_WEST(8);

		private int angle;

		private Angle(int angle) {
			this.angle = angle;
		}

		private int getIndex() {
			return this.angle;
		}

		public static Angle getAngle( int angle ) {
			return Angle.values()[ angle ];
		}

		public static Angle right( Angle angle ) {
			int result = (NORTH_WEST.getIndex() + angle.getIndex() + 1)%NORTH_WEST.getIndex();
			if( result == ZERO.getIndex())
				result = NORTH.getIndex();
			return Angle.getAngle( result );
		}

		public static Angle left( Angle angle ) {
			int result = (NORTH_WEST.getIndex() + angle.getIndex() - 1)%NORTH_WEST.getIndex();
			if( result == ZERO.getIndex())
				result = NORTH.getIndex();
			return Angle.getAngle( result );
		}

		public static Angle swap( Angle angle ) {
			int result = (NORTH_WEST.getIndex() + angle.getIndex() + SOUTH_EAST.getIndex())%NORTH_WEST.getIndex();
			if( result == ZERO.getIndex())
				result = SOUTH.getIndex();
			return Angle.getAngle( result );
		}

		@Override
		public String toString() {
			return StringStyler.prettyString(name());
		}
	}

	private Map<Form, ISymbiot> design;

	private ISymbiotCollection symbiots;

	private Collection<IOrganismListener> listeners;

	private Angle angle;

	public Organism() {
		super();
		this.angle = Angle.ZERO;
		symbiots = new SymbiotCollection();
		design();
		this.listeners = new ArrayList<>();
	}

	/**
	 * Design the organism
	 * @param step
	 */
	protected void design() {
		Eye leftEye = new Eye( Form.LEFT_EYE, true);
		symbiots.add(leftEye);
		design = new HashMap<>();
		design.put(Form.LEFT_EYE, leftEye);
		
		Eye rightEye = new Eye( Form.RIGHT_EYE, true);
		symbiots.add(rightEye);
		design.put(Form.RIGHT_EYE, rightEye);
		
		Flagellum leftFlagellum = new Flagellum( Form.LEFT_FLAGELLUM, true);
		leftFlagellum.addInfluence(leftEye);
		leftFlagellum.addInfluence(rightEye);
		symbiots.add(leftFlagellum);
		design.put(Form.LEFT_FLAGELLUM, leftFlagellum);
		
		Flagellum rightFlagellum = new Flagellum(Form.RIGHT_FLAGELLUM, true);
		symbiots.add(  rightFlagellum);
		leftFlagellum.addInfluence(leftEye);
		rightFlagellum.addInfluence(rightEye);
		rightFlagellum.addInfluence(leftFlagellum);
		design.put(Form.RIGHT_FLAGELLUM, rightFlagellum);		

		leftFlagellum.addInfluence(rightFlagellum);
	}

	@Override
	public Angle getAngle() {
		return angle;
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
	public void addListener( IOrganismListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener( IOrganismListener listener) {
		this.listeners.remove(listener);
	}

	protected void notifyListeners( OrganismEvent event ) {
		this.listeners.forEach( l->l.notifyOrganismChanged(event));
	}

	/**
	 * The actual movement of the organism
	 * | 8 | 1 | 2 |
	 * | 7 | 0 | 3 |
	 * | 6 | 5 | 4 |
	 * @param angle
	 */
	protected void move( Angle angle ) {
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
	public double geDistance( Form form ) {
		Eye eye = (Eye) design.get( form );
		return eye.getInput();
	}


	/**
	 * Get the angle, based on the a move left and move right:
	 * | 8 | 1 | 2 |
	 * | 7 | 0 | 3 |
	 * | 6 | 5 | 4 |
	 *
	 * -1 means <0 and +1 > 0
	 *
	 * @param moveLeft
	 * @param moveRight
	 * @return
	 */
	protected Angle getSimpleAngle( int outLeft, int outRight ) {
		if(( outLeft == 0 ) && ( outRight == 0))
			return Angle.ZERO;
		else if(( outLeft == 1 ) && ( outRight == 1 ))
			return Angle.NORTH;
		else if( outLeft == 0 ) {
			if ( outRight == 1 )
				return Angle.NORTH_WEST;
			else 
				return  ( outRight == 0)? Angle.WEST: Angle.SOUTH_WEST;
		}
		else if(( outLeft == 1 ) && ( outRight == -1 ))
			return Angle.NORTH_WEST;
		else if( outRight == 0 ) {
			if ( outLeft == 1 )
				return Angle.NORTH_EAST;
			else 
				return ( outLeft == 0)? Angle.EAST: Angle.SOUTH_EAST;
		}
		else if(( outRight == 1 ) && ( outLeft == -1 ))
			return Angle.SOUTH_EAST;
		else return (( outLeft == -1 ) && ( outRight == -1 ))? Angle.SOUTH: Angle.ZERO;	
	}

	protected void outputBehaviour( Flagellum leftFlagellum, Flagellum rightFlagellum ) {
		int outLeft = leftFlagellum.getOutput();
		int outRight = rightFlagellum.getOutput();
		this.angle = getSimpleAngle(outLeft, outRight);
		move(this.angle);
	}

	@Override
	public ISymbiot toSymbiot() {
		return new OrganismSymbiot( this.symbiots );
	}

	protected void updateEyes( Angle angle ) {
		Eye leftEye = (Eye) design.get(Form.LEFT_EYE);
		Eye rightEye = (Eye) design.get(Form.RIGHT_EYE);

		int x= getX();
		int y= getY();
		int offset = 1;		
		switch( angle ) {
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
			rightEye.setLocation(-+offset, y+offset);
			break;
		default:
			break;
		}
	}

	protected int getDistance( Form eye, Environment environment ) {
		int retval = environment.getNearestFoodDistance(getX(), getY());
		switch( angle ) {
		case NORTH:
			retval = Form.LEFT_EYE.equals(eye)? retval+1: retval-1;
			break;
		case SOUTH:
			retval = Form.LEFT_EYE.equals(eye)? retval-1: retval+1;
			break;
		default:
			break;
		}
		return retval;
	}
	
	@Override
	public void update( Environment environment ) {
		//First update the eyes to find the nearest food source
		int maxVision = environment.getDiagonal()+5;//add a ceiling
		Eye leftEye = (Eye) design.get(Form.LEFT_EYE);
		Eye rightEye = (Eye) design.get(Form.RIGHT_EYE);
		leftEye.setMaxVision(maxVision);
		rightEye.setMaxVision(maxVision);
		updateEyes(angle);

		int angle =  environment.getNearestFoodAngle(getX(), getY());

		int distance = environment.getNearestFoodDistance(leftEye.getX(), leftEye.getY());
		leftEye.setInput( distance);
		leftEye.setAngle(angle);

		distance = environment.getNearestFoodDistance(rightEye.getX(), rightEye.getY());
		rightEye.setInput(distance);
		rightEye.setAngle(angle);

		if( environment.noFood())
			return;

		//Then update the flagelii
		this.symbiots.updateSymbiots();

		Flagellum leftFlagellum = (Flagellum) design.get(Form.LEFT_FLAGELLUM);
		Flagellum rightFlagellum = (Flagellum) design.get(Form.RIGHT_FLAGELLUM);
		outputBehaviour(leftFlagellum, rightFlagellum);		
		notifyListeners( new OrganismEvent(this));
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
}