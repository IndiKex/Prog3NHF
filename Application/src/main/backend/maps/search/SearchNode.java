package main.backend.maps.search;

import graphs.Node;

public class SearchNode<T> extends Node {
	private T reference;

	public SearchNode(String name, T reference) {
		super(name);
		this.reference = reference;
	}
	
	public T getReference() {
		return reference;
	}

	public void setReference(T reference) {
		this.reference = reference;
	}
	
	
}
