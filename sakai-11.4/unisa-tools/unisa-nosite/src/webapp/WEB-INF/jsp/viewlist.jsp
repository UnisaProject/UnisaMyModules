<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.nosite.ApplicationResources"/>

<sakai:html>	  
	<html:form action="/nosite.do">	
	  <sakai:tool_bar>	 
     <html:link href="nosite.do?action=view"><input type="image" align="right" src="/library/image/printer.png" onClick="window.print()"/></html:link> 
	</sakai:tool_bar>
	<sakai:messages/>

	<sakai:heading><fmt:message key="nosite.heading"/></sakai:heading>
	 <sakai:instruction><fmt:message key="nosite.college.instruction"/></sakai:instruction>
	 <sakai:group_table>
	 <tr>
	 <td><fmt:message key="colleges.disc"/><td>
	 <td>
		<html:select name="nositeForm" property="college" >
					<html:option value="-1">All</html:option>
					<html:options property="colleges"/>
				</html:select>				
			</td>
	   </tr>
	
		<tr> 
			<td><fmt:message key="nosite.field.year"/> <td>
			<td>
			<html:select property="year">
			<html:option value="${nositeForm.lastYear}">
				<bean:write name="nositeForm" property="lastYear"/>
			</html:option><html:option value="${nositeForm.currentYear}">
				<bean:write name="nositeForm" property="currentYear"/>
			</html:option><html:option value="${nositeForm.nextYear}">
				<bean:write name="nositeForm" property="nextYear"/>
			</html:option></html:select></td>
			
		</tr>	

		 <tr>
		  <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="button.display"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
	 	  <sakai:flat_list>
	 	   	<th><fmt:message key="nosite.field.college"/></th>
			<th><fmt:message key="nosite.field.active"/></th>
			<th><fmt:message key="nosite.field.inactive"/></th>
			<th><fmt:message key="nosite.field.inactivewithstudents"/></th> 
		
		<logic:notEmpty name="nositeForm" property="collegeinfo">
		<logic:iterate name="nositeForm" property="collegeinfo" id="d" indexId="dindex">
			<tr>
			<td>
				<bean:write name="d" property="college"/>
			</td>	
			<td>
				<bean:write name="d" property="active"/>
			</td>	
			<td>
				<bean:write name="d" property="inactive"/>
			</td>	
			<td>
				<bean:write name="d" property="inactivewithstudents"/>
			</td>							
							                 				
			</tr>
			

			
		</logic:iterate>				
	</logic:notEmpty>
	</sakai:flat_list>
	

	</sakai:group_table>
	
	<logic:empty name="nositeForm" property="collegeinfo">
		<fmt:message key="nosite.notavailable"/>
	</logic:empty>	 
	

    <input type="hidden" name="action" value="view"/>
	</html:form>
</sakai:html>


