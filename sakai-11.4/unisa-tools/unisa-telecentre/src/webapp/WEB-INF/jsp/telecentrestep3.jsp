<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
<script>


$(document).ready(function(){
	
	
	$(".button").show();
    $(".button").click(function(){
    	
        $(".button").hide();
        $("body").css("cursor", "progress");
        
    });
});

</script>	

	 <sakai:tool_bar>	   	
       	<html:link href="telecentre.do?action=view"><input type="image" align=right src="/library/image/printer.png" onClick="window.print()"/></html:link> 
	</sakai:tool_bar>  
	<html:form action="/telecentre.do">	
	<sakai:messages/>
     <sakai:heading>
     <tr><td></td><fmt:message key="telecentre.heading"/></td></tr>
     </sakai:heading>
	 <sakai:group_heading>
	 <tr><td>
     <fmt:message key="telecentre.last"/></td>&nbsp;&nbsp;<td><bean:write name="telecentreForm" property="userId"/> </td></tr>
     </sakai:group_heading><br/>
	
	
	 <fmt:message key="telecentre.date"/>&nbsp;&nbsp;
	 <bean:write name="telecentreForm" property="today"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="telecentre.telecentreDetails"/>
	  <sakai:group_table>
	  <tr>
	   <td><fmt:message key="telecentre.name"/></td>
	   <td><bean:write name="telecentreForm" property="telecentreName"/></td>
	 </tr>
	 <tr>
	   <td><fmt:message key="telecentre.studentNr"/></td>
	   <td><bean:write name="telecentreForm" property="userId"/></td>
	 </tr>
	 <tr>
       <td><fmt:message key="telecentre.duration"/></td>	
       <td><bean:write name="telecentreForm" property="currentVisistDuration"/>&nbsp;&nbsp;
       <!-- Sifiso Changes:Added:2016/06/28-Added duration units below to indicate the duration used-->
       <bean:write name="telecentreForm" property="durationUnit"/> </td>
       <!-- <fmt:message key="tele.hours"/></td> Sifiso Changes:Commented out message and added duration unit above-2016/06/28-->
	 </tr>
	 <tr>
	   <td><fmt:message key="telecentre.timeleftForCentre"/></td>
	   <td><bean:write name="telecentreForm" property="centreAvailHrs"/>&nbsp;&nbsp;<fmt:message key="tele.hours"/></td>	
	 </tr>
	 <tr>
	   <td><fmt:message key="telecentre.timeleftForStu"/></td>
	   <td><bean:write name="telecentreForm" property="stuAvailHrs"/>&nbsp;&nbsp;<fmt:message key="tele.hours"/></td>	
	 </tr>
	 <tr><td>
		      <sakai:actions>
			      <html:submit styleClass="button" property="action">
			              <fmt:message key="telecentre.cancel"/>
			      </html:submit>
		     </sakai:actions>
		   </td>
	  </tr>
      </sakai:group_table>
	</html:form>
</sakai:html>





