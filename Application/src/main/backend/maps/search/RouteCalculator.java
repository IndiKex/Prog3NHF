package main.backend.maps.search;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import main.backend.maps.model.MapModel;
import main.backend.maps.model.Stop;
import main.backend.maps.model.Trip;

public class RouteCalculator {
	public static List<SearchRoute> calculateRoute(MapModel mapModel, Stop firstStop, Stop lastStop, LocalDateTime startTime) {
		List<SearchRoute> usefulSearchRoutes = new ArrayList<>();
		
		List<Trip> trips = mapModel.getLines();
		
		for (int i = 0; i < trips.size(); i++) {
			Trip trip = trips.get(i);
			int firstID = -1;
			int lastID = -1;
			
			List<Stop> stops = trip.getStops();
			for (int j = 0; j < stops.size(); j++) {
				Stop stop = stops.get(j);
				
				if (stop == firstStop) {
					firstID = j;
				}
				
				if (stop == lastStop) {
					lastID = j;
				}
				
				if (firstID != -1 && lastID != -1) {
					break;
				}
			}
			
			if (firstID == -1 || lastID == -1) {
				continue;
			}
			
			List<Stop> searchRouteStops = new ArrayList<>();
			for (int j = Integer.min(firstID, lastID); j < Integer.max(firstID, lastID); j++) {
				searchRouteStops.add(stops.get(j));
			}
			
			SearchTrip searchTrip = new SearchTrip(trip, firstID, lastID);
			SearchRoute searchRoute = new SearchRoute(searchRouteStops, List.of(searchTrip));
			usefulSearchRoutes.add(searchRoute);
		}
		
		orderSearchRoutesByDeparture(usefulSearchRoutes);
		
		return usefulSearchRoutes.subList(0, 10);
	}
	
	private static void orderSearchRoutesByDeparture(List<SearchRoute> searchRoutes) {
		for (int i = 0; i < searchRoutes.size(); i++) {
			for (int j = 0; j < searchRoutes.size() - 1 - i; j++) {
				SearchRoute searchRoute = searchRoutes.get(j);
				SearchRoute nextRoute = searchRoutes.get(j+1);
				
				LocalDateTime searchTime = searchRoute.getFirstTime();
				LocalDateTime nextTime = nextRoute.getFirstTime();
				
				if (nextTime.isBefore(searchTime)) {
					searchRoutes.set(j, nextRoute);
					searchRoutes.set(j+1, searchRoute);
				}
			}
		}
	}
}
