package org.be.kuleuven.hci.stepup.test;

import javax.ws.rs.PathParam;

import org.be.kuleuven.hci.stepup.controller.EventController;

public class TestReplaceString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String result = "\\\"";
		System.out.println(result);
		result = result.replaceAll("\\\\\"", "\"");
		System.out.println(result);*/
		
		//          getEventsByInquiryId("chikul19","{ \"pag\": \"0\"}");
		
	}
	
	/*public static String getEventsByInquiryId(String inquiryid, String json) {	
		String result = EventController.getEventsByInquiryId(inquiryid, json);
		if (result.contains("\"subphase\":\"ARLearn\""))
			result = result.replaceAll("\\\\\"", "\"").replaceAll("\"\\{", "{").replaceAll("}\"", "}");
		else{
			result = result.replaceAll("\\\\\\\\\\\\", "\\\\\\\\");
			result = result.replaceAll("\\\\\"", "\"").replaceAll("\"\\{", "{").replaceAll("}\"", "}");
		}
			
		return result;
	} */

}
