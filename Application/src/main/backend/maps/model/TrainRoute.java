package main.backend.maps.model;

import java.awt.Color;
import java.util.List;

public class TrainRoute {
	private String name;
	private int type;
	private Color color;
	
	private List<Trip> trips;
	
	public TrainRoute(String name, int type, Color color, List<Trip> trips) {
		this.setName(name);
		this.setType(type);
		this.setColor(color);
		this.setTrips(trips);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}
}
