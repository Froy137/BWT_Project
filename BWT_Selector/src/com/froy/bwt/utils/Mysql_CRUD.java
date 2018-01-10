package com.froy.bwt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.froy.bwt.bwt.generated.BorderWaitTime.Port;

//CREATE TABLE `PortRecord` (
//		  `Id` int(11) NOT NULL AUTO_INCREMENT,
//		  `fileName` VARCHAR(100),
//		  `portNumber` VARCHAR(100),
//		  `boarder` VARCHAR(100),
//		  `portName` VARCHAR(100),
//		  `crossingName` VARCHAR(100),
//		  `hours` VARCHAR(100),
//		  `date` VARCHAR(100),
//		  `portStatus` VARCHAR(100),
//		  `commercial_MaximumLanes` VARCHAR(100),
//		  `commercial_StandardLanes_operational_status` VARCHAR(100),
//		  `commercial_StandardLanes_update_time` VARCHAR(100),
//		  `commercial_StandardLanes_delay_minutes` VARCHAR(100),
//		  `commercial_StandardLanes_lanes_open` VARCHAR(100),
//		  `commercial_FastLanes_operational_status` VARCHAR(100),
//		  `commercial_FastLanes_update_time` VARCHAR(100),
//		  `commercial_FastLanes_delay_minutes` VARCHAR(100),
//		  `commercial_FastLanes_lanes_open` VARCHAR(100),
//
//		  `passenger_MaximumLanes` VARCHAR(100),
//		  `passenger_StandardLanes_operational_status` VARCHAR(100),
//		  `passenger_StandardLanes_update_time` VARCHAR(100),
//		  `passenger_StandardLanes_delay_minutes` VARCHAR(100),
//		  `passenger_StandardLanes_lanes_open` VARCHAR(100),
//		  `passenger_NexusSentryLanes_operational_status` VARCHAR(100),
//		  `passenger_NexusSentryLanes_update_time` VARCHAR(100),
//		  `passenger_NexusSentryLanes_delay_minutes` VARCHAR(100),
//		  `passenger_NexusSentryLanes_lanes_open` VARCHAR(100),
//		  `passenger_ReadyLanes_operational_status` VARCHAR(100),
//		  `passenger_ReadyLanes_update_time` VARCHAR(100),
//		  `passenger_ReadyLanes_delay_minutes` VARCHAR(100),
//		  `passenger_ReadyLanes_lanes_open` VARCHAR(100),
//
//		  `pedestrian_MaximumLanes` VARCHAR(100),
//		  `pedestrian_StandardLanes_operational_status` VARCHAR(100),
//		  `pedestrian_StandardLanes_update_time` VARCHAR(100),
//		  `pedestrian_StandardLanes_delay_minutes` VARCHAR(100),
//		  `pedestrian_StandardLanes_lanes_open` VARCHAR(100),
//		  `pedestrian_ReadyLanes_operational_status` VARCHAR(100),
//		  `pedestrian_ReadyLanes_update_time` VARCHAR(100),
//		  `pedestrian_ReadyLanes_delay_minutes` VARCHAR(100),
//		  `pedestrian_ReadyLanes_lanes_open` VARCHAR(100),
//		  PRIMARY KEY (`Id`),
//		  UNIQUE KEY `Id_UNIQUE` (`Id`)
//		) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1


public class Mysql_CRUD {
	
    private String myDriver = "com.mysql.jdbc.Driver";
    private String myUrl = "jdbc:mysql://localhost/BWT";
    private String user ="root";
    private String pass = "mat";
    private String PortRecordFields="fileName,portNumber,boarder,portName,crossingName,hours,date,portStatus,commercial_MaximumLanes,commercial_StandardLanes_operational_status,commercial_StandardLanes_update_time,commercial_StandardLanes_delay_minutes,commercial_StandardLanes_lanes_open,commercial_FastLanes_operational_status,commercial_FastLanes_update_time,commercial_FastLanes_delay_minutes,commercial_FastLanes_lanes_open,passenger_MaximumLanes,passenger_StandardLanes_operational_status,passenger_StandardLanes_update_time,passenger_StandardLanes_delay_minutes,passenger_StandardLanes_lanes_open,passenger_NexusSentryLanes_operational_status,passenger_NexusSentryLanes_update_time,passenger_NexusSentryLanes_delay_minutes,passenger_NexusSentryLanes_lanes_open,passenger_ReadyLanes_operational_status,passenger_ReadyLanes_update_time,passenger_ReadyLanes_delay_minutes,passenger_ReadyLanes_lanes_open,pedestrian_MaximumLanes,pedestrian_StandardLanes_operational_status,pedestrian_StandardLanes_update_time,pedestrian_StandardLanes_delay_minutes,pedestrian_StandardLanes_lanes_open,pedestrian_ReadyLanes_operational_status,pedestrian_ReadyLanes_update_time,pedestrian_ReadyLanes_delay_minutes,pedestrian_ReadyLanes_lanes_open";
    
    
	
    /**
     * Function inserts XML RAW data into Raw_XML table in mySQL DB.
     * @param
     * 	Data XML File read in string format from CBP website 
     * 
     * 	@param
     * title this is the PK to link record to main record in Raw_XML data table.
     * @return
     *     possible object is
     *     {@link void }
     *     
     */ 
public void insert_into_DB_RawXML(String Data,String title){
	try
   {
	  
	  System.out.println("Inserting into DB on RawXML table");
     // create a mysql database connection

     Class.forName(myDriver);
     Connection conn = DriverManager.getConnection(myUrl, user, pass);
    
     // the mysql insert statement
     String query = "insert into BWT.Raw_XML (xmlData,fileName)"
       + " values (?,?)";

     // create the mysql insert preparedstatement
     PreparedStatement preparedStmt = conn.prepareStatement(query);
     preparedStmt.setString (1, Data);
     preparedStmt.setString (2, title.toString());
     // execute the preparedstatement
     preparedStmt.execute();
     
     conn.close();
     System.out.println("insertion done on RawXML table!");
   }
   catch (Exception e)
   {
     System.err.println("Got an exception on insertion for RawXML table!");
     System.err.println(e.getMessage());
   }
 }

public String yearStrip(String date){
	String []array= date.split("/");
	return array[0]+"/"+array[1];
}


/**
 * Function inserts ArrayList of port object data into PortRecord table in mySQL DB.
 * @param
 * 	Data Port data object extracted from XML 
 * 
 * 	@param
 * title this is the FK to link record to main record in Raw_XML data table.
 * @return
 *     possible object is
 *     {@link void }
 *     
 */ 
public void insert_into_DB_PortRecord (ArrayList<Port> Data, String title)
{	

	for(Port port:Data){
	
		String[] fields=PortRecordFields.split(",");
		int amount_fields=fields.length;
	
try
 {
	  
	  System.out.println("Inserting into DB on Table PortRecord");
   // create a mysql database connection

   Class.forName(myDriver);
   Connection conn = DriverManager.getConnection(myUrl, user, pass);
  
   
   String question="";
   //build question marks
   for(int i=0;i<amount_fields;i++){
	   	   question=question+"?,"; 
   }
   //removing last comma
   question=question.substring(0, question.length()-1);
   
   // the mysql insert statement
   String query = "insert into BWT.PortRecord ("+PortRecordFields+")"
     + " values ("+question+")";

   // create the mysql insert preparedstatement
   PreparedStatement preparedStmt = conn.prepareStatement(query);
   
   //String [] fieldsArray=PortRecordFields.split(",");//39 fields? <- confirmed

		   preparedStmt.setString (1, title.toString());
		   preparedStmt.setString (2,port.getPortNumber());
		   preparedStmt.setString (3,port.getBorder());
		   preparedStmt.setString (4,port.getPortName());
		   preparedStmt.setString (5,port.getCrossingName());
		   preparedStmt.setString (6,port.getHours());
		   preparedStmt.setString (7,yearStrip(port.getDate()));
		   preparedStmt.setString (8,port.getPortStatus());
		   preparedStmt.setString (9,port.getCommercialVehicleLanes().getMaximumLanes());

		   String commercial_StandardLanes_operational_status="";
		   String commercial_StandardLanes_update_time="";
		   String commercial_StandardLanes_delay_minutes="";
		   String commercial_StandardLanes_lanes_open="";
		   List<JAXBElement<String>> OUD = port.getCommercialVehicleLanes().getStandardLanes().getOperationalStatusOrUpdateTimeOrDelayMinutes();
		   for(JAXBElement<String> list: OUD){
			   if(list.getName().getLocalPart().equals("operational_status")){commercial_StandardLanes_operational_status=list.getValue();}
			   else if (list.getName().getLocalPart().equals("update_time")){commercial_StandardLanes_update_time=list.getValue();}
			   else if (list.getName().getLocalPart().equals("delay_minutes")){commercial_StandardLanes_delay_minutes=list.getValue();}
			   else if (list.getName().getLocalPart().equals("lanes_open")){commercial_StandardLanes_lanes_open=list.getValue();}
		   }
		   preparedStmt.setString (10,commercial_StandardLanes_operational_status);
		   preparedStmt.setString (11,commercial_StandardLanes_update_time);
		   preparedStmt.setString (12,commercial_StandardLanes_delay_minutes);
		   preparedStmt.setString (13,commercial_StandardLanes_lanes_open);
   
		   String commercial_FastLanes_operational_status="";
		   String commercial_FastLanes_update_time="";
		   String commercial_FastLanes_delay_minutes="";
		   String commercial_FastLanes_lanes_open="";
		   List<JAXBElement<String>> OUDF = port.getCommercialVehicleLanes().getFASTLanes().getOperationalStatusOrUpdateTimeOrDelayMinutes();
		   for(JAXBElement<String> list: OUDF){
			   if(list.getName().getLocalPart().equals("operational_status")){commercial_FastLanes_operational_status=list.getValue();}
			   else if (list.getName().getLocalPart().equals("update_time")){commercial_FastLanes_update_time=list.getValue();}
			   else if (list.getName().getLocalPart().equals("delay_minutes")){commercial_FastLanes_delay_minutes=list.getValue();}
			   else if (list.getName().getLocalPart().equals("lanes_open")){commercial_FastLanes_lanes_open=list.getValue();}
		   }
   		   preparedStmt.setString (14,commercial_FastLanes_operational_status);
		   preparedStmt.setString (15,commercial_FastLanes_update_time);
		   preparedStmt.setString (16,commercial_FastLanes_delay_minutes);
		   preparedStmt.setString (17,commercial_FastLanes_lanes_open);
		   preparedStmt.setString (18,port.getPassengerVehicleLanes().getMaximumLanes());
		   
		   String passenger_StandardLanes_operational_status="";
		   String passenger_StandardLanes_update_time="";
		   String passenger_StandardLanes_delay_minutes="";
		   String passenger_StandardLanes_lanes_open="";
		   List<JAXBElement<String>> OUD2 = port.getPassengerVehicleLanes().getStandardLanes().getOperationalStatusOrUpdateTimeOrDelayMinutes();
		   for(JAXBElement<String> list: OUD2){
			   if(list.getName().getLocalPart().equals("operational_status")){passenger_StandardLanes_operational_status=list.getValue();}
			   else if (list.getName().getLocalPart().equals("update_time")){passenger_StandardLanes_update_time=list.getValue();}
			   else if (list.getName().getLocalPart().equals("delay_minutes")){
				   
				   if(list.getValue().equals("")){
				   passenger_StandardLanes_delay_minutes="0 to 0";
				   }
			   else{
				   passenger_StandardLanes_delay_minutes="0 to "+list.getValue();
			   }
			   }
			   else if (list.getName().getLocalPart().equals("lanes_open")){passenger_StandardLanes_lanes_open=list.getValue();}
		   }
		   preparedStmt.setString (19,passenger_StandardLanes_operational_status);
		   preparedStmt.setString (20,passenger_StandardLanes_update_time);
		   preparedStmt.setString (21,passenger_StandardLanes_delay_minutes);
		   preparedStmt.setString (22,passenger_StandardLanes_lanes_open);
		   
		   String passenger_NexusSentryLanes_operational_status="";
		   String passenger_NexusSentryLanes_update_time="";
		   String passenger_NexusSentryLanes_delay_minutes="";
		   String passenger_NexusSentryLanes_lanes_open="";
		   List<JAXBElement<String>> OUD2N = port.getPassengerVehicleLanes().getNEXUSSENTRILanes().getOperationalStatusOrUpdateTimeOrDelayMinutes();
		   for(JAXBElement<String> list: OUD2N){
			   if(list.getName().getLocalPart().equals("operational_status")){passenger_NexusSentryLanes_operational_status=list.getValue();}
			   else if (list.getName().getLocalPart().equals("update_time")){passenger_NexusSentryLanes_update_time=list.getValue();}
			   else if (list.getName().getLocalPart().equals("delay_minutes")){passenger_NexusSentryLanes_delay_minutes=list.getValue();}
			   else if (list.getName().getLocalPart().equals("lanes_open")){passenger_NexusSentryLanes_lanes_open=list.getValue();}
		   }
		   preparedStmt.setString (23,passenger_NexusSentryLanes_operational_status);
		   preparedStmt.setString (24,passenger_NexusSentryLanes_update_time);
		   preparedStmt.setString (25,passenger_NexusSentryLanes_delay_minutes);
		   preparedStmt.setString (26,passenger_NexusSentryLanes_lanes_open);
		   
		   String passenger_ReadyLanes_operational_status="";
		   String passenger_ReadyLanes_update_time="";
		   String passenger_ReadyLanes_delay_minutes="";
		   String passenger_ReadyLanes_lanes_open="";
		   List<JAXBElement<String>> OUD2R = port.getPassengerVehicleLanes().getReadyLanes().getOperationalStatusOrUpdateTimeOrDelayMinutes();
		   for(JAXBElement<String> list: OUD2R){
			   if(list.getName().getLocalPart().equals("operational_status")){passenger_ReadyLanes_operational_status=list.getValue();}
			   else if (list.getName().getLocalPart().equals("update_time")){passenger_ReadyLanes_update_time=list.getValue();}
			   else if (list.getName().getLocalPart().equals("delay_minutes")){passenger_ReadyLanes_delay_minutes=list.getValue();}
			   else if (list.getName().getLocalPart().equals("lanes_open")){passenger_ReadyLanes_lanes_open=list.getValue();}
		   }
		   preparedStmt.setString (27,passenger_ReadyLanes_operational_status);
		   preparedStmt.setString (28,passenger_ReadyLanes_update_time);
		   preparedStmt.setString (29,passenger_ReadyLanes_delay_minutes);
		   preparedStmt.setString (30,passenger_ReadyLanes_lanes_open);
		   preparedStmt.setString (31,port.getPedestrianLanes().getMaximumLanes());
		   
		   String pedestrian_StandardLanes_operational_status="";
		   String pedestrian_StandardLanes_update_time="";
		   String pedestrian_StandardLanes_delay_minutes="";
		   String pedestrian_StandardLanes_lanes_open="";
		   List<JAXBElement<String>> OUD3 = port.getPedestrianLanes().getStandardLanes().getOperationalStatusOrUpdateTimeOrDelayMinutes();
		   for(JAXBElement<String> list: OUD3){
			   if(list.getName().getLocalPart().equals("operational_status")){pedestrian_StandardLanes_operational_status=list.getValue();}
			   else if (list.getName().getLocalPart().equals("update_time")){pedestrian_StandardLanes_update_time=list.getValue();}
			   else if (list.getName().getLocalPart().equals("delay_minutes")){pedestrian_StandardLanes_delay_minutes=list.getValue();}
			   else if (list.getName().getLocalPart().equals("lanes_open")){pedestrian_StandardLanes_lanes_open=list.getValue();}
		   }
		   preparedStmt.setString (32,pedestrian_StandardLanes_operational_status);
		   preparedStmt.setString (33,pedestrian_StandardLanes_update_time);
		   preparedStmt.setString (34,pedestrian_StandardLanes_delay_minutes);
		   preparedStmt.setString (35,pedestrian_StandardLanes_lanes_open);

		   String pedestrian_ReadyLanes_operational_status="";
		   String pedestrian_ReadyLanes_update_time="";
		   String pedestrian_ReadyLanes_delay_minutes="";
		   String pedestrian_ReadyLanes_lanes_open="";
		   List<JAXBElement<String>> OUD3r = port.getPedestrianLanes().getReadyLanes().getOperationalStatusOrUpdateTimeOrDelayMinutes();
		   for(JAXBElement<String> list: OUD3r){
			   if(list.getName().getLocalPart().equals("operational_status")){pedestrian_ReadyLanes_operational_status=list.getValue();}
			   else if (list.getName().getLocalPart().equals("update_time")){pedestrian_ReadyLanes_update_time=list.getValue();}
			   else if (list.getName().getLocalPart().equals("delay_minutes")){pedestrian_ReadyLanes_delay_minutes=list.getValue();}
			   else if (list.getName().getLocalPart().equals("lanes_open")){pedestrian_ReadyLanes_lanes_open=list.getValue();}
		   }
		   preparedStmt.setString (36,pedestrian_ReadyLanes_operational_status);
		   preparedStmt.setString (37,pedestrian_ReadyLanes_update_time);
		   preparedStmt.setString (38,pedestrian_ReadyLanes_delay_minutes);
		   preparedStmt.setString (39,pedestrian_ReadyLanes_lanes_open);
   // execute the preparedstatement
   preparedStmt.execute();
   
   conn.close();
   System.out.println("insertion Done for PortRecordTable!");
 }
 catch (Exception e)
 {
   System.err.println("Got an exception on insertion for PortRecord Table!");
   System.err.println(e.getMessage());
 }

	}//end of for loop
}
/**
 * Function queries DB for data from Raw_XML table. (plans to make this generic read)
 * @param
 * 	id PK for Raw_XML table 
 * 
 * 	
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */ 

public String read_from_DB(int id){
	try
    {
      // create our mysql database connection
	  System.out.println("Reading from DB");
	  //String myDriver = "com.mysql.jdbc.Driver";
      //String myUrl = "jdbc:mysql://localhost/BWT";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, user, pass);
      
      // our SQL SELECT query. 
      // if you only need a few columns, specify them by name instead of using "*"
      String query = "SELECT * FROM BWT.Raw_XML where Id ="+id;

      // create the java statement
      Statement st = conn.createStatement();
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      String xmlData="";
      // iterate through the java resultset
      while (rs.next())
      {
        int ID = rs.getInt("Id");
        xmlData = rs.getString("xmlData");
   
        // print the results
       // System.out.format("%s, %s\n", ID, xmlData);
      }
      st.close();
      System.out.println("reading done!");
      return xmlData;
    }
    catch (Exception e)
    {
      System.err.println("Got an exception on insert!");
      System.err.println(e.getMessage());
      return null;
    }
	
  }

	
}
