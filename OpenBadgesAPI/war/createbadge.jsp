<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
    <head>
        <title>Upload Test</title>
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
    </head>
    <body>
        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data" data-ajax="false">
          <div class="ui-field-contain">
            <input type="hidden" id="context" name="context" value="<%=request.getParameter("context")%>">
            <label for="name">Name:</label><input type="text" id="name" name="name"><br/>
            <label for="description">Description:</label><input type="text" id="description" name="description"><br/>
            <label for="criteria">Criteria:</label><input type="text" id="criteria" name="criteria"><br/>            
            <label for="myFile">File:</label><input type="file" id="myFile" name="myFile"><br/>
            <input type="submit" value="Submit" data-icon="check" data-iconpos="right" data-inline="true">
           </div>
        </form>
    </body>
</html>
