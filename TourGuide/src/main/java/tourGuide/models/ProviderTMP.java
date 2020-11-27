package tourGuide.models;

import java.util.UUID;

public class ProviderTMP {
    public final String name;
    public final double price;
    public final UUID tripId;

    public ProviderTMP(UUID tripId, String name, double price) {
        this.name = name;
        this.tripId = tripId;
        this.price = price;
    }
}
