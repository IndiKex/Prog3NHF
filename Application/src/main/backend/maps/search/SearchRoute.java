package main.backend.maps.search;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import main.backend.maps.model.Stop;

public class SearchRoute {
	private LocalDate startDate;
	private List<Stop> stops;
	private List<SearchTrip> searchTrips;
	
	public SearchRoute(LocalDate startDate, List<Stop> stops, List<SearchTrip> searchTrips) {
		this.startDate = startDate;
		this.stops = stops;
		this.searchTrips = searchTrips;
	}
	
	public int calculatePrice() {
		return 100 * stops.size();
	}
	
	public Stop getFirstStop() {
		return stops.get(0);
	}
	
	public Stop getLastStop() {
		return stops.get(stops.size() - 1);
	}
	
	public LocalDateTime getFirstTime() {
		int firstStopID = searchTrips.get(0).getFirstStopID();
		return searchTrips.get(0).getTrip().getStopTimes().get(firstStopID).calculateDepartureTime(startDate);
	}
	
	public LocalDateTime getLastTime() {
		int lastStopID = searchTrips.getLast().getLastStopID();
		
		int firstStopID = searchTrips.get(0).getFirstStopID();
		LocalDateTime lastStopDate = searchTrips.getLast().getTrip().getStopTimes().get(lastStopID).calculateArrivalTime(startDate);
		LocalDateTime firstStopDate = searchTrips.get(0).getTrip().getStopTimes().get(firstStopID).calculateDepartureTime(startDate);
		
		if (firstStopDate.isAfter(lastStopDate)) {
			return lastStopDate.plusDays(1);
		} else {
			return lastStopDate;
		}
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	public List<SearchTrip> getSearchTrips() {
		return searchTrips;
	}

	public void setSearchTrips(List<SearchTrip> searchTrips) {
		this.searchTrips = searchTrips;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
}
