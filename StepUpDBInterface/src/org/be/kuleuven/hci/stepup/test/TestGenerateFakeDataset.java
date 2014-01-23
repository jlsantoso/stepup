package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.HttpException;
import org.be.kuleuven.hci.stepup.utils.RestClient;
import org.be.kuleuven.hci.stepup.utils.StepUpConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class TestGenerateFakeDataset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i=0;i<10;i++){
			try {
				String courset = "6816";
				String username = "google_10593913955110847352"+i;
				JSONObject event = new JSONObject();
				event.put("username", username);
				event.put("verb", "fake");
				event.put("object", "fake events in different phases");
				
				JSONObject course = new JSONObject();
				if (getRandomBoolean()){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S1);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S2);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S3);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S4);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S5);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S6);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S7);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S8);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE1);
				course.put("subphase", StepUpConstants.PHASE1_S9);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE2);
				course.put("subphase", StepUpConstants.PHASE2_S1);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE2);
				course.put("subphase", StepUpConstants.PHASE2_S2);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE2);
				course.put("subphase", StepUpConstants.PHASE2_S3);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE2);
				course.put("subphase", StepUpConstants.PHASE2_S4);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE2);
				course.put("subphase", StepUpConstants.PHASE2_S5);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE2);
				course.put("subphase", StepUpConstants.PHASE2_S6);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE2);
				course.put("subphase", StepUpConstants.PHASE2_S7);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S1);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S2);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S3);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S4);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S5);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S6);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S7);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S8);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S9);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE3);
				course.put("subphase", StepUpConstants.PHASE3_S10);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE4);
				course.put("subphase", StepUpConstants.PHASE4_S1);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE4);
				course.put("subphase", StepUpConstants.PHASE4_S2);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE4);
				course.put("subphase", StepUpConstants.PHASE4_S3);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE4);
				course.put("subphase", StepUpConstants.PHASE4_S4);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE4);
				course.put("subphase", StepUpConstants.PHASE4_S5);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE4);
				course.put("subphase", StepUpConstants.PHASE4_S6);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE5);
				course.put("subphase", StepUpConstants.PHASE5_S1);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE5);
				course.put("subphase", StepUpConstants.PHASE5_S2);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE5);
				course.put("subphase", StepUpConstants.PHASE5_S3);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE5);
				course.put("subphase", StepUpConstants.PHASE5_S4);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE5);
				course.put("subphase", StepUpConstants.PHASE5_S5);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE5);
				course.put("subphase", StepUpConstants.PHASE5_S6);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE5);
				course.put("subphase", StepUpConstants.PHASE5_S7);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE6);
				course.put("subphase", StepUpConstants.PHASE6_S1);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE6);
				course.put("subphase", StepUpConstants.PHASE6_S2);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE6);
				course.put("subphase", StepUpConstants.PHASE6_S3);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE6);
				course.put("subphase", StepUpConstants.PHASE6_S4);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE6);
				course.put("subphase", StepUpConstants.PHASE6_S5);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE6);
				course.put("subphase", StepUpConstants.PHASE6_S6);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE6);
				course.put("subphase", StepUpConstants.PHASE6_S7);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				if (getRandomBoolean()){
				course = new JSONObject();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, getRandomWeek());
				event.put("starttime", formatter.format(c.getTime()));
				course.put("course", courset);
				course.put("phase", StepUpConstants.PHASE6);
				course.put("subphase", StepUpConstants.PHASE6_S8);
				event.put("context", course);
				System.out.println(event.toString());System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", event.toString()));
				}
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static boolean getRandomBoolean() {
	       return Math.random() < 0.1;
	       //I tried another approaches here, still the same result
	}
	
	public static int getRandomWeek() {
	       double value = Math.random();
	       if (value>=0 && value<0.25){
	    	   return -7;
	       }
	       if (value>=0.25 && value<0.5){
	    	   return -14;
	       }
	       if (value>=0.5 && value<0.75){
	    	   return -28;
	       }
	       else{
	    	   return -32;
	       }
	       //I tried another approaches here, still the same result
	}

}
