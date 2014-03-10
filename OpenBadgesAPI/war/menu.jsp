<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <title>STEP UP!</title>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>

  </head>
  <body>
	<div align="center"></div>	
	<div data-role="header"> 
	<h1>Options</h1> 
	</div>
		<div><a href="javascript:location.replace('/createbadge.jsp?context=<%=request.getParameter("context")%>');" data-role="button">Create a badge</a></div> 
		<div><a href="javascript:location.replace('/awardbadge.jsp?context=<%=request.getParameter("context")%>');" data-role="button">Reward a badge</a></div>
		<div><a href="javascript:location.replace('/listAwardedBadges.jsp?context=<%=request.getParameter("context")%>');" data-role="button">List awarded badges</a></div>
  </body>
</html>