<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.publicexamtimetable.ApplicationResources"/>
<sakai:html>
<sakai:messages/>
	 <sakai:group_table>
						<tr>
							<td><img src="http://www.unisa.ac.za/static/corporate_web/Design/themes/default/images/unisa%20logo.svg"  WIDTH="216.7px" HEIGHT="46.5px"/></td>
						</tr>
  </sakai:group_table>
	<sakai:heading><fmt:message key="examtimetable.display.heading"/> for <bean:write name="examtimetableform" property="examPeriodDesc"/></sakai:heading>
		<html:form action="examtimetable">
		<sakai:group_table>
			<tr>
				<td><fmt:message key="examtimetable.label.date"/></td>
				<td><bean:write name="examtimetableform" property="listDate"/></td>
			</tr>
			<tr>
				<td><fmt:message key="examtimetable.label.status"/></td>
				<td><bean:write name="examtimetableform" property="timeTableStatus"/></td>
			</tr>
			<sakai:instruction>
				<fmt:message key="examtimetable.input.instruction3"/> <fmt:message key="examtimetable.input.instruction4"/>
			</sakai:instruction>
			<tr>
				<td colspan="2" align="left">
					<sakai:group_table>
						<tr>
							<th><fmt:message key="examtimetable.label.studyunit"/></th>
							<th><fmt:message key="examtimetable.label.paperno"/></th>
							<th><fmt:message key="examtimetable.label.examdate"/></th>
							<th><fmt:message key="examtimetable.label.time"/></th>
							<th><fmt:message key="examtimetable.label.duration"/></th>
						</tr>
						<logic:iterate name="examtimetableform" property="timetableList" id="c" indexId="cindex">
							<tr>
								<td><bean:write name="c" property="studyUnit"/></td>
								<td><bean:write name="c" property="paperNo"/></td>
								<td><bean:write name="c" property="examDate"/></td>
								<td><bean:write name="c" property="examTime"/></td>
								<td><bean:write name="c" property="duration"/></td>
							</tr>							
						</logic:iterate>
					</sakai:group_table>
				</td>
			</tr>
		</sakai:group_table>
		<logic:notEqual name="examtimetableform" property="printable" value="1">
   			<sakai:actions>
				<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
			</sakai:actions>
		</logic:notEqual>
	</html:form>
</sakai:html>
