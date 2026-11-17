package main.backend.maps.search;

import main.backend.maps.model.Trip;

public class SearchTrip {
	private Trip trip;
	private int firstStopID;
	private int lastStopID;
	
	public SearchTrip(Trip trip, int firstStop, int lastStop) {
		this.trip = trip;
		this.firstStopID = firstStop;
		this.lastStopID = lastStop;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public int getFirstStopID() {
		return firstStopID;
	}

	public void setFirstStopID(int firstStop) {
		this.firstStopID = firstStop;
	}

	public int getLastStopID() {
		return lastStopID;
	}

	public void setLastStopID(int lastStop) {
		this.lastStopID = lastStop;
	}
}
