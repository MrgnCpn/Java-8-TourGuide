package tourGuide.models;

import java.util.Date;
import java.util.UUID;

public class VisitedLocationTMP {
    public final UUID userId;
    public final LocationTMP location;
    public final Date timeVisited;

    public VisitedLocationTMP(UUID userId, LocationTMP location, Date timeVisited) {
        this.userId = userId;
        this.location = location;
        this.timeVisited = timeVisited;
    }
}

