<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="twitter4j.Twitter, org.be.kuleuven.hci.aggregationlayer.model.Course, 
	org.be.kuleuven.hci.aggregationlayer.model.utils.PersistanceLayer,
	org.be.kuleuven.hci.aggregationlayer.model.Blog,
	org.be.kuleuven.hci.aggregationlayer.model.Group,
	org.be.kuleuven.hci.aggregationlayer.model.Student,
	org.be.kuleuven.hci.aggregationlayer.model.Activity,
	java.util.ArrayList"%>


<%
	//Checking if the user is logged in 
	Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
	if (twitter==null) response.sendRedirect("index.jsp");
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <title>STEP UP!</title>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="css/jquery.qtip.css" type="text/css" media="print, projection, screen" />
	<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="js/jquery.qtip.min.js"></script> 
	<script src="js/jquery.sparkline.js"></script>
	<script src="js/jquery.tablesorter.min.js"></script>
	<script language="javascript" type="text/javascript" src="js/jquery.jqplot.min.js"></script>
	<script type="text/javascript" src="js/plugins/jqplot.barRenderer.min.js"></script>
	<script type="text/javascript" src="js/plugins/jqplot.categoryAxisRenderer.min.js"></script>
	<script type="text/javascript" src="js/plugins/jqplot.pointLabels.min.js"></script>	
	<script type="text/javascript" src="js/plugins/jqplot.canvasTextRenderer.min.js"></script>
	<script type="text/javascript" src="js/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/jquery.jqplot.css" />
	
    <script type="text/javascript">
   		var email = '<%=twitter.getScreenName()%>';
   		var filterColumns = "";
   		var filterRows = "";
		<%
		//Loading course info
		Course course = PersistanceLayer.getCourse("bigtablechikul13");
		if (course!=null){
			ArrayList<Blog> blogs = course.getBlogs();
			ArrayList<Student> externals = course.getExternals();
		
		%>
			var tableVar = new Array(<%=course.getStudents().size()+1%>);
			var tableExtVar = new Array(<%=externals.size()%>);
			for (var i = 0; i < <%=course.getStudents().size()+1%>; i++) {
				tableVar[i] = new Array(<%=(blogs.size()+6)%>);				
			}
			for (var i = 0; i < <%=externals.size()+1%>; i++) {
				tableExtVar[i] = new Array(<%=(blogs.size()+6)%>);				
			}
			//alert(<%=course.getStudents().size()%>+'-'+<%=(blogs.size()+6)%>);
			
			tableVar[0][0] = "<table id=\"tableBlogs\" class=\"tablesorter\" width=\"80%\"><thead><tr><th>Authors</th>";
			tableExtVar[0][0] = "<table id=\"tableBlogs_ext\" class=\"tablesorter\" width=\"80%\"><thead><tr><th>Externals</th>";	
			var column = 0;
			<%//Loading blogs				
				for (Blog b: blogs){
					%>					
					tableVar[0][++column]="<th><a href=\"<%=b.getUrl()%>\"><%=b.getName()%></a></th>";
					tableExtVar[0][column]="<th><a href=\"<%=b.getUrl()%>\"><%=b.getName()%></a></th>";
					i++;
					<%
				}
			%>
			tableVar[0][++column] = "<th>Total</th>";
			tableExtVar[0][column] = "<th>Total</th>";
			tableVar[0][++column] = "<th><a href=\"http://twitter.com\">Twitter</a></th>";;
			tableExtVar[0][column] = "<th><a href=\"http://twitter.com\">Twitter</a></th>";
			tableVar[0][++column] = "<th><a href=\"http://navi-hci.appspot.com/badgeboard\">Navi</a></th>";
			tableExtVar[0][column] = "<th>Soc. Net.</th><tbody>";
			tableVar[0][++column] = "<th>Soc. Net.</th>";
			tableVar[0][++column] = "<th><a href=\"http://navi-hci.appspot.com/badgeboard\">Badges</a></th><tbody>";

		    var tableFinal = "";
		 	<%//Loading Groups
		 		ArrayList<Group> groups = course.getGroups();
		 		int groupNumber = 0;
		 		%>
		 		var row = 0;
		 		<%
		 		for (Group g: groups){
		 			String colorGroup ="#FAFAFA";
		 			//String redColor = "#FFF6ED";
		 			String redColor = "#FAFAFA";
		 			String tweetColor = "#EBFFFB";
		 			String postColor = "#FFEBD5";
		 			String commentsColor = "EBF9FF";
		 			String badgesColor = "F5DA81";
		 			if (groupNumber % 2 ==1){
		 				colorGroup="#E6E6E6";
		 				//redColor = "#FFD8D8";
		 				redColor = "#E6E6E6";
		 				tweetColor = "#D4FFF7";
		 				postColor = "#FFDFBD";
		 				commentsColor = "CAF0FF";
		 				badgesColor = "FFBF00";
		 			}
		 			groupNumber++;
		 			ArrayList<Student> students = g.getStudents();
		 			for (Student s : students){		 				
		 				ArrayList<Integer> postBlogActivity = s.getPostActivity();
		 				ArrayList<Integer> commentBlogActivity = s.getCommentActivity();	
		 				%>
		 				column = 0;
		 				//alert(row);
		 				tableVar[++row][column]="<tr bgcolor=\"<%=colorGroup%>\"><td align=\"left\" ><%=s.getUsername()%></td>";
		 				<%
		 				int commentsTotal = 0;
		 				String color = "";
		 				for (int i=0;i<postBlogActivity.size();i++){		 					
			 				if (g.getFirstBlog().equals(blogs.get(i))){
			 				// POSTS OF THE USER
			 					if (postBlogActivity.get(i)==0) color = redColor;
			 					else color = postColor;
			 					commentsTotal+=commentBlogActivity.get(i); //Total comments also includes the comments done in their own blog
			 					%>
			 					tableVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+""+i);%>\"><%=postBlogActivity.get(i)%></td>";
			 					<%
			 				}else{
			 				// COMMENTS OF THE USER
			 					commentsTotal+=commentBlogActivity.get(i);
			 					if (commentBlogActivity.get(i)==0) color = redColor;
			 					else color = commentsColor;
			 					%>
			 					tableVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+""+i);%>\"><%=commentBlogActivity.get(i)%></td>";
			 					<%
			 				}
			 				
		 				}
		 				// TOTAL COMMENTS OF THE USER
		 				if (commentsTotal==0) color = redColor;
	 					else color = commentsColor;
		 				%>
		 					tableVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+"Total");%>\"><%=commentsTotal%></td>";
		 				
		 				<%
		 				// TWEETS
		 				int tweets = s.getTweetActivity();
		 				if (tweets==0) color = redColor;
	 					else color = tweetColor;%>
	 					tableVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+"twitter");%>\"><%=tweets%></td>";
	 					
	 					//BADGES
	 					<%int badges = s.getBadgeActivity();
		 				if (badges==0) color = redColor;
	 					else color = badgesColor;%>
	 					tableVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+"badges");%>\"><%=badges%></td>";
	 					<%
	 					//PRINT SPARKLINE REGARDING SOCIAL NETWORK
	 					ArrayList<Integer> postsweeks = s.getPostsActivityPerWeek();
	 					ArrayList<Integer> commentsweeks = s.getCommentsActivityPerWeek();
	 					ArrayList<Integer> tweetsweeks = s.getTweetsActivityPerWeek();
	 					%>
	 					tableVar[row][++column]="<td id =\"sparkline<%out.print(s.getUsername()+"social");%>\">";
	 					<%
	 					for (int i=0;i<tweetsweeks.size()-1;i++){
	 						int sum = tweetsweeks.get(i)+commentsweeks.get(i)+postsweeks.get(i);
	 						out.print("tableVar[row][column]+="+sum+"+\",\";");
	 					}
	 					int sum = tweetsweeks.get(tweetsweeks.size()-1)+commentsweeks.get(tweetsweeks.size()-1)+postsweeks.get(tweetsweeks.size()-1);
 						out.print("tableVar[row][column]+="+sum+";");
 						%>
 						tableVar[row][column]+="</td>";
 						
 						<%
	 					//PRINT SPARKLINE REGARDING SOCIAL NETWORK
	 					ArrayList<Integer> badgesweeks = s.getBadgeActivityPerWeek();
	 					%>
	 					tableVar[row][++column]="<td id =\"sparkline<%out.print(s.getUsername()+"badgeact");%>\">";
	 					<%
	 					for (int i=0;i<badgesweeks.size()-1;i++){
	 						sum = badgesweeks.get(i);
	 						out.print("tableVar[row][column]+="+sum+"+\",\";");
	 					}
	 					
	 					sum = badgesweeks.get(tweetsweeks.size()-1);
 						out.print("tableVar[row][column]+="+sum+";");
 						%>
 						tableVar[row][column]+="</td>";
 						
 						tableVar[row][column]+="</tr>";
		 				<%
		 			}
		 		}
		 	%>

			//table += tableFinal;
		    /*table+= "</tbody>";
			table+="</tr></thead>";
		    table+="</table>";*/
		    
		    row = 0;
		    
		    <%//Loading Groups
	 		
	 			
	 			for (Student s : externals){
	 				%>
	 				column = 0;
	 				<%
	 				ArrayList<Integer> postBlogActivity = s.getPostActivity();
	 				ArrayList<Integer> commentBlogActivity = s.getCommentActivity();	
	 				%>
	 				//alert(row);
	 				tableExtVar[++row][column]="<tr><td align=\"left\" ><%=s.getUsername()%></td>";
	 				<%
	 				int commentsTotal = 0;
	 				String color = "";
	 				for (int i=0;i<postBlogActivity.size();i++){		 					
		 					// COMMENTS OF THE USER
		 					commentsTotal+=commentBlogActivity.get(i);
		 					if (commentBlogActivity.get(i)==0) color = "#FFF6ED";
		 					else color = "#EBF9FF";
		 					%>
		 					tableExtVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+""+i);%>\"><%=commentBlogActivity.get(i)%></td>";
		 					<%
		 						 				
	 				}
	 				// TOTAL COMMENTS OF THE USER
	 				if (commentsTotal==0) color = "#FFF6ED";
 					else color = "#EBF9FF";
	 				%>
	 				tableExtVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+"Total");%>\"><%=commentsTotal%></td>";
	 				<%int tweets = s.getTweetActivity();
	 				if (tweets==0) color = "#FFF6ED";
 					else color = "#EBFFFB";%>
 					tableExtVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+"twitter");%>\"><%=tweets%></td>";
 					
 					
 					<%
 					//PRINT SPARKLINE REGARDING SOCIAL NETWORK
 					ArrayList<Integer> postsweeks = s.getPostsActivityPerWeek();
 					ArrayList<Integer> commentsweeks = s.getCommentsActivityPerWeek();
 					ArrayList<Integer> tweetsweeks = s.getTweetsActivityPerWeek();
 					%>
 					tableExtVar[row][++column]="<td id =\"sparkline<%out.print(s.getUsername()+"social");%>\">";
 					<%
 					for (int i=0;i<tweetsweeks.size()-1;i++){
 						int sum = tweetsweeks.get(i)+commentsweeks.get(i)+postsweeks.get(i);
 						out.print("tableExtVar[row][column]+="+sum+"+\",\";");
 					}
 					int sum = tweetsweeks.get(tweetsweeks.size()-1)+commentsweeks.get(tweetsweeks.size()-1)+postsweeks.get(tweetsweeks.size()-1);
						out.print("tableExtVar[row][column]+="+sum+";");
						%>
						tableExtVar[row][column]+="</td>";
						
						tableExtVar[row][column]+="</tr>";
	 				
	 				<%
	 				
	 			}
		    
	 		%>
		    //PRINT TABLES OF STUDENTS AND EXTERNALS
		    function print_tables(){
		    	var tableString = "";
		    	for (var i=0;i<=<%=course.getStudents().size()%>;i++){
		    		for(var j=0;j<<%=(blogs.size()+6)%>;j++){
		    			tableString += tableVar[i][j];		    			
		    		}
		    	}
		    	tableString+="</tbody></tr></thead></table>";
		    	//alert(tableString);
		    	$("<p>").append(tableString).appendTo("#tables");
		    	tableString = "";
		    	for (var i=0;i<=<%=externals.size()%>;i++){		    		
		    		for(var j=0;j<<%=(blogs.size()+4)%>;j++){
		    			//alert(i+'-'+j+'-'+tableExtVar[i][j]);
		    			tableString += tableExtVar[i][j];		    			
		    		}
		    	}
		    	tableString+="</tbody></tr></thead></table>";
		    	$("<p>").append(tableString).appendTo("#tables_ext");
		    }
		  //PRINT TABLES OF STUDENTS AND EXTERNALS WITH filters
		    function print_tables_filters(){
		    	var tableString = "";
		    	for (var i=0;i<=<%=course.getStudents().size()%>;i++){
		    		if (filterRows.indexOf(i+";") ==-1){
			    		for(var j=0;j<<%=(blogs.size()+6)%>;j++){
			    			if (filterColumns.indexOf(j+";") ==-1)
			    				tableString += tableVar[i][j];		    			
			    		}
		    		}
		    	}
		    	tableString+="</tbody></tr></thead></table>";
		    	//alert(tableString);
		    	//$("<p>").append(tableString).appendTo("#tables");
		    	$("#tables").html(tableString);
		    	applyJQueryPlugins();
		    	tableString = "";
		    }
		    
		    //CHANGE STYLE OF THE FILTERS	
		    function enableDisableGraphColumn(guid)
			{
				if($("#blog"+guid).attr("data-enabled") == "true")
				{	
					filterColumns += guid + ';';
					$("#blog"+guid).attr("data-enabled","false");
					$("#blog"+guid).attr("style","border:solid 0px black; display: inline-block;margin-left: 20px;");
					print_tables_filters();
				}
				else
				{
					filterColumns = filterColumns.replace(guid + ';', '');
					$("#blog"+guid).attr("data-enabled","true");
					$("#blog"+guid).attr("style","border:solid 1px black; display: inline-block;margin-left: 20px;");	
					print_tables_filters();
				}
			}
		    
		    function enableDisableGraphRow(guid)
			{
				if($("#user"+guid).attr("data-enabled") == "true")
				{	
					filterRows += guid + ';';
					$("#user"+guid).attr("data-enabled","false");
					$("#user"+guid).attr("style","border:solid 0px black; display: inline-block;margin-left: 20px;margin-top: 10px;");
					print_tables_filters();
				}
				else
				{
					filterRows = filterColumns.replace(guid + ';', '');
					$("#user"+guid).attr("data-enabled","true");
					$("#user"+guid).attr("style","border:solid 2px blue; display: inline-block;margin-left: 20px;margin-top: 10px;");	
					print_tables_filters();
				}
			}
		    
		    //PRINT FILTERS ON THE TOP
		    function print_filterCol(){
		    	var filter = "";
		    	<%//Loading blogs	
		    	int column=0;
				for (Blog b: blogs){
					column++;
					%>					
					filter += "<a href=\"javascript:enableDisableGraphColumn('<%=column%>')\">";
					filter += "<div id=\"blog<%=column%>\" data-enabled=\"true\" style=\"border:solid 1px black; display: inline-block;margin-left: 20px;\"><%=b.getName()%></div></a>";
					<%
				}
				%>
				$("<p>").append(filter).appendTo("#filtersCol");
		    }
		    
		    function print_filterRow(){
		    	var filter = "";
		    	<%//Loading blogs	
		    	int row=0;
		    	for (Group g: groups){
		 			ArrayList<Student> students = g.getStudents();
		 			for(Student s :students){
						row++;
						%>					
						filter += "<a href=\"javascript:enableDisableGraphRow('<%=row%>')\">";
						filter += "<div id=\"user<%=row%>\" data-enabled=\"true\" style=\"border:solid 1px black; display: inline-block;margin-left: 20px;margin-top: 10px;\"><%=s.getUsername()%></div></a>";
						<%
		 			}
				}
				%>
				$("<p>").append(filter).appendTo("#filtersRow");
		    }
		    
		    <%
		    ArrayList<Student> studentsBarCharts = course.getStudents();
		    for (Student s:studentsBarCharts){
		    	%>
		    	function <%=s.getUsername()%>socialBarChart(){
		    		var s1=new Array();// = [200, 600, 700, 1000];
		    		var s2=new Array();
		    		var s3=new Array();
		    		var ticks=new Array();// = ['May', 'June', 'July', 'August'];

		    		<%//for (int i=0;i<course.getWeeks().size();i++){
		    		int count = 0;	
		    		for (int i=course.getWeeks().size()-1;i>=0;i--){
		    			out.println("ticks["+count+"]='W'+"+course.getWeeks().get(i)+";");
		    			out.println("s1["+count+"]="+s.getPostsActivityPerWeek().get(i)+";");
		    			out.println("s2["+count+"]="+s.getCommentsActivityPerWeek().get(i)+";");
		    			out.println("s3["+count+"]="+s.getTweetsActivityPerWeek().get(i)+";");
		    			count++;									
		    		}%>

		    	    // Can specify a custom tick Array.
		    	    // Ticks should match up one for each y value (category) in the series.
		    		$('#chartdiv<%=s.getUsername()%>').html('');
		    	    var plot1 = $.jqplot('chartdiv<%=s.getUsername()%>', [s1,s2,s3], {
		    	        // The "seriesDefaults" option is an options object that will
		    	        // be applied to all series in the chart.
		    			stackSeries: true,
		    	        seriesDefaults:{
		    	            renderer:$.jqplot.BarRenderer,
		    	            rendererOptions: {fillToZero: true}
		    	        },
		    	        // Custom labels for the series are specified with the "label"
		    	        // option on the series option.  Here a series option object
		    	        // is specified for each series.
		    	        series:[
		    	            {label:'posts'},
		    				{label:'comments'},
		    				{label:'tweets'}
		    	        ],
		    	        // Show the legend and put it outside the grid, but inside the
		    	        // plot container, shrinking the grid to accomodate the legend.
		    	        // A value of "outside" would not shrink the grid and allow
		    	        // the legend to overflow the container.
		    	        legend: {
		    	            show: true,
		    	            placement: 'outsideGrid'
		    	        },
		    	        axes: {
		    	            // Use a category axis on the x axis and use our custom ticks.
		    	            xaxis: {
		    	                renderer: $.jqplot.CategoryAxisRenderer,
		    	                ticks: ticks
		    	            },
		    	            // Pad the y axis just a little so bars can get close to, but
		    	            // not touch, the grid boundaries.  1.2 is the default padding.
		    	            yaxis: {
		    	            	padMin: 0,
		    	            	pad: 1.05,
		    	                tickOptions: {formatString: '%d'}
		    	            }
		    	        }
		    	    });
		    	}
		    	
		    	function <%=s.getUsername()%>badgeBarChart(){
		    		var s1=new Array();// = [200, 600, 700, 1000];
		    		var ticks=new Array();// = ['May', 'June', 'July', 'August'];
		    	
		    		<%//for (int i=0;i<course.getWeeks().size();i++){
		    		count = 0;	
		    		for (int i=course.getWeeks().size()-1;i>=0;i--){
		    			out.println("ticks["+count+"]='W'+"+course.getWeeks().get(i)+";");
		    			out.println("s1["+count+"]="+s.getBadgeActivityPerWeek().get(i)+";");
		    			count++;									
		    		}%>
		    	
		    	    // Can specify a custom tick Array.
		    	    // Ticks should match up one for each y value (category) in the series.
		    		$('#chartdiv<%=s.getUsername()%>badgechart').html('');
		    		
		    	    var plot1 = $.jqplot('chartdiv<%=s.getUsername()%>badgechart', [s1], {
		    	        // The "seriesDefaults" option is an options object that will
		    	        // be applied to all series in the chart.
		    			stackSeries: true,
		    	        seriesDefaults:{
		    	            renderer:$.jqplot.BarRenderer,
		    	            rendererOptions: {fillToZero: true}
		    	        },
		    	        // Custom labels for the series are specified with the "label"
		    	        // option on the series option.  Here a series option object
		    	        // is specified for each series.
		    	        series:[
		    	            {label:'badges'},
		    	        ],
		    	        // Show the legend and put it outside the grid, but inside the
		    	        // plot container, shrinking the grid to accomodate the legend.
		    	        // A value of "outside" would not shrink the grid and allow
		    	        // the legend to overflow the container.
		    	        legend: {
		    	            show: true,
		    	            placement: 'outsideGrid'
		    	        },
		    	        axes: {
		    	            // Use a category axis on the x axis and use our custom ticks.
		    	            xaxis: {
		    	                renderer: $.jqplot.CategoryAxisRenderer,
		    	                ticks: ticks
		    	            },
		    	            // Pad the y axis just a little so bars can get close to, but
		    	            // not touch, the grid boundaries.  1.2 is the default padding.
		    	            yaxis: {
		    	            	padMin: 0,
		    	                pad: 1.05,
		    	                tickOptions: {formatString: '%d'}
		    	            }
		    	        }
		    	    });
		    	}
		    <%}%>
		    <%
		    ArrayList<Student> studentsLineCharts = course.getStudents();
		    for (Student s:studentsLineCharts){
		    	%>
		    	function <%=s.getUsername()%>socialLineChart(){
		    		var s1=new Array();// = [200, 600, 700, 1000];
		    		var s2=new Array();
		    		var s3=new Array();
		    		var ticks=new Array();// = ['May', 'June', 'July', 'August'];

		    		<%//for (int i=0;i<course.getWeeks().size();i++){
		    		int count = 0;	
		    		for (int i=course.getWeeks().size()-1;i>=0;i--){
		    			out.println("ticks["+count+"]='W'+"+course.getWeeks().get(i)+";");
		    			out.println("s1["+count+"]="+s.getPostsActivityPerWeek().get(i)+";");
		    			out.println("s2["+count+"]="+s.getCommentsActivityPerWeek().get(i)+";");
		    			out.println("s3["+count+"]="+s.getTweetsActivityPerWeek().get(i)+";");
		    			count++;									
		    		}%>

		    	    // Can specify a custom tick Array.
		    	    // Ticks should match up one for each y value (category) in the series.
		    	    $('#chartdiv<%=s.getUsername()%>').html('');
		    		 var plot3 = $.jqplot('chartdiv<%=s.getUsername()%>', [s1, s2, s3], 
		    			    { 
		    			      title:'Line Style Options', 
		    			      // Series options are specified as an array of objects, one object
		    			      // for each series.
		    			      series:[ 
		    			          {
		    			          	label:'posts',
		    			            // Change our line width and use a diamond shaped marker.
		    			            lineWidth:2, 
		    			            markerOptions: { style:'dimaond' }
		    			          }, 
		    			          {
		    			          	label:'comments',
		    			            // Don't show a line, just show markers.
		    			            // Make the markers 7 pixels with an 'x' style
		    			            showLine:false, 
		    			            markerOptions: { size: 7, style:"x" }
		    			          },
		    			          { 
		    			          	label:'tweets',
		    			            // Use (open) circlular markers.
		    			            markerOptions: { style:"circle" }
		    			          }
		    			      ],
		    			      seriesDefaults:{
		    			            rendererOptions: {fillToZero: true}
		    			        },
		    			        // Show the legend and put it outside the grid, but inside the
		    			        // plot container, shrinking the grid to accomodate the legend.
		    			        // A value of "outside" would not shrink the grid and allow
		    			        // the legend to overflow the container.
		    			        legend: {
		    			            show: true,
		    			            placement: 'outsideGrid'
		    			        },
		    			        axes: {
		    			            // Use a category axis on the x axis and use our custom ticks.
		    			            xaxis: {
		    			                renderer: $.jqplot.CategoryAxisRenderer,
		    			                ticks: ticks
		    			            },
		    			            // Pad the y axis just a little so bars can get close to, but
		    			            // not touch, the grid boundaries.  1.2 is the default padding.
		    			            yaxis: {
		    			            	padMin: 0,
		    			            	pad: 1.05,
		    			                tickOptions: {formatString: '%d'}
		    			            }
		    			        }
		    			      
		    			    }
		    			  );
		    	}
		    	
		    	function <%=s.getUsername()%>badgeLineChart(){
		    		var s1=new Array();// = [200, 600, 700, 1000];
		    		var ticks=new Array();// = ['May', 'June', 'July', 'August'];
		    	
		    		<%//for (int i=0;i<course.getWeeks().size();i++){
		    		count = 0;	
		    		for (int i=course.getWeeks().size()-1;i>=0;i--){
		    			out.println("ticks["+count+"]='W'+"+course.getWeeks().get(i)+";");
		    			out.println("s1["+count+"]="+s.getBadgeActivityPerWeek().get(i)+";");
		    			count++;									
		    		}%>
		    	
		    	    // Can specify a custom tick Array.
		    	    // Ticks should match up one for each y value (category) in the series.
		    		$('#chartdiv<%=s.getUsername()%>badgechart').html('');
		    	    var plot1 = $.jqplot('chartdiv<%=s.getUsername()%>badgechart', [s1], {
		    	        title:'Line Style Options', 
		    			      // Series options are specified as an array of objects, one object
		    			      // for each series.
		    			      series:[ 
		    			          {
		    			          	label:'badges',
		    			            // Change our line width and use a diamond shaped marker.
		    			            lineWidth:2, 
		    			            markerOptions: { style:'dimaond' }
		    			          }
		    			      ],
		    			      seriesDefaults:{
		    			            rendererOptions: {fillToZero: true}
		    			        },
		    			        // Show the legend and put it outside the grid, but inside the
		    			        // plot container, shrinking the grid to accomodate the legend.
		    			        // A value of "outside" would not shrink the grid and allow
		    			        // the legend to overflow the container.
		    			        legend: {
		    			            show: true,
		    			            placement: 'outsideGrid'
		    			        },
		    			        axes: {
		    			            // Use a category axis on the x axis and use our custom ticks.
		    			            xaxis: {
		    			                renderer: $.jqplot.CategoryAxisRenderer,
		    			                ticks: ticks
		    			            },
		    			            // Pad the y axis just a little so bars can get close to, but
		    			            // not touch, the grid boundaries.  1.2 is the default padding.
		    			            yaxis: {
		    			            	padMin: 0,
		    			            	pad: 1.05,
		    			                tickOptions: {formatString: '%d'}
		    			            }
		    			        }
		    			      
		    			    }
		    			  );
		    	}
		    <%}%>
		    
		    //APPLY JQUERY PLUGIN
		    function applyJQueryPlugins(){
		    	$("#tableBlogs").tablesorter();
				$("#tableBlogs_ext").tablesorter();		
				<%
				ArrayList<Student> students = course.getStudents();
				for (Student s:students){
					%>
					$('#sparkline<%=s.getUsername()%>social').sparkline();
					$('#sparkline<%=s.getUsername()%>social').qtip({
			              content: {
			                  text: '<a href=\"javascript:<%=s.getUsername()%>socialLineChart()\"><div id=\"usersocialchart<%=row%>\" data-enabled=\"true\" style=\"border:solid 1px white; display: inline-block;margin-left: 20px;margin-top: 10px;\">Draw line chart</div></a><a href=\"javascript:<%=s.getUsername()%>socialBarChart()\"><div id=\"usersocialchart<%=row%>\" data-enabled=\"true\" style=\"border:solid 1px white; display: inline-block;margin-left: 20px;margin-top: 10px;\">Draw baw chart</div></a> <div id="chartdiv<%=s.getUsername()%>" style="height:400px;width:500px;"></div>', // Use each elements title attribute
							  	title: {
									text: 'Weekly activity',
									button: 'Close'	
								}
			              },
						  	position: {
							    my: 'top right',
								at: 'bottom left',
								target: $('#sparkline<%=s.getUsername()%>social')
								},
							show: {
								event: 'click' 
							},
							hide: {
								event: 'click' 
							},
			                style: {
								classes: "ui-tooltip-jtools"
								},
						  events: 
						  {
							visible: function (event,api) 
							{
								
							}
						  }
			          	});
					$('#sparkline<%=s.getUsername()%>badgeact').sparkline();
					
					$('#sparkline<%=s.getUsername()%>badgeact').qtip({
			              content: {
			                  text: '<a href=\"javascript:<%=s.getUsername()%>badgeLineChart()\"><div id=\"userbadgechart<%=row%>\" data-enabled=\"true\" style=\"border:solid 1px white; display: inline-block;margin-left: 20px;margin-top: 10px;\">Draw line chart</div></a><a href=\"javascript:<%=s.getUsername()%>badgeBarChart()\"><div id=\"userbadgechart<%=row%>\" data-enabled=\"true\" style=\"border:solid 1px white; display: inline-block;margin-left: 20px;margin-top: 10px;\">Draw baw chart</div></a><div id="chartdiv<%=s.getUsername()%>badgechart" style="height:400px;width:500px;"></div>', // Use each elements title attribute
							  title: {
									text: '<a href="http://navi-hci.appspot.com/badgeboard?username=<%=s.getUsername()%>">Weekly activity</a>',
									button: 'Close'	
							  }
			              },
						  position: {
							    my: 'top right',
								at: 'bottom left',
								target: $('#sparkline<%=s.getUsername()%>badgeact')
						  },
						  show: {
								event: 'click' 
						  },
						  hide: {
								event: 'click' 
						  },
			              style: {
								classes: "ui-tooltip-jtools"
						  },
						  events: 
						  {
							visible: function (event,api) 
							{

							}
						  }
			          	});
					<%
				}
				students = course.getExternals();
				for (Student s:students){
					%>
					$('#sparkline<%=s.getUsername()%>social').sparkline();
					<%
				}
				%>
		    }

		    
		    $(document).ready(function() {
		    	print_tables();
		    	print_filterCol();
		    	print_filterRow();
		    	applyJQueryPlugins();								
				$('#PageRefresh').click(function() {
							location.reload();  							
				});
		    });
		<%}%>
			

		
	</script>
	<script type="text/javascript">
		function sortJsonArrayByProp(objArray, prop){
		    if (arguments.length<2){
		        throw new Error("sortJsonArrayByProp requires 2 arguments");
		    }
		    if (objArray && objArray.constructor===Array){
		        var propPath = (prop.constructor===Array) ? prop : prop.split(".");
		        objArray.sort(function(a,b){
		            for (var p in propPath){
		                if (a[propPath[p]] && b[propPath[p]]){
		                    a = a[propPath[p]];
		                    b = b[propPath[p]];
		                }
		            }
		            // convert numeric strings to integers
		            a = a.match(/^\d+$/) ? +a : a;
		            b = b.match(/^\d+$/) ? +b : b;
		            return ( (a < b) ? -1 : ((a > b) ? 1 : 0) );
		        });
		    }
		}
	</script>
	<script type="text/javascript">

	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-28975285-1']);
	  _gaq.push(['_trackPageview']);

	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();

	</script>
  </head>
  <body>
	<div align="center"><table class="tablesorter"><tr><td width="5px" bgcolor="#EBFFED"/><td>posts</td><td width="5px" bgcolor="#EBF9FF"/><td>comments</td><td width="5px" bgcolor="#EBFFFB"/><td>twitter</td><td width="5px" bgcolor="#EFFBF2"/><td>Toggl</td><td width="5px" bgcolor="#FFF6ED"/><td>no contribution</td></tr></table></div>	
	<div align="center"><button id="PageRefresh">Back to the initial sorted table</button></div>
	<div id="filtersCol" align="center">Column filter: </div>
	<div id="filtersRow" align="center">Row filter: </div>
		<div id="tables" align="center"></div>
		<div id="tables_ext" align="center"><br/></div>
  </body>
</html>