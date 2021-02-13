package swe.last.assigment;

import java.sql.SQLException;

public class GoogleEarthTweetMapper {

	public static void main(String[] args) throws SQLException {
	
		WMSConnection.wmsCon();
		TextTweetsConnection.Tweets();
		
	}

}
