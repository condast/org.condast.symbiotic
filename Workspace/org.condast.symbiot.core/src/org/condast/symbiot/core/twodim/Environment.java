package org.condast.symbiot.core.twodim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import org.condast.symbiot.core.organism.Food;
import org.condast.symbiotic.core.environment.EnvironmentEvent;
import org.condast.symbiotic.core.environment.IEnvironment;
import org.condast.symbiotic.core.environment.IEnvironmentListener;
import org.condast.symbiotic.core.environment.ILocation;
import org.condast.symbiotic.core.environment.Location;
import org.condast.symbiotic.core.organism.IOrganism;

public class Environment<E extends Enum<E>> implements IEnvironment<IOrganism<E>> {

	public static final int DEFAULT_BORDER = 10;
	
	private int x, y;
	
	private int border;
	
	private Collection<ILocation> field;
	
	private IOrganism<E> organism;
	
	private Collection<IEnvironmentListener<IOrganism<E>>> listeners;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public Environment( int x, int y) {
		this( x, y, DEFAULT_BORDER );
	}
	
	public Environment( int x, int y, int border) {
		super();
		this.x = x;
		this.y = y;
		this.border = border;
		field = new ArrayList<>();
		this.listeners = new ArrayList<>();
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public void addListener( IEnvironmentListener<IOrganism<E>> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener( IEnvironmentListener<IOrganism<E>> listener) {
		this.listeners.remove(listener);
	}

	protected void notifyListeners( EnvironmentEvent<IOrganism<E>> event ) {
		this.listeners.forEach( l->l.notifyEnvironmentChanged(event));
	}

	@Override
	public void clear() {
		setOrganism( null );
		this.field.clear();
	}
	
	public boolean addFood( int x, int y ) {
		logger.info("Food added at: {" + x + ", " + y + "}");
		return field.add( new Food( x, y));
	}

	public IOrganism<E> getOrganism() {
		return organism;
	}

	public boolean setOrganism( IOrganism<E> organism ) {
		if( this.organism != null )
			this.remove(this.organism);
		this.organism = organism;
		this.field.add(organism);
		notifyListeners( new EnvironmentEvent<IOrganism<E>>( this, this.organism ));
		return true;
	}

	@Override
	public ILocation getBorder() {
		return new Location( border, border );
	}

	@Override
	public ILocation get( int x, int y ) {
		for( ILocation l: this.field ) {
			if( l.equals(x,y))
				return l;
		}
		return null;		
	}

	public ILocation remove( int x, int y ) {
		ILocation loc = get( x, y );
		if( loc == null )
			field.remove(loc);
		return loc;
	}

	public boolean remove( ILocation obj ) {
		return field.remove(obj);
	}

	@Override
	public Iterator<ILocation> iterator(){
		return field.iterator();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init( int amountFood) {
       this.clear();
		logger.info("Environment: {" + x + ", " + y + "}");
		int xo, yo;
    	int range = 2*this.border;
    	int lengthx = getX() - range;
    	int lengthy = getY() - range;
    	for( int i=0; i<amountFood;i++) {
    		xo = this.border + (int) ( lengthx * Math.random());
    		yo = this.border + (int) (lengthy * Math.random());
    		addFood(xo, yo);
    	}
        Organism2D organism = new Organism2D();
        Object food = null;
        do{
        	xo = this.border + (int) (lengthx * Math.random());
        	yo = this.border + (int) (lengthy * Math.random());
        	food = get(xo, yo);
        }while( food != null );
        organism.setLocation(xo, yo);
		logger.info("Organism added at: {" + xo + ", " + yo + "}");
        setOrganism((IOrganism<E>) organism);

	}
	
	public int getNearestFoodDistance( int x, int y ) {
		double nearest = Double.MAX_VALUE;
		for( ILocation location: field ) {
			if(!( location instanceof Food ))
				continue;
			double distance = Math.sqrt( Math.pow(location.getX() - x, 2 ) + Math.pow(location.getY() - y,2 ));
			if( distance >= nearest )
				continue;
			nearest = distance;
		}
		return (int) nearest;
	}

	/**
	 * Get the angle to the nearest food source in degrees (0-360)
	 * @param x
	 * @param y
	 * @return
	 */
	public int getNearestFoodAngle( int x, int y ) {
		double angle = 0;
		double nearest = Double.MAX_VALUE;
		for( ILocation location: field ) {
			if(!( location instanceof Food ))
				continue;
			double x2 = Math.pow(location.getX() - x, 2 ); 
			double y2 = Math.pow(location.getY() - y, 2 ); 
			double distance = Math.sqrt( x2 + y2);
			if( distance >= nearest )
				continue;
			angle = Math.toDegrees( Math.acos( Math.sqrt( x2 ) / distance ));
			nearest = distance;
		}
		return (int) angle;
	}

	/**
	 * Get the diagonal of the field
	 * @param distance
	 * @return
	 */
	public int getDiagonal() {
		return (int) Math.sqrt(getX()* getX() + getY()*getY());
	}

	public ILocation getNearestFood( int x, int y ) {
		ILocation result = null;
		double nearest = Double.MAX_VALUE;
		for( ILocation location: field ) {
			if(!( location instanceof Food ))
				continue;
			if( result == null )
				result = location;
			double distance = Math.sqrt( Math.pow(location.getX() - x, 2 ) + Math.pow(location.getY() - y,2 ));
			if( distance >= nearest )
				continue;
			nearest = distance;
			result = location;
		}
		return result;
	}

	public boolean noFood(  ) {
		for( ILocation location: field ) {
			if( location instanceof Food )
				return false;
		}
		return true;
	}

	@Override
	public void update() {
		try {
			int[] location = this.organism.getLocation();
			logger.info("Organism at: {" + location[0] + ", " + location[1] + ")" );
			this.remove(organism);
			this.organism.update( this );
			this.setOrganism(organism);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
