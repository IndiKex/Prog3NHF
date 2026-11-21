package main.backend.maps.search;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import main.backend.maps.model.MapModel;
import main.backend.maps.model.Stop;
import main.backend.maps.model.StopTime;
import main.backend.maps.model.Trip;

public class RouteCalculator {
	public static List<SearchRoute> calculateRoute(MapModel mapModel, Stop firstStop, Stop lastStop, LocalDateTime startTime) {
		List<SearchRoute> usefulSearchRoutes = new ArrayList<>();
		
		List<Trip> trips = mapModel.getLines();
		
		for (int i = 0; i < trips.size(); i++) {
			Trip trip = trips.get(i);
			int firstID = -1;
			int lastID = -1;
			
			List<StopTime> stopTimes = trip.getStopTimes();
			for (int j = 0; j < stopTimes.size(); j++) {
				Stop stop = stopTimes.get(j).getStop();
				
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
			
			StopTime firstStopTime = stopTimes.get(firstID);
			StopTime lastStopTime = stopTimes.get(lastID);
			if (firstStopTime.getSequence_id() > lastStopTime.getSequence_id()) {
				continue;
			}

			if (trip.getTrainRoute().getName().equals("EC 140 HORTOBÁGY")) {
				System.out.println("");
				System.out.println(i);
				System.out.println(trip.getTrainRoute().getName());
			}
			
			
			List<Stop> searchRouteStops = new ArrayList<>();
			for (int j = Integer.min(firstID, lastID); j < Integer.max(firstID, lastID); j++) {
				searchRouteStops.add(stopTimes.get(j).getStop());
			}
			
			SearchTrip searchTrip = new SearchTrip(trip, firstID, lastID);
			SearchRoute searchRoute = new SearchRoute(startTime.toLocalDate(), searchRouteStops, List.of(searchTrip));
			
			
			if (searchRoute.getFirstTime() != null && searchRoute.getLastTime() != null && searchRoute.getFirstTime().isAfter(startTime)) {
				usefulSearchRoutes.add(searchRoute);
			}
		}
		
		orderSearchRoutesByDeparture(usefulSearchRoutes);
		
		//return usefulSearchRoutes.subList(0, 10);
		return usefulSearchRoutes;
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
