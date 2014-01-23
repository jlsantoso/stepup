<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html> 
<html>  
  <head>
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.css" />  
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.js"></script>
    
		<title>StepUp!</title>
  </head>
  <body>
    <div data-role="page" data-theme="d" align="center">
	        <div data-role="header" data-theme="c">
	            <img src="images/logomobile.png" alt="Twitter" widht="200px" align="middle"/>
	        </div>
	        <div data-role="content">
				<img src="images/twitter-roto.png" alt="Twitter" height="150px"/>
				<a id="login-btn" href="#" data-role="button" data-theme="c">Login with twitter</a>
	        </div>
	        <div data-role="footer" data-position="fixed" data-theme="c">
	            <h1>&copy; 2012 KU Leuven</h1>
	        </div>
	    </div>
		<script type="text/javascript">
			$('#login-btn').click(function(event){
				_gaq.push(['_trackEvent', 'login', 'click', 'twitter']);
				event.preventDefault();
				window.location = 'auth/twitter';
			});
		</script>
  </body>
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
</html>