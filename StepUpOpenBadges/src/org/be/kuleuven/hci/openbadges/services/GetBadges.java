package org.be.kuleuven.hci.openbadges.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.be.kuleuven.hci.openbadges.badges.Badges;
import org.json.JSONObject;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/getBadge")


public class GetBadges {
	
	//first Badge
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/erik/first")
	public String getErikFirstBadge() {	
		return Badges.firstBadge("erik.duval@cs.kuleuven.be");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sven/first")
	public String getSvenFirstBadge() {	
		return Badges.firstBadge("sven.charleer@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gonzalo/first")
	public String getGonzaloFirstBadge() {	
		return Badges.firstBadge("gonzalo.parra.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/joseluis/first")
	public String getJoseFirstBadge() {	
		return Badges.firstBadge("joseluis.santos.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sam/first")
	public String getSamBadge() {	
		return Badges.firstBadge("sam.agten@cs.kuleuven.be");
	} 
	
	//second badge
	
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/erik/second")
	public String getErikSecondBadge() {	
		return Badges.secondBadge("erik.duval@cs.kuleuven.be");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sven/second")
	public String getSvenSecondBadge() {	
		return Badges.secondBadge("sven.charleer@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gonzalo/second")
	public String getGonzaloSecondBadge() {	
		return Badges.secondBadge("gonzalo.parra.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/joseluis/second")
	public String getJoseSecondBadge() {	
		return Badges.secondBadge("joseluis.santos.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sam/second")
	public String getSamSecondBadge() {	
		return Badges.secondBadge("sam.agten@cs.kuleuven.be");
	}
	
	//third badge
	
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/erik/third")
	public String getErikThirdBadge() {	
		return Badges.thirdBadge("erik.duval@cs.kuleuven.be");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sven/third")
	public String getSvenThirdBadge() {	
		return Badges.thirdBadge("sven.charleer@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gonzalo/third")
	public String getGonzaloThirdBadge() {	
		return Badges.thirdBadge("gonzalo.parra.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/joseluis/third")
	public String getJoseThirdBadge() {	
		return Badges.thirdBadge("joseluis.santos.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sam/third")
	public String getSamThirdBadge() {	
		return Badges.thirdBadge("sam.agten@cs.kuleuven.be");
	}
	
	
	// Fourth badge
	
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/erik/fourth")
	public String getErikFourthBadge() {	
		return Badges.fourthBadge("erik.duval@cs.kuleuven.be");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sven/fourth")
	public String getSvenFourthBadge() {	
		return Badges.fourthBadge("sven.charleer@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gonzalo/fourth")
	public String getGonzaloFourthBadge() {	
		return Badges.fourthBadge("gonzalo.parra.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/joseluis/fourth")
	public String getJoseFourthBadge() {	
		return Badges.fourthBadge("joseluis.santos.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sam/fourth")
	public String getSamFourthBadge() {	
		return Badges.fourthBadge("sam.agten@cs.kuleuven.be");
	}
	
	//Fifth Badge
	
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/erik/fifth")
	public String getErikFifthBadge() {	
		return Badges.fifthBadge("erik.duval@cs.kuleuven.be");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sven/fifth")
	public String getSvenFifthBadge() {	
		return Badges.fifthBadge("sven.charleer@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gonzalo/fifth")
	public String getGonzaloFifthBadge() {	
		return Badges.fifthBadge("gonzalo.parra.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/joseluis/fifth")
	public String getJoseFifthBadge() {	
		return Badges.fifthBadge("joseluis.santos.cs@gmail.com");
	} 
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sam/fifth")
	public String getSamFifthBadge() {	
		return Badges.fifthBadge("sam.agten@cs.kuleuven.be");
	}	
}
