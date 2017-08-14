package org.condast.symbiotic.core.environment;

import org.condast.commons.data.graph.EdgeList;
import org.condast.symbiotic.def.INeighbourhood;
import org.condast.symbiotic.def.IStressListener;
import org.condast.symbiotic.def.ISymbiot;
import org.condast.symbiotic.def.StressEvent;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class Environment {

	private Graph graph;
	
	private IStressListener listener = new IStressListener(){

		@Override
		public void notifyStressChanged(StressEvent event) {
			Iterable<Vertex> vertices = graph.getVertices();
			for( Vertex vertex: vertices ){
				if( event.getSource().equals( vertex.getId()))
					continue;
				ISymbiot symbiot = (ISymbiot) vertex.getId();
				symbiot.updateStressLevels(symbiot);
			}
			
		}
		
	};
	
	public Environment() {
		graph = new EdgeList<ISymbiot, String>();
	}
	
	public Vertex addSymbiot( ISymbiot symbiot ){
		symbiot.addStressListener(listener);
		return graph.addVertex( symbiot);
	}

	public void removeSymbiot( ISymbiot symbiot ){
		symbiot.removeStressListener(listener);
		Vertex vertex = graph.getVertex(symbiot);
		graph.removeVertex( vertex );
	}
	
	public void addNeighbourhood( ISymbiot sym1, ISymbiot sym2, INeighbourhood<?,?> nb ){
		//graph.addEdge(nb, addSymbiot( sym1 ), addSymbiot( sym2 ), nb.getName());
	}

}
