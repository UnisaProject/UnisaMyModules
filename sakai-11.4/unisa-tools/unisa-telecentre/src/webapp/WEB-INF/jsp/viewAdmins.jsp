<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
   <sakai:messages/>
   <!-- Sifiso Changes:2016/08/19:Added logic below to display toolbar view for regional staff only-->
   <logic:equal name="telecentreForm" property="isRegionalStaff" value="1">
	   <logic:equal name="telecentreForm" property="isAdmin" value="1">
		   <sakai:tool_bar>
				<html:link href="telecentre.do?action=displayStartingPage" >
					<fmt:message key="link.displayVisit.selected"/>
				</html:link>
				<html:link href="telecentre.do?action=exportVisit" >
					<fmt:message key="link.exportVisitList.unselected"/>
				</html:link>
		   </sakai:tool_bar>
	   </logic:equal>
   </logic:equal>
      <!-- Sifiso Changes:2016/08/19:Added logic below to display toolbar view for regional staff only-->
   <logic:equal name="telecentreForm" property="isRegionalStaff" value="1">
	   <logic:notEqual name="telecentreForm" property="isAdmin" value="1">
		   <sakai:tool_bar>
				<html:link href="telecentre.do?action=displayStartingPage" >
					<fmt:message key="link.displayVisit.selected"/>
				</html:link>
				<html:link href="telecentre.do?action=exportVisit" >
					<fmt:message key="link.exportVisitList.unselected"/>
				</html:link>
		   </sakai:tool_bar>
	   </logic:notEqual>
   </logic:equal>
   <!-- Sifiso Changes:2016/08/19:Added logic below to display toolbar view for admin staff only-->
   <logic:notEqual name="telecentreForm" property="isRegionalStaff" value="1">
   		<logic:equal name="telecentreForm" property="isAdmin" value="1">
			<sakai:tool_bar>
					<!-- Sifiso Changes:2016/08/05:Added the toolbar tab below to display as selected tab-->
					<html:link href="telecentre.do?action=displayStartingPage" >
						<fmt:message key="link.displayVisit.unselected"/>
					</html:link>
					<html:link href="telecentre.do?action=exportVisit" >
						<fmt:message key="link.exportVisitList.unselected"/>
					</html:link>
					<html:link href="telecentre.do?action=extendHours" >
						<fmt:message key="link.extendHours.unselected"/>
					</html:link>
					<!-- Sifiso Changes:Added below:2016-07-27:Manage telecentres links -->
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
		</logic:equal>
	</logic:notEqual>
    <sakai:heading>
	     <fmt:message key="profile.manage.admin.view.heading"/>                
	</sakai:heading>
  	<sakai:group_table>
  		<sakai:flat_list>	
	    	<logic:notEmpty name="telecentreForm" property="adminInfo">
		  	    <th><fmt:message key="profile.manage.adminInfo.user"/></th>
		 	   	<th><fmt:message key="profile.manage.adminInfo.email"/></th>
		 	   	<th><fmt:message key="profile.manage.adminInfo.name"/></th>
		 	   	<th><fmt:message key="profile.manage.adminInfo.region"/></th>
			    <logic:iterate name="telecentreForm" property="adminInfo" id="d" >
					<tr>
						<td>
							<bean:write name="d" property="adminADusername"/>
						</td>
						<td>
							<bean:write name="d" property="adminEmail"/>
						</td>	
						<td>
							<bean:write name="d" property="adminName"/>
						</td>
						<td>
							<bean:write name="d" property="adminRegionFlag"/>
						</td>	
			        </tr>
				</logic:iterate>				
			</logic:notEmpty>
		</sakai:flat_list>
		<html:form action="/telecentre.do">	
   		<tr>
			<td>
				<sakai:actions>
					<html:submit styleClass="button" property="action">
						<fmt:message key="telecentre.main"/>
					</html:submit>				
				</sakai:actions>
			</td>
		</tr>
		</html:form>	
 	</sakai:group_table>
</sakai:html>





