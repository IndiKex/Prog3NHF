package main.backend.maps.search;

import graphs.Edge;
import graphs.Node;

public class SearchEdge<T> extends Edge {
	private T reference;

	public SearchEdge(String name, Node startNode, Node destNode, T reference) {
		super(name, startNode, destNode);
		this.reference = reference;
	}
	
	public T getReference() {
		return reference;
	}

	public void setReference(T reference) {
		this.reference = reference;
	}
	
	
}
