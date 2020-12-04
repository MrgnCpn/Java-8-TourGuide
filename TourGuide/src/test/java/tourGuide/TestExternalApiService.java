package tourGuide;

import org.json.JSONException;
import org.junit.Test;
import tourGuide.models.Attraction;
import tourGuide.models.Location;
import tourGuide.models.Provider;
import tourGuide.models.VisitedLocation;
import tourGuide.service.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class TestExternalApiService {
    @Test
    public void testGetApiServerUrl(){
        ExternalApiService externalApiService = new ExternalApiService(new HTTPRequestService(), "src/main/resources/application.properties");

        assertThat(externalApiService.getApiServerUrl("gpsutil.host")).isEqualTo("http://localhost:8081");
        assertThat(externalApiService.getApiServerUrl("trippricer.host")).isEqualTo("http://localhost:8082");
        assertThat(externalApiService.getApiServerUrl("rewardcentral.host")).isEqualTo("http://localhost:8083");
        assertThat(externalApiService.getApiServerUrl("unknownParam.host")).isNullOrEmpty();
    }

    @Test
    public void testGetUserLocation() throws IOException, JSONException {
        GpsUtilService gpsUtilService = new GpsUtilService(
                new HTTPRequestService(),
                "src/main/resources/application.properties"
        );

        UUID uuid = UUID.randomUUID();
        VisitedLocation visitedLocation = gpsUtilService.getUserLocation(uuid);

        assertThat(visitedLocation).isNotNull();
        assertThat(visitedLocation.userId).isEqualTo(uuid);
        assertThat(visitedLocation.location).isInstanceOf(Location.class);
        assertThat(visitedLocation.location.latitude).isNotNull();
        assertThat(visitedLocation.location.latitude).isInstanceOf(Double.class);
        assertThat(visitedLocation.location.longitude).isNotNull();
        assertThat(visitedLocation.location.longitude).isInstanceOf(Double.class);
        assertThat(visitedLocation.timeVisited).isNotNull();
        assertThat(visitedLocation.timeVisited).isInstanceOf(Date.class);
    }

    @Test
    public void testGetAttractions() throws IOException, JSONException {
        GpsUtilService gpsUtilService = new GpsUtilService(
                new HTTPRequestService(),
                "src/main/resources/application.properties"
        );

        List<Attraction> attractionList = gpsUtilService.getAttractions();

        assertThat(attractionList).isNotNull();
        assertThat(attractionList.size()).isGreaterThanOrEqualTo(0);
        assertThat(attractionList.size()).isEqualTo(26);

        assertThat(attractionList.get(0)).isInstanceOf(Attraction.class);
        assertThat(attractionList.get(0).attractionId).isInstanceOf(UUID.class);
        assertThat(attractionList.get(0).attractionId).isNotNull();
        assertThat(attractionList.get(0).attractionName).isNotBlank();
        assertThat(attractionList.get(0).city).isNotBlank();
        assertThat(attractionList.get(0).state).isNotBlank();
        assertThat(attractionList.get(0).latitude).isNotNull();
        assertThat(attractionList.get(0).latitude).isInstanceOf(Double.class);
        assertThat(attractionList.get(0).longitude).isNotNull();
        assertThat(attractionList.get(0).longitude).isInstanceOf(Double.class);
    }

    @Test
    public void testGetAttractionRewardPoints() throws IOException, JSONException {
        RewardCentralService rewardCentralService = new RewardCentralService(
                new HTTPRequestService(),
                "src/main/resources/application.properties"
        );

        int reward = rewardCentralService.getAttractionRewardPoints(UUID.randomUUID(), UUID.randomUUID());

        assertThat(reward).isNotNull();
        assertThat(reward).isPositive();
    }

    @Test
    public void testGetPrice() throws IOException, JSONException {
        TripPricerService tripPricerService = new TripPricerService(
                new HTTPRequestService(),
                "src/main/resources/application.properties"
        );

        List<Provider> providerList = tripPricerService.getPrice("apiKey", UUID.randomUUID(), 1, 2, 1, 100);

        assertThat(providerList).isNotNull();
        assertThat(providerList.size()).isEqualTo(5);
        assertThat(providerList.get(0)).isInstanceOf(Provider.class);
        assertThat(providerList.get(0)).isNotNull();
        assertThat(providerList.get(0).name).isNotBlank();
        assertThat(providerList.get(0).tripId).isNotNull();
        assertThat(providerList.get(0).tripId).isInstanceOf(UUID.class);
        assertThat(providerList.get(0).price).isPositive();
    }
}
