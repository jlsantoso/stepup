<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.regex.Pattern, java.util.regex.*, twitter4j.Twitter, org.be.kuleuven.hci.aggregationlayer.modelwespot.Course, 
	org.be.kuleuven.hci.aggregationlayer.modelwespot.utils.PersistanceLayer,
	org.be.kuleuven.hci.aggregationlayer.modelwespot.Phase,
	org.be.kuleuven.hci.aggregationlayer.modelwespot.Student,
	org.be.kuleuven.hci.aggregationlayer.modelwespot.Subphase,
	java.util.ArrayList, org.be.kuleuven.hci.aggregationlayer.model.utils.RestClient,
	org.json.JSONArray,org.json.JSONException,org.json.JSONObject"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipalImpl"%>

<%
	String principalName =  request.getRemoteUser();

	session.setAttribute("isActive", true);
	session.setAttribute("principalName", principalName);

	AttributePrincipalImpl principal = (AttributePrincipalImpl) request
			.getUserPrincipal();

	Map<String, Object> attributes = principal.getAttributes();
	

	Iterator attributeNames = attributes.keySet().iterator();
	String suffix = "";
	if (principalName.contains("#")){		
		if (principalName.toLowerCase().contains("google")){
			suffix = "google_";
		}else if (principalName.toLowerCase().contains("twitter")){
			suffix = "twitter_";
		}else if (principalName.toLowerCase().contains("facebook")){
			suffix = "facebook_";
		}else if (principalName.toLowerCase().contains("linkedin")){
			suffix = "linkedin_";
		}
		principalName = principalName.substring(principalName.indexOf("#")+1);		
	}
	
	String inquiries_URL = "http://wespot.kmi.open.ac.uk/services/api/rest/json/?method=user.inquiries&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&oauthId="+principalName+"&oauthProvider="+suffix.replaceAll("_","");
	out.println(inquiries_URL);
	String result_inquiries = RestClient.doGet(inquiries_URL);
	out.println(result_inquiries);

%>
