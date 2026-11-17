package main.backend.maps.model;

import java.util.List;

public class Trip {
	private String name;
	private TrainRoute trainRoute;
	private List<Stop> stops;
	private List<StopTime> stopTimes;
	
	public Trip(String name, TrainRoute trainRoute, List<Stop> stops, List<StopTime> stopTimes) {
		this.name = name;
		this.trainRoute = trainRoute;
		this.stops = stops;
		this.stopTimes = stopTimes;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}
	
	public List<Stop> getStops() {
		return stops;
	}
	
	public void setStopTimes(List<StopTime> stopTimes) {
		this.stopTimes = stopTimes;
	}
	
	public List<StopTime> getStopTimes() {
		return stopTimes;
	}

	public TrainRoute getTrainRoute() {
		return trainRoute;
	}

	public void setTrainRoute(TrainRoute trainRoute) {
		this.trainRoute = trainRoute;
	}
}
