<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.examtimetable.ApplicationResources"/>

<sakai:html>
	 <head>
	    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	    <title>Google Maps</title>
	    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAAxTKyJhjlQN4NBLJr6sloxRMzdhcF3PEMd7cQEizHX70v3AJdRSyl-CjWbPapPxwhH2TKnd4mxdztA type="text/javascript"></script>
  	</head>
 	 <body onunload="GUnload()">     
     <script type="text/javascript">
    //<![CDATA[
    
    function  displayVenueMap(latitude,longitude,venueAddress){	
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
	      var marker = createMarker(point,'<div style="width:240px">' + venueAddress + '<\/div>');
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
       
    <html:form action="/displayExamTimetable">
		<html:hidden property="currentPage" value="displayMap"/>
		<html:hidden property="latitude"/>
		<html:hidden property="longitude"/>
		<html:hidden property="venueAddress"/>	
	<sakai:group_table>
	<tr><td align="center">
		<sakai:heading>
			<fmt:message key="page.heading.venueMap"/>:		
			<bean:write name="examTimetableDisplayForm" property="venueDesc"/>			
		</sakai:heading>	
		(<fmt:message key="page.latitude"/>:	
		<bean:write name="examTimetableDisplayForm" property="latitude"/>
		<fmt:message key="page.longitude"/>:	
		<bean:write name="examTimetableDisplayForm" property="longitude"/>)<br>
	</td></tr>
	<tr><td>
		<div id="map" style="width: 550px; height: 450px"></div>
	</td></tr>
	</sakai:group_table>
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.back"/>
		</html:submit>			
	</sakai:actions>	
 	</html:form>   	
 	 <script type="text/javascript">
 	  	displayVenueMap(document.forms["examTimetableDisplayForm"].elements["latitude"].value,document.forms["examTimetableDisplayForm"].elements["longitude"].value,document.forms["examTimetableDisplayForm"].elements["venueAddress"].value); 	  	
 	  </script>
 	</body> 	 
</sakai:html>