package swe.last.assigment;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.geotools.ows.ServiceException;
import org.geotools.ows.wms.Layer;
import org.geotools.ows.wms.WMS1_1_0.GetMapRequest;
import org.geotools.ows.wms.WMSCapabilities;
import org.geotools.ows.wms.WMSUtils;
import org.geotools.ows.wms.WebMapServer;
import org.geotools.ows.wms.response.GetMapResponse;


public class WMSConnection {
	
	public static String path;
		
	
	public static void wmsCon(){
    	
   	 String wmsGetCapabilitiesURL = "http://maps.heigit.org/osm-wms/service?service=WMS&request=GetCapabilities&version=1.1.0"; //explore other possibilities
   	 URL url = null;
   	 
	try {
		 url = new URL(wmsGetCapabilitiesURL);
   	 
   	 	 WebMapServer wms = null;
   	
   		 wms = new WebMapServer(url);
   	  	
   		 //GetCapabilities request
   		 WMSCapabilities capabilities = wms.getCapabilities();
   	
   		 //Assign the desired attributes to the map request
   		 GetMapRequest request = (GetMapRequest) wms.createGetMapRequest();
   		 request.setFormat("image/png");
   		 request.setDimensions("1000", "1000");
   		 request.setTransparent(true);
   		 request.setSRS("EPSG:4326");
   		 request.setBBox("-80.77,39.07,-72.55,45.03");
   		 
   
   		 for ( Layer layer : WMSUtils.getNamedLayers(capabilities) ) {
   		request.addLayer(layer);
   		 }
   		 
   		 //Prints the final URL (just to check the output URL before saving the image)
   		 System.out.println("Final URL: " + request.getFinalURL());
	 
   		 
   		 // New Class: WMSImageWriter
  
   		 path = "data\\image.png";
 		
 		 //Read and Write Image to File 
   		 File filePath = new File (path);
	
   		 GetMapResponse response;
	
		 response = (GetMapResponse) wms.issueRequest(request);
		 BufferedImage image = ImageIO.read(response.getInputStream());
		 ImageIO.write(image, "png", filePath);
		
		
		} catch (IOException | ServiceException e) {
		e.printStackTrace();
		
		}//catch
   	    	  
    }//wmsCon
      
}// class
