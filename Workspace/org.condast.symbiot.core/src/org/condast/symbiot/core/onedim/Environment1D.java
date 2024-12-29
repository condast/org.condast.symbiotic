package org.condast.symbiot.core.onedim;

import java.util.logging.Logger;

import org.condast.symbiot.core.twodim.Environment;

public class Environment1D extends Environment<Organism1D.Form> {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	public Environment1D( int x) {
		this( x, DEFAULT_BORDER );
	}
	
	public Environment1D( int x, int border) {
		super(x, x);
	}

	public boolean addFood( int x ) {
		int y = getY()/2;
		return super.addFood(x, y);
	}

	public int getNearestFoodDistance(int x) {
		int y = getY()/2;
		return super.getNearestFoodDistance(x, y);
	}

	@Override
	public void init( int amountFood) {
		this.clear();
		int border = super.getBorder().getX();
		int y = getY()/2;
		logger.info("Environment: {" + border + "}");
		int xo;
		int range = 2*border;
		int lengthx = getX() - range;
		for( int i=0; i<amountFood;i++) {
			xo = border + (int) ( lengthx * Math.random());
			addFood(xo);
		}
		Organism1D organism = new Organism1D();
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
