<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.examtimetable.ApplicationResources"/>

<sakai:html>
	<html:form action="/displayExamTimetable">
		<html:hidden property="currentPage" value="displayPrelim"/>
	<!--place alert message beneath tool heading-->	
	<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>	
	
	<sakai:group_table>	
	<tr>
		<td><fmt:message key="page.examYear"/>&nbsp;</td>
		<td><html:text property="examYear" size="6" maxlength="4"/></td>
	</tr><tr>
		<td><fmt:message key="page.examPeriod"/>&nbsp;</td>
		<td><html:select property="examPeriod">
					<html:options collection="examPeriods" property="code" labelProperty="engDescription"/>
					</html:select>
		</td>
	</tr><tr>
		<td><fmt:message key="page.practicalType"/>&nbsp;</td>
		<td><html:select property="practicalType">
					<html:options collection="examPracTypes" property="code" labelProperty="engDescription"/>
					</html:select>
		</td>
	</tr><tr>
		<td><fmt:message key="page.studentNumber"/>&nbsp;</td>
		<logic:equal name="examTimetableDisplayForm" property="studentUser" value="false">
			<td><html:text property="student.number" size="10" maxlength="8"/></td>
		</logic:equal>
		<logic:equal name="examTimetableDisplayForm" property="studentUser" value="true">
			<td><bean:write name="examTimetableDisplayForm" property="student.number"/></td>
		</logic:equal>
	</tr>
	</sakai:group_table>
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.display"/>
		</html:submit>			
	</sakai:actions>	
	<hr/>
	<table>
		<tr>
			<td><strong><fmt:message key="page.studentNumber"/></strong></td>
			<td><bean:write name="examTimetableDisplayForm" property="student.number"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.studentName"/></strong></td>
			<td><bean:write name="examTimetableDisplayForm" property="student.title"/>&nbsp;
			<bean:write name="examTimetableDisplayForm" property="student.initials"/>&nbsp;
			<bean:write name="examTimetableDisplayForm" property="student.name"/>&nbsp;</td>
		</tr><tr>
			<td><strong><fmt:message key="page.timetableStatus"/></strong></td>
			<td><bean:write name="examTimetableDisplayForm" property="timetableStatusDesc"/>
		</tr>
		

		
	</table>
	<sakai:actions>
		<logic:equal name="examTimetableDisplayForm" property="studentUser" value="true">
			<html:submit property="action">
				<fmt:message key="button.sendStudentEmail"/>
			</html:submit>
		</logic:equal>
			
	</sakai:actions>
	
	<sakai:flat_list>
		<tr>
			<th><fmt:message key="table.heading.studyUnit"/></th>
			<th><fmt:message key="table.heading.paperNo"/></th>
			<th><fmt:message key="table.heading.examDate"/></th>
		</tr>
		<logic:iterate name="examTimetableDisplayForm" property="examTimetableRecord" id="record" indexId="i">
			<tr>
				<td><logic:empty name="record" property="calcString">
						&nbsp;
						<bean:write name="record" property="studyUnit"/>
					</logic:empty>
					<logic:notEmpty name="record" property="calcString">
						<bean:write name="record" property="calcString"/>
						<bean:write name="record" property="studyUnit"/>
					</logic:notEmpty>
				</td>
				<td><bean:write name="record" property="paperNo"/></td>
				<td><bean:write name="record" property="examDate"/></td>
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	<logic:equal  name="examTimetableDisplayForm" property="calcTextFlag" value="Y">
		<sakai:instruction><fmt:message key="table.footer.calc"/></sakai:instruction>
	</logic:equal>
	<p/>
	<sakai:flat_list>
		<tr>
	  		<th colspan=2><fmt:message key="page.venue.heading"/></th>	  	
		</tr>
		<tr>
			<td><bean:write name="examTimetableDisplayForm" property="venueCode"/>&nbsp;</td>
			<td><bean:write name="examTimetableDisplayForm" property="venueDesc"/>&nbsp;</td>
		</tr>			
	</sakai:flat_list>
   	</html:form>
</sakai:html>