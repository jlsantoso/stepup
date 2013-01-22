package org.be.kuleuven.hci.stepup.persistanceLayer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.notifications.SendMail;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventPostgreSQL {
	
	public static void insertEvent(Event event){
		Connection conn = null;
		Statement stmt = null;
		String query = "";
		try {
			InitialContext cxt = new InitialContext();
			if ( cxt == null ) {
			   throw new Exception("Uh oh -- no context!");
			}
			DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );
			if ( ds == null ) {
			   throw new Exception("Data source not found!");
			}
			conn = ds.getConnection();
			if(conn != null) 
			{
				stmt = conn.createStatement();
				 query = createInsertStatementEvent(event);		
				stmt.executeUpdate(query);
				stmt.close();
				conn.close();
			}
		} catch (NamingException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL", e.toString()).send();
				try {
					stmt.close();
					conn.close();
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			} catch (MessagingException e1) {
				
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (Exception e) {
			System.out.println(query);
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL", e.toString()).send();
				try {
					stmt.close();
					conn.close();
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		
	}
	
	public static String getOpenDB(String query, String pagination) {
		try {
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
				String querytxt=query.replace("from", ",count(*) OVER() AS full_count from") + " limit "+(10)+" OFFSET "+(Integer.parseInt(pagination)*10);
				System.out.println(querytxt);
				ResultSet rs = stmt.executeQuery(querytxt);
				JSONArray events = new JSONArray();
				ResultSetMetaData md = rs.getMetaData();
				int columns = md.getColumnCount();
				while (rs.next()){
					JSONObject jsonEvent = new JSONObject();
					for (int i=1;i<=columns;i++){
						jsonEvent.put(md.getColumnLabel(i), rs.getString(i));
					}
					events.put(jsonEvent);
				}
				rs.close();
				stmt.close();
				conn.close();
				//System.out.println(events.toString());
				return events.toString();
			}
			return null;
		    //return timestampmax;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} //load the driver
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 	
	}
	
	
	public static String createInsertStatementEvent(Event event){
		String query = "INSERT INTO event(";
		String attributes="";
		String values = "";
		if (event.getUsername()!=null&&event.getVerb()!=null&&event.getObject()!=null&&event.getStartTime()!=null&&event.getOriginalRequest()!=null){
			attributes = "timestamp,username,verb,starttime,object,originalrequest";
			values = "'"+event.getUsername()+"','"+event.getVerb()+"','"+new Timestamp(event.getStartTime().getTime())+"','"+event.getObject()+"','"+event.getOriginalRequest().toString().replaceAll("\"", "\\\"").replaceAll("'", "''")+"'";
			if (event.getEndTime()!=null){
				attributes = attributes + ",endtime";
				values = values +",'"+ new Timestamp(event.getEndTime().getTime())+"'";
			}
			if (event.getContext()!=null){
				attributes = attributes + ",context";
				values = values +",'"+ event.getContext()+"'";
			}
			if (event.getTarget()!=null){
				attributes = attributes + ",target";
				values = values +",'"+ event.getTarget()+"'";
			}
			if (event.getLocation()!=null){
				attributes = attributes + ",location";
				values = values +",'"+ event.getLocation()+"'";
			}
			query = query + attributes + ") values ('"+new Timestamp(Calendar.getInstance().getTimeInMillis())+"'," + values + ")"; 
		}
		return query;
	}
	
	
}
