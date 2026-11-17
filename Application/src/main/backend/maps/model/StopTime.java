package main.backend.maps.model;

import java.time.LocalDateTime;

public class StopTime {
	private Stop stop;
	private Trip trip;
	private LocalDateTime arrivalTime;
	private LocalDateTime departureTime;
	private int sequence_id;
	
	public StopTime(Stop stop, Trip trip, LocalDateTime arrivalTime, LocalDateTime departureTime, int sequence_id) {
		this.setStop(stop);
		this.setTrip(trip);
		this.setArrivalTime(arrivalTime);
		this.setDepartureTime(departureTime);
		this.setSequence_id(sequence_id);
	}
	
	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public int getSequence_id() {
		return sequence_id;
	}

	public void setSequence_id(int sequence_id) {
		this.sequence_id = sequence_id;
	}
}
