package org.condast.symbiot.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.condast.symbiot.core.env.Environment;
import org.condast.symbiot.symbiot.AbstractSymbiot;
import org.condast.symbiot.symbiot.Eye;
import org.condast.symbiot.symbiot.Flagellum;
import org.condast.symbiot.symbiot.Stomach;
import org.condast.symbiotic.core.collection.SymbiotCollection;

public class Organism extends Location{

	public enum Form{
		LEFT_EYE,
		RIGHT_EYE,
		LEFT_FLAGELLUM,
		RIGHT_FLAGELLUM,
		STOMACH;
	}
	
	private Map<Form, AbstractSymbiot> design;
	
	private SymbiotCollection symbiots;

	private Collection<IOrganismListener> listeners;

	public Organism() {
		super();
		symbiots = new SymbiotCollection();
		float step = 0.01f;
		Eye eye = new Eye( this, step, true);
		symbiots.add(eye);
		design = new HashMap<>();
		design.put(Form.LEFT_EYE, eye);
		eye = new Eye( this, step, true);
		symbiots.add(eye);
		design.put(Form.RIGHT_EYE, eye);
		Flagellum flagellum = new Flagellum( this, step, true);
		symbiots.add(flagellum);
		design.put(Form.LEFT_FLAGELLUM, flagellum);
		flagellum = new Flagellum( this, step, true);
		symbiots.add(flagellum);
		design.put(Form.RIGHT_FLAGELLUM, flagellum);
		Stomach stomach = new Stomach( this, step, true);
		symbiots.add(stomach);
		design.put(Form.STOMACH, stomach);
		this.listeners = new ArrayList<>();
	}

	public void addListener( IOrganismListener listener) {
		this.listeners.add(listener);
	}

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
	
	public void update( Environment environment ) {		
		Eye leftEye = (Eye) design.get(Form.LEFT_EYE);
		
		int distance = environment.getNearestFoodDistance(getX()-1, getY());
		boolean hasFood = (distance == 0);
		leftEye.update( distance);

		Eye rightEye = (Eye) design.get(Form.RIGHT_EYE);
		distance = environment.getNearestFoodDistance(getX()+1, getY());
		hasFood |= (distance == 0);
		rightEye.update(distance);

		Stomach stomach = (Stomach) design.get( Form.STOMACH);
		stomach.update( hasFood );

		Flagellum leftFlagellum = (Flagellum) design.get(Form.LEFT_FLAGELLUM);
		int moveLeft = leftFlagellum.update();

		Flagellum rightFlagellum = (Flagellum) design.get(Form.RIGHT_FLAGELLUM);
		int moveRight = rightFlagellum.update(); 
		move(moveLeft, moveRight);
		
		/*
		Random rand = new Random();
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
