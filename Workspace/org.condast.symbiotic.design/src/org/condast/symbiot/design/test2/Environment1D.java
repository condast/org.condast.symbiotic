package org.condast.symbiot.design.test2;

import java.util.logging.Logger;

import org.condast.symbiot.design.Organism2D;
import org.condast.symbiotic.ecosystem.environment.AbstractEnvironment;
import org.condast.symbiotic.ecosystem.organism.IOrganism;

public class Environment1D extends AbstractEnvironment<Organism2D.Form> {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	public Environment1D( int x) {
		this( x, DEFAULT_BORDER );
	}
	
	public Environment1D( int x, int border) {
		super(x, x);
	}

	public boolean addFood( int x ) {
		int y = getWidth()/2;
		return super.addFood(x, y);
	}

	public int getNearestFoodDistance(int x) {
		int y = getWidth()/2;
		return super.getNearestFoodDistance(x, y);
	}

	@Override
	public void init( int amountFood) {
		this.clear();
		int border = super.getBorder().getX();
		int y = getWidth()/2;
		logger.info("Environment: {" + border + "}");
		int xo = getLength()/2;
		int range = 2*border;
		int lengthx = getLength() - range;
		addFood(xo);
		for( int i=1; i<amountFood;i++) {
			xo = border + (int) ( lengthx * Math.random());
			addFood(xo);
		}
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
