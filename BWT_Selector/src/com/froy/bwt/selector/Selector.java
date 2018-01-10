package com.froy.bwt.selector;

import java.io.File;

import com.froy.bwt.utils.FileManipulation;

public class Selector {

	/**
	 * Function reads data from CBP server, stores file in Database and moves
	 * processed file to "Files_in_DB" folder.
	 * 
	 * @param storeinDB
	 *            {activates storing mechanism in DB and file storage if any txt
	 *            file is found in working dir}
	 * @return possible object is {@link void }
	 * 
	 */
	public void start(boolean storeinDB) {
		// Instantiating FileManipulation object
				FileManipulation fileManip = new FileManipulation();

		System.out.println("App Directory:" + System.getProperty("user.dir"));

		// getting working directory
		String workingDir = System.getProperty("user.dir");
		// getting all of the files in the directory
		File[] fileArray = fileManip.finder(workingDir + "//");
		SchemaInjector schemaInjector = new SchemaInjector();
		if (storeinDB == true) {
			if (fileArray.length > 0) {
				System.out.println("File found! Storage Mechanism Init");
				for (int i = 0; i < fileArray.length; i++) {
					// reading file from array
					String DataFileRead = fileManip.readFile(fileArray[i]
							.toString());
					String fileName = fileManip
							.returnFileNameFromPath(fileArray[i].getPath());
					// inserting into DB
					schemaInjector.dataIntoSchema(DataFileRead, fileName);
					// moving file to folder
					fileManip.move_file(fileArray[i].toString().split(
							workingDir)[1]);
				}
			} else {
				System.out
						.println("No Files found, Skipping storage mechanism");
			}
		}

	}

}
