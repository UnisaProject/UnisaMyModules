<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.auditview.ApplicationResources"/>

<sakai:html>
	<html:form action="/auditView.do?action=input" >
	
	<sakai:group_heading> <fmt:message key="auditview.heading"/> </sakai:group_heading>
	<p><fmt:message key="event.archivenote"/></p>
	<fmt:message key="auditview.info"/>
	
	<p>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:group_table>
		<tr>
			<td><fmt:message key="auditview.userid"/>&nbsp;<font color="red"><sakai:required/></font></td>
			<td><html:text property="userId" size="15"></html:text> </td>
		</tr>
	  <tr>
			
		<td><fmt:message key="auditview.eventset"/> </td>
		<td>
		<html:select name="auditViewForm"  property="eventset" >
			<html:option value="${auditViewForm.current}">
				<fmt:message key="current.desc"/>
			</html:option>
			<html:option value="${auditViewForm.archived}">
				<fmt:message key="archive.desc"/>
			</html:option>
			<html:option value="${auditViewForm.prevYear}">
				<fmt:message key="prevYear.desc"/>
		     </html:option>
			<html:option value="${auditViewForm.prevYearLessOne}">
				<fmt:message key="prevYearlessone.desc"/>
		</html:option>
		</html:select></td>
			
		</tr>
	</sakai:group_table>
	
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.continue"/>
		</html:submit>
	</sakai:actions>
	</html:form>
	
	
</sakai:html>