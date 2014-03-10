package org.be.kuleuven.hci.openbadges.badgestore;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.model.Issuer;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerBadge;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Upload extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        BlobKey blobKey = blobs.get("myFile").get(0);
       
        if (blobKey == null) {
            res.sendRedirect("/");
        } else {
        	Badge badge = new Badge();
            String img_path = "http://"+req.getServerName()+":"+req.getServerPort()+"/serve?blob-key=" + blobKey.getKeyString();
            JSONObject badgejson = new JSONObject();
            System.out.println("Entrar-1");
            try {
				badgejson.put("image", img_path);
				//System.out.println("object:"+req.getParameter("context").toString());
				badgejson.put("context", req.getParameter("context"));
	            badgejson.put("name", req.getParameter("name"));
	            badgejson.put("description", req.getParameter("description"));
	            badgejson.put("criteria", req.getParameter("criteria"));  
	            badgejson.put("version", "1.0.0");  
	            System.out.println("Entrar-2: "+badgejson.toString());
				if (badgejson.has("context")&&badgejson.has("version")&&badgejson.has("name")&&badgejson.has("image")&&badgejson.has("description")&&badgejson.has("criteria")){
					System.out.println("Entrar-3");
					badgejson.put("issuer", Issuer.getIssuer());
					badge.setNameApp("editor");
					badge.setContext(badgejson.getString("context"));
					badgejson.remove("context");
					badge.setjsonBadge(new Text(badgejson.toString()));
					System.out.println("Entrar");
					String id = PersistanceLayerBadge.saveBadge(badge);
				}else
					System.out.println("NO ALL FIELDS PRESENT");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}            
        }
        //req.getRequestDispatcher("/menu.jsp?context="+req.getParameter("context")).forward(req, res);
        res.sendRedirect("/menu.jsp?context="+req.getParameter("context"));
    }
}
