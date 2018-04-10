<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>

	<h3><fmt:message key="admin.heading"/></h3>
	<hr/><br/>
	<sakai:instruction>
		<fmt:message key="label.addcoursenotetitle"/>
	</sakai:instruction>
	<sakai:messages/>
    		<sakai:messages message="true"/>
        	
	<html:form action="booklistadmin">
		
	
		<sakai:group_table>
			<tr>
				<td><fmt:message key="function.label.cnotes"/></td>
				<td><sakai:html_area property="courseNote" rows="2" cols="60"></sakai:html_area></td>
			</tr>
			
		</sakai:group_table>
	
		<html:hidden property="submitOption" value="COURSENOTE"/>
		<input type="hidden" name="cancelOption" value="editModule">
		
		<p/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.submit"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	
	</html:form>

</sakai:html>
	