<html>
    <head>
        <title>Upload Test</title>
        <link rel="stylesheet" href="css/jquery.mobile-1.4.2.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
    </head>
    <body>
		<div data-role="header" > 
			<a href="javascript:location.replace('/menu.jsp?context=<%=request.getParameter("context")%>&inquiryserver=<%=request.getParameter("inquiryserver")%>')" data-icon="back" class="ui-btn-left" title="Go back">Back</a>
			<h1>Create a new badge</h1>
		</div>
        <form action="/uploadwespot" method="get" enctype="multipart/form-data" data-ajax="false">
          <div class="ui-field-contain">
            <input type="hidden" id="context" name="context" value="<%=request.getParameter("context")%>">
            <input type="hidden" id="file" name="file" value="http://openbadges-wespot.appspot.com/badges/7generic.png">
            <input type="hidden" id="inquiryserver" name="inquiryserver" value="<%=request.getParameter("inquiryserver")%>">
            <label for="name">Name:</label><input type="text" id="name" name="name"><br/>
            <label for="description">Description:</label><input type="text" id="description" name="description"><br/>
            <label for="criteria">Criteria:</label><input type="text" id="criteria" name="criteria"><br/>            
            <input type="submit" value="Submit" data-icon="check" data-iconpos="right" data-inline="true">
           </div>
        </form>
    </body>
</html>
