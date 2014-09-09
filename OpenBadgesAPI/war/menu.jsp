<%@ page import="org.be.kuleuven.hci.openbadges.persistanceLayer.*"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONObject"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray"%>
<%@ page import="org.be.kuleuven.hci.openbadges.utils.RestClient"%>
<html>
<head>
	<script language="javascript" type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
	<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
	<script src="js/utils.js"></script>
	<script src="js/svg.js"></script>
	<link rel="stylesheet" type="text/css" href="http://jlsantos.tel.unir.net/d3/css/styles.css"/>
	<link rel="stylesheet" href="css/styletable.css" type="text/css" media="screen"/>
	<link rel="stylesheet" href="css/jquery.mobile-1.4.2.css">    	
</head>
<body>
	<div id="header" data-role="header"> 
		<div data-role="controlgroup" data-type="horizontal" class="ui-btn-right">
			<a href="javascript:location.replace('/createbadge.jsp?context=<%=request.getParameter("context")%>&inquiryserver=<%=request.getParameter("inquiryserver")%>');" data-role="button" data-icon="plus"  title="Create new badge">Create new badge</a>
			<a href="javascript:location.replace('/awardbadge.jsp?context=<%=request.getParameter("context")%>&inquiryserver=<%=request.getParameter("inquiryserver")%>');" data-role="button" data-icon="star"  title="Award badge">Award badge</a>
		</div>
		<h1>Badges</h1>
	</div>
	<div data-role="body"> 
		<p/>
<script>
	var badges_array = <%=PersistanceLayerBadge.getbadgeByContext(request.getParameter("context"), "")%>;
	
	if (badges_array.length>0){
		var badges_awarded_array = <%=PersistanceLayerAwardedBadge.getAwardedBadgeByContext(request.getParameter("context"),"1354615295762", "")%>;
		var users = <%=RestClient.doGet("http://"+request.getParameter("inquiryserver")+"/services/api/rest/json/?method=inquiry.users&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiryId="+request.getParameter("context"))%>;
		var users_array = users.result;
		var count = 0;
		var width = $(window).width();
		var badge_size = width/15;
		var padding = badge_size + (badge_size/10);
		var height = 0;
		var manualrows = 0;
		var manualbadges_total = 0; 
		var shift_manual = 0;
		var shift_automatic = 0;
		updateManualBadgesValues();
		updateHeightValue();	
	

		var manualbadges = 0;
	
		var svg = createSVG();
	
		var height_header =  parseInt(d3.select("#header").style("height").replace("px",""));
		var x = 0;
		var y = 0;
		var oppacity = 0.3; 
		var tooltip = createTooltip();
		
		var height_title = badge_size / 4;
		var size_text = badge_size / 7;
		
		writeExplanations();
		
		generateBadgeImages();
		
		generateBadgeIcons();
		
		generateBadgeNumbers();
		
	}else{
		$("p").text("Please select an inquiry");
	}
</script>
</div>

</body>
</html>