<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/placementLogMaintenance">
		<sakai:heading>
			<fmt:message key="heading.placementLogEntryDet"/> 
		</sakai:heading>
		<sakai:group_table>
		        <tr >
				     <td style="white-space:nowrap;align:left" >
				        <b><fmt:message key="prompt.column.studentNumber"/></b>
				     </td>	
				     <td style="white-space:nowrap;align:left">	
						<bean:write name="studentPlacementForm" property="placementLogDetails.stuNum"/>
						(<bean:write name="studentPlacementForm" property="placementLogDetails.name"/>)
					</td>
				</tr>	
				<tr>
				     <td style="white-space:nowrap;align:left" >
				        <b><fmt:message key="prompt.column.module"/></b>
				     </td>
				     <td style="white-space:nowrap;align:left">	
						<bean:write name="studentPlacementForm" property="placementLogDetails.module"/>
					</td>
				</tr>
				<tr>
				    <td style="white-space:nowrap;align:left" >
				        <b><fmt:message key="prompt.column.school"/></b>
				    </td>
				    <td style="white-space:nowrap;align:left">	
						<bean:write name="studentPlacementForm" property="placementLogDetails.schoolDesc"/>
					</td>
				</tr>
				<tr>
				    <td style="white-space:nowrap;align:left" >
				       <b><fmt:message key="prompt.column.supervisor"/></b>
				    </td>
				    <td style="white-space:nowrap;align:left">	
						<bean:write name="studentPlacementForm" property="placementLogDetails.supervisorName"/>
					</td>
				</tr>
				<tr>
				    <td style="white-space:nowrap;align:left" >
				        <b><fmt:message key="prompt.column.startDate"/></b>
				    </td>
				    <td style="white-space:nowrap;align:left">	
						<bean:write name="studentPlacementForm" property="placementLogDetails.startDate"/>
					</td>
				</tr>
				<tr>
				     <td style="white-space:nowrap;align:left" >
				        <b><fmt:message key="prompt.column.endDate"/></b>
				     </td>
				     <td style="white-space:nowrap;align:left">	
						<bean:write name="studentPlacementForm" property="placementLogDetails.endDate"/>
					</td>
				</tr>
				<tr>
				     <td style="white-space:nowrap;align:left" >
				         <b><fmt:message key="prompt.numberOfWeeks"/></b>
				     </td>
				     <td style="white-space:nowrap;align:left">	
						<bean:write name="studentPlacementForm" property="placementLogDetails.numberOfWeeks"/>
					</td>
				</tr>
				<tr>
				     <td style="white-space:nowrap;align:left" >
				         <b><fmt:message key="prompt.column.evaluationMark"/></b>
				     </td>
				     <td style="white-space:nowrap;align:left">	
						<bean:write name="studentPlacementForm" property="placementLogDetails.evaluationMark"/>
					</td>
				</tr>
	</sakai:group_table>
	<html:submit property="action">
				<fmt:message key="button.back"/>
			</html:submit>
    </html:form>
</sakai:html>