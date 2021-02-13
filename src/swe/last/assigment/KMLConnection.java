package swe.last.assigment;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

public class KMLConnection {
  	 static String googleEarthExecPath;
  	 static String kmlFilePath;
  	 static String kmlString;
  	 static String lon, lat, createdAt, tweets;


	public static void kmlCon() {
		
			 //Setting the image, google earth and kml file path
			 File imagePath = new File ("C:\\Users\\vi_lu\\Desktop\\Java_Eclipse\\LastAssigment\\data\\image.png");
		   	 googleEarthExecPath = "C:\\Program Files\\\\Google\\Google Earth Pro\\client\\googleearth.exe";
		   	 kmlFilePath = "C:\\Users\\vi_lu\\Desktop\\Java_Eclipse\\LastAssigment\\data\\wmsKML.kml";
		   	 
		   	 //Defines the KML structure using the image file created in the previous step and defines transparency of 70%
		     kmlString = "<?xml version= \"1.0\" encoding=\"UTF-8\"?>\n"
		   			 + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"
		   			 + "<Folder> \n"
		   			 +"<name>WMS Ground Overlay</name>\n"
		   			 +"<description>An overlay of the WMS Map in Google Earth Desktop for Tweets made on the 5th of November, 2012</description>\n"
		   			 +"<GroundOverlay>\n"
		   			 +"<name>Study Area Overlay:Boston</name>\n"
		   			 +"<description>Overlay of the Areas of Boston where the tweets were made</description>\n"
		   			 +"<Icon>\n"
		   			 +"<href>" + imagePath + "</href>\n"
		   			 +"</Icon>\n"
		   			 + "<color> B3FFFFFF </color>"
		   			 +"<LatLonBox>\n"
		   			 +"<north>39.07</north>\n"
		   			 +"<south>45.03</south>\n"
		   			 +"<east>-80.77</east>\n"
		   			 +"<west>-72.55</west>\n"
		   			 +"</LatLonBox>\n"
		   			 +"</GroundOverlay>\n"
		   			 +"</Folder> \n"
		   			 +"</kml>";		   	 	   	 
		   	 
		   	 //NewClass: KMLFileWriter
		   	 //Writes the kml string into the kml file and opens the kml file in Google Earth
		   	 try {
		   	 FileWriter fileWriter = new FileWriter(kmlFilePath);
		   	 fileWriter.write(kmlString);
		   	 fileWriter.close();    
		   	 
		   	 
		   	 System.out.println("Launching Google Earth");
		   	 String params[] = new String[2];
		   	 params[0]= googleEarthExecPath;
		   	 params[1] = kmlFilePath;
		   	
		   	 System.out.println("Displaying the WMS");
		   	 Runtime.getRuntime().exec(params);
		   	 System.out.println("You should see the WMS in Google Earth Pro now");
		   	 } catch (IOException e) {
		   		 System.out.println("Google Earth Pro or the WMS file could not be opened");
		   		 e.printStackTrace();
		   	 }//catch
	
	}//main	 
		
}//class


