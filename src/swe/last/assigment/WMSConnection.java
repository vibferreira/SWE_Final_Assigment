package swe.last.assigment;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.geotools.ows.ServiceException;
import org.geotools.ows.wms.Layer;
import org.geotools.ows.wms.WMSCapabilities;
import org.geotools.ows.wms.WMSUtils;
import org.geotools.ows.wms.WebMapServer;
import org.geotools.ows.wms.request.GetMapRequest;
import org.geotools.ows.wms.response.GetMapResponse;
import org.xml.sax.SAXException;



public class WMSConnection {
	static BufferedImage image = null;
	
	public static void main(String[] args) {
		getLayer();
		
	}//main

	public static BufferedImage getLayer() {
		URL url = null;
		try {
		  url = new URL("https://giswebservices.massgis.state.ma.us/geoserver/wms");
		} catch (MalformedURLException e) {
		}

		WebMapServer wms = null;
		try {
		  wms = new WebMapServer(url);
		} catch (IOException e) {
		} catch (ServiceException e) {
		} catch (@SuppressWarnings("hiding") SAXException e) {
		}
	
		WMSCapabilities capabilities = wms.getCapabilities();
		//String serverName = capabilities.getService().getName();
		//String serverTitle = capabilities.getService().getTitle();
		//System.out.println("Capabilities retrieved from server: " + serverName + " (" + serverTitle + ")");
		
		GetMapRequest request = wms.createGetMapRequest();
		request.setFormat("image/png");
		request.setDimensions("800", "650"); 
		request.setTransparent(true);
		request.setSRS("EPSG:4326");
		request.setBBox("-71.13,42.32,-71.03,42.42");
		
		for ( Layer layer : WMSUtils.getNamedLayers(capabilities) ) {
			  request.addLayer(layer);
			}
		
		GetMapResponse response;
		try {
			response = wms.issueRequest(request);
			image = ImageIO.read(response.getInputStream());
			return image;
		
		} catch (IOException |ServiceException e) {e.printStackTrace();
			return null;
		} //catch
					
	
	}//getLayer

}//class
