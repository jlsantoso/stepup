<%@ page import="org.be.kuleuven.hci.openbadges.persistanceLayer.*"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONObject"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray"%>
<%@ page import="org.be.kuleuven.hci.openbadges.utils.RestClient"%>
<html>
<head>


<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
</head>
<body>
<form action="/awardbadges" method="get" enctype="multipart/form-data" data-ajax="false">
<div class="ui-field-contain">
<input type="hidden" name="context" value="<%=request.getParameter("context")%>">
<%JSONObject result = new JSONObject(RestClient.doGet("http://inquiry.wespot.net/services/api/rest/json/?method=inquiry.users&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiryId="+request.getParameter("context")));
JSONArray users_list = result.getJSONArray("result");
JSONArray badges_array = new JSONArray(PersistanceLayerBadge.getbadgeByContext(request.getParameter("context"), ""));
%>
<select id="student" name="student" >
<%
	
    
    for (int i=0;i<users_list.length();i++){
    	JSONObject user = users_list.getJSONObject(i);
%>
 		<option value="<%=user.getString("oauthProvider").toLowerCase()%>_<%=user.getString("oauthId").toLowerCase()%>"><%=user.getString("name")%>	</option><br/>
<%
    }
%>
</select>
<select id="badge" name="badge">
<% 
    for (int i=0;i<badges_array.length();i++){
    	JSONObject badge = badges_array.getJSONObject(i);
%>
 		<option value="<%=badge.getString("id")%>"><%=badge.getJSONObject("jsonBadge").getString("name")%></option>
<%
    }
%>
</select>
<br/>
<label for="evidence">Justify why you award the badge:</label><input type="text" id="evidence" name="evidence">
<br/><br/>
<input type="submit" value="Award the badge"  data-icon="check" data-iconpos="right" data-inline="true">
</div>
</form>
	
</body>
</html>