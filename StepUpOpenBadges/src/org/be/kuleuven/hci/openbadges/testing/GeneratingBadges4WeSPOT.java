package org.be.kuleuven.hci.openbadges.testing;

import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.ChiCourse;
import org.be.kuleuven.hci.openbadges.badges.WeSPOTCourse;
import org.be.kuleuven.hci.openbadges.mailnotification.Mail;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.be.kuleuven.hci.openbadges.utils.StepUpConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class GeneratingBadges4WeSPOT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject course = new JSONObject();
			course.put("course", "arLearn-fake");
			course.put("phase", StepUpConstants.PHASE1);
			course.put("subphase", "General");

			Badge badge = new Badge();
			badge.setDescription(WeSPOTCourse.createFirstPhaseBadge("google_116743449349920850150", "fakecourseidentifier"));
			//PersistanceLayer.sendBadgeAsEvent("google_116743449349920850150", badge.getDescription(),course.toString());
			
			course = new JSONObject();
			course.put("course", "arLearn-fake");
			course.put("phase", StepUpConstants.PHASE2);
			course.put("subphase", "General");

			badge = new Badge();
			badge.setDescription(WeSPOTCourse.createSecondPhaseBadge("google_116743449349920850150", "fakecourseidentifier"));
			PersistanceLayer.sendBadgeAsEvent("google_116743449349920850150", badge.getDescription(),course.toString());
			
			course = new JSONObject();
			course.put("course", "arLearn-fake");
			course.put("phase", StepUpConstants.PHASE3);
			course.put("subphase", "General");

			badge = new Badge();
			badge.setDescription(WeSPOTCourse.createThirdPhaseBadge("google_116743449349920850150", "fakecourseidentifier"));
			PersistanceLayer.sendBadgeAsEvent("google_116743449349920850150", badge.getDescription(),course.toString());
			
			course = new JSONObject();
			course.put("course", "arLearn-fake");
			course.put("phase", StepUpConstants.PHASE4);
			course.put("subphase", "General");

			badge = new Badge();
			badge.setDescription(WeSPOTCourse.createFourthPhaseBadge("google_116743449349920850150", "fakecourseidentifier"));
			PersistanceLayer.sendBadgeAsEvent("google_116743449349920850150", badge.getDescription(),course.toString());
			
			course = new JSONObject();
			course.put("course", "arLearn-fake");
			course.put("phase", StepUpConstants.PHASE5);
			course.put("subphase", "General");

			badge = new Badge();
			badge.setDescription(WeSPOTCourse.createFifthPhaseBadge("google_116743449349920850150", "fakecourseidentifier"));
			PersistanceLayer.sendBadgeAsEvent("google_116743449349920850150", badge.getDescription(),course.toString());
			
			course = new JSONObject();
			course.put("course", "arLearn-fake");
			course.put("phase", StepUpConstants.PHASE6);
			course.put("subphase", "General");

			badge = new Badge();
			badge.setDescription(WeSPOTCourse.createSixthPhaseBadge("google_116743449349920850150", "fakecourseidentifier"));
			PersistanceLayer.sendBadgeAsEvent("google_116743449349920850150", badge.getDescription(),course.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
