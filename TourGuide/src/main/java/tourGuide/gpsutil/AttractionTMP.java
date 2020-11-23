package tourGuide.gpsutil;

import gpsUtil.location.Location;

import java.util.UUID;

public class AttractionTMP extends Location {
    public final String attractionName;
    public final String city;
    public final String state;
    public final UUID attractionId;

    public AttractionTMP(String attractionName, String city, String state, double latitude, double longitude) {
        super(latitude, longitude);
        this.attractionName = attractionName;
        this.city = city;
        this.state = state;
        this.attractionId = UUID.randomUUID();
    }
}
