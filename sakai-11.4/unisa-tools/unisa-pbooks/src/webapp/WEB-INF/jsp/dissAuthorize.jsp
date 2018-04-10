<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<html:form action="authorisation">
		<sakai:heading>
			<fmt:message key="function.dissbooklistinstruction"/>
			<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/>
		</sakai:heading>
    	<p/>
   		<sakai:group_table>
       		<tr>
           		<td><fmt:message key="function.datelastmodified"/></td>
               	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
           	</tr>
       		<tr>
       			<td><fmt:message key="function.lastmodifiedby"/></td>
       			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
       		</tr>
       		<tr>
           		<td><fmt:message key="function.currentstatus"/></td>
           		<td><fmt:message key="function.tutorbookstatus"/></td>
           	</tr><tr/>
           	<tr>
           		<td><fmt:message key="function.contractedtutors"/></td>
           		<td><bean:write name="bookMenuForm" property="tutorCount"/></td>
           	</tr><tr/>
       		<tr>
				<td><fmt:message key="function.athorizationcomment"/></td>
               	<td><html:textarea name='bookMenuForm' property="authComment" rows="2" cols="60"></html:textarea></td>
			</tr>
       	</sakai:group_table>    
  		<p/>
	    <html:hidden property="cancelOption" value="TOMAINVIEW"/>
	    <sakai:actions>
	        <html:submit styleClass="active" property="action"><fmt:message key="button.authorize"/></html:submit>
	        <html:submit styleClass="active" property="action"><fmt:message key="button.sendback"/></html:submit>
	        <html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
	    </sakai:actions>
  </html:form>
</sakai:html>      	