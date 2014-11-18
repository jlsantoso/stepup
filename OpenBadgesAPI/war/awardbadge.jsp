	<%@ page import="org.be.kuleuven.hci.openbadges.persistanceLayer.*"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONObject"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray"%>
<%@ page import="org.be.kuleuven.hci.openbadges.utils.RestClient"%>
<html>
<head>


<link rel="stylesheet" href="css/jquery.mobile-1.4.2.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
		<script>
			$.mobile.selectmenu.prototype.options.hidePlaceholderMenuItems = false;			
		</script>
</head>
<body>
<div data-role="header"> 
			<a href="javascript:location.replace('/menu.jsp?context=<%=request.getParameter("context")%>&inquiryserver=<%=request.getParameter("inquiryserver")%>')" data-icon="back" class="ui-btn-left" title="Go back">Back</a>
			<h1>Award a badge</h1>
</div>
<form action="/awardbadges" method="get" enctype="multipart/form-data" data-ajax="false">
<div class="ui-field-contain">
<input type="hidden" name="context" value="<%=request.getParameter("context")%>">
            <input type="hidden" id="inquiryserver" name="inquiryserver" value="<%=request.getParameter("inquiryserver")%>">
<%JSONObject result = new JSONObject(RestClient.doGet("http://"+request.getParameter("inquiryserver")+"/services/api/rest/json/?method=inquiry.users&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiryId="+request.getParameter("context")));
JSONArray users_list = result.getJSONArray("result");
JSONArray badges_array = new JSONArray(PersistanceLayerBadge.getbadgeByContext(request.getParameter("context"), ""));
%>
<select id="badge" name="badge"> <option>Choose one badge...</option>
<% 
    for (int i=0;i<badges_array.length();i++){
    	JSONObject badge = badges_array.getJSONObject(i);
    	if (!badge.getJSONObject("jsonBadge").has("nameApp") || !badge.getJSONObject("jsonBadge").getString("nameApp").contains("wespot_automatic_badges")){
%>
 			<option value="<%=badge.getString("id")%>"><%=badge.getJSONObject("jsonBadge").getString("name")%></option>
<%
    	}
    }
%>
</select>
<div data-role="fieldcontain">
    <fieldset data-role="controlgroup">
    	<legend>Choose one or more students:</legend>
<%
	
    
    for (int i=0;i<users_list.length();i++){
    	JSONObject user = users_list.getJSONObject(i);
%>
		<label><input id="<%=user.getString("oauthProvider").toLowerCase()%>_<%=user.getString("oauthId").toLowerCase()%>" type="checkbox" name="checkbox-students" value="<%=user.getString("oauthProvider").toLowerCase()%>_<%=user.getString("oauthId").toLowerCase()%>"/> <%=user.getString("name")%> </label>

<%
    }
%>
	</fieldset>
</div>

<br/>
<label for="evidence">Justify why you award the badge:</label><input type="text" id="evidence" name="evidence">
<br/><br/>
	<div style="text-align: right;">
		<input type="submit" value="Award the badge"  data-icon="check" data-iconpos="right" data-inline="true">
	</div>
</div>
</form>
<script>
	var badges = <%=badges_array%>;
	//$('#google_107159181208333521690').prop('disabled', true);
	//$('#google_107159181208333521690').parent().hide();
	var awarded_badges=<%=PersistanceLayerAwardedBadge.getAwardedBadgeByContext(request.getParameter("context"), "1354615295762", "")%>
	var users = <%=users_list%>;
	$( "#badge" )
		.change(function () {			
			  var $this = $(this),
		      val = $this.val();
			  for(var i in users){
				 $('#'+(users[i].oauthProvider).toLowerCase()+'_'+users[i].oauthId).show();
				 $('#'+(users[i].oauthProvider).toLowerCase()+'_'+users[i].oauthId).parent().show();
			  }
			  for(var i in badges){
				  if (val == badges[i].id){
					  var badge_obj = badges[i];
					  for(var j in awarded_badges){
						  if (badge_obj.jsonBadge.name == awarded_badges[j].jsonBadge.badge.name && badge_obj.jsonBadge.description == awarded_badges[j].jsonBadge.badge.description && badge_obj.jsonBadge.criteria == awarded_badges[j].jsonBadge.badge.criteria){
							  console.log("value of the badge: "+val);
							  $("#"+awarded_badges[j].username.toLowerCase()).hide();
							  $("#"+awarded_badges[j].username.toLowerCase()).parent().hide();
						  }
					  }
				  }
			  }				
		  
		});
</script>
</body>
</html>