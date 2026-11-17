package main.backend.maps.model;

public class Coord {
	private float latitude;
	private float longitude;
	
	public Coord(float longitude, float latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public float getLatitude() {
		return latitude;
	}
}
