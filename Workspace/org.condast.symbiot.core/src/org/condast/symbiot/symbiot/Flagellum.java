package org.condast.symbiot.symbiot;

import java.util.Collection;

import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.IOrganism.Form;
import org.condast.symbiotic.core.def.ISymbiot;

public class Flagellum extends AbstractSymbioticEntity<Double> {

	private IOrganism.Form form;
	
	public Flagellum( IOrganism.Form form, IOrganism organism, float step, boolean active) {
		super( form.name(), organism, step, active);
		this.form = form;
	}

	public IOrganism.Form getForm() {
		return form;
	}

	@Override
	public void update( double distance) {
		// TODO Auto-generated method stub	
	}

	
	@Override
	public void update(Collection<ISymbiot> symbiots) {
		symbiots.forEach( s->{
			IOrganism.Form thisForm = IOrganism.Form.valueOf(getId());
			if( !this.equals(s)) {
				IOrganism.Form form = IOrganism.Form.valueOf(s.getId());
				switch( form ) {
				case LEFT_EYE:
					if( Form.LEFT_FLAGELLUM.equals(thisForm))
						setStress(s.getStress());
					break;
				case RIGHT_EYE:
					if( Form.RIGHT_FLAGELLUM.equals(thisForm))
						setStress(s.getStress());
					break;
				default:
					break;
				}
			}
		});
		super.update(symbiots);
	}

	public int update() {
		float stress = getStress();
		if( Math.abs(stress) < Float.MIN_VALUE )
			return 0;
		return( stress < 0 )?-1:1;
	}

	
}
