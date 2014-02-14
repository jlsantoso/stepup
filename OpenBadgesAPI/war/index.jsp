<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
    <head>
        <title>Upload Test</title>
    </head>
    <body>
        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
            Context:<input type="text" name="context"><br/>
            Name:<input type="text" name="name"><br/>
            Description:<input type="text" name="description"><br/>
            Criteria:<input type="text" name="criteria"><br/>            
            File:<input type="file" name="myFile"><br/>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
