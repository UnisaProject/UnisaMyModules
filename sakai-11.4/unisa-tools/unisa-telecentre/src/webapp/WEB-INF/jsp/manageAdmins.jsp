<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
	<sakai:tool_bar>
		<html:link href="telecentre.do?action=displayStartingPage" >
			<fmt:message key="link.displayVisit.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=exportVisit" >
			<fmt:message key="link.exportVisitList.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=extendHours" >
			<fmt:message key="link.extendHours.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=createCentres" >
			<fmt:message key="profile.create.link.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=updateCentres" >
			<fmt:message key="profile.update.link.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=removeCentres" >
			<fmt:message key="profile.remove.link.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=activateCentres" >
			<fmt:message key="profile.activate.link.unselected"/>
		</html:link>	
	    <html:link href="telecentre.do?action=manageAdmins" >
			<fmt:message key="profile.manage.admin.selected"/>
		</html:link>	
	</sakai:tool_bar>
	<sakai:messages/>
	<sakai:heading>
	     <fmt:message key="profile.manage.admin.heading"/>                
	</sakai:heading>
	<html:form action="/telecentre.do">
	<logic:equal name="telecentreForm" property="telManageAdminsReached" value="1">
		<sakai:instruction>
		     <fmt:message key="profile.manage.admin.instruction"/>                
		</sakai:instruction>
		<sakai:group_table>
		    <tr>
		    	<td>
		    		<sakai:actions>
		    			<html:submit styleClass="button" property="action">
		    				<fmt:message key="profile.manage.admin.viewBtn"/>
		    			</html:submit>
					</sakai:actions>
			 	</td>
		    	<td>
		    		<sakai:actions>
		    			<html:submit styleClass="button" property="action">
		    				<fmt:message key="profile.manage.admin.addBtn"/>
		    			</html:submit>
					</sakai:actions>
			 	</td>
		    	<td>
		    		<sakai:actions>
		    			<html:submit styleClass="button" property="action">
		    				<fmt:message key="profile.manage.admin.removeBtn"/>
		    			</html:submit>
					</sakai:actions>
			 	</td>
			 	<td>
		    		<sakai:actions>
		    			<html:submit styleClass="button" property="action">
		    				<fmt:message key="telecentre.main"/>
		    			</html:submit>
					</sakai:actions>
			 	</td>
			</tr>   
		</sakai:group_table>
	</logic:equal>
	<!-- <logic:equal name="telecentreForm" property="telActivateListTracker" value="1">
		<logic:equal name="telecentreForm" property="telecentreActivateName" value="-1">
			<sakai:instruction>
			     <fmt:message key="profile.activate.instruction"/>                
			</sakai:instruction>
			<sakai:group_table>
				<tr>
			        <td><fmt:message key="telecentre.selectName"/></td>
				     <td>
				        <html:select name="telecentreForm" property="telecentreActivateName" onchange="submit();">
						   <html:option value="-1">Select a Telecentre to Activate/Deactivate</html:option>
				     	   <html:options  collection="centreNames" property="value" labelProperty="label" />
				 		</html:select> 
				     </td>
			    </tr>
			    <tr>
			    	
				</tr>    
			</sakai:group_table>
		</logic:equal>
	</logic:equal>
	<logic:equal name="telecentreForm" property="telActivateListTracker" value="1">
		<logic:notEqual name="telecentreForm" property="telecentreActivateName" value="-1">
			<sakai:instruction>
			     <fmt:message key="profile.activate.instruction2"/>                
			</sakai:instruction>
			<sakai:group_table>
				<sakai:group_heading>
					<fmt:message key="profile.activate.table.heading"/>
				</sakai:group_heading>
				<sakai:flat_list>
					<tr>
					     <th><fmt:message key="profile.update.table.name"/></th>
					     <th><fmt:message key="profile.update.table.email"/></th>
					     <th><fmt:message key="profile.update.table.province"/></th>
					     <th><fmt:message key="profile.update.table.active"/></th>
					     <th><fmt:message key="profile.update.table.telecode"/></th>
					</tr>
					<logic:iterate name="telecentreForm" property="telecentreInfo" id="removeId">
					<tr>
					     <td><bean:write name ="removeId" property="centre"/></td>
					     <td><bean:write name ="removeId" property="email"/></td>
					     <td><bean:write name ="removeId" property="province"/></td>
					     <td><bean:write name ="removeId" property="status"/></td>
					     <td><bean:write name ="removeId" property="code"/></td>
					</tr>
					</logic:iterate>
				</sakai:flat_list>
			    <tr>
			    	<td>
			    		<sakai:actions>
			    			<html:submit styleClass="button" property="action">
			    				<fmt:message key="telecentre.main"/>
			    			</html:submit>
			    			<html:submit styleClass="button" property="action">
			    				<fmt:message key="profile.activate.activateBtn"/>
			    			</html:submit>
			    			<html:submit styleClass="button" property="action">
			    				<fmt:message key="profile.activate.deactivateBtn"/>
			    			</html:submit>		    			
						</sakai:actions>
				 	</td>
				</tr>
			</sakai:group_table>
		</logic:notEqual>
	</logic:equal>
	<html:hidden property="action" value="handleActivateListBoxAction"/> -->
	</html:form>
</sakai:html>
