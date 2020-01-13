<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle
	basename="za.ac.unisa.lms.tools.mdrpmresults.ApplicationResources" />

<sakai:html>
<html:form action="/displaymdrpmresults">

	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading>
		<fmt:message key="page2.main.title" />
	</sakai:heading>

	<sakai:group_heading><fmt:message key="group.heading.studentdetails"/></sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td><strong><fmt:message key="page.label.studentnumber"/></strong></td>
				<td><bean:write name="mdRPMResultsForm" property="student.studentNumber"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.label.name"/></strong></td>
				<td colspan="3"><bean:write name="mdRPMResultsForm" property="student.name"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.label.qualification"/></strong></td>
				<td><bean:write name="mdRPMResultsForm" property="student.qualification.qualCode"/>&nbsp;&nbsp;
					<bean:write name="mdRPMResultsForm" property="student.qualification.qualDesc"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.label.studyunit"/></strong></td>
				<td><bean:write name="mdRPMResultsForm" property="student.studyUnit.code"/>&nbsp;&nbsp;
					<bean:write name="mdRPMResultsForm" property="student.studyUnit.description"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.label.homenumber"/></strong></td>
				<td><bean:write name="mdRPMResultsForm" property="student.addressPH.homeNumber"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.label.cellnumber"/></strong></td>
				<td><bean:write name="mdRPMResultsForm" property="student.addressPH.cellNumber"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.label.email"/></strong></td>
				<td><bean:write name="mdRPMResultsForm" property="student.addressPH.email"/></td>
			</tr>
		</sakai:group_table>
	
	<sakai:group_heading><fmt:message key="group.heading.rpmresults"/></sakai:group_heading>	
		<sakai:group_table>
			<!--<logic:iterate id="result" name="mdRPMResultsForm" property="results">
			<tr><td>
			  <html:multibox property="selectedResults">
			   <bean:write name="result"  property="value"/> 
			  </html:multibox> 
			   <bean:write name="result" property="label"/> </td>
			</tr>
			</logic:iterate>-->
			
			<logic:iterate id="result" name="mdRPMResultsForm" property="results"> 
			  <tr><td>
			  	<html:radio property="selectedResults" value="value" idName="result"/> 
			  <bean:write name="result" property="label"/> </td>
			</tr>
			</logic:iterate>
		</sakai:group_table>
	<sakai:group_heading><fmt:message key="group.heading.title"/></sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td><bean:write name="mdRPMResultsForm" property="title"/></td>
			</tr>
		</sakai:group_table>
	<sakai:group_heading><fmt:message key="group.heading.supervisor"/></sakai:group_heading>
		<sakai:group_table>
			<tr>
				<th align="left" width="40%"><fmt:message key="table.heading.name"/>&nbsp;</th>
				<th align="left" width="20%"><fmt:message key="table.heading.telephone"/>&nbsp;</th>
				<th align="left" width="20%"><fmt:message key="table.heading.email"/>&nbsp;</th>
			</tr>
			<logic:notEmpty name="mdRPMResultsForm" property="supervisors">
				<logic:iterate name="mdRPMResultsForm" property="supervisors" id="staffRecord" indexId="i">
				<tr>
					<td><bean:write name="staffRecord" property="name"/>&nbsp;</td>
					<td><bean:write name="staffRecord" property="telephone"/>&nbsp;</td>
					<td><bean:write name="staffRecord" property="emailAddress"/>&nbsp;</td>
				</tr>
				</logic:iterate>
			</logic:notEmpty>
		</sakai:group_table>
	<sakai:group_heading><fmt:message key="group.heading.revieworfinalsignoff"/></sakai:group_heading>
		<sakai:group_table>
			<tr><td>
			  <sakai:instruction>
				<fmt:message key="page.instruction.review" />
			  </sakai:instruction></td></tr>
			  
			<tr>
				<td>
				<html:checkbox name="mdRPMResultsForm" property="reviewoption" value="RW" >&nbsp;</html:checkbox>
				<fmt:message key="checkbox.label.review"/>
				</td>
			</tr>		
			<tr>
				<td>
				<logic:equal name="mdRPMResultsForm" property="finalSignOff" value="true" >  				
					<html:checkbox name="mdRPMResultsForm" property="finaloption" value="FINAL" disabled="true">&nbsp;</html:checkbox>
				</logic:equal>
				<logic:equal name="mdRPMResultsForm" property="finalSignOff" value="false" >  				
					<html:checkbox name="mdRPMResultsForm" property="finaloption" value="FINAL" disabled="false">&nbsp;</html:checkbox>
				</logic:equal>
				<fmt:message key="checkbox.label.finalsignoff"/>
				</td>				
			</tr>			
			<tr>
				<td><strong><fmt:message key="page.label.amend"/></strong>&nbsp;
				<html:submit property="action" disabled="true"><fmt:message key="button.amend"/></html:submit></td>
			</tr>
		</sakai:group_table>
	<sakai:group_heading><fmt:message key="group.heading.comments"/></sakai:group_heading>
		<br/>
		<sakai:flat_list>
		<tr>
			<th align="left" width="20%"><fmt:message key="table.heading.date"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.staffnoname"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.results"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.comment"/>&nbsp;</th>
		</tr>
		<logic:notEmpty name="mdRPMResultsForm" property="signOffList">
			<logic:iterate name="mdRPMResultsForm" property="signOffList" id="signoffRecord" indexId="i">
			<tr>
				<td><bean:write name="signoffRecord" property="signOffDate"/></td>
				<td><bean:write name="signoffRecord" property="staffNr" />&nbsp;<bean:write name="signoffRecord" property="name" /></td>
				<td><bean:write name="signoffRecord" property="signOffStatus"/></td>
				<td style="word-break:break-all;"><bean:write name="signoffRecord" property="signOffComment"/>&nbsp;</td>
			</tr>
			</logic:iterate>
		</logic:notEmpty>
		</sakai:flat_list>
	<sakai:group_heading><fmt:message key="group.heading.addcomment"/></sakai:group_heading>
		<sakai:flat_list>
			<tr>
			  <td><html:textarea name="mdRPMResultsForm" property="comment" rows="4" cols="125"/></td>
			</tr>
		</sakai:flat_list>
	<sakai:actions>
		<logic:equal name="mdRPMResultsForm" property="canSignOff" value="true">
			<html:submit property="action"><fmt:message key="button.signoff"/></html:submit>
		</logic:equal>		
		<logic:equal name="mdRPMResultsForm" property="canSignOff" value="false">
			<html:submit property="action" disabled="true"><fmt:message key="button.signoff"/></html:submit>
		</logic:equal>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>

</html:form>
</sakai:html>
