package main.backend.maps.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StopTime {
	private Stop stop;
	private Trip trip;
	private int arrivalTime;
	private int departureTime;
	private int sequenceId;
	
	public static int parseTime(String s) {
		String[] split = s.split(":");
		int hour = Integer.parseInt(split[0]);
		int minute = Integer.parseInt(split[1]);
		int second = Integer.parseInt(split[2]);
		return 3600*hour + 60*minute + second;
	}
	
	public StopTime(Stop stop, Trip trip, int arrivalTime, int departureTime, int sequenceID) {
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.setStop(stop);
		this.setTrip(trip);
		this.setSequence_id(sequenceID);
	}
	
	public LocalDateTime calculateArrivalTime(LocalDate baseDay) {
		int days = (int)((int)(arrivalTime / 3600) / 24);
		int hours = (int)(arrivalTime / 3600) % 24;
		int minutes = (int)(arrivalTime % 3600) / 60;
		int seconds = arrivalTime % 60;
		
		LocalDate dayCorrectedTime = baseDay.minusDays(days);
		
		Service service = trip.getService();
		if (!service.isDayOfWeek(dayCorrectedTime.getDayOfWeek())) {
			return null;
		}
		
		LocalDateTime finalTime = LocalDateTime.of(dayCorrectedTime, LocalTime.of(0, 0, 0, 0));
		finalTime = finalTime.plusDays(days);
		finalTime = finalTime.plusHours(hours);
		finalTime = finalTime.plusMinutes(minutes);
		finalTime = finalTime.plusSeconds(seconds);
		
		return finalTime;
	}
	
	public LocalDateTime calculateDepartureTime(LocalDate baseDay) {
		int days = (int)((int)(departureTime / 3600) / 24);
		int hours = (int)(departureTime / 3600) % 24;
		int minutes = (int)(departureTime % 3600) / 60;
		int seconds = departureTime % 60;
		
		LocalDate dayCorrectedTime = baseDay.minusDays(days);
		
		Service service = trip.getService();
		if (!service.isDayOfWeek(dayCorrectedTime.getDayOfWeek()) || service.getStartDate().isAfter(baseDay) || service.getEndDate().isBefore(baseDay)) {
			return null;
		}
		
		LocalDateTime finalTime = LocalDateTime.of(dayCorrectedTime, LocalTime.of(0, 0, 0, 0));
		finalTime = finalTime.plusDays(days);
		finalTime = finalTime.plusHours(hours);
		finalTime = finalTime.plusMinutes(minutes);
		finalTime = finalTime.plusSeconds(seconds);
		
		return finalTime;
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

	public int getSequence_id() {
		return sequenceId;
	}

	public void setSequence_id(int sequence_id) {
		this.sequenceId = sequence_id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}
}
