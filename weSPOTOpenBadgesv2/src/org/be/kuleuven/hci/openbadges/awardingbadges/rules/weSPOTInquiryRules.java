package org.be.kuleuven.hci.openbadges.awardingbadges.rules;

import java.io.UnsupportedEncodingException;




import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;









import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.badges.WeSPOT;
import org.be.kuleuven.hci.openbadges.model.AwardedBadges;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAwardedBadges;
import org.be.kuleuven.hci.openbadges.utils.OpenBadgeAPI;
//import org.be.kuleuven.hci.openbadges.badges.rules.utils.Dates;
import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.be.kuleuven.hci.openbadges.utils.weSPOTBadgesConstants;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class weSPOTInquiryRules {
	
	private static final Logger log = Logger.getLogger(weSPOTInquiryRules.class.getName());
	
	//inquiry + WeSPOT.getFileName(phase, level) + user
	//check in the datastore if the previous badges were awarded
	public static void awardInquiriesBadges(int level){
		AwardedBadges awardedBadges =  PersistanceLayerAwardedBadges.getAwardedBadges();
		ArrayList<String> badgesIds = awardedBadges.getBadgesId();
		Hashtable <String, ArrayList<String>> awarded = new Hashtable <String, ArrayList<String>>(); 
		for (String badgeId:badgesIds){
			for (int phase = 1; phase <7; phase++){
				if (badgeId.contains(WeSPOT.getFileName(phase, level))){
					int position = badgeId.indexOf(WeSPOT.getFileName(phase, level));
					String inquiry = badgeId.substring(0, position);
					String userId = badgeId.substring(position+WeSPOT.getFileName(phase, level).length()).toLowerCase();
					if (awarded.containsKey(inquiry+":"+userId)){
						ArrayList<String> badgesfiles = awarded.get(inquiry+":"+userId);
						if (!badgesfiles.contains(WeSPOT.getFileName(phase, level)))
							badgesfiles.add(WeSPOT.getFileName(phase, level));
						awarded.put(inquiry+":"+userId, badgesfiles);
					}else{
						ArrayList<String> badgesfiles = new ArrayList<String>();
						badgesfiles.add(WeSPOT.getFileName(phase, level));
						awarded.put(inquiry+":"+userId, badgesfiles);
					}
				}
			}
		}
		
		Enumeration<String> enumeration = awarded.keys();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			ArrayList<String> evidences = awarded.get(key);
		   if (evidences.size()>=3){
			   	String[] split = key.split(":");
				String username = split[1];
				String inquiry = split[0];
				awardBadge( inquiry, username , new ArrayList<String>(), 0,  level);	
		   }
		}		
	}
	
	public static void awardBadge(String inquiry, String user, ArrayList<String> evidences, int phase, int level){
		String evidence_result = "";
		for (String evidence:evidences){
			evidence_result += evidence+",";
		}
		evidence_result = evidence_result.substring(0, evidence_result.lastIndexOf(",")-1);
		Badge badge = WeSPOT.weSPOTBadge(inquiry, OpenBadgeAPI.getAuthKey(), phase, level);
		AwardedBadges awardedBadges = PersistanceLayerAwardedBadges.getAwardedBadges();
		boolean check = PersistanceLayerAwardedBadges.checkIfAwardedBadges(awardedBadges, inquiry, WeSPOT.getFileName(phase, level), user);
		if (!check){
			String control = OpenBadgeAPI.sendBadge(badge, OpenBadgeAPI.getAuthKey().getKeyApp(), user, evidence_result);
			if (control.compareTo("")!=0){
				awardedBadges.addBadgeId(inquiry + WeSPOT.getFileName(phase, level) + user);
				PersistanceLayerAwardedBadges.saveAwardedBadges(awardedBadges);
			}			
		}
	}
	
}
