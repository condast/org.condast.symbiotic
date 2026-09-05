package org.condast.symbiot.design;

import java.util.logging.Logger;

import org.condast.symbiotic.ecosystem.environment.AbstractEnvironment;
import org.condast.symbiotic.ecosystem.environment.ILocation;
import org.condast.symbiotic.ecosystem.organism.IOrganism;

public class Environment extends AbstractEnvironment<Organism2D.Form> {

	public static final int DEFAULT_BORDER = 10;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public Environment( int x, int y) {
		this( x, y, DEFAULT_BORDER );
	}
	
	public Environment( int x, int y, int border) {
		super(x, y, border);
	}

	@Override
	public void init( int amountFood) {
		this.clear();
		logger.info("Environment: {" + super.getX() + ", " + super.getY() + "}");
		int xo, yo;
		ILocation border = super.getBorder();
		int range = 2*border.getX();
		int lengthx = getX() - range;
		int lengthy = getY() - range;
		for( int i=0; i<amountFood;i++) {
			xo = border.getX() + (int) ( lengthx * Math.random());
			yo = border.getY() + (int) (lengthy * Math.random());
			addFood(xo, yo);
		}
		Object food = null;
		do{
			xo = border.getX() + (int) (lengthx * Math.random());
			yo = border.getY() + (int) (lengthy * Math.random());
			food = get(xo, yo);
		}while( food != null );
		
		IOrganism<Organism2D.Form> organism = new Organism2D();
		organism.setLocation(xo, yo);
		logger.info("Organism added at: {" + xo + ", " + yo + "}");
		setOrganism( organism );

	}
}
