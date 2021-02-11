package swe.last.assigment;

import java.io.IOException;
import java.io.File;
import java.net.URL;
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

	public static void main(String[] args){
    	
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
   		 request.setBBox("-71.13,42.32,-71.03,42.42");
   		 	 
   		 //Prints the final URL (just to check the output URL)
   		 System.out.println("Final URL: " + request.getFinalURL());
   	 
   		 for ( Layer layer : WMSUtils.getNamedLayers(capabilities) ) {
   		request.addLayer(layer);
   		 }
	 
   		 //Read and Write Image to File
   		 File filePath = new File ("C:\\Users\\vi_lu\\Desktop\\Java_Eclipse\\LastAssigment\\data\\image.png");
	
   		 GetMapResponse response;
	
		 response = (GetMapResponse) wms.issueRequest(request);
		 BufferedImage image = ImageIO.read(response.getInputStream());
		 ImageIO.write(image, "png", filePath);
		
		
	} catch (IOException | ServiceException e) {
		e.printStackTrace();
		
		
	}//catch
   	    	  
    }//main
      
}// class
