<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

<sakai:html>
	
	<sakai:heading><p/>
			<fmt:message key="function.booklist"/> <bean:write name="bookMenuForm" property="courseId"/> <bean:write name="bookMenuForm" property="acadyear"/> 
	</sakai:heading>
	<p/>
	<sakai:messages/>
	<sakai:instruction>
		<fmt:message key="function.hodapprovalinstruction"/>
	</sakai:instruction>
	<p/>
	<html:form action="prebook">

		<sakai:flat_list>
			<tr>
				<th><fmt:message key="label.tableheaderauthor"/></th>
				<th><fmt:message key="label.tableheaderyear"/></th>
				<th><fmt:message key="label.tableheadertitle"/></th>
				<th><fmt:message key="label.tableheaderedition"/></th>
				<th><fmt:message key="label.tableheaderpublisher" /></th>
			</tr>
			<p/>
			<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<tr>
				<td><bean:write name="c" property="txtAuthor" /></td>
				<td><bean:write name="c" property="txtYear" /></td>
				<td><bean:write name="c" property="txtTitle" /></td>
				<td><bean:write name="c" property="txtEdition" /></td>
				<td><bean:write name="c" property="txtPublisher" /></td>
			</tr>
			</logic:iterate>
		</sakai:flat_list>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="label.hodapprovalcomment"/></td>
				<td><html:textarea property="hodComments" rows="10" cols="50"></html:textarea></td>
				
			</tr>
			
		</sakai:group_table>
	<html:hidden property="cancelOption" value="BOOKLISTSTATUSVIEW"/>
		<p/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.approve"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.requestedit"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>
	
	
	
	