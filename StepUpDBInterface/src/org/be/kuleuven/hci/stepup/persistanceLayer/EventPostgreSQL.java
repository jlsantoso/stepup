package org.be.kuleuven.hci.stepup.persistanceLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.be.kuleuven.hci.stepup.model.Event;

public class EventPostgreSQL {
	
	public static void insertEvent(Event event) throws Exception{
		InitialContext cxt = new InitialContext();
		if ( cxt == null ) {
		   throw new Exception("Uh oh -- no context!");
		}

		DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

		if ( ds == null ) {
		   throw new Exception("Data source not found!");
		}
		Connection conn = ds.getConnection();
	    
		if(conn != null) 
		{
			Statement stmt = conn.createStatement();
			String query = createInsertStatementEvent(event);				
			stmt.executeUpdate(query);
			conn.close();
		}
	}
	
	public static String createInsertStatementEvent(Event event){
		String query = "INSERT INTO event(";
		String attributes="";
		String values = "";
		if (event.getUsername()!=null&&event.getVerb()!=null&&event.getObject()!=null&&event.getStartTime()!=null&&event.getOriginalRequest()!=null){
			attributes = "username,verb,starttime,object,originalrequest";
			values = "'"+event.getUsername()+"','"+event.getVerb()+"','"+new Timestamp(event.getStartTime().getTime())+"','"+event.getObject()+"','"+event.getOriginalRequest();
			if (event.getEndTime()!=null){
				attributes = attributes + ",endtime";
				values = values +","+ new Timestamp(event.getEndTime().getTime());
			}
			if (event.getContext()!=null){
				attributes = attributes + ",context";
				values = values +","+ event.getContext();
			}
			if (event.getTarget()!=null){
				attributes = attributes + ",target";
				values = values +","+ event.getTarget();
			}
			if (event.getLocation()!=null){
				attributes = attributes + ",location";
				values = values +","+ event.getLocation();
			}
			query = query + attributes + ") values (" + values + ")"; 
		}
		return null;
	}
}
