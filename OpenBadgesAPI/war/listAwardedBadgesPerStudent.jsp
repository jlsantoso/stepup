<%@ page import="org.be.kuleuven.hci.openbadges.persistanceLayer.*"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONObject"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray"%>
<%@ page import="org.be.kuleuven.hci.openbadges.utils.RestClient"%>
<html>
<head>

<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
		<link rel="stylesheet" type="text/css" href="http://jlsantos.tel.unir.net/d3/css/styles.css"/>
		<link rel="stylesheet" href="css/styletable.css" type="text/css" media="screen"/>
		
    	<script language="javascript" type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
</head>
<body>
<div id="header" data-role="header"> 
<h1>Badges</h1>
</div>
<script>
var badges_array = <%=PersistanceLayerBadge.getbadgeByContext(request.getParameter("context"), "")%>;
var badges_awarded_array = <%=PersistanceLayerAwardedBadge.getAwardedBadgeByContext(request.getParameter("context"),"1354615295762", "")%>;
var users = <%=RestClient.doGet("http://"+request.getParameter("inquiryserver")+"/services/api/rest/json/?method=inquiry.users&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiryId="+request.getParameter("context"))%>;
var users_array = users.result;

var opacity = 0.3; 

var count = 0;
var width = $(window).width();
var badge_size = width/15;
var padding = badge_size + (badge_size/10);
var height = 0;

if (badges_array.length<43){
	height = padding * 7;
}

var svg = d3.select("body")
	.append("svg")
	.attr("width", width)
	.attr("height", height);
var manualbadges = 0;

function ycoordinates(imagename){
	if (imagename.indexOf("0phase") > -1) return 0;
	else if (imagename.indexOf("1phase") > -1) return padding*1;
	else if (imagename.indexOf("2phase") > -1) return padding*2;
	else if (imagename.indexOf("3phase") > -1) return padding*3;
	else if (imagename.indexOf("4phase") > -1) return padding*4;
	else if (imagename.indexOf("5phase") > -1) return padding*5;
	else if (imagename.indexOf("6phase") > -1) return padding*6;
	else{
		var row = Math.floor( manualbadges / 3);
		return padding * row;
	}
}

function xcoordinates(imagename, type){
	if (imagename.indexOf("phase1") > -1) return padding*1;
	else if (imagename.indexOf("phase2") > -1) return padding*2;
	else if (imagename.indexOf("phase3") > -1) return padding*3;
	else {		
		var mod = manualbadges % 3;
		mod = mod + 5;
		//type is 1 when we finish to draw the badge, the text and the person icon
		manualbadges=+type;
		return padding*mod
	}
}

function awardedBadges(badge){
	var badgesNumber = 0;
	badges_awarded_array.forEach(function(d) {
		if ((d.jsonBadge.badge.image == badge.jsonBadge.image)&&
				(d.jsonBadge.badge.name == badge.jsonBadge.name)&&
				(d.jsonBadge.badge.description == badge.jsonBadge.description)){
			badgesNumber++;
		}
	});
	return badgesNumber;
	
}

function awardedBadgesNames(badge){
	var usernames = "";
	badges_awarded_array.forEach(function(d) {
		if ((d.jsonBadge.badge.image == badge.jsonBadge.image)&&
				(d.jsonBadge.badge.name == badge.jsonBadge.name)&&
				(d.jsonBadge.badge.description == badge.jsonBadge.description)){
				users_array.forEach(function(data) {
					var user_id=data.oauthProvider.toLowerCase()+'_'+data.oauthId.toLowerCase();
					if ((d.username.toLowerCase() == user_id)){
						usernames += data.name+'<br/> ';
					}
				});
		}
	});
	return usernames;
	
}

function calculateopacity(badge){
	var opac = 0.3
	badges_awarded_array.forEach(function(d) {
		if ((d.jsonBadge.badge.image == badge.jsonBadge.image)&&
				(d.jsonBadge.badge.name == badge.jsonBadge.name)&&
				(d.jsonBadge.badge.description == badge.jsonBadge.description)&&
				(d.username.toLowerCase() == user_elgg)){
				opac = 1;		
		}
	});	
	return opac;
}

</script>
<div data-role="body"> 
<script>

var height_header =  parseInt(d3.select("#header").style("height").replace("px",""));
var x = 0;
var y = 0;

var tooltip = d3.select("body")
	.append("div")
	.style("position", "absolute")
	.style("z-index", "10")
	.style("visibility", "hidden")
	.html("A simple");
svg.selectAll("img")
	.data(badges_array.sort(function(a,b) { 
			return d3.ascending(a.jsonBadge.image, b.jsonBadge.image);
		}))
		.enter()
		.append("image")
		.attr("xlink:href", function(d) {
			return d.jsonBadge.image;
		})
		.attr("x", function (d){
			return xcoordinates(d.jsonBadge.image, 0);
		})
		.attr("y", function (d){
			return ycoordinates(d.jsonBadge.image);
		})
		.attr("width", badge_size)
		.attr("height", badge_size)
		.attr("opacity", function (d) {
			return calculateopacity(d);
		})
		.on("mouseover", function (d) {
            d3.select(this)
                    .attr("width", badge_size*1.05)
                    .attr("height", badge_size*1.05)
                    .attr("opacity", "1");

            tooltip.html('<table class="table3"><tbody><tr><th scope="row">Name</th><td>'+d.jsonBadge.name+'</td></tr><tr><th scope="row">Description</th><td>'+d.jsonBadge.description+'</td></tr><tr><th scope="row">Criteria</th><td>'+d.jsonBadge.criteria+'</td></tr></tbody></table>');
            tooltip.style("visibility", "visible");
            var x_badge = parseInt(d3.select(this).attr("x"))+parseInt(badge_size);
            //var y_badge = parseInt(d3.select(this).attr("y"))+(parseInt(badge_size)/2);
            var y_badge = parseInt(d3.select(this).attr("y"))+height_header;
            //alert(x_badge+"-"+y_badge);
            tooltip.style("top", (y_badge)+"px").style("left",(x_badge)+"px");

            })
        .on("mouseout", function (d) {
            d3.select(this)
                    .attr("opacity", function (d) {
            			return calculateopacity(d);
            		})
                    .attr("width", badge_size)
                    .attr("height", badge_size);
            tooltip.style("visibility", "hidden");});
svg.selectAll("img")
	.data(badges_array.sort(function(a,b) { 
			return d3.ascending(a.jsonBadge.image, b.jsonBadge.image);
		}))
		.enter()
		.append("image")
		.attr("xlink:href", function(d) {
			return 'https://cdn1.iconfinder.com/data/icons/dot/256/man_person_mens_room.png';
		})
		.attr("x", function (d){
			return xcoordinates(d.jsonBadge.image, 0)+badge_size/1.2;
		})
		.attr("y", function (d){
			return ycoordinates(d.jsonBadge.image);
		})
		.attr("width", badge_size/5)
		.attr("height", badge_size/5)
		.attr("opacity", 0.5);
svg.selectAll("text")
	.data(badges_array.sort(function(a,b) { 
		return d3.ascending(a.jsonBadge.image, b.jsonBadge.image);
	}))
	.enter()
	.append("text")
	.attr("x", function (d){
		return xcoordinates(d.jsonBadge.image, 1)+badge_size;
	})
	.attr("y", function (d){
		return ycoordinates(d.jsonBadge.image)+badge_size/6;
	})
	.attr('font-family', 'FontAwesome')
	.attr('font-style', 'bold')
	.attr('fill', 'black')
	.attr('font-size', function(d) { return badge_size/5;} )
	.attr("opacity", 0.5)
	.text(function(d) {return awardedBadges(d);}); 
//.on("mousemove", function(){return tooltip.style("top", (event.pageY-10)+"px").style("left",(event.pageX+10)+"px");})
		

</script>
</div>

</body>
</html>