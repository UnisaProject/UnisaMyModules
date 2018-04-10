<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>


<script language="JavaScript">
	function setAction() {
		document.courseForm.action = 'course.do?act=updatePrimLecturer';
		document.courseForm.submit();
	}
</script>

<sakai:html>
<html:form action="/course">
	<html:hidden property="atstep" value="updPrimLectConfirm"/>
	<sakai:group_heading>
		<fmt:message key="priml.info1"/>
		<bean:write name="courseForm" property="course"/> 
	</sakai:group_heading>
	
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<logic:empty name="courseForm" property="acadPeriodOptions">
		No Course sites available for this course
	</logic:empty>
	
	<logic:notEmpty name="courseForm" property="acadPeriodOptions">
	<table>
		<tr>
			<td> &nbsp; </td>
			<td> <fmt:message key="course.tab.regperiod"/> </td>
			<td>
				<html:select name="courseForm" property="selectedPeriod" onchange="setAction();">
					<html:option value="-1">..</html:option>
					<html:options collection="acadPeriodOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td> &nbsp; </td>
			<td> <fmt:message key="priml.info2"/> </td>
			<td><bean:write name="courseForm" property="primlName"/></td>
		</tr>
		<tr><td> &nbsp; </td></tr>
		<tr>
			<td colspan='2'> <fmt:message key="priml.info3"/> </td>
		</tr>
		<tr>
			<td> &nbsp; </td>
			<td> <fmt:message key="priml.info6"/> </td>
			<td><html:text name="courseForm" property="newPrimlNetworkCode" size="10" maxlength="10"/></td>
		</tr>
		<logic:notEmpty name="courseForm" property="primLecturerOptions">
			<tr>
				<td><fmt:message key="priml.info4"/></td>
			</tr>
			<tr>
				<td> &nbsp; </td>
				<td colspan='2'> <fmt:message key="priml.info5"/> </td>
			</tr>
			<tr>
				<td> &nbsp; </td>
				<td> <fmt:message key="course.tab.network"/> </td>
				<td>
					<html:select name="courseForm" property="newPrimlNetworkCode2">
						<html:option value="">..</html:option>
						<html:options collection="primLecturerOptions" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
		</logic:notEmpty>
		
		<p>
		<tr>
			<td> &nbsp; </td>
			<td> <fmt:message key="priml.info7"/> </td>
			<td>
				<html:radio name = "courseForm" property="keepPriml" value="No"/> No
				<html:radio name = "courseForm" property="keepPriml"  value="Yes" /> Yes
			</td>
		</tr>
	</table>
	</logic:notEmpty>
	
	<p>
	<sakai:actions>
		<logic:notEmpty name="courseForm" property="acadPeriodOptions">
			<html:submit styleClass="active" property="act">
				<fmt:message key="button.submit"/>
			</html:submit>
		</logic:notEmpty>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>