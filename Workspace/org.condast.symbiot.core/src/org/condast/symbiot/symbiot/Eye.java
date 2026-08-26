package org.condast.symbiot.symbiot;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;

public class Eye implements ISymbiot{ //extends AbstractEnumInputSymbiot<IOrganism.Form, Integer> {

	private int maxVision;
	
	public Eye( IOrganism.Form form, float step, boolean active) {
		//super( form, step, active);
		this.maxVision = Integer.MAX_VALUE;
		//super.setInput( Integer.MAX_VALUE );
	}

	public int getMaxVision() {
		return maxVision;
	}

	public void setMaxVision(int maxVision) {
		this.maxVision = maxVision;
	}
	
	protected boolean updateStress(Integer input) {
		float stress = (input == null )?0:  input.floatValue()/maxVision;
		setStress( stress);
		return false;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getStress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addStressListener(IStressListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStressListener(IStressListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearStress() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float increaseStress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float decreaseStress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStress(float stress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IStressData getStressData(ISymbiot symbiot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getOverallStress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOverallWeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
