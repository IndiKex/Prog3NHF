package main.backend.maps.model;

import java.util.List;

public class MapModel {
	private List<Stop> stations;
	private List<Trip> lines;
	
	public MapModel(List<Stop> stations, List<Trip> lines) {
		this.stations = stations;
		this.lines = lines;
	}
	
	public List<Trip> getLines() {
		return lines;
	}
	
	public void setLines(List<Trip> lines) {
		this.lines = lines;
	}
	
	public List<Stop> getStations() {
		return stations;
	}
	
	public void setStations(List<Stop> stations) {
		this.stations = stations;
	}
}
