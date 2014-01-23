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
	
	String inquiries_URL = "http://inquiry.wespot.net/services/api/rest/json/?method=user.inquiries&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&oauthId="+principalName+"&oauthProvider="+suffix.replaceAll("_","");
	//out.println(inquiries_URL);
	String result_inquiries = RestClient.doGet(inquiries_URL);
	//out.println(result_inquiries);
	try{
		JSONArray inquiries = new JSONObject(result_inquiries).getJSONArray("result");
	
%>


<%
	//Checking if the user is logged in 
	/*Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
	if (twitter==null) response.sendRedirect("index.jsp");*/
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <title>STEP UP!</title>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="css/jquery.qtip.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.1/jquery.mobile-1.2.1.min.css" />
	<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.2.1/jquery.mobile-1.2.1.min.js"></script> 
	<script src="js/jquery.sparkline.js"></script>
	<script src="js/jquery.tablesorter.min.js"></script>
	<script language="javascript" type="text/javascript" src="js/jquery.jqplot.min.js"></script>
	<script type="text/javascript" src="js/plugins/jqplot.barRenderer.min.js"></script>
	<script type="text/javascript" src="js/plugins/jqplot.categoryAxisRenderer.min.js"></script>
	<script type="text/javascript" src="js/plugins/jqplot.pointLabels.min.js"></script>	
	<script type="text/javascript" src="js/plugins/jqplot.canvasTextRenderer.min.js"></script>
	<script type="text/javascript" src="js/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/jquery.jqplot.css" />

  </head>
  <body>
	<div align="center"></div>	
	<div data-role="header"> 
	<h1>Inquiries available:</h1> 
	</div> 
	
	<% 
	    //out.println("Total inquiries:"+inquiries.length()+"-"+result_inquiries);
		for (int i=0;i<inquiries.length();i++){
			JSONObject inquiry = inquiries.getJSONObject(i);
			%>
			<div><a href="javascript:location.replace('dashboardwespot.jsp?inquiry=<%=inquiry.getString("inquiryId")%>');" data-role="button"><table align="center"><tr><td><img src="<%=inquiry.getString("icon")%>" width="40px" /></td><td><%=inquiry.getString("title")%></td></tr></table></a>
			<%					
		}
	
	}catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}%>

  </body>
</html>