package com.froy.bwt.noficationservice;

import org.apache.log4j.Logger;


import com.froy.bwt.utils.FileManipulation;

public class Read_gov_server {
	static final Logger log = Logger.getLogger(Read_gov_server.class);
	/**
	 * Function reads data from CBP server, stores file in Database and moves
	 * processed file to "Files_in_DB" folder.
	 * 
	 * @param read
	 *            {enables reading functionality from CBP website}
	 * @param storeinDB
	 *            {activates storing mechanism in DB and file storage if any txt
	 *            file is found in working dir}
	 * @return possible object is {@link void }
	 * 
	 */
	public void start(boolean read) {
		// Instantiating FileManipulation object
		FileManipulation fileManip = new FileManipulation();

		if (read == true) {
			log.info("Startitng ....");
			log.info("Gathered Data from gov server....");
			String data = FileManipulation
					.getUrlContents("https://apps.cbp.gov/bwt/bwt.xml");
			log.info("Done Gathered Data....");
			log.info("Searching Last updated date");
			int index1_date = data.indexOf("<last_updated_date>");
			int index2_date = data.indexOf("</last_updated_date>");
			log.info("Done");
			log.info("Searching Time Last Updated");
			int index1_time = data.indexOf("<last_updated_time>");
			int index2_time = data.indexOf("</last_updated_time>");
			log.info("Done");

			String date = data.substring(index1_date + 19, index2_date);
			String time = data.substring(index1_time + 19, index2_time);

			String title = date + "_" + time + ".txt";
			log.info("Doc Name:" + title);

			// creating document
			fileManip.write_to_file(data, title);
		}
		// *****************************************************************************


	}

}
