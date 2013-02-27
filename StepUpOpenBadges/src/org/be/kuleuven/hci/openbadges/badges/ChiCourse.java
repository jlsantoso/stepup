package org.be.kuleuven.hci.openbadges.badges;

import org.be.kuleuven.hci.openbadges.utils.StepUpConstants;

public class ChiCourse {
	
	final static String extension=".png";

//Linguistic badges
	//Challenge
	public static String createBiWeeklyGoldLinguisticChallenge(String username, String week){
		String badgeName = "pos_linguistic_challenger_gold";
		return Badges.openBadgeFormat(username, week, "The Linguistic Challenger Gold medal", "Well done! You are the student who has used more challenging key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}	
	
	public static String createBiWeeklySilverLinguisticChallenge(String username, String week){
		String badgeName = "pos_linguistic_challenger_silver";
		return Badges.openBadgeFormat(username, week, "The Linguistic Challenger Silver medal", "Well done! You are the second student who has used more challenging key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	
	public static String createBiWeeklyBronzeLinguisticChallenge(String username, String week){
		String badgeName = "pos_linguistic_challenger_bronze";
		return Badges.openBadgeFormat(username, week, "The Linguistic Challenger Bronze medal", "Well done! You are the third student who has used more challenging key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	//Evaluation
	public static String createBiWeeklyGoldLinguisticEvaluation(String username, String week){
		String badgeName = "pos_linguistic_evaluator_gold";
		return Badges.openBadgeFormat(username, week, "The Linguistic Evaluator Gold medal", "Well done! You are the student who has used more evaluation key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}	
	
	public static String createBiWeeklySilverLinguisticEvaluation(String username, String week){
		String badgeName = "pos_linguistic_evaluator_silver";
		return Badges.openBadgeFormat(username, week, "The Linguistic Evaluator Silver medal", "Well done! You are the second student who has used more evaluation key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	
	public static String createBiWeeklyBronzeLinguisticEvaluation(String username, String week){
		String badgeName = "pos_linguistic_evaluator_bronze";
		return Badges.openBadgeFormat(username, week, "The Linguistic Evaluator Bronze medal", "Well done! You are the second student who has used more evaluation key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	//Extension
	public static String createBiWeeklyGoldLinguisticExtension(String username, String week){
		String badgeName = "pos_linguistic_extender_gold";
		return Badges.openBadgeFormat(username, week, "The Linguistic Extender Gold medal", "Well done! You are the student who has used more extension key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}	
	
	public static String createBiWeeklySilverLinguisticExtension(String username, String week){
		String badgeName = "pos_linguistic_extender_silver";
		return Badges.openBadgeFormat(username, week, "The Linguistic Extender Silver medal", "Well done! You are the second student who has used more extension key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	
	public static String createBiWeeklyBronzeLinguisticExtension(String username, String week){
		String badgeName = "pos_linguistic_extender_bronze";
		return Badges.openBadgeFormat(username, week, "The Linguistic Extender Bronze medal", "Well done! You are the second student who has used more extension key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	//Reasoning
	public static String createBiWeeklyGoldLinguisticReasoning(String username, String week){
		String badgeName = "pos_linguistic_reasoner_gold";
		return Badges.openBadgeFormat(username, week, "The Linguistic Reasoner Gold medal", "Well done! You are the student who has used more reasoning key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}	
	
	public static String createBiWeeklySilverLinguisticReasoning(String username, String week){
		String badgeName = "pos_linguistic_reasoner_silver";
		return Badges.openBadgeFormat(username, week, "The Linguistic Reasoner Silver medal", "Well done! You are the second student who has used more reasoning key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	
	public static String createBiWeeklyBronzeLinguisticReasoning(String username, String week){
		String badgeName = "pos_linguistic_reasoner_bronze";
		return Badges.openBadgeFormat(username, week, "The Linguistic Reasoner Bronze medal", "Well done! You are the second student who has used more reasoning key sentences in your constructive comments (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}

//Comments on others blogs
	public static String createBiWeeklyGoldCommentsOnOthersBlogs(String username, String week, String startdate, String enddate){
		String badgeName = "pos_commentpercent_gold";
		return Badges.openBadgeFormat(username, week, "Commenter Gold medal", "Well done! You have commented on 33% others blogs (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive" , startdate, enddate);		
	}	
	
	public static String createBiWeeklySilverCommentsOnOthersBlogs(String username, String week, String startdate, String enddate){
		String badgeName = "pos_commentpercent_silver";
		return Badges.openBadgeFormat(username, week, "Commenter Silver medal", "Well done! You have commented on 66% others blogs (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive", startdate, enddate);		
	}
	
	public static String createBiWeeklyBronzeCommentsOnOthersBlogs(String username, String week, String startdate, String enddate){
		String badgeName = "pos_commentpercent_gold";
		return Badges.openBadgeFormat(username, week, "Commenter Bronze medal", "Well done! You have commented on 100% others blogs (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive", startdate, enddate);		
	}
//Earlier on publish results
	public static String createEarlierOnPublishResults(String username, String iteration){
		String badgeName = "neutral_cheetah";
		return Badges.openBadgeFormat(username, iteration, "Cheetah badge", "Well done! You have been the fastest on publish the results (This badge is assigned per iteration and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}	
//Earlier on getting a comment on your results
	public static String createEarlierOnGetAComment(String username, String iteration){
		String badgeName = "pos_obama_badge";
		return Badges.openBadgeFormat(username, iteration, "Obama badge", "Well done! You have been the fastest on getting a comment on the results (This badge is assigned per iteration and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
//Quality assurance blog
	public static String createBiWeekly5CommentsOnYourBlog(String username, String week, String startdate, String enddate){
		String badgeName = "pos_nice_gold";
		return Badges.openBadgeFormat(username, week, "5 comments on your blog badge", "Well done! You got 5 comments on your blog. (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive", startdate, enddate);		
	}	
	
	public static String createBiWeekly10CommentsOnYourBlog(String username, String week, String startdate, String enddate){
		String badgeName = "pos_nice_silver";
		return Badges.openBadgeFormat(username, week, "10 comments on your blog", "Well done! You got 10 comments on your blog (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive", startdate, enddate);		
	}
	
	public static String createBiWeekly15CommentsOnYourBlog(String username, String week, String startdate, String enddate){
		String badgeName = "pos_nice_bronze";
		return Badges.openBadgeFormat(username, week, "15 comments on your blog", "Well done! You got 15 comments on your blog (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive", startdate, enddate);		
	}
//Quality assurance evaluations
	public static String create5PeopleInvolvedInEvaluation(String username, String iteration){
		String badgeName = "pos_sus_bronze";
		return Badges.openBadgeFormat(username, iteration, "SUS bronze medal", "Well done! You got 5 people involved in your evaluation. (This badge is assigned per iteration and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}	
	
	public static String create10PeopleInvolvedInEvaluation(String username, String iteration){
		String badgeName = "pos_sus_silver";
		return Badges.openBadgeFormat(username, iteration, "SUS silver medal", "Well done! You got 10 people involved in your evaluation. (This badge is assigned per iteration and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}	
	
	public static String create15PeopleInvolvedInEvaluation(String username, String iteration){
		String badgeName = "pos_sus_gold";
		return Badges.openBadgeFormat(username, iteration, "SUS gold medal", "Well done! You got 15 people involved in your evaluation. (This badge is assigned per iteration and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}		
//Groupie badge
	public static String createBiWeeklyGroupieBadge(String username, String iteration){
		String badgeName = "pos_groupie";
		return Badges.openBadgeFormat(username, iteration, "Groupie badge", "Well done! You are part of the group which got more comments! (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
//Prolix post badge
	public static String createBiWeeklyProlixPostBadge(String username, String week){
		String badgeName = "neutral_prolix_post";
		return Badges.openBadgeFormat(username, week, "Prolix Post badge", "Well done! You did the longest post! (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "neutral");		
	}
//Prolix comment badge
	public static String createBiWeeklyProlixCommentBadge(String username, String week){
		String badgeName = "neutral_prolix_comments";
		return Badges.openBadgeFormat(username, week, "Prolix Comment badge", "Well done! You did the longest comment! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "neutral");		
	}
//Improvement SUS
	public static String createSUSimprovementBadge(String username, String iteration){
		String badgeName = "pos_sus_improved";
		return Badges.openBadgeFormat(username, iteration, "Improved SUS badge", "Well done! You have improved the usability of your prototype! (This badge is assigned per iteration and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
//The participative one
	public static String createBiWeeklyParticipativeBadge(String username, String week){
		String badgeName = "pos_participation";
		return Badges.openBadgeFormat(username, week, "Participation badge", "Well done! You have been the more active commenting on others blog! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
//Troll comment
	public static String createBiWeeklyTrollBadge(String username, String week){
		String badgeName = "neg_troll";
		return Badges.openBadgeFormat(username, week, "Troll badge", "Negative point! Try to be more constructive on your comments! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "negative");		
	}
//No comment on any blog
	public static String createBiWeeklyNoCommentOnAnyBlogBadge(String username, String week){
		String badgeName = "neg_asocial";
		return Badges.openBadgeFormat(username, week, "I don't want to talk to you no more badge", "Negative point! You haven't commented on any blog! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "negative");		
	}
//No received comment on your blog
	public static String createBiWeeklyNoCommentOnYourBlogBadge(String username, String week){
		String badgeName = "neg_sadkeanu";
		return Badges.openBadgeFormat(username, week, "Sad Keanu badge", "Take a look to other blogs! Maybe you can make your posts more interesting for your peers! (This badge is assigned biweekly)", StepUpConstants.IMGPATH+badgeName+extension, "group", "negative");		
	}
//Popular post
	public static String createBiWeeklyBlogPostBadge(String username, String week){
		String badgeName = "pos_fonzie";
		return Badges.openBadgeFormat(username, week, "Fonzie badge", "Well done! You did the most commented post! (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
//Illustrative post
	public static String createBiWeeklyMostIllustrativePostBadge(String username, String week){
		String badgeName = "neutral_illustrative";
		return Badges.openBadgeFormat(username, week, "Most illustrative post badge", "Well done! You did the most illustrative post! (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "neutral");		
	}
//Linking post
	public static String createBiWeekly2LinksOnPostBadge(String username, String week){
		String badgeName = "pos_link_bronze";
		return Badges.openBadgeFormat(username, week, "+2 link post badge", "Well done! You included 2 external links on your post! (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
	public static String createBiWeekly5LinksOnPostBadge(String username, String week){
		String badgeName = "pos_link_silver";
		return Badges.openBadgeFormat(username, week, "+5 link post badge", "Well done! You included 5 external links on your post! (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
	public static String createBiWeekly8LinksOnPostBadge(String username, String week){
		String badgeName = "pos_link_gold";
		return Badges.openBadgeFormat(username, week, "+8 link post badge", "Well done! You included 8 external links on your post! (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
//Most usable prototype
	public static String createBiWeeklyMostUsablePrototype(String username, String week){
		String badgeName = "pos_prototype";
		return Badges.openBadgeFormat(username, week, "Prototastic badge", "Well done! You have the most usable prototype so far! (This badge is assigned biweekly and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
//Tweets
	public static String createBiWeekly5Tweets(String username, String week, String startdate, String enddate){
		String badgeName = "pos_tweets_bronze";
		return Badges.openBadgeFormat(username, week, "+5 tweets badge", "Well done! You have done 5 tweets! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive", startdate, enddate);		
	}
	public static String createBiWeekly10Tweets(String username, String week, String startdate, String enddate){
		String badgeName = "pos_tweets_silver";
		return Badges.openBadgeFormat(username, week, "+10 tweets badge", "Well done! You have done 10 tweets! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive", startdate, enddate);		
	}
	public static String createBiWeekly15Tweets(String username, String week, String startdate, String enddate){
		String badgeName = "pos_tweets_gold";
		return Badges.openBadgeFormat(username, week, "+15 tweets badge", "Well done! You have done 15 tweets! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive", startdate, enddate);		
	}
//QA tweets
	public static String createBiWeekly5QATweets(String username, String week){
		String badgeName = "pos_retweets_bronze";
		return Badges.openBadgeFormat(username, week, "+5 QA tweets badge", "Well done! You got 5 retweets/replies_to/mentions! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	public static String createBiWeekly10QATweets(String username, String week){
		String badgeName = "pos_retweets_silver";
		return Badges.openBadgeFormat(username, week, "+10 QAtweets badge", "Well done! You have done 10 retweets/replies_to/mentions! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
	public static String createBiWeekly15QATweets(String username, String week){
		String badgeName = "pos_retweets_gold";
		return Badges.openBadgeFormat(username, week, "+15 QA tweets badge", "Well done! You have done 15 retweets/replies_to/mentions! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive");		
	}
//Bookmarking
	public static String createBiWeekly5Diigo(String username, String week, String startdate, String enddate){
		String badgeName = "pos_diigo_bronze";
		return Badges.openBadgeFormat(username, week, "+5 bookmarks badge", "Well done! You have done 5 bookmarks! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive", startdate, enddate);		
	}
	public static String createBiWeekly10Diigo(String username, String week, String startdate, String enddate){
		String badgeName = "pos_diigo_silver";
		return Badges.openBadgeFormat(username, week, "+10 bookmarks badge", "Well done! You have done 10 bookmarks! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive", startdate, enddate);		
	}
	public static String createBiWeekly15Diigo(String username, String week, String startdate, String enddate){
		String badgeName = "pos_diigo_gold";
		return Badges.openBadgeFormat(username, week, "+15 bookmarks badge", "Well done! You have done 15 bookmarks! (This badge is assigned biweekly and individually)", StepUpConstants.IMGPATH+badgeName+extension, "individual", "positive", startdate, enddate);		
	}
//Progress
	public static String createIterations(String username, String iteration){
		String badgeName = "pos_progress_"+iteration;		
		return Badges.openBadgeFormat(username, iteration, "New iteration badge", "Well done! You have done a new iteration!  (This badge is assigned per iteration and per group)", StepUpConstants.IMGPATH+badgeName+extension, "group", "positive");		
	}
	
}
