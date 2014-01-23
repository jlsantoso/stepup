<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.regex.Pattern, java.util.regex.*, twitter4j.Twitter, org.be.kuleuven.hci.aggregationlayer.modelwespot.Course, 
	org.be.kuleuven.hci.aggregationlayer.modelwespot.utils.PersistanceLayer,
	org.be.kuleuven.hci.aggregationlayer.modelwespot.Phase,
	org.be.kuleuven.hci.aggregationlayer.modelwespot.Student,
	org.be.kuleuven.hci.aggregationlayer.modelwespot.Subphase,
	java.util.ArrayList"%>
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
	if (principalName.contains("#")){
		String suffix = "";
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
	<script src="http://code.jquery.com/mobile/1.2.1/jquery.mobile-1.2.1.min.js"></script> 
	<link rel="stylesheet" type="text/css" href="css/jquery.jqplot.css" />
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.1/jquery.mobile-1.2.1.min.css" />
	
	
    <script type="text/javascript">
   		var email = '';
   		var filterColumns = "";
   		var filterRows = "";
		<%
		//Loading course info
		Course course = PersistanceLayer.getCourse("wespot"+request.getParameter("inquiry"));
		if (course!=null){
			int weeks = course.getWeeks().size();
			ArrayList<Phase> phases = course.getPhases();
		
		%>
			var tableVar = new Array(<%=course.getStudents().size()+2%>);

			for (var i = 0; i < <%=course.getStudents().size()+2%>; i++) {
				tableVar[i] = new Array(<%=(phases.size()+3)%>);
			}


			
			tableVar[0][0] = "<table id=\"tableBlogs\" class=\"tablesorter\" width=\"80%\"><thead><tr><th>Authors</th>";

			var column = 0;
			<%//Loading blogs				
				for (Phase p: phases){
					%>					
					tableVar[0][++column]="<th><a href=\"<%=p.getName()%>\"><%=p.getName()%></a></th>";
					i++;
					<%
				}
			%>
			tableVar[0][++column] = "<th>Total</th>";
			tableVar[0][++column] = "<th>Sparkline</th><tbody>";
			
		    var tableFinal = "";
		 	<%//Loading Groups
		 		ArrayList<Student> students = course.getStudents();
		 		int groupNumber = 0;
		 		%>
		 		var row = 0;
		 		<%
		 		for (Student s: students){
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
		 				
		 				%>
		 				column = 0;
		 				//alert(row);
		 				tableVar[++row][column]="<tr bgcolor=\"<%=colorGroup%>\"><td align=\"left\" ><%
		 						if (s.getUsername().compareTo("google_"+principalName)==0)
		 							out.print(attributes.get("name"));
		 						else out.print("User"+(students.indexOf(s)+1));
		 						%></td>";
		 				<%
		 				int commentsTotal = 0;
		 				String color = "";
		 				ArrayList<Phase> phasesStudent = s.getActivity();
		 				
		 				for (int i=0;i<phasesStudent.size();i++){		 					
			 				// POSTS OF THE USER
			 					if (phasesStudent.get(i).getActivity()==0) color = redColor;
			 					else color = postColor;
			 					commentsTotal+=phasesStudent.get(i).getActivity(); //Total comments also includes the comments done in their own blog
			 					%>
			 					tableVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")+phasesStudent.get(i).getName().replaceAll(" ",""));%>\"><%=phasesStudent.get(i).getActivity()%></td>";
			 					<%
			 				
		 				}
		 				%>
		 				tableVar[row][++column]="<td align=\"center\" bgcolor=\"<%=color%>\" id=\"<%out.print(s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")+"total");%>\"><%=commentsTotal%></td>";
		 				tableVar[row][++column]="<td id =\"sparkline<%out.print(s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]",""));%>\">";<%
		 				ArrayList<Integer> weeklyActivity = new ArrayList<Integer>();
						for (Phase p: phasesStudent){
							if (phasesStudent.indexOf(p) == 0) weeklyActivity = p.getActivityWeeks();
							else{
								for (int i=0; i<p.getActivityWeeks().size(); i++){
									weeklyActivity.set(i, weeklyActivity.get(i)+p.getActivityWeeks().get(i));
								}	
							}													
							
						}
						//AQUI EL ERROR!!!!!!!!!!!!!!!!!!!!!
						//out.print("tableVar[row][column]+=\"0</td>\"");
						int position = 0;
						for (Integer act:weeklyActivity){
							if (position==(weeklyActivity.size()-1)){
								out.print("tableVar[row][column]+=\""+act+"</td></tr>\";");
							}
							else{
								out.print("tableVar[row][column]+=\""+act+",\";");
							}
							position++;
							
						}
						
					%>
		 				
		 			<%}%>
		 		
		 		


			
		    //PRINT TABLES OF STUDENTS AND EXTERNALS
		    function print_tables(){
		    	var tableString = "";
		    	for (var i=0;i<=<%=course.getStudents().size()%>;i++){
		    		for(var j=0;j<<%out.print(phases.size()+3);%>;j++){
		    			tableString += tableVar[i][j];		    			
		    		}
		    	}
		    	tableString+="</tbody></tr></thead></table>";
		    	//alert(tableString);
		    	$("<p>").append(tableString).appendTo("#tables");
		    	
		    }
		   //PRINT TABLES OF STUDENTS AND EXTERNALS WITH filters
		    function print_tables_filters(){
		    	var tableString = "";
		    	for (var i=0;i<=<%=course.getStudents().size()%>;i++){
		    		if (filterRows.indexOf(i+";") ==-1){
			    		for(var j=0;j<<%=(phases.size()+2)%>;j++){
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
				for (Phase p: phases){
					column++;
					%>					
					filter += "<a href=\"javascript:enableDisableGraphColumn('<%=column%>')\">";
					filter += "<div id=\"blog<%=column%>\" data-enabled=\"true\" style=\"border:solid 1px black; display: inline-block;margin-left: 20px;\"><%=p.getName()%></div></a>";
					<%
				}
				%>
				$("<p>").append(filter).appendTo("#filtersCol");
		    }
		  
		    function print_filterRow(){
		    	var filter = "";
		    	<%//Loading blogs	
		    	int row=0;

	 			//ArrayList<Student> studentsfilter = course.getStudents();
	 			for(Student s1 :students){
					row++;
					%>					
					filter += "<a href=\"javascript:enableDisableGraphRow('<%=row%>')\">";
					filter += "<div id=\"user<%=row%>\" data-enabled=\"true\" style=\"border:solid 1px black; display: inline-block;margin-left: 20px;margin-top: 10px;\"><%
 						if (s1.getUsername().compareTo("google_"+principalName)==0)
 							out.print(attributes.get("name"));
 						else out.print("User"+row);
 						%></div></a>";
					<%
	 			}
				
				%>
				$("<p>").append(filter).appendTo("#filtersRow");
		    }
		  
		    <%}%>
		    
		  	
		    
		    <%
		    ArrayList<Student> studentsBarCharts = course.getStudents();
		    for (Student s:studentsBarCharts){
	    		ArrayList<Phase> phaseBarCharts = s.getActivity();
	    		
	    		int countPhases=0;
	    		for (Phase p: phaseBarCharts){
	    			%>	    		
	    			function Chart<%=s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")%><%=p.getName().replaceAll(" ","")%>(){
	    			<%
	    				
	    				out.println("var ticks=new Array();");
		    			ArrayList<Subphase> subphases = p.getSubPhases();
		    			int countSubPhases=0;
		    			String subphasesNames = "";	
		    			String subphasesVariables = "";
		    			for (Subphase sp:subphases){
		    				if (countSubPhases!=0){
		    					subphasesNames += ",";
		    					subphasesVariables += ",";
		    				}
		    				subphasesVariables += "s"+countSubPhases;
		    				subphasesNames += "{label:'"+sp.getName()+"'}";
		    				out.println("var s"+countSubPhases+"=new Array();");
			    			countSubPhases++;
		    			}		
		    			String ticks = "";		    				    			
		    			int countWeeks = 0;	
			    		for (int i=course.getWeeks().size()-1;i>=0;i--){
			    			out.println("ticks["+countWeeks+"]='W'+"+course.getWeeks().get(i)+";");
			    			countSubPhases=0;
			    			for (Subphase sp:subphases){
			    				out.println("s"+countSubPhases+"["+countWeeks+"]="+sp.getActivityWeeks().get(i)+";");
				    			countSubPhases++;
			    			}		
			    			countWeeks++;
			    		}
			    		%>
			    		// Can specify a custom tick Array.
			    	    // Ticks should match up one for each y value (category) in the series.
			    		$('#chartdiv<%=s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")%><%=p.getName().replaceAll(" ","")%>').html('');
			    		var plot1 = $.jqplot('chartdiv<%=s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")%><%=p.getName().replaceAll(" ","")%>', [<%=subphasesVariables%>], {
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
			    	            <%=subphasesNames%>
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
			    		
		    		function ChartLine<%=s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")%><%=p.getName().replaceAll(" ","")%>(){
			    		<% out.println("var ticks=new Array();");
		    			ArrayList<Subphase> subphases2 = p.getSubPhases();
		    			int countSubPhases2=0;
		    			String subphasesNames2 = "";	
		    			String subphasesVariables2 = "";
		    			for (Subphase sp:subphases2){
		    				if (countSubPhases2!=0){
		    					subphasesNames2 += ",";
		    					subphasesVariables2 += ",";
		    				}
		    				subphasesVariables2 += "s"+countSubPhases2;
		    				subphasesNames2 += "{label:'"+sp.getName()+"'}";
		    				out.println("var s"+countSubPhases2+"=new Array();");
			    			countSubPhases2++;
		    			}		
		    			String ticks2 = "";		    				    			
		    			int countWeeks2 = 0;	
			    		for (int i=course.getWeeks().size()-1;i>=0;i--){
			    			out.println("ticks["+countWeeks2+"]='W'+"+course.getWeeks().get(i)+";");
			    			countSubPhases2=0;
			    			for (Subphase sp:subphases2){
			    				out.println("s"+countSubPhases2+"["+countWeeks2+"]="+sp.getActivityWeeks().get(i)+";");
				    			countSubPhases2++;
			    			}		
			    			countWeeks2++;
			    		}
			    		%>
			    		// Can specify a custom tick Array.
			    	    // Ticks should match up one for each y value (category) in the series.
			    		$('#chartdiv<%=s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")%><%=p.getName().replaceAll(" ","")%>').html('');
			    		var plot3 = $.jqplot('chartdiv<%=s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")%><%=p.getName().replaceAll(" ","")%>', [<%=subphasesVariables%>],{ 
		    			      title:'Line Style Options', 
		    			      // Series options are specified as an array of objects, one object
		    			      // for each series.
		    			      series:[<%=subphasesNames2%>],
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
			    		});
			    		}
				    		
			    		<%
		    			countPhases++;
			    	
	    			
	    		}	    		
	    	}
	    	
	    	%>

	    	//APPLY JQUERY PLUGIN
		    function applyJQueryPlugins(){
		    	<%
			    ArrayList<Student> studentsBarCharts2 = course.getStudents();
			    for (Student s:studentsBarCharts2){
			    	%>$('#sparkline<%out.print(s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]",""));%>').sparkline();
	 				<%
		    		ArrayList<Phase> phaseBarCharts = s.getActivity();		    		
		    		int countPhases=0;
		    		for (Phase p: phaseBarCharts){
		    			%>
		    			$('#<%out.print(s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")+p.getName().replaceAll(" ",""));%>').qtip({
				              content: {
				                  text: '<div id="chartdiv<%out.print(s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")+p.getName().replaceAll(" ",""));%>" style="height:400px;width:500px;"></div>',
								  	title: {
										text: 'Weekly activity',
										button: 'Close'	
									}
				              },
							  	position: {
								    my: <%if (phaseBarCharts.indexOf(p)<phaseBarCharts.size()/2) out.println("'top left'");
									else out.println("'top right'");%>,
									at: <%if (phaseBarCharts.indexOf(p)<phaseBarCharts.size()/2) out.println("'bottom right'");
									else out.println("'bottom left'");%>,
									target: $('#<%out.print(s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")+p.getName().replaceAll(" ",""));%>')
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
									Chart<%out.print(s.getUsername().substring(s.getUsername().lastIndexOf('/') + 1).replaceAll("[^a-zA-Z1-9]","")+p.getName().replaceAll(" ",""));%>();
								}
							  }
				          	});
		    			<%
		    		}
	    		}%>	
		    	$("#tableBlogs").tablesorter();
		    	
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
	<div data-role="header"> 
	<h1>Inquiries available:</h1> 
	</div> 
	<div align="center"><button id="PageRefresh">Back to the initial sorted table</button></div>
	
		<div id="tables" align="center"></div>
		<div id="tables_ext" align="center"><br/></div>
		<div id="filtersCol" align="center">Column filter: </div>
	<div id="filtersRow" align="center">Row filter: </div>
  </body>
</html>