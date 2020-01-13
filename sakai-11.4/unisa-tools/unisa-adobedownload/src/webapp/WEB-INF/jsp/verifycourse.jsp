<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page   import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.adobedownload.ApplicationResources"/>

<sakai:html>
<sakai:messages/>
<sakai:messages message="true"/>
<html:form action="/addcourse"> 
	
	<sakai:group_heading>
		<fmt:message key="download.title"/> 
	</sakai:group_heading>
	
	<sakai:instruction>
		<fmt:message key="download.instruction1"/><br><br>
		<fmt:message key="download.instruction2"/>
		
	</sakai:instruction>

	<fmt:message key="required"/> <sakai:required/>
	<p>
	<table>
		<tr>
			<td><fmt:message key="course.acadyear"/> <sakai:required/> </td>
			<td>
				<html:select name="courseForm" property="year">
					<html:options collection="yearOptions" property="value" labelProperty="label"/>
				</html:select>				
			</td>	
	
		</tr>
		<tr>
			<td><fmt:message key="course.acadperiod"/> <sakai:required/> </td>
			
			<td>
				<html:select name="courseForm" property="acadPeriod">
					<html:options collection="periodOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
				<td><fmt:message key="course.tab.course"/> <sakai:required/> </td>
				<td><html:text property="course" size="10" maxlength="10"/></td>
			</tr>
	</table>

	
	<sakai:actions>
				<html:submit property="action">
					<fmt:message key="course.button.continue" />
				</html:submit>
				<html:submit property="action">
					<fmt:message key="course.button.cancel" />
				</html:submit>
	</sakai:actions>
	
	
	<span id="0"><font color="FF3333" size="3" ><fmt:message key="download.delay.message" /></font></span>
	

</html:form>
</sakai:html>