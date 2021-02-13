package eot.hira.rufai.vitoria;

import java.sql.SQLException;

//calls the methods created in the previous classes
public class GoogleEarthTweetMapper {

	public static void main(String[] args) throws SQLException {
	
		//Calling the methods created in the classes WMSConnection and KMLConnection	
		WMSConnection.wmsCon();
		KMLConnection.Tweets();
		
	}//main
}//class
