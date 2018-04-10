<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<script language="JavaScript">
	function setAction() {
		document.mainForm.action = 'main.do?act=goCopyCourse';
		document.mainForm.submit();
	}
</script>


<sakai:html>
<html:form action="/main"> 
	<sakai:group_heading>
		<fmt:message key="copy.info.heading"/>
	</sakai:group_heading>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<p>
	<fmt:message key="copy.info1"/> <br>
	<fmt:message key="copy.info2"/> <br>
	<fmt:message key="copy.info3"/> <br>
	<fmt:message key="copy.info4"/> <br>
	</p>
	
	<table>
		<tr>
			<td> <fmt:message key="copy.which"/></td>
			<td> 
				<html:select property="selectedView" onchange="setAction();">
					<html:options collection="viewOptionList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td valign='top'> 
				<fmt:message key="copy.enter1"/> <br><br>
				<fmt:message key="copy.enter2"/> 
			</td>
			<td>
				<html:textarea name="mainForm" property="selectedCourses" rows="10">
				</html:textarea>
			</td> 
		</tr>
	</table>
	<table>
		<tr>
			<logic:notEqual name="mainForm" property="selectedView" value="E"> 
				<td colspan='3'> <fmt:message key="copy.enter3reg"/></td>
			</logic:notEqual>
			<logic:equal name="mainForm" property="selectedView" value="E">
				<td colspan='3'> <fmt:message key="copy.enter3ex"/></td> 
			</logic:equal>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan='2'><fmt:message key="course.year" /> <html:select
				name="mainForm" property='fromYear'>
				<html:options collection="yearOptions" property="value"
					labelProperty="label" />
			</html:select> <logic:notEqual name="mainForm" property="selectedView" value="E">
				<fmt:message key="course.acadperiod" />
			</logic:notEqual>
			 <logic:equal name="mainForm" property="selectedView" value="E">
				<fmt:message key="person.add.experiod" />
			</logic:equal>
			 <html:select name="mainForm" property='fromPeriod'>
				<html:options collection="periodOptions" property="value"
					labelProperty="label" />
			</html:select></td>
		</tr>
		<tr><td>&nbsp; </td></tr>
		<tr>
			<logic:notEqual name="mainForm" property="selectedView" value="E">
				<td colspan='3'> <fmt:message key="copy.enter4reg"/></td>
			</logic:notEqual>
			<logic:equal name="mainForm" property="selectedView" value="E">
				<td colspan='3'> <fmt:message key="copy.enter4ex"/></td>
			</logic:equal>
		</tr>
		<tr>
			<td>&nbsp; </td>
			<td colspan='2'> 
				<fmt:message key="course.year"/>
				<html:select name="mainForm" property='toYear'>
					<html:options collection="yearOptions" property="value" labelProperty="label"/>
				</html:select>
				<logic:notEqual name="mainForm" property="selectedView" value="E"> 
					<fmt:message key="course.acadperiod"/>
				</logic:notEqual>
				<logic:equal name="mainForm" property="selectedView" value="E"> 
					<fmt:message key="person.add.experiod"/>
				</logic:equal>
				<html:select name="mainForm" property='toPeriod'>
					<html:options collection="periodOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
	</table>
	
	<p>
	<sakai:actions>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.submit"/>
		</html:submit>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>