<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>
<html:form action="booklistadmin">
<sakai:heading><fmt:message key="admin.heading"/></sakai:heading>
<sakai:messages/>
	<hr/><br/>	
			<sakai:instruction><b>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><fmt:message key="label.searchbookinstruction2"/></logic:equal>
		 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><fmt:message key="label.searchbookinstructionforR"/></logic:equal>
		 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="E"><fmt:message key="label.searchbookinstructionforE"/></logic:equal>
		</b></sakai:instruction>		
			<sakai:group_heading>		
			<fmt:message key="label.searchforbookheading"/>
		</sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="label.searchforcodeinstr"/></td>
				</tr>
				<tr>
				<td><fmt:message key="label.entermodulecode"/><sakai:required/></td>
				<td><html:text property="courseId"></html:text> &nbsp;&nbsp; <b>AND</b>
				<fmt:message key="label.yearinstr"/> <html:select property="year">
			<html:option value="${booklistAdminForm.lastYear}">
				<bean:write name="booklistAdminForm" property="lastYear"/>
			</html:option><html:option value="${booklistAdminForm.currentYear}">
				<bean:write name="booklistAdminForm" property="currentYear"/>
			</html:option><html:option value="${booklistAdminForm.nextYear}">
				<bean:write name="booklistAdminForm" property="nextYear"/>
			</html:option></html:select></td>
		     	</tr>
				</sakai:group_table>				
				<input type="hidden" name="searchOption" value="searchCourse">
				<input type="hidden" name="cancelOption" value="TOCOURSEVIEW">
				<input type="hidden" name="fromButton" value="searchCourse">
					<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.search"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.clear"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	
	</html:form>
	</sakai:html>