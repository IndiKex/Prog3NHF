package main.backend.maps.search;

import java.time.LocalDateTime;
import java.util.List;

import main.backend.maps.model.Stop;

public class SearchRoute {
	private List<Stop> stops;
	private List<SearchTrip> searchTrips;
	
	public SearchRoute(List<Stop> stops, List<SearchTrip> searchTrips) {
		this.stops = stops;
		this.searchTrips = searchTrips;
	}
	
	public Stop getFirstStop() {
		return stops.get(0);
	}
	
	public Stop getLastStop() {
		return stops.get(stops.size() - 1);
	}
	
	public LocalDateTime getFirstTime() {
		int firstStopID = searchTrips.get(0).getFirstStopID();
		return searchTrips.get(0).getTrip().getStopTimes().get(firstStopID).getDepartureTime();
	}
	
	public LocalDateTime getLastTime() {
		int lastStopID = searchTrips.getLast().getLastStopID();
		return searchTrips.getLast().getTrip().getStopTimes().get(lastStopID).getArrivalTime();
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
}
