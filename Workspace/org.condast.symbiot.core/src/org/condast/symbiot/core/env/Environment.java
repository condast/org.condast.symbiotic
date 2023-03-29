package org.condast.symbiot.core.env;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import org.condast.symbiot.core.Food;
import org.condast.symbiot.core.ILocation;
import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.Location;
import org.condast.symbiot.core.Organism;

public class Environment {

	private int x, y;
	private Collection<Location> field;
	
	private Organism organism;
	
	private Collection<IEnvironmentListener<Organism>> listeners;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	public Environment( int x, int y) {
		super();
		this.x = x;
		this.y = y;
		field = new ArrayList<>();
		this.listeners = new ArrayList<>();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void addListener( IEnvironmentListener<Organism> listener) {
		this.listeners.add(listener);
	}

	public void removeListener( IEnvironmentListener<Organism> listener) {
		this.listeners.remove(listener);
	}

	protected void notifyListeners( EnvironmentEvent<Organism> event ) {
		this.listeners.forEach( l->l.notifyEnvironmentChanged(event));
	}

	public void clear() {
		setOrganism( null );
		this.field.clear();
	}
	
	public boolean addFood( int x, int y ) {
		return field.add( new Food( x, y));
	}

	public IOrganism getOrganism() {
		return organism;
	}

	public boolean setOrganism( Organism organism ) {
		if( this.organism != null )
			this.remove(this.organism);
		this.organism = organism;
		this.field.add(organism);
		notifyListeners( new EnvironmentEvent<Organism>( this, organism ));
		return true;
	}
	
	public ILocation get( int x, int y ) {
		for( Location l: this.field ) {
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

	public Iterator<Location> iterator(){
		return field.iterator();		
	}

	public void init( int amountFood) {
        this.clear();
		int x, y;
        for( int i=0; i<amountFood;i++) {
        	x = (int) (getX() * Math.random());
        	y = (int) (getY() * Math.random());
        	addFood(x, y);
        }
        Organism organism = new Organism();
        Object food = null;
        int list = 10;
        int length = getX() - 2*list;
        do{
        	x = (int) (list + length * Math.random());
            length = getY() - 2*list;
        	y = (int) (list+length * Math.random());
        	food = get(x, y);
        }while( food != null );
        organism.setLocation(x, y);
        setOrganism(organism);

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

	public void update() {
		try {
			int[] location = this.organism.getLocation();
			logger.info("Position organism:" + this.getOrganism().toString() + "(" + location[0] + ", " + location[1] + ")" );
			this.remove(organism);
			this.organism.update( this );
			this.setOrganism(organism);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
