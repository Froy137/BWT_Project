package com.froy.bwt.noficationservice;

import org.apache.log4j.Logger;



public class Main {
	static final Logger log = Logger.getLogger(Main.class);
	
	
	public static void main(String[] args) throws InterruptedException {
		log.info("Entry Notification Service");
		// TODO Auto-generated method stub
		Read_gov_server RGS = new Read_gov_server();
		
		while(true){
			Thread.sleep(5000); 
			RGS.start(true);
		}
	}

}
