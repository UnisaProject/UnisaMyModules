<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>
	
		<sakai:heading><p/>
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E"><fmt:message key="function.booklistforE"/></logic:equal>
		<bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> 			
	</sakai:heading>

	<p/>
	<sakai:messages/>
	<html:form action="booklistadmin">	
	
	<html:hidden property="cancelOption" value="BOOKLISTSTATUSVIEW"/>
	<html:hidden property="backOption" value="editModule"/>
		<p/>
		<sakai:actions>
			<html:submit styleClass="active" property="action">
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">
			<fmt:message key="button.noItems"/></logic:equal>
			
			<logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E">
			<fmt:message key="button.noBooks"/></logic:notEqual>
			</html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>
	