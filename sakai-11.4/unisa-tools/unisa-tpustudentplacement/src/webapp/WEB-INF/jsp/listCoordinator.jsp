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
			<fmt:message key="heading.wscoordinatorlist"/> 
		</sakai:heading>
		
		<sakai:flat_list>
			<tr >
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.name"/></th>		
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.networkcode"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.persno"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.telNumber"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.email"/></th>	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.workstation"/></th>	
		     </tr>
			<logic:iterate name="studentPlacementForm" property="listCoordinator" id="rec" indexId="index">
				<tr>
					<td>	
						<html:multibox property="indexNrSelected"><bean:write name="index"/></html:multibox>				
						 <bean:write name="rec" property="name"/>
					</td>
					<td><bean:write name="rec" property="networkCode"/></td>
					<td><bean:write name="rec" property="personnelNumber"/></td>									
					<td><bean:write name="rec" property="contactNumber"/></td>
					<td><bean:write name="rec" property="emailAddress"/></td>
					<td><bean:write name="rec" property="workStationDescr"/></td>		
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.add"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.edit"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.delete"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>
	</html:form>
</sakai:html>