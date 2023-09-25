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
import org.condast.symbiotic.core.collection.ISymbiotCollection;
import org.condast.symbiotic.core.collection.SymbiotCollection;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.IStressListener;
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

	public enum Behaviour{
		SIMPLE,
		ON_STRESS,
		ON_DELTA,
		ON_WEIGHT;

		@Override
		public String toString() {
			return StringStyler.prettyString(name());
		}
	}

	private Map<Form, ISymbiot> design;

	private ISymbiotCollection symbiots;

	private Collection<IOrganismListener> listeners;

	private Angle angle;

	private Behaviour behaviour;

	public Organism() {
		super();
		this.angle = Angle.ZERO;
		this.behaviour = Behaviour.ON_WEIGHT;
		symbiots = new SymbiotCollection();
		float step = 0.01f;
		Eye leftEye = new Eye( Form.LEFT_EYE, step, true);
		symbiots.add(leftEye);
		design = new HashMap<>();
		design.put(Form.LEFT_EYE, leftEye);
		Eye rightEye = new Eye( Form.RIGHT_EYE, step, true);
		symbiots.add(rightEye);
		design.put(Form.RIGHT_EYE, rightEye);
		Flagellum flagellum = new Flagellum( Form.LEFT_FLAGELLUM, step, true);
		symbiots.add(flagellum);
		flagellum.addInfluence(leftEye);
		design.put(Form.LEFT_FLAGELLUM, flagellum);
		flagellum = new Flagellum(Form.RIGHT_FLAGELLUM, step, true);
		symbiots.add(flagellum);
		flagellum.addInfluence(rightEye);
		design.put(Form.RIGHT_FLAGELLUM, flagellum);
		this.listeners = new ArrayList<>();
	}

	@Override
	public Angle getAngle() {
		return angle;
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
	 * | 10  |  11  | 01  |
	 * | 1-1 |  00  | -11 |
	 * | -10 | -1-1 | 0-1 |
	 *
	 * -1 means <0 and +1 > 0
	 *
	 * @param moveLeft
	 * @param moveRight
	 * @return
	 */
	protected Angle getSimpleAngle( double moveLeft, double moveRight ) {
		boolean leftZero = Math.abs( moveLeft ) < Double.MIN_VALUE ;
		boolean rightZero = Math.abs( moveRight ) < Double.MIN_VALUE ;
		if( leftZero && rightZero )
			return Angle.ZERO;
		//West
		if(rightZero) {
			if( moveLeft > Double.MIN_VALUE)
				return Angle.NORTH_WEST;
			if( moveLeft < -Double.MIN_VALUE)
				return Angle.SOUTH_WEST;
		//East
		}else if( leftZero) {
			if( moveRight > Double.MIN_VALUE)
				return Angle.NORTH_EAST;
			if( moveRight < -Double.MIN_VALUE)
				return Angle.SOUTH_EAST;
		}else if( moveLeft > Double.MIN_VALUE)
			return ( moveRight > Double.MIN_VALUE)? Angle.NORTH: Angle.EAST;
		return ( moveRight > Double.MIN_VALUE)? Angle.WEST: Angle.SOUTH;
	}

	protected void stressBehaviour( Flagellum leftFlagellum, Flagellum rightFlagellum ) {
		double moveLeft = leftFlagellum.getStress();
		double moveRight = rightFlagellum.getStress();
		this.angle = getSimpleAngle(moveLeft, moveRight);
		move(this.angle);
	}

	protected void deltaStressBehaviour( Flagellum leftFlagellum, Flagellum rightFlagellum ) {
		double moveLeft = leftFlagellum.getDeltaStress( false);
		double moveRight = rightFlagellum.getDeltaStress( false );
		this.angle = getSimpleAngle(moveLeft, moveRight);
		move(this.angle);
	}

	protected void weightBehaviour( Flagellum leftFlagellum, Flagellum rightFlagellum ) {
		double moveLeft = leftFlagellum.getFactor();
		double moveRight = rightFlagellum.getFactor();
		this.angle = getSimpleAngle(moveLeft, moveRight);
		move(this.angle);
	}

	@Override
	public ISymbiot toSymbiot() {
		return new OrganismSymbiot( this.symbiots );
	}

	@Override
	public void update( Environment environment ) {
		int maxVision = environment.getDiagonal()+5;//add a ceiling
		Eye leftEye = (Eye) design.get(Form.LEFT_EYE);
		leftEye.setMaxVision(maxVision);

		int distance = environment.getNearestFoodDistance(getX(), getY());
		leftEye.setInput( distance);

		Eye rightEye = (Eye) design.get(Form.RIGHT_EYE);
		rightEye.setMaxVision(maxVision);
		distance = environment.getNearestFoodDistance(getX(), getY());
		rightEye.setInput(distance);

		if( environment.noFood())
			return;

		Flagellum leftFlagellum = (Flagellum) design.get(Form.LEFT_FLAGELLUM);

		Flagellum rightFlagellum = (Flagellum) design.get(Form.RIGHT_FLAGELLUM);
		this.symbiots.updateSymbiots();

		switch( this.behaviour ) {
		case ON_WEIGHT:
			weightBehaviour(leftFlagellum, rightFlagellum);
			break;
		case ON_DELTA:
			deltaStressBehaviour(leftFlagellum, rightFlagellum);
			break;
		default:
			stressBehaviour(leftFlagellum, rightFlagellum);
			break;
		}
		notifyListeners( new OrganismEvent(this));
	}

	/**
	 * This symbiot is mainly intended for purposes of visualisation, and does not contribute to the activities
	 */
	private static class OrganismSymbiot implements ISymbiot{

		private ISymbiotCollection symbiots;
		private double stress;

		public OrganismSymbiot( ISymbiotCollection organism ) {
			super();
			this.symbiots = organism;
			this.stress = 0;
		}

		@Override
		public String getId() {
			return S_ORGANISM;
		}

		@Override
		public boolean isActive() {
			return true;
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
		public double getDeltaStress( boolean strict ) {
			return this.stress - this.symbiots.getAverageStress();
		}

		@Override
		public void addStressListener(IStressListener listener) {
			// NOTHING
		}

		@Override
		public void removeStressListener(IStressListener listener) {
			// NOTHING
		}
	
		@Override
		public void addInfluence(ISymbiot symbiot) {
			this.symbiots.add(symbiot);
		}

		@Override
		public IStressData getStressData(ISymbiot symbiot) {
			return new StressData( symbiot );
		}
		
		@Override
		public void updateStress() {
			this.symbiots.forEach((s) -> s.updateStress());			
		}

		@Override
		public double getOverallStress() {
			return this.symbiots.getAverageStress();
		}

		@Override
		public double getOverallWeight() {
			return 0;
		}

		@Override
		public double getFactor() {
			return 0;
		}

		@Override
		public Map<ISymbiot, IStressData> getSignals() {
			Map<ISymbiot, IStressData> results = new HashMap<>();
			this.symbiots.forEach((s) -> results.put(s, new StressData( s )));
			return results;
		}

	}
}