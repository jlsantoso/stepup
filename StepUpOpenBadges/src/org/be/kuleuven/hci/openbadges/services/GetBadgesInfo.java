package org.be.kuleuven.hci.openbadges.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.be.kuleuven.hci.openbadges.badges.Badges;
import org.be.kuleuven.hci.openbadges.badges.ChiCourse;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/getinfo")


public class GetBadgesInfo {
	
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/badges")
	public String getBadgesList() {	
		JSONArray badges = new JSONArray();
		try {
			JSONObject badge = new JSONObject(Badges.firstBadge(""));
			badges.put(badge);
			badge = new JSONObject(Badges.secondBadge(""));
			badges.put(badge);
			badge = new JSONObject(Badges.thirdBadge(""));
			badges.put(badge);
			badge = new JSONObject(Badges.fourthBadge(""));
			badges.put(badge);
			badge = new JSONObject(Badges.fifthBadge(""));
			badges.put(badge);
			return badges.toString();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/chibadges")
	public String getChiBadgesList() {	
		JSONArray badges = new JSONArray();
		try {
			JSONObject badge = new JSONObject(ChiCourse.createBiWeeklyGoldLinguisticChallenge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklySilverLinguisticChallenge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyBronzeLinguisticChallenge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyGoldLinguisticEvaluation("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklySilverLinguisticEvaluation("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyBronzeLinguisticEvaluation("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyGoldLinguisticExtension("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklySilverLinguisticExtension("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyBronzeLinguisticExtension("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyGoldLinguisticReasoning("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklySilverLinguisticReasoning("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyBronzeLinguisticReasoning("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyGoldCommentsOnOthersBlogs("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklySilverCommentsOnOthersBlogs("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyBronzeCommentsOnOthersBlogs("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createEarlierOnPublishResults("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createEarlierOnGetAComment("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly5CommentsOnYourBlog("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly10CommentsOnYourBlog("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly15CommentsOnYourBlog("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.create5PeopleInvolvedInEvaluation("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.create10PeopleInvolvedInEvaluation("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.create15PeopleInvolvedInEvaluation("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyGroupieBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyProlixPostBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyProlixCommentBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createSUSimprovementBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyParticipativeBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyTrollBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyNoCommentOnAnyBlogBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyNoCommentOnYourBlogBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyBlogPostBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyMostIllustrativePostBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly2LinksOnPostBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly5LinksOnPostBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly8LinksOnPostBadge("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeeklyMostUsablePrototype("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly5Tweets("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly10Tweets("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly15Tweets("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly5QATweets("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly10QATweets("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly15QATweets("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly5Diigo("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly10Diigo("", ""));
			badges.put(badge);
			badge = new JSONObject(ChiCourse.createBiWeekly15Diigo("", ""));
			badges.put(badge);
			for (int i=1;i<=5;i++){
				badge = new JSONObject(ChiCourse.createIterations("", ""+i));
				badges.put(badge);
			}		
			return badges.toString();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/badgesusers")
	public String getBadgeUsers() {	
		return PersistanceLayer.badgesrewarded().toString();
	} 
	
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{badgeId}")
	public String getBadge(@PathParam("badgeId") String badge) {	
		return PersistanceLayer.badgeId(Long.valueOf(badge));
	} 
	
}
