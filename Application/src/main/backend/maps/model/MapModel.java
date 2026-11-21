package main.backend.maps.model;

import java.util.List;

public class MapModel {
	private List<Stop> stations;
	private List<Trip> lines;
	private List<TrainRoute> trainRoutes;
	
	public MapModel(List<Stop> stations, List<Trip> lines, List<TrainRoute> trainRoutes) {
		this.stations = stations;
		this.lines = lines;
		this.trainRoutes = trainRoutes;
	}
	
	public Stop getStopFromName(String stopName) {
		for (Stop stop : stations) {
			if (stop.getName().equals(stopName))
				return stop;
		}
		return null;
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

	public List<TrainRoute> getTrainRoutes() {
		return trainRoutes;
	}

	public void setTrainRoutes(List<TrainRoute> trainRoutes) {
		this.trainRoutes = trainRoutes;
	}
}
