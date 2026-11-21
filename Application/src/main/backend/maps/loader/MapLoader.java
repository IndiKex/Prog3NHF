package main.backend.maps.loader;

import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.backend.maps.model.Coord;
import main.backend.maps.model.MapModel;
import main.backend.maps.model.Service;
import main.backend.maps.model.Stop;
import main.backend.maps.model.StopTime;
import main.backend.maps.model.TrainRoute;
import main.backend.maps.model.Trip;
import main.io.csvparser.CSVParser;
import main.io.csvparser.Container;

public class MapLoader {
	public static MapModel loadMap(File dataFolder) {
		List<Stop> stops = new ArrayList<>();
		List<Trip> trips = new ArrayList<>();
		List<TrainRoute> trainRoutes = new ArrayList<>();
		
		Map<String, Stop> stopMap = loadStops(dataFolder, stops);
		Map<String, TrainRoute> trainRouteMap = loadTrainRoutes(dataFolder, trainRoutes);
		Map<String, Service> serviceMap = loadServices(dataFolder);
		Map<String, Trip> tripMap = loadTrips(dataFolder, trips, trainRouteMap, serviceMap);
		loadStopTimes(dataFolder, stopMap, tripMap);
		
		return new MapModel(stops, trips, trainRoutes);
	}
	
	private static boolean parseBoolean(String s) {
		if (s.equals("1"))
			return true;
		else
			return false;
	}
	
	private static Map<String, Service> loadServices(File dataFolder) {
		Map<String, Service> serviceMap = new HashMap<>();
		
		CSVParser.parse(new File(dataFolder, "calendar.txt"), new Container() {

			@Override
			public void addRow(List<String> data) {
				String serviceID = data.get(1);
				LocalDate startDate = LocalDate.of(Integer.parseInt(data.get(9).substring(0,4)), Integer.parseInt(data.get(9).substring(4,6)), Integer.parseInt(data.get(9).substring(6,8)));
				LocalDate endDate = LocalDate.of(Integer.parseInt(data.get(10).substring(0,4)), Integer.parseInt(data.get(10).substring(4,6)), Integer.parseInt(data.get(10).substring(6,8)));
				
				Service service = new Service(parseBoolean(data.get(2)), parseBoolean(data.get(3)), parseBoolean(data.get(4)), parseBoolean(data.get(5)), parseBoolean(data.get(6)), parseBoolean(data.get(7)), parseBoolean(data.get(8)), startDate, endDate);
				serviceMap.put(serviceID, service);
			}

			@Override
			public void addFields(List<String> fields) {
				// sigma
			}
			
		});
		
		return serviceMap;
	}
	
	private static void loadStopTimes(File dataFolder, Map<String, Stop> stopMap, Map<String, Trip> tripMap) {
		CSVParser.parse(new File(dataFolder, "stop_times.txt"), new Container() {

			@Override
			public void addRow(List<String> data) {
				String tripID = data.get(1);
				String stopID = data.get(4);
				int sequenceID = Integer.parseInt(data.get(5));
				int arrivalTime = StopTime.parseTime(data.get(2));
				int departureTime = StopTime.parseTime(data.get(3));
				
				StopTime stopTime = new StopTime(stopMap.get(stopID), tripMap.get(tripID), arrivalTime, departureTime, sequenceID);
				stopMap.get(stopID).getStopTimes().add(stopTime);
				tripMap.get(tripID).getStopTimes().add(stopTime);
			}

			@Override
			public void addFields(List<String> fields) {
				// sigma
			}
			
		});
	}
	
	private static Map<String, Stop> loadStops(File dataFolder, List<Stop> stops) {
		Map<String, Stop> stopMap = new HashMap<>();
		
		CSVParser.parse(new File(dataFolder, "stops.txt"), new Container() {

			@Override
			public void addRow(List<String> data) {
				String stopID = data.get(1);
				String stopName = data.get(3);
				float latitude = Float.parseFloat(data.get(5));
				float longitude = Float.parseFloat(data.get(6));
				
				List<StopTime> stopTimes = new ArrayList<>();
				Stop stop = new Stop(stopName, new Coord(longitude, latitude), stopTimes);
				
				stops.add(stop);
				stopMap.put(stopID, stop);
			}

			@Override
			public void addFields(List<String> fields) {
				// sigma
			}
			
		});
		
		return stopMap;
	}
	
	private static Map<String, Trip> loadTrips(File dataFolder, List<Trip> trips, Map<String, TrainRoute> trainRoutes, Map<String, Service> serviceMap) {
		Map<String, Trip> tripMap = new HashMap<>();
		
		CSVParser.parse(new File(dataFolder, "trips.txt"), new Container() {

			@Override
			public void addRow(List<String> data) {
				String routeID = data.get(1);
				String serviceID = data.get(2);
				String tripID = data.get(3);
				
				List<StopTime> stopTimes = new ArrayList<>();
				Trip trip = new Trip("", serviceMap.get(serviceID), trainRoutes.get(routeID), stopTimes);
				
				trips.add(trip);
				tripMap.put(tripID, trip);
				trainRoutes.get(routeID).getTrips().add(trip);
			}

			@Override
			public void addFields(List<String> fields) {
				// sigma
			}
			
		});
		
		return tripMap;
	}
	
	private static Map<String, TrainRoute> loadTrainRoutes(File dataFolder, List<TrainRoute> trainRoutes) {
		Map<String, TrainRoute> trainRouteMap = new HashMap<>();
		
		CSVParser.parse(new File(dataFolder, "routes.txt"), new Container() {

			@Override
			public void addRow(List<String> data) {
				String routeID = data.get(1);
				String routeName;
				if (!data.get(4).isEmpty()) {
					routeName = data.get(4);
				} else {
					routeName = data.get(3);
				}
				
				
				int routeType = Integer.parseInt(data.get(6));
				Color routeColor = parseColor(data.get(8));
				
				List<Trip> trips = new ArrayList<>();
				TrainRoute trainRoute = new TrainRoute(routeName, routeType, routeColor, trips);
				
				trainRoutes.add(trainRoute);
				trainRouteMap.put(routeID, trainRoute);
			}

			@Override
			public void addFields(List<String> fields) {
				// sigma
			}
			
		});
		
		return trainRouteMap;
	}
	
	private static Color parseColor(String colorHex) {
		if (colorHex.length() != 6)
			return null;
		
		int value = Integer.parseInt(colorHex, 16);
		int redValue = (value >> 16) & 0xff;
		int greenValue = (value >> 8) & 0xff;
		int blueValue = value & 0xff;
		
		return new Color(redValue, greenValue, blueValue);
	}
	
}
