package org.condast.symbiotic.core.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.condast.symbiotic.core.def.ITransformer;

public class TimedNode implements ITransformer<Date, Boolean>{

	private long time;//time needed to finish the job
	private long currentTime;
	private boolean completed;

	public TimedNode( long time ) {
		this.currentTime = 0;
		this.time = time;
		this.completed = false;
	}

	
	@Override
	public void clearInputs() {
		//NOTHING
	}


	@Override
	public boolean addInput(Date input) {
		return true;
	}

	@Override
	public boolean removeInput(Date input) {
		return true;
	}


	public boolean isCompleted(){
		return completed;
	}
			
	public long getRemainingTime(){
		return ( this.time  - this.currentTime );
	}
	
	protected boolean isCompleteWithinOffset( int offset ){
		if( completed)
			return true;
		return this.currentTime >= (this.time + offset );
	}
	
	public boolean update( int interval){
		this.currentTime += interval;
		this.completed =  ( this.currentTime >= this.time );
		return completed;
	}

	@Override
	public Boolean transform( Iterator<Date> inputs) {
		return this.completed;
	}

	@Override
	public Collection<Date> getInputs() {
		// TODO Auto-generated method stub
		return null;
	}
}
