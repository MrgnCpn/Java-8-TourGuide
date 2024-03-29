package tourGuide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import tourGuide.helper.InternalTestHelper;
import tourGuide.models.Attraction;
import tourGuide.models.Provider;
import tourGuide.models.User;
import tourGuide.models.VisitedLocation;
import tourGuide.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class TestTourGuideService {
	private String configurationFilePath = "src/main/resources/application.properties";
	private ExecutorService executorService;
	private HTTPRequestService httpRequestService;
	private GpsUtilService gpsUtilService;
	private TripPricerService tripPricerService;
	private RewardCentralService rewardCentralService;
	private RewardsService rewardsService;
	private TourGuideService tourGuideService;
	private User user;
	private User user2;

	private void initTest(Integer internalUser){
		executorService = Executors.newFixedThreadPool(1000);
		httpRequestService = new HTTPRequestService();
		tripPricerService = new TripPricerService(httpRequestService, configurationFilePath);
		rewardCentralService = new RewardCentralService(httpRequestService, configurationFilePath);
		gpsUtilService = new GpsUtilService(httpRequestService, configurationFilePath);
		rewardsService = new RewardsService(gpsUtilService, rewardCentralService, executorService);
		InternalTestHelper.setInternalUserNumber(internalUser);
		tourGuideService = new TourGuideService(gpsUtilService, rewardsService, tripPricerService, executorService);
		user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
	}

	private void undefTest(){
		executorService.shutdown();
		executorService = null;
		gpsUtilService = null;
		rewardsService = null;
		tourGuideService = null;
		user = null;
		user2 = null;
	}

	@Test
	public void getUserLocation() throws ExecutionException, InterruptedException {
		initTest(0);
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		tourGuideService.tracker.stopTracking();
		assertEquals(visitedLocation.userId, user.getUserId());
		undefTest();
	}
	
	@Test
	public void addUser() {
		initTest(0);
		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());
		tourGuideService.tracker.stopTracking();
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
		undefTest();
	}
	
	@Test
	public void getAllUsers() {
		initTest(0);
		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		List<User> allUsers = tourGuideService.getAllUsers();
		tourGuideService.tracker.stopTracking();
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
		undefTest();
	}
	
	@Test
	public void trackUser() throws ExecutionException, InterruptedException {
		initTest(0);
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		tourGuideService.tracker.stopTracking();
		assertEquals(user.getUserId(), visitedLocation.userId);
		undefTest();
	}
	
	@Test
	public void getNearbyAttractions() throws ExecutionException, InterruptedException, IOException, JSONException {
		initTest(0);
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		List<Attraction> attractions = tourGuideService.getNearbyAttractions(visitedLocation);
		tourGuideService.tracker.stopTracking();
		assertEquals(5, attractions.size());
		undefTest();
	}

	@Test
	public void getTripDeals() throws IOException, JSONException {
		initTest(0);
		List<Provider> providers = tourGuideService.getTripDeals(user);
		tourGuideService.tracker.stopTracking();
		assertEquals(5, providers.size());
		undefTest();
	}

	@Test
	public void getAllUsersLocations(){
		initTest(100);
		assertEquals(100, tourGuideService.getAllUsersLocations().size());
		undefTest();
	}

	@Test
	public void getAllUsersLocationsJSON() throws JSONException {
		initTest(100);
		int count = 0;
		List<String> usersIdList = new ArrayList<>();

		tourGuideService.getAllUsers().stream().forEach(u -> usersIdList.add(u.getUserId().toString()));
		JSONObject jsonObject = new JSONObject(tourGuideService.getAllUsersLocationsJSON());

		for (String userId : usersIdList) {
			count ++;
			JSONObject userLocation = jsonObject.getJSONObject(userId);
			assertNotNull(userLocation);
			assertNotNull(userLocation.getDouble("latitude"));
			assertNotNull(userLocation.getDouble("longitude"));
		}

		assertEquals(100, count);
		undefTest();
	}

	@Test
	public void getFiveClosestAttractionJSON() throws ExecutionException, InterruptedException, JSONException, IOException {
		initTest(1);
		String result = tourGuideService.getFiveClosestAttractionJSON(tourGuideService.getAllUsers().get(0));

		JSONObject jsonObj = new JSONObject(result);
		JSONObject userLocation = jsonObj.getJSONObject("userLocation");
		assertNotNull(userLocation);
		assertNotNull(userLocation.getDouble("latitude"));
		assertNotNull(userLocation.getDouble("longitude"));
		JSONArray closestAttractions = jsonObj.getJSONArray("closestAttractions");
		assertEquals(5, closestAttractions.length());
		for (int i = 0; i < closestAttractions.length(); i++) {
			JSONObject attraction = closestAttractions.getJSONObject(i);
			assertNotNull(attraction);
			assertNotNull(attraction.getString("attractionName"));
			assertNotNull(attraction.getString("city"));
			assertNotNull(attraction.getString("state"));
			assertNotNull(attraction.getDouble("distance"));
			assertNotNull(attraction.getDouble("reward"));
		}
		undefTest();
	}
}