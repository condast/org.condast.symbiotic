package org.condast.symbiot.design;

import java.util.logging.Logger;

import org.condast.symbiotic.ecosystem.environment.AbstractEnvironment;
import org.condast.symbiotic.ecosystem.environment.ILocation;
import org.condast.symbiotic.ecosystem.organism.IOrganism;

public class Environment extends AbstractEnvironment<Organism2D.Form> {

	private boolean centreFood;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public Environment( int x, int y, boolean centreFood) {
		this( x, y, DEFAULT_BORDER, centreFood );
	}
	
	public Environment( int x, int y, int border, boolean centreFood) {
		super(x, y, border);
		this.centreFood = centreFood;
	}

	@Override
	public void init( int amountFood) {
		this.clear();
		logger.info("Environment: {" + super.getLength() + ", " + super.getWidth() + "Centre Food is " + this.centreFood +  "}");
		int xo, yo;
		ILocation border = super.getBorder();
		int range = 2*border.getX();
		int lengthx = getLength() - range;
		int lengthy = getWidth() - range;
		int amount = this.centreFood?1: amountFood;
		
		for( int i=0; i<amount;i++) {
			double xpos = (centreFood?lengthx/2 :lengthx * Math.random());
			xo = border.getX() + (int) ( xpos );

			double ypos = (centreFood?lengthy/2 :lengthy * Math.random());
			yo = border.getY() + (int) (ypos );
			addFood(xo, yo);
		}
		Object food = null;
		do{
			xo = border.getX() + (int) (lengthx * Math.random());
			yo = border.getY() + (int) (lengthy * Math.random());
			food = get(xo, yo);
		}while( food != null );
		
		IOrganism<Organism2D.Form> organism = super.getOrganism();
		organism.setLocation(xo, yo);
		logger.info("Organism added at: {" + xo + ", " + yo + "}");
		setOrganism( organism );

	}
}
