<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
	<logic:equal name="telecentreForm" property="telActivatePageReached" value="2">
		<sakai:heading>
		   <fmt:message key="profile.activate.confirm.heading"/>
		</sakai:heading>
		<sakai:messages/>
		<sakai:instruction>
		   <fmt:message key="profile.activate.confirm.instruction"/>
		</sakai:instruction>
		<html:form action="/telecentre.do">	
		<sakai:group_table>
			<sakai:group_heading>
				<fmt:message key="profile.activate.confirm.table.heading"/>
			</sakai:group_heading>
			<sakai:flat_list>
				<tr>
				     <th><fmt:message key="profile.update.table.name"/></th>
				     <th><fmt:message key="profile.update.table.email"/></th>
				     <th><fmt:message key="profile.update.table.province"/></th>
				     <th><fmt:message key="profile.update.table.active"/></th>
				     <th><fmt:message key="profile.update.table.telecode"/></th>
				</tr>
				<logic:iterate name="telecentreForm" property="telecentreInfo" id="updateId">
					<tr>
					     <td><bean:write name ="updateId" property="centre"/></td>
					     <td><bean:write name ="updateId" property="email"/></td>
					     <td><bean:write name ="updateId" property="province"/></td>
					     <td><bean:write name ="updateId" property="status"/></td>
					     <td><bean:write name ="updateId" property="code"/></td>
					</tr>
				</logic:iterate>
			</sakai:flat_list>
			<tr></tr>
	        <tr>
	            <td>
	             	<sakai:actions>
				       <html:submit styleClass="button" property="action">
			                <fmt:message key="telecentre.back"/>
			           </html:submit>
			           <html:submit styleClass="button" property="action">
			           		<fmt:message key="profile.activate.confirm.activateBtn"/>
			           </html:submit>
		            </sakai:actions>
		        </td>	
			</tr>
		</sakai:group_table>
		</html:form >
	</logic:equal>
	<logic:equal name="telecentreForm" property="telActivatePageReached" value="3">
		<sakai:heading>
		   <fmt:message key="profile.activate.confirm.heading2"/>
		</sakai:heading>
		<sakai:messages/>
		<sakai:instruction>
		   <fmt:message key="profile.activate.confirm.instruction2"/>
		</sakai:instruction>
		<html:form action="/telecentre.do">	
		<sakai:group_table>
			<sakai:group_heading>
				<fmt:message key="profile.activate.confirm.table.heading"/>
			</sakai:group_heading>
			<sakai:flat_list>
				<tr>
				     <th><fmt:message key="profile.update.table.name"/></th>
				     <th><fmt:message key="profile.update.table.email"/></th>
				     <th><fmt:message key="profile.update.table.province"/></th>
				     <th><fmt:message key="profile.update.table.active"/></th>
				     <th><fmt:message key="profile.update.table.telecode"/></th>
				</tr>
				<logic:iterate name="telecentreForm" property="telecentreInfo" id="updateId">
					<tr>
					     <td><bean:write name ="updateId" property="centre"/></td>
					     <td><bean:write name ="updateId" property="email"/></td>
					     <td><bean:write name ="updateId" property="province"/></td>
					     <td><bean:write name ="updateId" property="status"/></td>
					     <td><bean:write name ="updateId" property="code"/></td>
					</tr>
				</logic:iterate>
			</sakai:flat_list>
			<tr></tr>
	        <tr>
	            <td>
	             	<sakai:actions>
				       <html:submit styleClass="button" property="action">
			                <fmt:message key="telecentre.back"/>
			           </html:submit>
			           <html:submit styleClass="button" property="action">
			           		<fmt:message key="profile.activate.confirm.deactivateBtn"/>
			           </html:submit>
		            </sakai:actions>
		        </td>	
			</tr>
		</sakai:group_table>
		</html:form >
	</logic:equal>
</sakai:html>   