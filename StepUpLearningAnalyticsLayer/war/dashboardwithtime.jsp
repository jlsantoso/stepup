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
	<link rel="stylesheet" type="text/css" href="css/jquery.jqplot.css" />
	
    <script type="text/javascript">
   		var email = '<%=twitter.getScreenName()%>';
		<%
		//Loading course info
		Course course = PersistanceLayer.getCourse("bigtablechikul13");
		if (course!=null){
		%>
			table = "<table id=\"tableBlogs\" class=\"tablesorter\" width=\"80%\">";
			table+= "<thead><tr><th>Authors</th>"; 
			table_ext = "<table id=\"tableBlogs_ext\" class=\"tablesorter\" width=\"80%\"><thead><tr><th>Externals</th>";
			<%//Loading blogs
				ArrayList<Blog> blogs = course.getBlogs();
				for (Blog b: blogs){
					%>
					table+="<th><a href=\"<%=b.getUrl()%>\"><%=b.getName()%></a></th>";
					table_ext+="<th><a href=\"<%=b.getUrl()%>\"><%=b.getName()%></a></th>";
					<%
				}
			%>
			table+="<th>Total</th>";
			table_ext+="<th>Total</th>";
			table+="<th>Toggl</th>";
			table_ext+="<th>Toggl</th>";
			table+="<th><a href=\"http://twitter.com\">Twitter</a></th>";
			table_ext+="<th><a href=\"http://twitter.com\">Twitter</a></th>";
			table+="<th>Soc. Net.</th><th>Toggl</th><tbody>";
		    table_ext+="<th>Soc. Net.</th><th>Toggl</th><tbody>";
		    var tableFinal = "";
		 	<%//Loading Groups
		 		ArrayList<Group> groups = course.getGroups();
		 		for (Group g: groups){
		 			ArrayList<Student> students = g.getStudents();
		 			for (Student s : students){
		 				ArrayList<Integer> postBlogActivity = s.getPostActivity();
		 				ArrayList<Integer> commentBlogActivity = s.getCommentActivity();	
		 				%>
		 				table+="<tr><td align=\"left\" ><%=s.getUsername()%></td>";
		 				<%
		 				int commentsTotal = 0;
		 				String color = "";
		 				for (int i=0;i<postBlogActivity.size();i++){		 					
			 				if (g.getFirstBlog().equals(blogs.get(i))){
			 				// POSTS OF THE USER
			 					if (postBlogActivity.get(i)==0) color = "#FFF6ED";
			 					else color = "#EBFFED";
			 					commentsTotal+=commentBlogActivity.get(i); //Total comments also includes the comments done in their own blog
			 					%>
			 					table+="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+""+i);%>\"><%=postBlogActivity.get(i)%></td>";
			 					<%
			 				}else{
			 				// COMMENTS OF THE USER
			 					commentsTotal+=commentBlogActivity.get(i);
			 					if (commentBlogActivity.get(i)==0) color = "#FFF6ED";
			 					else color = "#EBF9FF";
			 					%>
			 					table+="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+""+i);%>\"><%=commentBlogActivity.get(i)%></td>";
			 					<%
			 				}
			 				
		 				}
		 				// TOTAL COMMENTS OF THE USER
		 				if (commentsTotal==0) color = "#FFF6ED";
	 					else color = "#EBF9FF";
		 				%>
		 					table+="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+"Total");%>\"><%=commentsTotal%></td>";
		 				<%
		 				// TOTAL SPENT TIME ON THE COURSE
		 				int timeSpent = s.getTimeSpentByActivities();
		 				%>
		 				var time=<%=timeSpent%>/3600;
						var time_spent=Math.floor(<%=timeSpent%>/3600);
						var min=(<%=timeSpent%>/60)%60;
						var hour = time_spent.toFixed(0);
						if (hour<10) hour = '0'+hour;
						if (hour<100) hour = '0'+hour;
						//time_spent=time_spent-min;
						//alert(toggl);
						table+="<td align=\"center\" bgcolor=\"#EFFBF2\" id=\"<%out.print(s.getUsername()+"toggl");%>\">"+hour+" h "+min.toFixed(0)+" m"+"</td>";
		 				<%int tweets = s.getTweetActivity();
		 				if (tweets==0) color = "#FFF6ED";
	 					else color = "#EBFFFB";%>
	 					table+="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername()+"twitter");%>\"><%=tweets%></td>";
	 					
	 					
	 					<%
	 					//PRINT SPARKLINE REGARDING SOCIAL NETWORK
	 					ArrayList<Integer> postsweeks = s.getPostsActivityPerWeek();
	 					ArrayList<Integer> commentsweeks = s.getCommentsActivityPerWeek();
	 					ArrayList<Integer> tweetsweeks = s.getTweetsActivityPerWeek();
	 					%>
	 					table+="<td id =\"sparkline<%out.print(s.getUsername()+"social");%>\">";
	 					<%
	 					for (int i=0;i<tweetsweeks.size()-1;i++){
	 						int sum = tweetsweeks.get(i)+commentsweeks.get(i)+postsweeks.get(i);
	 						out.print("table+="+sum+"+\",\";");
	 					}
	 					int sum = tweetsweeks.get(tweetsweeks.size()-1)+commentsweeks.get(tweetsweeks.size()-1)+postsweeks.get(tweetsweeks.size()-1);
 						out.print(sum+";");
 						%>
 						table+="</td>";
 						table+="<td id =\"sparkline<%out.print(s.getUsername()+"toggl");%>\">";
 						<%
 						//PRINT SPARKLINE REGARDING SOCIAL NETWORK
 						ArrayList<Activity> activities = s.getListTimeSpentByActivity();
	 					for (int i=0;i<tweetsweeks.size()-1;i++){ 
	 						sum = 0;
 							for (Activity a: activities){
	 							sum += a.getActivityWeek().get(i);
	 						}
 							out.print("table+="+sum+"+\",\";");
	 					}
	 					sum = 0;
						for (Activity a: activities){
 							sum += a.getActivityWeek().get(tweetsweeks.size()-1);
 						}
						out.print("table+="+sum+"+\",\";");
	 					%>
	 					table+="</td>";
						table+="</tr>";
		 				<%
		 			}
		 		}
		 	%>

			//table += tableFinal;
		    table+= "</tbody>";
			table+="</tr></thead>";
		    table+="</table>";
		    table_ext+="</tbody></tr></thead></table>";
		    
		    $(document).ready(function() {
			    $("<p>").append(table).appendTo("#tables");
		
				$("#tableBlogs").tablesorter();
				<%
				ArrayList<Student> students = course.getStudents();
				for (Student s:students){
					%>
					$('#sparkline<%=s.getUsername()%>toggl').sparkline();
					$('#sparkline<%=s.getUsername()%>social').sparkline();
					<%
				}
				%>
				
				
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
		<div id="tables" align="center"></div>
		<div id="tables_ext" align="center"><br/></div>
  </body>
</html>