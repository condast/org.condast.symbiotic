package org.condast.symbiot.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiot.core.env.Environment;
import org.condast.symbiot.symbiot.Eye;
import org.condast.symbiot.symbiot.Flagellum;
import org.condast.symbiotic.core.collection.AbstractSymbiotCollection;
import org.condast.symbiotic.core.collection.ISymbiotCollection;
import org.condast.symbiotic.core.def.ISymbiot;

public class Organism extends Location implements IOrganism{

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
		ON_DELTA;

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
		this.behaviour = Behaviour.SIMPLE;
		symbiots = new SymbiotCollection();
		float step = 0.01f;
		Eye eye = new Eye( Form.LEFT_EYE, step, true);
		symbiots.add(eye);
		design = new HashMap<>();
		design.put(Form.LEFT_EYE, eye);
		eye = new Eye( Form.RIGHT_EYE, step, true);
		symbiots.add(eye);
		design.put(Form.RIGHT_EYE, eye);
		Flagellum flagellum = new Flagellum( Form.LEFT_FLAGELLUM, step, true);
		symbiots.add(flagellum);
		design.put(Form.LEFT_FLAGELLUM, flagellum);
		flagellum = new Flagellum(Form.RIGHT_FLAGELLUM, step, true);
		symbiots.add(flagellum);
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
	 * Get the angle, based on the two stress signals
	 * @param leftFlagellum
	 * @param rightFlagellum
	 * @return
	 */
	protected Angle getSimpleAngle( Flagellum leftFlagellum, Flagellum rightFlagellum ) {
		double moveLeft = leftFlagellum.getStress();
		double moveRight = rightFlagellum.getStress(); 
		if(( Math.abs( moveLeft - moveRight) < Double.MIN_VALUE ))
			return Angle.ZERO;
		Angle horizontal = Angle.ZERO;
		if( moveLeft > moveRight )
			horizontal = Angle.EAST;
		else if( moveLeft < moveRight )
			horizontal = Angle.WEST;
		double delta = leftFlagellum.getDeltaStress();
		Angle vertical = this.angle;
		if(( Math.abs( delta) >= 0 ))
			return angle;
		
		
		return angle;
	}

	/**
	 * Get the angle, based on the two stress signals
	 * @param leftFlagellum
	 * @param rightFlagellum
	 * @return
	 */
	protected Angle getAngle( Flagellum leftFlagellum, Flagellum rightFlagellum, Angle current ) {
		double moveLeft = leftFlagellum.getStress();
		double moveRight = rightFlagellum.getStress(); 
		if(( Math.abs( moveLeft - moveRight) < Double.MIN_VALUE ))
			return current;
		Angle angle = current;
		if( moveLeft > moveRight )
			angle = Angle.right(this.angle);
		else if( moveLeft < moveRight )
			angle = Angle.left(this.angle);
		return angle;
	}

	protected void stressBehaviour( Flagellum leftFlagellum, Flagellum rightFlagellum ) {
		this.angle = getAngle( leftFlagellum, rightFlagellum, this.angle);
		double moveLeft = leftFlagellum.getStress();
		double moveRight = rightFlagellum.getStress(); 
		if(( Math.abs( moveLeft ) < Double.MIN_VALUE ) && ( Math.abs( moveRight ) < Double.MIN_VALUE ))
			this.angle = Angle.ZERO;

		move(this.angle);
	}

	protected void deltaStressBehaviour( Flagellum leftFlagellum, Flagellum rightFlagellum ) {
		double stressLeft = leftFlagellum.getDeltaStress();
		double stressRight = rightFlagellum.getDeltaStress(); 
		if(( stressLeft < 0 ) && ( stressRight < 0 ))
			this.angle = Angle.swap(this.angle);
		if(( stressLeft <= 0 ) && ( stressRight > 0 ))
			this.angle = Angle.left(this.angle);
		else if(( stressRight <= 0 ) && ( stressLeft > 0 ))
			this.angle = Angle.right(this.angle);
		move(this.angle);
	}

	@Override
	public void update( Environment environment ) {		
		int maxVision = environment.getDiagonal();
		Eye leftEye = (Eye) design.get(Form.LEFT_EYE);
		leftEye.setMaxVision(maxVision);

		int distance = environment.getNearestFoodDistance(getX()-1, getY());
		leftEye.setInput( distance);

		Eye rightEye = (Eye) design.get(Form.RIGHT_EYE);
		rightEye.setMaxVision(maxVision);
		distance = environment.getNearestFoodDistance(getX()+1, getY());
		rightEye.setInput(distance);

		if( environment.noFood())
			return;

		Flagellum leftFlagellum = (Flagellum) design.get(Form.LEFT_FLAGELLUM);

		Flagellum rightFlagellum = (Flagellum) design.get(Form.RIGHT_FLAGELLUM);
		this.symbiots.updateSymbiots();
		
		switch( this.behaviour ) {
		case ON_DELTA:
			deltaStressBehaviour(leftFlagellum, rightFlagellum);
			break;
		default:
			stressBehaviour(leftFlagellum, rightFlagellum);
			break;
		}
		notifyListeners( new OrganismEvent(this));
	}

	private class SymbiotCollection extends AbstractSymbiotCollection{
		
		@Override
		public void updateSymbiot(ISymbiot symbiot, ISymbiot reference) {
			IOrganism.Form form = IOrganism.Form.valueOf(symbiot.getId());
			IOrganism.Form refForm = IOrganism.Form.valueOf(reference.getId());
			switch( form ) {
			case LEFT_FLAGELLUM:
				if( Form.LEFT_EYE.equals(refForm)) {
					symbiot.setStress( getStress( reference.getStress()));
				}
				break;
			case RIGHT_FLAGELLUM:
				if( Form.RIGHT_EYE.equals(refForm)) {
					symbiot.setStress( getStress( reference.getStress()));
				}
				break;
			default:
				break;
			}
		}

		private double getStress( double reference ) {
			double result = reference;
			if( Math.abs( reference )< Float.MIN_VALUE ) {
				result = ( reference < 0)? -Float.MIN_VALUE: Float.MIN_VALUE;			
			}
			return result;
		}
	}
}
