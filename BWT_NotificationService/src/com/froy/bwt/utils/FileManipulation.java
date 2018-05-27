package com.froy.bwt.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;



/*#CREATE DATABASE `BWT` !40100 DEFAULT CHARACTER SET latin1 ;
#Create table BWT.Raw_XML (xmlData varchar(65000xmlData));*/

public class FileManipulation {
	static final Logger log = Logger.getLogger(FileManipulation.class);
	
	//this will return all txt files found under a dirName
	 public File[] finder( String dirName){
	        
		 File dir = new File(dirName);

	        return dir.listFiles(new FilenameFilter() { 
	                 public boolean accept(File dir, String filename)
	                      { 
	                	 return filename.endsWith(".txt");
	                	 }
	        } );
	 }

	 
	 //readding file from working DIR
	 public String readFile(String fileName){
	 //read file
		BufferedReader br = null;
		FileReader fr = null;
		String Data = "";
		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;
			
			while ((sCurrentLine = br.readLine()) != null) {
				Data+=sCurrentLine;
				//log.info(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return Data;
	}
	 
	 

//This method moves the files from the working directory to a folder in the working directory call "Files_in_DB"
public void move_file(String file_name){

	InputStream inStream = null;
    OutputStream outStream = null;
///2017-12-24_22:21:38.txt
	try{

		log.info("Moving File");
		String workingDir=System.getProperty("user.dir");
		
		File afile =new File(workingDir+"//"+file_name);
		
	    File bfile =new File(workingDir+"//Files_in_DB//"+file_name.substring(1, file_name.length()));
	    

	    inStream = new FileInputStream(afile);
	    outStream = new FileOutputStream(bfile);

	    byte[] buffer = new byte[15024];

	    int length;
	    //copy the file content in bytes
	    while ((length = inStream.read(buffer)) > 0){

	    	outStream.write(buffer, 0, length);

	    }

	    inStream.close();
	    outStream.close();

	    //delete the original file
	    afile.delete();

	    log.info("File copied successfully!");

	}catch(IOException e){
	    e.printStackTrace();
	}
	
}

//This method gets the content (XML Data) from the provided URL 
public static String getUrlContents(String theUrl)
{
  StringBuilder content = new StringBuilder();

  // many of these calls can throw exceptions, so i've just
  // wrapped them all in one try/catch statement.
  try
  {
    // create a url object
    URL url = new URL(theUrl);

    // create a urlconnection object
    URLConnection urlConnection = url.openConnection();

    // wrap the urlconnection in a bufferedreader
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

    String line;

    // read from the urlconnection via the bufferedreader
    while ((line = bufferedReader.readLine()) != null)
    {
      content.append(line + "\n");
    }
    bufferedReader.close();
  }
  catch(Exception e)
  {
    e.printStackTrace();
  }
  return content.toString();
}

//This method writes a file in working dir with specified data and title
public void write_to_file(String data,String title){
	PrintWriter writer;
	try {
		writer = new PrintWriter(title, "UTF-8");
		writer.println(data);
		writer.close();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	//This method will return a file name from a given string path
public String returnFileNameFromPath(String path){
	
	///Users/Froy/Documents/workspace/bwt/2017-12-24_22:21:38.txt
	
	String [] splitArray=path.split("/");
	int arrayLength = splitArray.length;
	String retValue = splitArray[arrayLength-1];
	return retValue;
	
}
	
}




