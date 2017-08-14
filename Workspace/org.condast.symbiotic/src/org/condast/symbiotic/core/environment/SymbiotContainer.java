package org.condast.symbiotic.core.environment;

import java.util.Collection;
import java.util.Iterator;

import org.condast.symbiotic.def.IStressListener;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.StressEvent;

public class SymbiotContainer<I,O extends Object> {

	private Collection<ISymbiot> symbiots;
	
	private IStressListener listener = new IStressListener() {
		
		@Override
		public void notifyStressChanged(StressEvent event) {
			for( ISymbiot symbiot: symbiots){
				//if( !symbiot.equals( event.getSource() ))
				//	symbiot.setStrategy(reasoner.setStrategy((ISymbiot) event.getSource()));
			}	
		}
	};
		
	public void addSymbiot( ISymbiot symbiot ){
		symbiots.add( symbiot);
		symbiot.addStressListener(listener);
	}

	public void removeSymbiot( ISymbiot symbiot ){
		symbiots.remove( symbiot );
		symbiot.removeStressListener(listener);
	}
	
	public int size(){
		return symbiots.size();
	}
	
	public Iterator<ISymbiot> iterator(){
		return symbiots.iterator();
	}
}