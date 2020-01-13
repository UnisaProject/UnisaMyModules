<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdadm.ApplicationResources"/>

<sakai:html>

	<html:form action="/displaymdadmission">

		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
		<sakai:messages/>

    <sakai:group_heading><fmt:message key="page.heading.studdetails"/></sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td><strong><fmt:message key="page.heading.studentnr"/></strong></td>
				<td><bean:write name="mdAdmissionForm" property="student.studentNumber"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.name"/></strong></td>
				<td colspan="3"><bean:write name="mdAdmissionForm" property="student.name"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.qualification"/></strong></td>
				<td><bean:write name="mdAdmissionForm" property="application.qualification.qualCode"/>&nbsp;&nbsp;<bean:write name="mdAdmissionForm" property="application.qualification.qualDesc"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.speciality"/></strong></td>
				<td><bean:write name="mdAdmissionForm" property="application.qualification.specCode"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.homenumber"/></strong></td>
				<td><bean:write name="mdAdmissionForm" property="student.addressPH.homeNumber"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.cellnumber"/></strong></td>
				<td><bean:write name="mdAdmissionForm" property="student.addressPH.cellNumber"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.email"/></strong></td>
				<td><bean:write name="mdAdmissionForm" property="student.addressPH.email"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.lecturerConsulted"/></strong></td>
				<td><bean:write name="mdAdmissionForm" property="application.lecturerConsulted"/></td>
			</tr>
		</sakai:group_table>
		
		
		<sakai:group_heading><fmt:message key="page.heading.supportdocs"/></sakai:group_heading>
		<sakai:group_table>
		  	<logic:notEmpty name="mdAdmissionForm" property="documentList">
			<logic:iterate name="mdAdmissionForm" property="documentList" id="uFile" indexId="i">
			<tr>
			  <td><html:link href="displaymdadmission.do?action=getFile" paramName="uFile" paramProperty="uniqueId" paramId="selectedFile"><bean:write name="uFile" property="displayName"/></html:link></td>
			</tr>
			</logic:iterate>
			</logic:notEmpty>
		</sakai:group_table>
		
     <sakai:group_heading><fmt:message key="page.heading.prev"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<th align="left"><fmt:message key="table.heading.qualification"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.institution"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.compyear"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.source"/>&nbsp;</th>
		</tr>
		<logic:notEmpty name="mdAdmissionForm" property="application.prevQualification">
			<logic:iterate name="mdAdmissionForm" property="application.prevQualification" id="prevQualRecord" indexId="i">
			<tr>
				<td><bean:write name="prevQualRecord" property="qualification"/>&nbsp;</td>
				<td><bean:write name="prevQualRecord" property="institution"/></td>
				<td><bean:write name="prevQualRecord" property="yearCompleted"/>&nbsp;</td>
				<td><bean:write name="prevQualRecord" property="accreditationSource"/>&nbsp;</td>
			</tr>
			</logic:iterate>
		</logic:notEmpty>
		</sakai:group_table>

	<sakai:group_heading><fmt:message key="page.heading.interest"/></sakai:group_heading>
		<sakai:group_table>
		 <tr>
		   <td><bean:write name="mdAdmissionForm" property="application.areaOfInterest"/></td>
		 </tr>
	</sakai:group_table>
	
	<sakai:group_heading><fmt:message key="page.heading.title"/></sakai:group_heading>
		<sakai:group_table>
		 <tr>
		   <td><bean:write name="mdAdmissionForm" property="application.title"/></td>
		 </tr>
	</sakai:group_table>
	
	<sakai:group_heading><fmt:message key="page.heading.comment"/></sakai:group_heading>
		<sakai:group_table>
		 <tr>
		   <td><bean:write name="mdAdmissionForm" property="application.advisorComment"/></td>
		 </tr>
	</sakai:group_table>
	<logic:equal name="mdAdmissionForm" property="haveToSetRecommendation" value="true">
		<logic:equal name="mdAdmissionForm" property="application.status" value="A">
			<sakai:group_heading><fmt:message key="page.heading.appeal"/></sakai:group_heading>
			<sakai:group_table>
				<tr><td><bean:write name="mdAdmissionForm" property="application.previousRecommendation"/></td></tr>
				<tr><td><bean:write name="mdAdmissionForm" property="application.previousRecommendationComment"/></td></tr>
			</sakai:group_table>			
		</logic:equal>
	</logic:equal>		
	<sakai:group_heading><fmt:message key="table.heading.recom"/></sakai:group_heading>
	<sakai:group_table>
	<logic:equal name="mdAdmissionForm" property="haveToSetRecommendation" value="true">	
	<tr>
		<td><html:radio property="application.recommendation" value="yes"/><fmt:message key="radio1"/></td>
	</tr><tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="table.contact.person"/>&nbsp;<bean:write name="mdAdmissionForm" property="application.contactPerson.name"/></td>
		<td><fmt:message key="table.telno"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.contactPerson.telephone"/></td>
		<td><fmt:message key="table.email"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.contactPerson.emailAddress"/></td>
		<td><html:submit property="action.search1"><fmt:message key="button.searchStaff"/></html:submit>&nbsp;<html:submit property="action.clear1"><fmt:message key="button.clear"/></html:submit></td>
	</tr><tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="table.supervisor"/>&nbsp; &nbsp;<bean:write name="mdAdmissionForm" property="application.supervisor.name"/></td>
		<td><fmt:message key="table.telno"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.supervisor.telephone"/></td>
		<td><fmt:message key="table.email"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.supervisor.emailAddress"/></td>
		<td><html:submit property="action.search2"><fmt:message key="button.searchStaff"/></html:submit>&nbsp;<html:submit property="action.clear2"><fmt:message key="button.clear"/></html:submit></td>
	</tr><tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="table.joint"/>&nbsp;&nbsp;<bean:write name="mdAdmissionForm" property="application.jointSupervisor.name"/></td>
		<td><fmt:message key="table.telno"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.jointSupervisor.telephone"/></td>
		<td><fmt:message key="table.email"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.jointSupervisor.emailAddress"/></td>
		<td><html:submit property="action.search3"><fmt:message key="button.searchStaff"/></html:submit>&nbsp;<html:submit property="action.clear3"><fmt:message key="button.clear"/></html:submit></td>
	</tr>
	<!-- <th align="left" colspan="4"><fmt:message key="table.heading.struct"/>&nbsp;</th> -->
	<tr>
		<td colspan="4"><html:radio property="application.recommendation" value="struct"/><fmt:message key="radio2"/></td>				
	</tr><tr>
		<td colspan="4"><html:textarea name="mdAdmissionForm" property="application.structuredComment" rows="3" cols="100"/></td>
	</tr><tr>
		<td colspan="4"><html:radio property="application.recommendation" value="addreq"/><fmt:message key="radio3"/></td>
	</tr><tr>
		<td colspan="4"><html:textarea name="mdAdmissionForm" property="application.requirementComment" rows="3" cols="100"/></td>
	</tr><tr>
		<td colspan="4"><html:radio property="application.recommendation" value="no"/><fmt:message key="radio4"/></td>
	</tr><tr>
		<td colspan="4"><html:textarea name="mdAdmissionForm" property="application.notAdmittedComment" rows="3" cols="100"/></td>
	</tr>
	</logic:equal>
	<logic:equal name="mdAdmissionForm" property="haveToSetRecommendation" value="false">
		<logic:equal name="mdAdmissionForm" property="application.recommendation" value="yes">
			<tr>
		    <th><fmt:message key="radio1"/></th>
	   	 </tr><tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="table.contact.person"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.contactPerson.name"/></td>
			<td><fmt:message key="table.telno"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.contactPerson.telephone"/></td>
			<td><fmt:message key="table.email"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.contactPerson.emailAddress"/></td>
	 	 </tr><tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="table.supervisor"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.supervisor.name"/></td>
			<td><fmt:message key="table.telno"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.supervisor.telephone"/></td>
			<td><fmt:message key="table.email"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.supervisor.emailAddress"/></td>
	  	</tr><tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="table.joint"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.jointSupervisor.name"/></td>
			<td><fmt:message key="table.telno"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.jointSupervisor.telephone"/></td>
			<td><fmt:message key="table.email"/> &nbsp;<bean:write name="mdAdmissionForm" property="application.jointSupervisor.emailAddress"/></td>
	  	</tr>
	  </logic:equal>
	  <logic:equal name="mdAdmissionForm" property="application.recommendation" value="struct">
		<tr>
		  <th><fmt:message key="radio2"/></th>				
		</tr><tr>
		 <td style="word-break:break-all;"><bean:write name="mdAdmissionForm" property="application.structuredComment"/></td>
		</tr>
	  </logic:equal>
	  <logic:equal name="mdAdmissionForm" property="application.recommendation" value="addreq">
		<tr>
		  <th><fmt:message key="radio3"/></th>				
		</tr><tr>
		 <td style="word-break:break-all;"><bean:write name="mdAdmissionForm" property="application.requirementComment"/></td>
		</tr>
	  </logic:equal>
	  <logic:equal name="mdAdmissionForm" property="application.recommendation" value="no">
		<tr>
		  <th><fmt:message key="radio4"/></th>				
		</tr><tr>
		 <td style="word-break:break-all;"><bean:write name="mdAdmissionForm" property="application.notAdmittedComment"/></td>
		</tr>
	  </logic:equal>
	</logic:equal>
    </sakai:group_table>		
	<sakai:flat_list>
		<tr>
			<th align="left" width="20%"><fmt:message key="table.heading.user"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.date"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.comment"/>&nbsp;</th>
		</tr>
		<logic:notEmpty name="mdAdmissionForm" property="signOffList">
			<logic:iterate name="mdAdmissionForm" property="signOffList" id="signoffRecord" indexId="i">
			<tr>
				<td><bean:write name="signoffRecord" property="name" />&nbsp;</td>
				<td><bean:write name="signoffRecord" property="signOffDate"/></td>
				<td style="word-break:break-all;"><bean:write name="signoffRecord" property="signOffComment"/>&nbsp;</td>
			</tr>
			</logic:iterate>
		</logic:notEmpty>
		</sakai:flat_list>
		<br/><br/>
	<sakai:flat_list>
		<tr>
		  <td><fmt:message key="page.heading.signcomment"/>&nbsp;</td>
		</tr><tr>
		  <td><html:textarea name="mdAdmissionForm" property="application.signOffComment" rows="3" cols="100"/></td>
		</tr>
	</sakai:flat_list>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.signoff"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		</html:form>
</sakai:html>
