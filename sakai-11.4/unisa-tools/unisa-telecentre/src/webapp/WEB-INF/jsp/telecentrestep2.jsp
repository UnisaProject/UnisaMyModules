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
	<fmt:message key="telecentre.heading"/>                
	</sakai:heading>
	<sakai:group_heading>
	<tr>
	<td><html:link href="telecentre.do?action=myTelecentre&amp;type=K">My Telecentre Visits</html:link></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <td><html:link href="telecentre.do?action=myTelecentre&amp;type=P">Procedures</html:link></td> 
	</tr>
	</sakai:group_heading>
	<sakai:heading>
	<fmt:message key="welcome.heading"/>                
	</sakai:heading>
	<sakai:group_heading>
	<tr>
	<td><fmt:message key="student.number"/></td>&nbsp;&nbsp;
	<td> <bean:write name="telecentreForm" property="userId"/></td>&nbsp;&nbsp;&nbsp;&nbsp;
	 </tr>
	</sakai:group_heading><br/>
	 <fmt:message key="telecentre.date"/>&nbsp;&nbsp;
	 <bean:write name="telecentreForm" property="today"/>  
	  <sakai:group_table>
	  <tr>
	   <td><fmt:message key="telecentre.name"/></td>
			
			<td>
			 <bean:write name="telecentreForm" property="telecentreName"/>
			</td>	
			</tr>
			
			<tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.end"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
		
	  </sakai:group_table>
	</html:form>
</sakai:html>





