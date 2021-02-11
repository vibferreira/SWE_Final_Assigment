package swe.last.assigment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class TextTweetsConnection {
	
	static String coord;
	public static void main(String[] args) throws SQLException {
		String fileURL = "C:\\Users\\vi_lu\\Desktop\\Java_Eclipse\\LastAssigment\\data\\tweet_db.db";
		String kmlFilePath = "C:\\Users\\vi_lu\\Desktop\\Java_Eclipse\\LastAssigment\\data\\tweetsKML.kml";
		String googleEarthExecPath = "C:\\Program Files\\\\Google\\Google Earth Pro\\client\\googleearth.exe";
	   	 
		
		// check if the file can be found 
		File f = new File(fileURL);
		boolean fileIsThere = f.exists();
		if (fileIsThere) {
				System.out.println("File found.");
			} else {
				System.out.println("File not found.");
			}
		
		String db_url = "jdbc:sqlite:" + fileURL;
		Connection conn = DriverManager.getConnection(db_url);
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM twitter");
		
		int total = 0;
		String latitude = null;
		String longitude = null;
		String createdAt = null;
		String tweets = null;
		
		while (resultSet.next ()) {
			total++;
			longitude = resultSet.getString("lng");
			latitude = resultSet.getString("lat");
			createdAt = resultSet.getString("created_at");
			tweets = resultSet.getString("tweet");
			//System.out.println(longitude + " - " + latitude + " - " + createdAt + " - " + tweets + "- " + total);
		}
		
		//Checking if the total of tweets is correct
		System.out.println("Total of tweets is:" + total);
		
		// Defining the KML documents	
		String kmlString = ""+
				"<?xml version = \"1.0\" encoding = \"UTF-8\"?>"+
				"<kml xmlns= \"/http://www.opengis.net/kml/2.2\">" + 
				"<Document>";
		
		for (int i = 0; i < total; i++) {
			
			kmlString += "<Placemark>\n"+
				    "<name> Tweet: " + i + "</name> \n" +
				    "<description>" + "Tweet: " + tweets + " \n Created at: " + createdAt + "</description> \n" +
				    "<Point>"+
				      "<coordinates>" + longitude  + "," + latitude + "</coordinates> \n" +
				    "</Point>" +
				  "</Placemark>";
		}//for
		
		kmlString += "</Document>\n" 
					+ "</kml>";
		
		
		 //Writes the kml string into the kml file and opens the kml file in Google Earth
	   	 try {
	   	 FileWriter fileWriter = new FileWriter(kmlFilePath);
	   	 fileWriter.write(kmlString);
	   	 fileWriter.close();    
	   	
	   	System.out.println("Launching Google Earth");
	   	 String params[] = new String[2];
	   	 params[0]= googleEarthExecPath;
	   	 params[1] = kmlFilePath;
	   		 
	   	 System.out.println("Displaying the KML Location");
	   	 Runtime.getRuntime().exec(params);
	   	 System.out.println("You should see the Location in Google Earth Pro now");
	   	 } catch (IOException e) {
	   		 System.out.println("Google Earth Pro or the KML file could not be opened");
	   		 e.printStackTrace();
	   	 }//catch
		
	}//main

}//class
