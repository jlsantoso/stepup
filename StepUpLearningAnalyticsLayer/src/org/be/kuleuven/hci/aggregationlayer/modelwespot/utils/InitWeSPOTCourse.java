package org.be.kuleuven.hci.aggregationlayer.modelwespot.utils;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.logging.Level;

import org.be.kuleuven.hci.aggregationlayer.StepUpConstants;
import org.be.kuleuven.hci.aggregationlayer.model.utils.RestClient;
import org.be.kuleuven.hci.aggregationlayer.modelwespot.Course;
import org.be.kuleuven.hci.aggregationlayer.modelwespot.Group;
import org.be.kuleuven.hci.aggregationlayer.modelwespot.Student;
import org.be.kuleuven.hci.aggregationlayer.modelwespot.Phase;
import org.be.kuleuven.hci.aggregationlayer.modelwespot.Subphase;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class InitWeSPOTCourse {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws JSONException 
	 */
	public static Course wespot(String name) throws UnsupportedEncodingException, JSONException{
		Course course = new Course();
		int blogs = 9;
		course.setName(name);
		Calendar startCourse = Calendar.getInstance();
		startCourse.set(2013, 8, 14, 00, 00, 00);
		course.setStartCourse(startCourse.getTime());
		int numberOfWeeks = course.getWeeks().size();
		course.addPhase(createPhase1(numberOfWeeks));
		course.addPhase(createPhase2(numberOfWeeks));
		course.addPhase(createPhase3(numberOfWeeks));
		course.addPhase(createPhase4(numberOfWeeks));
		course.addPhase(createPhase5(numberOfWeeks));
		course.addPhase(createPhase6(numberOfWeeks));
		String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select distinct lower(username) as username from event where context like '%course___"+name.replaceAll("wespot","")+"%'\", \"pag\": \"0\"}");
		System.out.println("students: "+result);
		JSONArray results = new JSONArray(result);
		for (int i=0; i<results.length();i++){
			Student s = new Student();			 
			s.setUsername(results.getJSONObject(i).getString("username"));
			//if (s.getUsername().compareTo("facebook_682307588")==0) System.out.println("******************************************ENCONTRADO***********************");
			s.addPhase(createPhase1(numberOfWeeks));
			s.addPhase(createPhase2(numberOfWeeks));
			s.addPhase(createPhase3(numberOfWeeks));
			s.addPhase(createPhase4(numberOfWeeks));
			s.addPhase(createPhase5(numberOfWeeks));
			s.addPhase(createPhase6(numberOfWeeks));		
			//System.out.println(s.getActivity().size());
			course.addStudent(s);
		}	
		return course;
	}
	

	static Phase createPhase1(int numberOfWeeks){
		Phase phase = new Phase();
		phase.setName(StepUpConstants.PHASE1);
		phase.initweeksactivity(numberOfWeeks);
		Subphase subphase1 = new Subphase();
		subphase1.setName(StepUpConstants.PHASE1_S1);
		subphase1.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase1);
		Subphase subphase2 = new Subphase();
		subphase2.setName(StepUpConstants.PHASE1_S2);
		subphase2.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase2);
		Subphase subphase3 = new Subphase();
		subphase3.setName(StepUpConstants.PHASE1_S3);
		subphase3.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase3);
		Subphase subphase4 = new Subphase();
		subphase4.setName(StepUpConstants.PHASE1_S4);
		subphase4.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase4);
		Subphase subphase5 = new Subphase();
		subphase5.setName(StepUpConstants.PHASE1_S5);
		subphase5.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase5);
		Subphase subphase6 = new Subphase();
		subphase6.setName(StepUpConstants.PHASE1_S6);
		subphase6.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase6);
		Subphase subphase7 = new Subphase();
		subphase7.setName(StepUpConstants.PHASE1_S7);
		subphase7.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase7);
		Subphase subphase8 = new Subphase();
		subphase8.setName(StepUpConstants.PHASE1_S8);
		subphase8.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase8);
		Subphase subphase9 = new Subphase();
		subphase9.setName(StepUpConstants.PHASE1_S9);
		subphase9.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase9);
		return phase;		
	}
	
	static Phase createPhase2(int numberOfWeeks){
		Phase phase = new Phase();
		phase.setName(StepUpConstants.PHASE2);
		phase.initweeksactivity(numberOfWeeks);
		Subphase subphase1 = new Subphase();
		subphase1.setName(StepUpConstants.PHASE2_S1);
		subphase1.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase1);
		Subphase subphase2 = new Subphase();
		subphase2.setName(StepUpConstants.PHASE2_S2);
		subphase2.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase2);
		Subphase subphase3 = new Subphase();
		subphase3.setName(StepUpConstants.PHASE2_S3);
		subphase3.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase3);
		Subphase subphase4 = new Subphase();
		subphase4.setName(StepUpConstants.PHASE2_S4);
		subphase4.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase4);
		Subphase subphase5 = new Subphase();
		subphase5.setName(StepUpConstants.PHASE2_S5);
		subphase5.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase5);
		Subphase subphase6 = new Subphase();
		subphase6.setName(StepUpConstants.PHASE2_S6);
		subphase6.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase6);
		Subphase subphase7 = new Subphase();
		subphase7.setName(StepUpConstants.PHASE2_S7);
		subphase7.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase7);
		return phase;
	}
	
	static Phase createPhase3(int numberOfWeeks){
		Phase phase = new Phase();
		phase.setName(StepUpConstants.PHASE3);
		phase.initweeksactivity(numberOfWeeks);
		Subphase subphase1 = new Subphase();
		subphase1.setName(StepUpConstants.PHASE3_S1);
		subphase1.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase1);
		Subphase subphase2 = new Subphase();
		subphase2.setName(StepUpConstants.PHASE3_S2);
		subphase2.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase2);
		Subphase subphase3 = new Subphase();
		subphase3.setName(StepUpConstants.PHASE3_S3);
		subphase3.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase3);
		Subphase subphase4 = new Subphase();
		subphase4.setName(StepUpConstants.PHASE3_S4);
		subphase4.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase4);
		Subphase subphase5 = new Subphase();
		subphase5.setName(StepUpConstants.PHASE3_S5);
		subphase5.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase5);
		Subphase subphase6 = new Subphase();
		subphase6.setName(StepUpConstants.PHASE3_S6);
		subphase6.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase6);
		Subphase subphase7 = new Subphase();
		subphase7.setName(StepUpConstants.PHASE3_S7);
		subphase7.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase7);
		Subphase subphase8 = new Subphase();
		subphase8.setName(StepUpConstants.PHASE3_S8);
		subphase8.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase8);
		Subphase subphase9 = new Subphase();
		subphase9.setName(StepUpConstants.PHASE3_S9);
		subphase9.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase9);
		Subphase subphase10 = new Subphase();
		subphase10.setName(StepUpConstants.PHASE3_S10);
		subphase10.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase10);
		return phase;
	}
	
	static Phase createPhase4(int numberOfWeeks){
		Phase phase = new Phase();
		phase.setName(StepUpConstants.PHASE4);
		phase.initweeksactivity(numberOfWeeks);
		Subphase subphase1 = new Subphase();
		subphase1.setName(StepUpConstants.PHASE4_S1);
		subphase1.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase1);
		Subphase subphase2 = new Subphase();
		subphase2.setName(StepUpConstants.PHASE4_S2);
		subphase2.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase2);
		Subphase subphase3 = new Subphase();
		subphase3.setName(StepUpConstants.PHASE4_S3);
		subphase3.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase3);
		Subphase subphase4 = new Subphase();
		subphase4.setName(StepUpConstants.PHASE4_S4);
		subphase4.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase4);
		Subphase subphase5 = new Subphase();
		subphase5.setName(StepUpConstants.PHASE4_S5);
		subphase5.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase5);
		Subphase subphase6 = new Subphase();
		subphase6.setName(StepUpConstants.PHASE4_S6);
		subphase6.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase6);
	
		return phase;
	}
	
	static Phase createPhase5(int numberOfWeeks){
		Phase phase = new Phase();
		phase.setName(StepUpConstants.PHASE5);
		phase.initweeksactivity(numberOfWeeks);
		Subphase subphase1 = new Subphase();
		subphase1.setName(StepUpConstants.PHASE5_S1);
		subphase1.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase1);
		Subphase subphase2 = new Subphase();
		subphase2.setName(StepUpConstants.PHASE5_S2);
		subphase2.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase2);
		Subphase subphase3 = new Subphase();
		subphase3.setName(StepUpConstants.PHASE5_S3);
		subphase3.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase3);
		Subphase subphase4 = new Subphase();
		subphase4.setName(StepUpConstants.PHASE5_S4);
		subphase4.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase4);
		Subphase subphase5 = new Subphase();
		subphase5.setName(StepUpConstants.PHASE5_S5);
		subphase5.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase5);
		Subphase subphase6 = new Subphase();
		subphase6.setName(StepUpConstants.PHASE5_S6);
		subphase6.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase6);
		Subphase subphase7 = new Subphase();
		subphase7.setName(StepUpConstants.PHASE5_S7);
		subphase7.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase7);
	
		return phase;
	}
	
	static Phase createPhase6(int numberOfWeeks){
		Phase phase = new Phase();
		phase.setName(StepUpConstants.PHASE6);
		phase.initweeksactivity(numberOfWeeks);
		Subphase subphase1 = new Subphase();
		subphase1.setName(StepUpConstants.PHASE6_S1);
		subphase1.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase1);
		Subphase subphase2 = new Subphase();
		subphase2.setName(StepUpConstants.PHASE6_S2);
		subphase2.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase2);
		Subphase subphase3 = new Subphase();
		subphase3.setName(StepUpConstants.PHASE6_S3);
		subphase3.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase3);
		Subphase subphase4 = new Subphase();
		subphase4.setName(StepUpConstants.PHASE6_S4);
		subphase4.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase4);
		Subphase subphase5 = new Subphase();
		subphase5.setName(StepUpConstants.PHASE6_S5);
		subphase5.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase5);
		Subphase subphase6 = new Subphase();
		subphase6.setName(StepUpConstants.PHASE6_S6);
		subphase6.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase6);
		Subphase subphase7 = new Subphase();
		subphase7.setName(StepUpConstants.PHASE6_S7);
		subphase7.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase7);
		Subphase subphase8 = new Subphase();
		subphase8.setName(StepUpConstants.PHASE6_S8);
		subphase8.initweeksactivity(numberOfWeeks);
		phase.addSubphase(subphase8);
		
		return phase;
	}
	
	
}
