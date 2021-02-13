package eot.hira.rufai.vitoria;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*Connects to the tweet database, retrieves the necessary information from it, 
 * generates the KML file, writes the KML string into a KML file and opens it in Google Earth. 
 */

public class KMLConnection {
	
	static String lon, lat, createdAt, tweets, timeStamp;
	public static String kmlString;
	
	public static void Tweets() throws SQLException {
		
		//defying the paths to the database, kml file, google earth exe file and the image
		String fileURL = "data\\tweet_db.db";
		String kmlFilePath = "C:\\Users\\vi_lu\\Desktop\\Java_Eclipse\\LastAssigment\\data\\tweetsKML.kml";
		String googleEarthExecPath = "C:\\Program Files\\Google\\Google Earth Pro\\client\\googleearth.exe";
		File imagePath = new File ("C:\\Users\\vi_lu\\Desktop\\Java_Eclipse\\LastAssigment\\data\\image.png");

		//Asks the user to input the desired path to store the image, the tweet kml and to access google earth
//		Scanner scan = new Scanner(System.in);
// 		System.out.println("Enter the file path of your Google Earth and the name of your googleearth.exe file: ");
// 		System.out.println("Your path should look like this:" + googleEarthExecPath);
// 		String pathInput = scan.nextLine();
// 		System.out.println("You entered: " + pathInput);  
 		
		// check if the file can be found 
		File f = new File(fileURL);
		boolean fileIsThere = f.exists();
		if (fileIsThere) {
				System.out.println("File found.");
			} else {
				System.out.println("File not found.");
			}
		
		//execute the sql query to retrieve the intended data from the database
		String db_url = "jdbc:sqlite:" + fileURL;
		Connection conn = DriverManager.getConnection(db_url);
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM twitter");
		
		List<Object> longitude = new LinkedList<Object>();
		List<Object> latitude = new LinkedList<Object>();
		List<Object> createdDate = new LinkedList<Object>();
		List<Object> tweetsMade = new LinkedList<Object>();
		List<Object> timeStampFinal = new LinkedList<Object>();
		int total = 0;

		while (resultSet.next ()) {
			total++;
			lon = resultSet.getString("lng");
			lat = resultSet.getString("lat");
			createdAt = resultSet.getString("created_at");
			timeStamp = resultSet.getString("time_stamp");
			tweets = resultSet.getString("tweet");
			longitude.add(lon);
			latitude.add(lat);
			createdDate.add(createdAt);
			tweetsMade.add(tweets);
			timeStampFinal.add(timeStamp);
		}//while
		
		// Defining the KML file and styling the point icon	
		String kmlString = ""+
				"<?xml version = \"1.0\" encoding = \"UTF-8\"?> \n"+
				"<kml xmlns= \"/http://www.opengis.net/kml/2.2\"> \n" + 
				"<Document> \n"
	   			 +"<name>WMS Ground Overlay</name>\n"
	   			 +"<description>An overlay of the WMS Map in Google Earth Desktop for Tweets made on the 5th of November, 2012</description>\n"
	   			 +"<GroundOverlay>\n"
	   			 +"<name>Study Area Overlay:Boston</name>\n"
	   			 +"<description>Overlay of the Areas of Boston where the tweets were made</description>\n"
	   			 +"<Icon>\n"
	   			 +"<href>" + imagePath + "</href>\n"
	   			 +"</Icon>\n"
	   			 +"<Style id=\"randomColorIcon\">"
	   			 + "<IconStyle>\n"
	   			 +"<scale>" + 1 +"</scale>"
	   			 +"<Icon>"
	   			 +"<href>" + "https://github.com/vibferreira/SWE_Final_Assigment/blob/main/data/twitter_img.png?raw=true" + "</href>"
	   			 +"</Icon>"
	   			 +"</IconStyle>"
	   			 +"</Style>"
	   			 + "<color> B3FFFFFF </color>"
	   			 +"<LatLonBox>\n"
	   			 +"<north>39.07</north>\n"
	   			 +"<south>45.03</south>\n"
	   			 +"<east>-80.77</east>\n"
	   			 +"<west>-72.55</west>\n"
	   			 +"</LatLonBox>\n"
	   			 +"</GroundOverlay>\n";
		
		//Loops into the information retrieved from the query to generates the tweet points for each location
		for (int i = 0; i < total; i++) {
			kmlString += "<Placemark>\n"+
					"<styleUrl>" + "#randomColorIcon" +"</styleUrl> \n" +
					"<TimeStamp> \n"+
					"\t <when>" + timeStampFinal.get(i) +"</when> \n" +
					"</TimeStamp>"+
				    "<description>" + "Tweet: "  + tweetsMade.get(i)+ " \n Created at: " + createdDate.get(i) + "</description> \n" +
				    "<Point>"+
				      "<coordinates>" + longitude.get(i) + "," + latitude.get(i) + 400 + "</coordinates> \n" +
				      "<altitudeMode>" + "relativeToGround" + "</altitudeMode>" +
				      "<extrude>" + 1 +"</extrude>"+
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
