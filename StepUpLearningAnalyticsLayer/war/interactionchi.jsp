<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.memcache.ErrorHandlers,com.google.appengine.api.memcache.MemcacheService,com.google.appengine.api.memcache.MemcacheServiceFactory,java.util.logging.Level,java.util.ArrayList"%>
<!DOCTYPE html> 
<html>  
  <head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

<script type="text/javascript">
$(document).bind('mobileinit',function(){
	$.mobile.selectmenu.prototype.options.nativeMenu = false;
});
		
</script>
    
		<title>StepUp!</title>
  </head>
  <body>
    <div data-role="fieldcontain">
	<select name="select-choice-1" id="select-choice-1">
		<option value="">Select the username</option>
		<%MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO)); 
	    ArrayList<String> users = (ArrayList<String>)syncCache.get("usernameschikul");
	    for (int i=0;i<users.size();i++){%>
		<option value="<%=users.get(i)%>"><%=users.get(i)%></option>
		<%} %>
	</select>
	
</div>
<div id="texto"></div>

<script type="text/javascript">
$("select").change(function () {
	  var str = "";
	  $("select option:selected").each(function () {
		    if ($(this).val()!=""){
		    	$.ajax({ 
		             type: "GET",
		             dataType: "json",
		             url: "http://bigtablechi13.appspot.com/rest/getRevealItInfo/putpersonchi?person="+$(this).val(),
		             success: function(data){        
		                alert(data);
		             }
		         });
	        	str += "The visualization will highlight the user "+$(this).val() + " soon...";
	  		}
	      });
	  $("#texto").text(str);
	})
	.trigger('change');
</script>
</html>