package org.condast.symbiot.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
//import java.util.random.RandomGenerator;
import org.condast.symbiot.core.env.Environment;
import org.condast.symbiot.symbiot.Eye;
import org.condast.symbiot.symbiot.Flagellum;
import org.condast.symbiot.symbiot.Stomach;
import org.condast.symbiotic.core.collection.SymbiotCollection;
import org.condast.symbiotic.core.def.ISymbiot;

public class Organism extends Location implements IOrganism{
	
	private Map<Form, ISymbiot> design;
	
	private SymbiotCollection symbiots;

	private Collection<IOrganismListener> listeners;

	public Organism() {
		super();
		symbiots = new SymbiotCollection();
		float step = 0.01f;
		Eye eye = new Eye( Form.LEFT_EYE, this, step, true);
		symbiots.add(eye);
		design = new HashMap<>();
		design.put(Form.LEFT_EYE, eye);
		eye = new Eye( Form.RIGHT_EYE, this, step, true);
		symbiots.add(eye);
		design.put(Form.RIGHT_EYE, eye);
		Flagellum flagellum = new Flagellum( Form.LEFT_FLAGELLUM, this, step, true);
		symbiots.add(flagellum);
		design.put(Form.LEFT_FLAGELLUM, flagellum);
		flagellum = new Flagellum(Form.RIGHT_FLAGELLUM, this, step, true);
		symbiots.add(flagellum);
		design.put(Form.RIGHT_FLAGELLUM, flagellum);
		Stomach stomach = new Stomach( Form.STOMACH, this, step, true);
		symbiots.add(stomach);
		design.put(Form.STOMACH, stomach);
		this.listeners = new ArrayList<>();
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

	protected void move( int moveLeft, int moveRight ) {
		if(( moveLeft == 0 ) && ( moveRight == 0 ))
			return;
		if(( moveLeft > 0 ) && ( moveRight < 0 ))
			return;
		if(( moveLeft < 0 ) && ( moveRight > 0 ))
			return;
		if(( moveLeft > 0 ) && ( moveRight > 0 ))
			super.setY( getY() + 1);
		else if(( moveLeft < 0 ) && ( moveRight < 0 ))
			super.setY( getY() - 1);
		else if( moveLeft == 0 ) {
			if( moveRight < 0 )
				super.setY(getY() - 1);
			else
				super.setY(getY()+1);
			super.setX( getX() - 1);			
		} else if( moveRight == 0 ) {
			if( moveLeft < 0 )
				super.setY(getY() - 1);
			else
				super.setY(getY()+1);
			super.setX( getX() + 1);			
		}
	}

	@Override
	public double geDistance( Form form ) {
		Eye eye = (Eye) design.get( form);
		return eye.getData(); 
	}
	
	@Override
	public void update( Environment environment ) {		
		Eye leftEye = (Eye) design.get(Form.LEFT_EYE);
		
		int distance = environment.getNearestFoodDistance(getX()-1, getY());
		boolean hasFood = (distance == 0);
		leftEye.update( distance);
		leftEye.update(symbiots);

		Eye rightEye = (Eye) design.get(Form.RIGHT_EYE);
		distance = environment.getNearestFoodDistance(getX()+1, getY());
		hasFood |= (distance == 0);
		rightEye.update(distance);
		rightEye.update(symbiots);

		Stomach stomach = (Stomach) design.get( Form.STOMACH);
		stomach.update( hasFood );
		stomach.update(symbiots);

		Flagellum leftFlagellum = (Flagellum) design.get(Form.LEFT_FLAGELLUM);
		leftFlagellum.update( symbiots);
		int moveLeft = leftFlagellum.update();

		Flagellum rightFlagellum = (Flagellum) design.get(Form.RIGHT_FLAGELLUM);
		rightFlagellum.update( symbiots);
		int moveRight = rightFlagellum.update(); 
		move(moveLeft, moveRight);
		
		/*
		RandomGenerator rand = RandomGenerator.getDefault();
		int step =  rand.nextInt(-1, 2);
		int x = super.getX() + step;
		int xMax = environment.getX();
		x = ( x < 0 )?0:(x > xMax)?xMax:x;
		super.setX(x);
		
		step =  rand.nextInt(-1, 2);
		int y = super.getY() + step;
		int yMax = environment.getY();
		y = ( y < 0 )?0:(y > yMax)?yMax:y;
		super.setY(y);
		*/
		
		notifyListeners( new OrganismEvent(this));
	}
}
