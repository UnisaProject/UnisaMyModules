<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/coordinatorMaintenance">
			<sakai:messages/>
		    <sakai:messages message="true"/>
            <sakai:heading>
			     <fmt:message key="heading.deletewscoordinator"/> 
		    </sakai:heading>
		    <sakai:group_table>	
			<tr>
				  <td><fmt:message key="prompt.column.persno"/></td>
				  <td><bean:write name="studentPlacementForm" property="coordinator.personnelNumber"/>
				      <fmt:message key="prompt.openingBracket"/>
				         <bean:write name="studentPlacementForm" property="coordinator.name"/>
				      <fmt:message key="prompt.closingBracket"/>
				 </td>
			</tr>	
			<tr>
			       <td><fmt:message key="prompt.column.networkcode"/></td>
				   <td><bean:write name="studentPlacementForm" property="coordinator.networkCode"/></td>
			</tr>	
			<tr>
			       <td><fmt:message key="prompt.column.telNumber"/></td>
				   <td><bean:write name="studentPlacementForm" property="coordinator.contactNumber"/></td>
			</tr>
			<tr>
			       <td><fmt:message key="prompt.column.email"/></td>
				   <td><bean:write name="studentPlacementForm" property="coordinator.emailAddress"/></td>
			</tr>
			<tr>   
			       <td><fmt:message key="prompt.column.sadecIntIndicator"/></td>
			       <td>
			                 <html:radio name= "studentPlacementForm"  property="coordinator.sadecInt"  disabled="true" value="Y"> <fmt:message key="prompt.yes"/></html:radio>
			                 <html:radio name= "studentPlacementForm"  property="coordinator.sadecInt" disabled="true"  value="N"><fmt:message key="prompt.no"/></html:radio>
			      </td>	
			</tr>	
		    <tr>
		           <td><fmt:message key="prompt.column.workstation"/></td>
				   <td><bean:write name="studentPlacementForm" property="coordinator.workStationDescr"/></td>	
			</tr>					
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.confirmdelete"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>	
   </html:form>
</sakai:html>