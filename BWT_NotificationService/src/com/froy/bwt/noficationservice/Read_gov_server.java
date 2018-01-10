package com.froy.bwt.noficationservice;

import com.froy.bwt.utils.FileManipulation;

public class Read_gov_server {

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
			System.out.println("Startitng ....");
			System.out.println("Gathered Data from gov server....");
			String data = FileManipulation
					.getUrlContents("https://apps.cbp.gov/bwt/bwt.xml");
			System.out.println("Done Gathered Data....");
			System.out.println("Searching Last updated date");
			int index1_date = data.indexOf("<last_updated_date>");
			int index2_date = data.indexOf("</last_updated_date>");
			System.out.println("Done");
			System.out.println("Searching Time Last Updated");
			int index1_time = data.indexOf("<last_updated_time>");
			int index2_time = data.indexOf("</last_updated_time>");
			System.out.println("Done");

			String date = data.substring(index1_date + 19, index2_date);
			String time = data.substring(index1_time + 19, index2_time);

			String title = date + "_" + time + ".txt";
			System.out.println("Doc Name:" + title);

			// creating document
			fileManip.write_to_file(data, title);
		}
		// *****************************************************************************


	}

}
