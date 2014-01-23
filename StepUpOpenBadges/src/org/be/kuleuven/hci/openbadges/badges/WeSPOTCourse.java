package org.be.kuleuven.hci.openbadges.badges;

import org.be.kuleuven.hci.openbadges.utils.StepUpConstants;

public class WeSPOTCourse {
	
	final static String extension=".png";

	public static String createFirstPhaseBadge(String username, String course){
		String badgeName = "wespot_phase1_badge";
		return Badges.openBadgeFormat(username, course, "Phase 1 started!", "Well done! You have started the first phase!", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}	
	
	public static String createSecondPhaseBadge(String username, String course){
		String badgeName = "wespot_phase2_badge";
		return Badges.openBadgeFormat(username, course, "Phase 2 started!", "Well done! You have started the second phase!", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
	
	public static String createThirdPhaseBadge(String username, String course){
		String badgeName = "wespot_phase3_badge";
		return Badges.openBadgeFormat(username, course, "Phase 3 started!", "Well done! You have started the third phase!", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}

	public static String createFourthPhaseBadge(String username, String course){
		String badgeName = "wespot_phase4_badge";
		return Badges.openBadgeFormat(username, course, "Phase 4 started!", "Well done! You have started the fourth phase!", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}	
	
	public static String createFifthPhaseBadge(String username, String course){
		String badgeName = "wespot_phase5_badge";
		return Badges.openBadgeFormat(username, course, "Phase 5 started!", "Well done! You have started the fifth phase!", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
	
	public static String createSixthPhaseBadge(String username, String course){
		String badgeName = "wespot_phase6_badge";
		return Badges.openBadgeFormat(username, course, "Phase 6 started!", "Well done! You have started the sixth phase!", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
	
	public static String createPassedPhaseBadge(String username, String course, String phase, String score){
		String badgeName = "wespot_phase"+phase+"_badge";
		return Badges.openBadgeFormat(username, course, "Passed phase "+phase+"!", "Well done! You scored "+score+"% on the test.", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}	
	
}
