package main.backend.maps.model;

import java.util.List;

public class Trip {
	private String name;
	private Service service;
	private TrainRoute trainRoute;
	private List<StopTime> stopTimes;
	
	public Trip(String name, Service service, TrainRoute trainRoute, List<StopTime> stopTimes) {
		this.name = name;
		this.service = service;
		this.trainRoute = trainRoute;
		this.stopTimes = stopTimes;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
}
