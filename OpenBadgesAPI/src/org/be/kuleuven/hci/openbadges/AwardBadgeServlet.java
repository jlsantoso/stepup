package org.be.kuleuven.hci.openbadges;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.be.kuleuven.hci.openbadges.model.AwardedBadge;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAwardedBadge;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerBadge;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class AwardBadgeServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String studentId = req.getParameter("student");
		System.out.println("Student identifier: "+studentId);
		String badgeId = req.getParameter("badge");
		System.out.println("Badge identifier: "+badgeId);
		String context = req.getParameter("context");
		String evidence = req.getParameter("evidence");
		//AwardedBadge awardedBadge = new AwardedBadge();
		Badge badge = PersistanceLayerBadge.getbadgeById(badgeId);
		AwardedBadge awardedBadge = new AwardedBadge();
		awardedBadge.setContext(badge.getContext());
		awardedBadge.setBadgeId(badgeId);
		awardedBadge.setusername(studentId);
		awardedBadge.setStarttime(Calendar.getInstance().getTime());				
		awardedBadge.setEventsRelated(new Text(evidence));
		JSONObject completedBadge = new JSONObject();
		try {
			completedBadge.put("badge", new JSONObject(badge.getjsonBadge().getValue()));
			completedBadge.put("recipient", studentId);
			completedBadge.put("issued_on", awardedBadge.getStarttime().toString());
			completedBadge.put("evidence", evidence);				
			System.out.println("badge: "+badge.getjsonBadge().getValue());
							
			awardedBadge.setAwardedJSONBadge(new Text(completedBadge.toString()));
			System.out.println("completed: "+completedBadge.toString());
			System.out.println("awarded: "+awardedBadge.getJSON().toString());
			awardedBadge.setNameApp("editor");
			System.out.println(awardedBadge.getJSON().toString());
			String id = PersistanceLayerAwardedBadge.saveAwardedBadge(awardedBadge);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//req.getRequestDispatcher("/menu.jsp?context="+req.getParameter("context")+"&inquiryserver="+req.getParameter("inquiryserver")).forward(req, resp);
		
		resp.sendRedirect("/menu.jsp?context="+req.getParameter("context")+"&inquiryserver="+req.getParameter("inquiryserver"));
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		doGet(req, resp);
	}
}
