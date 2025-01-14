package org.condast.symbiot.core.test;

import org.condast.commons.strings.StringStyler;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.enumid.AbstractProcessSymbiot;
import org.condast.symbiotic.core.process.AbstractProcess;
import org.condast.symbiotic.core.process.IProcess;

/**
 * The angle of movement of the organism
 */
public class AngleControl extends AbstractProcessSymbiot<Organism2D.Form, Integer, Double>{

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

		public static Angle rightTurn( Angle angle ) {
			int angl = ( angle.getIndex() + 1 )%Angle.NORTH_WEST.getIndex();
			return getAngle( angl );
		}

		public static Angle leftTurn( Angle angle ) {
			int angl = ( Angle.NORTH_WEST.getIndex() + angle.getIndex() - 1 )%Angle.NORTH_WEST.getIndex();
			return getAngle( angl );
		}

		public Angle clockWise( Angle angle ) {
			this.angle = ( this.angle + angle.getIndex() )%Angle.NORTH_WEST.getIndex();
			return getAngle( this.angle );
		}

		public Angle counterClockWise( Angle angle ) {
			this.angle = (Angle.NORTH_WEST.getIndex() + this.angle - angle.getIndex() )%Angle.NORTH_WEST.getIndex();
			return getAngle( this.angle );
		}
		
		@Override
		public String toString() {
			return StringStyler.prettyString(name());
		}
	}

	//The response to changing the angle is different
	public enum AngleBehaviour{
		SIMPLE_ANGLE,
		STEP_ANGLE,
		RELATIVE_ANGLE,
		SYMBIOTIC_ANGLE;

		public static String[] items() {
			String[] retval = new String[values().length];
			int index = 0;
			for( AngleBehaviour ab: values())
				retval[index++] = ab.name();
			return retval;
		}
		
		@Override
		public String toString() {
			return StringStyler.sentence( name() );
		}
	}

	private Angle angle;//The angle of the movement
	private AngleBehaviour behaviour;
	
	public AngleControl( Organism2D.Form form ) {
		this( form, AngleBehaviour.SIMPLE_ANGLE );
	}

	public AngleControl( Organism2D.Form form, AngleBehaviour behaviour ) {
		super( form );
		this.angle = Angle.ZERO;
		this.behaviour = behaviour;
	}

	@Override
	protected IProcess<Integer, Double> createProcess(ISymbiot symbiot) {
		return new Process( symbiot );
	}

	@Override
	public void setInput(Integer input) {
		super.getProcess().setInput(input);
	}

	@Override
	public ISymbiot getSymbiot() {
		return this;
	}

	public Angle getAngle() {
		return angle;
	}

	public AngleBehaviour getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(AngleBehaviour behaviour) {
		this.behaviour = behaviour;
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

	/**
	 * Get the angle, based on the a move left and move right:
	 * | L | R | A 		  |
	 * | 0 | 0 | 0 		  |
	 * | 0 | 1 | decrease |
	 * | 1 | 0 | increase |
	 * | 1 | 1 | 0
	 *
	 * -1 means <0 and +1 > 0
	 *
	 * @param moveLeft
	 * @param moveRight
	 * @return
	 */
	protected Angle stepAngle( int outLeft, int outRight ) {
		if(( outLeft == outRight )) 
			return Angle.ZERO;
		return ( outLeft == 1 )? Angle.rightTurn( this.angle): Angle.leftTurn(this.angle);
	}

	@Override
	public boolean enableUpdate(String reference) {
		boolean retval = false;
		switch( this.behaviour) {
		case SYMBIOTIC_ANGLE:
			Organism2D.Form refForm = Organism2D.Form.valueOf(reference);
			//retval = (Form.LEFT_FLAGELLUM.equals(refForm) || Form.RIGHT_FLAGELLUM.equals(refForm));
		default:
			break;
		}
		return retval;
	}

	public Angle update( int outLeft, int outRight )
	{
		switch( this.behaviour) {
		case RELATIVE_ANGLE:
			this.angle = this.angle.clockWise(getSimpleAngle(outLeft, outRight));
			break;
		case STEP_ANGLE:
			this.angle = stepAngle(outLeft, outRight);
			break;
		case SYMBIOTIC_ANGLE:
			break;
			
		default:
			this.angle = getSimpleAngle(outLeft, outRight);
			break;
		}
		return this.angle;
	}
	
	private class Process extends AbstractProcess<Integer, Double>{

		protected Process(ISymbiot symbiot) {
			super(symbiot);
		}

		@Override
		public double getStress() {
			return 0;
		}

		@Override
		protected double normalisedInput() {
			return super.getInput();
		}

		@Override
		protected Double transformOutput(double output) {
			double factor = getFactor();
			return 0d;
		}
	}

}