package tourGuide;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.junit.Test;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.models.User;
import tourGuide.models.UserReward;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRewardsService {
	private ExecutorService executorService;
	private GpsUtil gpsUtil;
	private RewardsService rewardsService;
	private TourGuideService tourGuideService;
	private User user;

	private void initTest(Integer internalUser){
		executorService = Executors.newFixedThreadPool(1000);
		gpsUtil = new GpsUtil();
		rewardsService = new RewardsService(gpsUtil, new RewardCentral(), executorService);
		InternalTestHelper.setInternalUserNumber(internalUser);
		tourGuideService = new TourGuideService(gpsUtil, rewardsService, executorService);
		user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	}

	private void undefTest(){
		executorService.shutdown();
		executorService = null;
		gpsUtil = null;
		rewardsService = null;
		tourGuideService = null;
		user = null;
	}

	@Test
	public void userGetRewards() throws ExecutionException, InterruptedException {
		initTest(0);
		Attraction attraction = gpsUtil.getAttractions().get(0);
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));
		tourGuideService.trackUserLocation(user).get();
		List<UserReward> userRewards = user.getUserRewards();
		tourGuideService.tracker.stopTracking();

		assertEquals(1, userRewards.size());
		undefTest();
	}

	@Test
	public void isWithinAttractionProximity() {
		initTest(0);
		Attraction attraction = gpsUtil.getAttractions().get(0);
		assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
		undefTest();
	}

	@Test
	public void nearAllAttractions() {
		initTest(1);

		rewardsService.setProximityBuffer(Integer.MAX_VALUE);
		rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
		List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));

		tourGuideService.tracker.stopTracking();

		assertEquals(gpsUtil.getAttractions().size(), userRewards.size());
		undefTest();
	}
}
