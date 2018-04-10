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

	
  <logic:equal name="telecentreForm" property="isAdmin" value="0">
	<html:form action="/telecentre.do">	
	<sakai:messages/>

	<sakai:heading>
	<fmt:message key="telecentre.heading"/>                
	</sakai:heading>
	<sakai:group_heading>
	<tr>
	<td><html:link href="telecentre.do?action=myTelecentre&amp;type=M">My Telecentre Visits</html:link></td>
	</tr>
	</sakai:group_heading>
	<sakai:heading>
	<fmt:message key="welcome.heading"/>                
	</sakai:heading>
	<tr><td>
	 <sakai:instruction><fmt:message key="telecentre.instruction"/></sakai:instruction>
	 </td></tr>
	 <tr><td>
	  <sakai:instruction><fmt:message key="telecentre.instruction1"/></sakai:instruction>
	  </td></tr>
	    <sakai:group_table>
	   <tr><td>
	 <fmt:message key="telecentre.instruction2"/>
	  </td></tr>
	 <tr><td>
	  <fmt:message key="telecentre.instruction3"/>
	  </td></tr>
	  </sakai:group_table>
	  	<sakai:group_heading>
	<tr>
	   <td><fmt:message key="telecentre.studentNr"/></td>
	   <td><bean:write name="telecentreForm" property="userId"/></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="telecentre.telecentreDetails"/></td>
	</tr>
	</sakai:group_heading>
	  <tr><td>
	  <sakai:instruction><fmt:message key="telecentre.required"/></sakai:instruction>
	  </td></tr>
	

	 <fmt:message key="telecentre.date"/>&nbsp;&nbsp;
	 <bean:write name="telecentreForm" property="today"/>  
	  <sakai:group_table>
	  <tr>
	   <td><fmt:message key="telecentre.name"/></td>
			
			<td>
			<html:select property="code">
					<html:option value="-1">Please choose telecentre</html:option>	 
        		   <html:options  collection="telecentres" property="value" labelProperty="label" />
    		</html:select> 
			</td>	
	   </tr>
	   <tr>
		   <td><fmt:message key = "telecentre.code"></fmt:message></td>
		   <td><html:password property="telecentreCode" size="" maxlength="" ></html:password></td>
		   <td><fmt:message key = "telecentre.instruction4"></fmt:message></td>
	   </tr>
	   <tr>
	       <td>
			   <logic:notEqual name="telecentreForm" property="activeSessions" value="0">
		            <sakai:actions>
			          <html:submit styleClass="button" styleId="startBtn"  property="action" disabled="true">
			               <fmt:message key="telecentre.start"/>
			          </html:submit>
		           </sakai:actions>
		       </logic:notEqual>
		       <logic:equal name="telecentreForm" property="activeSessions" value="0">
		            <sakai:actions>
			          <html:submit styleClass="button" styleId="startBtn"  property="action" disabled="false">
			               <fmt:message key="telecentre.start"/>
			          </html:submit>
		           </sakai:actions>
		       </logic:equal>
		   </td>
	   </tr>
		
	  </sakai:group_table>
	   <html:hidden property="visited" value="false"/>
	</html:form>
	</logic:equal>
</sakai:html>





