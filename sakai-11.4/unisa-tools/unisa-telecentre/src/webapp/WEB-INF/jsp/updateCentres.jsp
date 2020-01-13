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
			<fmt:message key="profile.update.link.selected"/>
		</html:link>
		<html:link href="telecentre.do?action=removeCentres" >
			<fmt:message key="profile.remove.link.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=activateCentres" >
			<fmt:message key="profile.activate.link.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=manageAdmins" >
			<fmt:message key="profile.manage.admin.unselected"/>
		</html:link>	
	</sakai:tool_bar>
	<sakai:messages/>
	<sakai:heading>
	     <fmt:message key="profile.update.heading"/>                
	</sakai:heading>
	<html:form action="/telecentre.do">
	<logic:notEqual name="telecentreForm" property="telUpdateListTracker" value="1">
		<sakai:instruction>
		     <fmt:message key="profile.update.instruction"/>                
		</sakai:instruction>
		<sakai:group_table>
			<tr>
		        <td><fmt:message key="telecentre.selectName"/></td>
			     <td>
			        <html:select name="telecentreForm" property="telecentreCurrentName" onchange="submit();">
					   <html:option value="-1">Select a Telecentre to update</html:option>	 
			     	   <html:options  collection="centreNames" property="value" labelProperty="label" />
			 		</html:select> 
			     </td>
		    </tr>
		    <tr>
		    	<td>
		    		<sakai:actions>
		    			<html:submit styleClass="button" property="action">
		    				<fmt:message key="telecentre.main"/>
		    			</html:submit>
					</sakai:actions>
			 	</td>
			</tr>    
		</sakai:group_table>
	</logic:notEqual>
	<logic:equal name="telecentreForm" property="telUpdateListTracker" value="1">
		<logic:equal name="telecentreForm" property="telecentreCurrentName" value="Select a Telecentre to update">
			<sakai:instruction>
			     <fmt:message key="profile.update.instruction"/>                
			</sakai:instruction>
			<sakai:group_table>
				<tr>
			        <td><fmt:message key="telecentre.selectName"/></td>
				     <td>
				        <html:select name="telecentreForm" property="telecentreCurrentName" onchange="submit();">
						   <html:option value="-1">Select a Telecentre to update</html:option>	 
				     	   <html:options  collection="centreNames" property="value" labelProperty="label" />
				 		</html:select> 
				     </td>
			    </tr>
			    <tr>
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
	</logic:equal>
	<logic:equal name="telecentreForm" property="telUpdateListTracker" value="1">
		<logic:notEqual name="telecentreForm" property="telecentreCurrentName" value="Select a Telecentre to update">
			<sakai:instruction>
			     <fmt:message key="profile.update.instruction2"/>                
			</sakai:instruction>
			<sakai:group_table>
			<sakai:group_heading>
				<fmt:message key="profile.update.table.heading"/>
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
			</sakai:group_table>
			<br><br>
			<sakai:group_table>
			<sakai:group_heading>
				<fmt:message key="profile.update.table.heading2"/>
			</sakai:group_heading>
			<sakai:instruction>
			     <fmt:message key="profile.update.instruction3"/>                
			</sakai:instruction>
				<tr>
					<td><html:checkbox name="telecentreForm" property="telUpdateNameCheckBox" value="true" onchange="submit();"/>
						<fmt:message key="profile.update.name.check"/>
					</td>
					<logic:equal name="telecentreForm" property="telUpdateNameCheckBox" value="true">
						<td><fmt:message key="profile.update.name.text"/></td>
						<td><html:text property="telecentreName" size="60" maxlength="70" value=""></html:text></td>
						<html:hidden name="telecentreForm" property="telUpdateNameCheckBox" value="false" onchange="submit();"/>
						<html:hidden name="telecentreForm" property="telecentreName" onchange="submit();"/>
					</logic:equal>
				</tr>
				<tr>
					<td><html:checkbox name="telecentreForm" property="telUpdateEmailCheckBox" value="true" onchange="submit();"/>
						<fmt:message key="profile.update.email.check"/>
						<html:hidden name="telecentreForm" property="telecentreName" onchange="submit();"/>
					</td>
					<logic:equal name="telecentreForm" property="telUpdateEmailCheckBox" value="true">
						<td><fmt:message key="profile.update.email.text"/></td>
						<td><html:text property="telecentreEmail" size="40" maxlength="50" value=""></html:text></td>
						<html:hidden name="telecentreForm" property="telUpdateEmailCheckBox" value="false" onchange="submit();"/>
						<html:hidden name="telecentreForm" property="telecentreEmail" onchange="submit();"/>
					</logic:equal>
				</tr>
				<tr>
					<td><html:checkbox name="telecentreForm" property="telUpdateProvinceCheckBox" value="true" onchange="submit();"/>
						<fmt:message key="profile.update.province.check"/>
					</td>
					<logic:equal name="telecentreForm" property="telUpdateProvinceCheckBox" value="true">
						 <td><fmt:message key="profile.update.province.text"/></td>
					     <td>
					        <html:select name="telecentreForm" property="province">
							   <html:option value="-1">Choose Province</html:option>	 
					     	   <html:options  collection="provinceList" property="value" labelProperty="label" />
					 		</html:select> 
					     </td>
						<html:hidden name="telecentreForm" property="telUpdateProvinceCheckBox" value="false" onchange="submit();"/>
						<html:hidden name="telecentreForm" property="province" onchange="submit();"/>
					</logic:equal>
				</tr>
				<tr>
					<td><html:checkbox name="telecentreForm" property="telUpdateActiveCheckBox" value="true" onchange="submit();"/>
						<fmt:message key="profile.update.active.check"/>
					</td>
					<logic:equal name="telecentreForm" property="telUpdateActiveCheckBox" value="true">
						 <td><fmt:message key="profile.update.active.text"/></td>
					     <td>
					      	 <html:select name="telecentreForm" property="telecentreStatus">
									<html:options collection="activeStatusList" property="value" labelProperty="label"/>
							</html:select> 
					     </td>
						<html:hidden name="telecentreForm" property="telUpdateActiveCheckBox" value="false" onchange="submit();"/>
						<html:hidden name="telecentreForm" property="telecentreStatus" onchange="submit();"/>
					</logic:equal>
				</tr>			
				<tr>
					<td>
					    <sakai:actions>
							<html:submit styleClass="button" property="action">
							    <fmt:message key="telecentre.main"/>
							</html:submit>					    
							<html:submit styleClass="button" property="action">
							    <fmt:message key="profile.update.submitBtn"/>
							</html:submit>
						 </sakai:actions>
				 	</td>
				</tr>
			</sakai:group_table>
		</logic:notEqual>
	</logic:equal>
	<html:hidden property="action" value="handleUpdateListBoxAction"/>
	</html:form>
</sakai:html>
