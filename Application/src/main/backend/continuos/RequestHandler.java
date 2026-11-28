package main.backend.continuos;

import java.awt.Point;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import main.backend.continuos.dbm.DataBaseManager;
import main.backend.continuos.dbm.Database;
import main.backend.continuos.dbm.Database.KeyExistsException;
import main.backend.continuos.dbm.Database.KeyNotExistsException;
import main.backend.maps.model.Coord;
import main.backend.maps.model.MapModel;
import main.backend.maps.model.Stop;
import main.backend.maps.search.RouteCalculator;
import main.backend.maps.search.SearchRoute;
import main.backend.maps.view.ViewData;
import main.comm.asnwer.MapBorderPointsAnswer;
import main.comm.asnwer.MapStopDataAnswer;
import main.comm.asnwer.StopNameAnswer;
import main.comm.asnwer.TicketAddFareAnswer;
import main.comm.asnwer.TicketAddTicketAnswer;
import main.comm.asnwer.TicketInvalidListAnswer;
import main.comm.asnwer.TicketListAnswer;
import main.comm.asnwer.TicketValidListAnswer;
import main.comm.asnwer.UserAuthAnswer;
import main.comm.asnwer.UserChangePasswordAnswer;
import main.comm.asnwer.UserDataAnswer;
import main.comm.asnwer.UserDeleteAnswer;
import main.comm.asnwer.UserRegistAnswer;
import main.comm.products.FareData;
import main.comm.products.TicketData;
import main.comm.request.MapBorderPointsRequest;
import main.comm.request.MapStopDataRequest;
import main.comm.request.StopNameRequest;
import main.comm.request.TicketAddFareRequest;
import main.comm.request.TicketAddTicketRequest;
import main.comm.request.TicketInvalidListRequest;
import main.comm.request.TicketListRequest;
import main.comm.request.TicketValidListRequest;
import main.comm.request.UserAuthRequest;
import main.comm.request.UserChangePasswordRequest;
import main.comm.request.UserDataRequest;
import main.comm.request.UserDeleteRequest;
import main.comm.request.UserRegistRequest;
import main.util.FareCalculator;
import main.util.FareCalculator.FareValidityLength;

public class RequestHandler {
	
	public MapStopDataAnswer handleRequest(MapStopDataRequest request) {
		MapModel mapModel = ContinousBackend.get_instance().getMapModel();
		ArrayList<Point> points = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		
		List<Stop> stops = mapModel.getStations();
		points.ensureCapacity(stops.size());
		names.ensureCapacity(stops.size());
		
		for (Stop stop : stops) {
			Coord coord = stop.getCoord();
			points.add(new Point((int)(coord.getLongitude() * 10000), (int)(coord.getLatitude() * 10000)));
			names.add(stop.getName());
		}
		return new MapStopDataAnswer(true, true, points, names);
	}
	
	public MapBorderPointsAnswer handleRequest(MapBorderPointsRequest request) {
		ViewData viewData = ContinousBackend.get_instance().getViewData();
		List<List<Point>> points = new ArrayList<>();
		for (int i = 0; i < viewData.getBorderDatas().size(); i++) {
			List<Point> borderPoints = viewData.getBorderDatas().get(i).optimizePoints(request.boundingBox);
			//List<Point> borderPoints = viewData.getBorderDatas().get(i).getPoints();
			points.add(borderPoints);
		}  
		return new MapBorderPointsAnswer(true, true, points);
	}
	
	public StopNameAnswer handleRequest(StopNameRequest request) {
		MapModel mapModel = ContinousBackend.get_instance().getMapModel();
		List<Stop> stops = mapModel.getStations();
		
		ArrayList<String> res = new ArrayList<>();
		res.ensureCapacity(stops.size());
		
		for (Stop stop : stops) {
			res.add(stop.getName());
		}
		
		return new StopNameAnswer(true, true, res);
	}
	
	public TicketAddFareAnswer handleRequest(TicketAddFareRequest request) {
		if (request.username == null) {
			return new TicketAddFareAnswer(true, false);
		}
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database fareBase = dbm.getDatabase("fare");
		
		List<String> fareData = request.fareData.getData();
		
		int i = 0;
		while(true) {
			try {
				List<String> record = List.of(request.username + i, fareData.get(0), fareData.get(1), fareData.get(2));
				fareBase.addRecord(record);
				break;
			} catch (KeyExistsException e) {}
			i++;
		}
		
		return new TicketAddFareAnswer(true, true);
	}
	
	public TicketAddTicketAnswer handleRequest(TicketAddTicketRequest request) {
		if (request.username == null) {
			return new TicketAddTicketAnswer(true, false);
		}
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database ticketBase = dbm.getDatabase("ticket");
		
		List<String> ticketData = request.ticketData.getData();
		
		int i = 0;
		while(true) {
			try {
				List<String> record = List.of(request.username + i, ticketData.get(0), ticketData.get(1), ticketData.get(2), ticketData.get(3), ticketData.get(4));
				ticketBase.addRecord(record);
				break;
			} catch (KeyExistsException e) {}
			i++;
		}
		
		return new TicketAddTicketAnswer(true, true);
	}
	
	public TicketValidListAnswer handleRequest(TicketValidListRequest request) {
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database ticketBase = dbm.getDatabase("ticket");
		Database fareBase = dbm.getDatabase("fare");
		
		List<TicketData> resTickets = new ArrayList<>();
		List<FareData> resFares = new ArrayList<>();
		
		int i = 0;
		try {
			while (true) {
				List<String> readRecord = ticketBase.getRecord(request.username + i);
				System.out.println(readRecord);
				TicketData ticketData = new TicketData(readRecord.get(1), readRecord.get(2), readRecord.get(3), LocalDateTime.parse(readRecord.get(4)), LocalDateTime.parse(readRecord.get(5)), -1);
				
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime arrivalTime = LocalDateTime.parse(ticketData.getData().get(4));
				if (arrivalTime.isAfter(now)) {
					resTickets.add(ticketData);
				}
				i++;
			}
		} catch (KeyNotExistsException ignored) {}
		
		i = 0;
		try {
			while (true) {
				List<String> readRecord = fareBase.getRecord(request.username + i);
				FareData fareData = new FareData(readRecord.get(1), LocalDate.parse(readRecord.get(2)), readRecord.get(3), -1);
				
				LocalDateTime now = LocalDateTime.now();
				LocalDate validityStart = LocalDate.parse(readRecord.get(2));
				LocalDate validityEnd = FareCalculator.calculate(validityStart, FareValidityLength.valueOf(readRecord.get(3)));
				if (validityEnd.isAfter(now.toLocalDate())) {
					resFares.add(fareData);
				}
				i++;
			}
		} catch (KeyNotExistsException ignored) {}
		
		return new TicketValidListAnswer(true, true, resTickets, resFares);
	}
	
	public TicketInvalidListAnswer handleRequest(TicketInvalidListRequest request) {
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database ticketBase = dbm.getDatabase("ticket");
		Database fareBase = dbm.getDatabase("fare");
		
		List<TicketData> resTickets = new ArrayList<>();
		List<FareData> resFares = new ArrayList<>();
		
		int i = 0;
		try {
			while (true) {
				List<String> readRecord = ticketBase.getRecord(request.username + i);
				TicketData ticketData = new TicketData(readRecord.get(1), readRecord.get(2), readRecord.get(3), LocalDateTime.parse(readRecord.get(4)), LocalDateTime.parse(readRecord.get(5)), -1);
				
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime arrivalTime = LocalDateTime.parse(ticketData.getData().get(4));
				if (arrivalTime.isBefore(now)) {
					resTickets.add(ticketData);
				}
				i++;
			}
		} catch (KeyNotExistsException ignored) {}
		
		i = 0;
		try {
			while (true) {
				List<String> readRecord = fareBase.getRecord(request.username + i);
				FareData fareData = new FareData(readRecord.get(1), LocalDate.parse(readRecord.get(2)), readRecord.get(3), -1);
				
				LocalDateTime now = LocalDateTime.now();
				LocalDate validityStart = LocalDate.parse(readRecord.get(2));
				LocalDate validityEnd = FareCalculator.calculate(validityStart, FareValidityLength.valueOf(readRecord.get(3)));
				if (validityEnd.isBefore(now.toLocalDate())) {
					resFares.add(fareData);
				}
				i++;
			}
		} catch (KeyNotExistsException ignored) {}
		
		return new TicketInvalidListAnswer(true, true, resTickets, resFares);
	}
	
	public TicketListAnswer handleRequest(TicketListRequest request) {
		MapModel mapModel = ContinousBackend.get_instance().getMapModel();
		
		Stop startStop = mapModel.getStopFromName(request.startStopName);
		Stop destStop = mapModel.getStopFromName(request.destStopName);
		LocalDateTime startTime = request.startTime;
		
		if (startStop == null || destStop == null) {
			return new TicketListAnswer(true, false, null);
		}
		
		List<TicketData> data = new ArrayList<>();
		
		int i = 0;
		while (data.size() < 10 && i < 2) {
			List<SearchRoute> searchResult = RouteCalculator.calculateRoute(mapModel, startStop, destStop, startTime);
			
			
			for (SearchRoute searchRoute : searchResult) {
				TicketData ticketData = new TicketData(searchRoute.getSearchTrips().get(0).getTrip().getTrainRoute().getName(), request.startStopName, request.destStopName, searchRoute.getFirstTime(), searchRoute.getLastTime(), searchRoute.calculatePrice() * request.peopleNum); 
				data.add(ticketData);
			}
			
			startTime = LocalDateTime.of(startTime.plusDays(1).toLocalDate(), LocalTime.of(0, 0, 0, 0));
			i++;
		}
		
		return new TicketListAnswer(true, true, data.subList(0, Integer.min(data.size(), 10)));
	}
	
	public UserDeleteAnswer handleRequest(UserDeleteRequest request) {
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			userBase.deleteRecord(request.username);
			return new UserDeleteAnswer(true, true);
		} catch (KeyNotExistsException e) {
			return new UserDeleteAnswer(true, false);
		}
	}
	
	public UserChangePasswordAnswer handleRequest(UserChangePasswordRequest request) {
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			List<String> readRecord = userBase.getRecord(request.username);
			int passwordID = userBase.getFieldID("password");
			if (readRecord.get(passwordID).equals(request.password)) {
				return new UserChangePasswordAnswer(true, false);
			}
			
			List<String> userRecord = new ArrayList<>();
			userRecord.addAll(readRecord);
			userRecord.set(userBase.getFieldID("password"), request.password);
			
			userBase.changeRecord(request.username, userRecord);
			
			return new UserChangePasswordAnswer(true, true);
		} catch (KeyNotExistsException e) {
			return new UserChangePasswordAnswer(true, false);
		}
	}
	
	public UserAuthAnswer handleRequest(UserAuthRequest request) {
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			List<String> userRecord = userBase.getRecord(request.username);
			int passwordID = userBase.getFieldID("password");
			if (!userRecord.get(passwordID).equals(request.password)) {
				return new UserAuthAnswer(true, false, null);
			}
			
			return new UserAuthAnswer(true, true, request.username);
		} catch (KeyNotExistsException e) {
			return new UserAuthAnswer(true, false, null);
		}
	}
	
	public UserDataAnswer handleRequest(UserDataRequest request) {
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			List<String> userRecord = userBase.getRecord(request.username);
			return new UserDataAnswer(true, true, userRecord.get(0), userRecord.get(2), userRecord.get(3), userRecord.get(4));
			
		} catch (KeyNotExistsException e) {
			return new UserDataAnswer(true, false, null, null, null, null);
		}
	}
	
	public UserRegistAnswer handleRequest(UserRegistRequest request) {
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			List<String> record = List.of(request.username, request.password, request.dateOfBirth, request.IDNumber, request.address);
			userBase.addRecord(record);
			return new UserRegistAnswer(true, true, request.username);
		} catch (KeyExistsException e) {
			return new UserRegistAnswer(true, false, null);
		}
	}
}
