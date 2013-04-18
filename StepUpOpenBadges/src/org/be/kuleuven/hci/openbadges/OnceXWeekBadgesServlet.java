package org.be.kuleuven.hci.openbadges;

import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.servlet.http.*;


import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.Badges;
import org.be.kuleuven.hci.openbadges.badges.GiveBadges;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesLinguistics;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesParticipation;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesPerPost;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesProlix;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesRules;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesRulesFaster;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesRulesLinks;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesRulesQATweets;
import org.be.kuleuven.hci.openbadges.badges.rules.ChiCourseBadgesRulesQuantityComments;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.be.kuleuven.hci.openbadges.utils.Event;
import org.be.kuleuven.hci.openbadges.utils.JSONandEvent;
import org.be.kuleuven.hci.openbadges.utils.RestClient;

import org.json.JSONArray;
import org.json.JSONException;



@SuppressWarnings("serial")
public class OnceXWeekBadgesServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(OnceXWeekBadgesServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ChiCourseBadgesRulesQuantityComments.getPopularGroup();
		ChiCourseBadgesRulesQuantityComments.getNoCommentsGroup();
		ChiCourseBadgesPerPost.illustrativePost();
		ChiCourseBadgesPerPost.popularPost();
		ChiCourseBadgesProlix.prolixPost();
		ChiCourseBadgesProlix.prolixComment();
		ChiCourseBadgesParticipation.mostAndNoParticipativeStudent();
		ChiCourseBadgesLinguistics.mostReasonerStudent();
		ChiCourseBadgesLinguistics.mostChallengerStudent();
		ChiCourseBadgesLinguistics.mostEvaluatorStudent();
		ChiCourseBadgesLinguistics.mostExtenderStudent();
	}
}
