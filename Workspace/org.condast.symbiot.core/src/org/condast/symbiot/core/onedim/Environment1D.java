package org.condast.symbiot.core.onedim;

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

public class Environment1D implements IEnvironment<IOrganism<Organism1D.Form>> {

	public static final int DEFAULT_BORDER = 10;
	
	private int x,y;
	
	private int border;
	
	private Collection<ILocation> field;
	
	private IOrganism<Organism1D.Form> organism;
	
	private Collection<IEnvironmentListener<IOrganism<Organism1D.Form>>> listeners;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public Environment1D( int x) {
		this( x, DEFAULT_BORDER );
	}
	
	public Environment1D( int x, int border) {
		super();
		this.x = x;
		this.y = x/2;
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
	public void addListener( IEnvironmentListener<IOrganism<Organism1D.Form>> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener( IEnvironmentListener<IOrganism<Organism1D.Form>> listener) {
		this.listeners.remove(listener);
	}

	protected void notifyListeners( EnvironmentEvent<IOrganism<Organism1D.Form>> event ) {
		this.listeners.forEach( l->l.notifyEnvironmentChanged(event));
	}

	@Override
	public void clear() {
		setOrganism( null );
		this.field.clear();
	}
	
	public boolean addFood( int x ) {
		logger.info("Food added at: {" + x + "}");
		return field.add( new Food( x, y));
	}

	public IOrganism<Organism1D.Form> getOrganism() {
		return organism;
	}

	public boolean setOrganism( IOrganism<Organism1D.Form> organism ) {
		if( this.organism != null )
			this.remove(this.organism);
		this.organism = organism;
		this.field.add(organism);
		notifyListeners( new EnvironmentEvent<IOrganism<Organism1D.Form>>( this, this.organism ));
		return true;
	}
	
	
	@Override
	public ILocation getBorder() {
		return new Location( this.border, y );
	}

	@Override
	public ILocation get( int x, int y ) {
		for( ILocation l: this.field ) {
			if( l.equals(x,y))
				return l;
		}
		return null;		
	}

	public ILocation remove( int x ) {
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

	@Override
	public void init( int amountFood) {
		this.clear();
		logger.info("Environment: {" + x + "}");
		int xo;
		int range = 2*this.border;
		int lengthx = getX() - range;
		for( int i=0; i<amountFood;i++) {
			xo = this.border + (int) ( lengthx * Math.random());
			addFood(xo);
		}
		Organism1D organism = new Organism1D();
		Object food = null;
		do{
			xo = this.border + (int) (lengthx * Math.random());
			food = get(xo,y);
		}while( food != null );
		organism.setLocation(xo,y);
		logger.info("Organism added at: {" + xo + "}");
		setOrganism(organism);

	}
	
	public int getNearestFoodDistance( int x ) {
		double nearest = Double.MAX_VALUE;
		for( ILocation location: field ) {
			if(!( location instanceof Food ))
				continue;
			double distance = Math.abs(location.getX() - x);
			if( distance >= nearest )
				continue;
			nearest = distance;
		}
		return (int) nearest;
	}

	@Override
	public ILocation getNearestFood(int x, int y) {
		return new Location( getNearestFoodDistance(x), y);
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
