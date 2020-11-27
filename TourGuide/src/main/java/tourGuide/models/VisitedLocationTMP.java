package tourGuide.models;

import gpsUtil.location.Location;

import java.util.Date;
import java.util.UUID;

public class VisitedLocationTMP {
    public final UUID userId;
    public final Location location;
    public final Date timeVisited;

    public VisitedLocationTMP(UUID userId, Location location, Date timeVisited) {
        this.userId = userId;
        this.location = location;
        this.timeVisited = timeVisited;
    }
}

