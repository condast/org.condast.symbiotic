package org.condast.symbiotic.core.environment;

import org.condast.commons.graph.EdgeList;
import org.condast.symbiotic.def.INeighbourhood;
import org.condast.symbiotic.def.ISymbiot;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class Environment {

	private Graph graph;
	
	public Environment() {
		graph = new EdgeList<ISymbiot, String>();
	}
	
	public Vertex addSymbiot( ISymbiot symbiot ){
		return graph.addVertex( symbiot);
	}

	public void removeSymbiot( ISymbiot symbiot ){
		Vertex vertex = graph.getVertex(symbiot);
		graph.removeVertex( vertex );
	}
	
	public void addNeighbourhood( ISymbiot sym1, ISymbiot sym2, INeighbourhood nb ){
		//graph.addEdge(nb, addSymbiot( sym1 ), addSymbiot( sym2 ), nb.getName());
	}

}
