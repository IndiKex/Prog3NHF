package main.backend.maps.model;

import java.util.List;

public class Stop {
	private String name;
	private Coord coord;
	
	private List<StopTime> stopTimes;
	
	public Stop(String name, Coord coord, List<StopTime> stopTimes) {
		this.name = name;
		this.coord = coord;
		this.stopTimes = stopTimes;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCoord(Coord coord) {
		this.coord = coord;
	}
	
	public Coord getCoord() {
		return coord;
	}
	
	public void setStopTimes(List<StopTime> stopTimes) {
		this.stopTimes = stopTimes;
	}
	
	public List<StopTime> getStopTimes() {
		return stopTimes;
	}
}
