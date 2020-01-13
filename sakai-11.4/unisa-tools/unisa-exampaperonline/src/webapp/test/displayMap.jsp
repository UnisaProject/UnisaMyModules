<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd" > 

<HTML>
 <head>
	    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	    <title>Google Maps</title>
	    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAAxTKyJhjlQN4NBLJr6sloxRMzdhcF3PEMd7cQEizHX70v3AJdRSyl-CjWbPapPxwhH2TKnd4mxdztA type="text/javascript"></script>
 </head>
 	 <body onunload="GUnload()">     
     <script type="text/javascript">
    //<![CDATA[
    
    function  displayVenueMap(latitude,longitude,markerText){	
    	  if (GBrowserIsCompatible()) { 
	
	      // A function to create the marker and set up the event window
	      // Dont try to unroll this function. It has to be here for the function closure
	      // Each instance of the function preserves the contends of a different instance
	      // of the "marker" and "html" variables which will be needed later when the event triggers.    
	      function createMarker(point,html) {
	        var marker = new GMarker(point);
	        GEvent.addListener(marker, "click", function() {
	          marker.openInfoWindowHtml(html);
	        });
	        return marker;
	      }
	
	      // Display the map, with some controls and set the initial location 
	      var map = new GMap2(document.getElementById("map"));
	      map.addControl(new GLargeMapControl());
	      map.addControl(new GMapTypeControl());
	      map.setCenter(new GLatLng(latitude,longitude),8);
	      
		  map.setZoom(15);
	            	    
	      // Set a marker with info windows 
	      var point = new GLatLng(latitude,longitude);
	      var marker = createMarker(point,'<div style="width:240px">' + markerText + '<\/div>');
	      map.addOverlay(marker);
	    }
	    //'<div style="width:240px">venueAddress<\/div>'
	    // display a warning if the browser was not compatible
	    else {
	      alert("Sorry, the Google Maps API is not compatible with this browser");
	    }
	}
	
    // This Javascript is based on code provided by the
    // Community Church Javascript Team
    // http://www.bisphamchurch.org.uk/   
    // http://econym.org.uk/gmap/

    //]]>
    </script>
	<form name="my_form">
		<%@page import="java.util.*" %>
		<% String latitude="";
		   String longitude="";
		   String markerText="";
			if (request.getParameter("latitude")==null)
				latitude="0.0";
			else
				latitude=request.getParameter("latitude");
			if (request.getParameter("longitude")==null)
				longitude="0.0";
			else
				longitude=request.getParameter("longitude");
			if (request.getParameter("markerText")==null)
				markerText="";
			else
				markerText=request.getParameter("markerText");
			String referrer="";
			  if (request.getParameter("referrer")==null)
					referrer="";
				else
					referrer=request.getParameter("referrer");
		%>
		<input type=hidden name="latitude" value=<%=latitude%>></input>
		<input type=hidden name="longitude" value=<%=longitude%>></input>
		<input type=hidden name="markerText" value="<%=markerText%>"></input>
		<table>
			<tr>
				<td align="center">(Latitude : <%=latitude%>, Longitude : <%=longitude%>)</td>
			</tr>
			<tr>
				<td>
					<div id="map" style="width: 550px; height: 450px"></div>
				</td>
			</tr>
	   	</table>
	   <% if (!referrer.equalsIgnoreCase("")) 
	   {%>
	   		<a href=<%=referrer%>>Back</a>
	   <%	   
	   }
	   %>
		
  	</form>
   	<script type="text/javascript">
 	  	displayVenueMap(document.forms["my_form"].elements["latitude"].value,document.forms["my_form"].elements["longitude"].value,document.forms["my_form"].elements["markerText"].value); 	  	
 	</script>
 </body> 
</html>
