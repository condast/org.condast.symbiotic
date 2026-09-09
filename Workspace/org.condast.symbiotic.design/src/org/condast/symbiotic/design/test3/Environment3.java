package org.condast.symbiotic.design.test3;

import java.util.logging.Logger;

import org.condast.symbiotic.design.Organism2D;
import org.condast.symbiotic.ecosystem.environment.AbstractEnvironment;
import org.condast.symbiotic.ecosystem.organism.IOrganism;

public class Environment3 extends AbstractEnvironment<Organism2D.Form> {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	public Environment3( int x) {
		this( x, DEFAULT_BORDER );
	}
	
	public Environment3( int x, int border) {
		super(x, x);
	}

	@Override
	public void init( int amountFood) {
		this.clear();
		int border = super.getBorder().getX();
		int y = getWidth()/2;
		logger.info("Environment: {" + border + "}");
		int xo = getLength()/2;
		int range = 2*border;
		int lengthx = range;
		addFood(xo, y);
		IOrganism<Organism2D.Form> organism = super.getOrganism();
		Object food = null;
		do{
			xo = border + (int) (lengthx * Math.random());
			food = get(xo, y);
		}while( food != null );
		organism.setLocation(xo, y);
		logger.info("Organism added at: {" + xo + "}");
		super.setOrganism(organism);
	}
}
