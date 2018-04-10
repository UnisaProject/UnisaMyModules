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
		<h3><fmt:message key="admin.heading"/></h3>
	<hr/><br/>
	<logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E">
	<sakai:heading><p/>
	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P">
			<fmt:message key="function.booklist"/></logic:equal>
				<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R">
			<fmt:message key="function.booklistforR"/></logic:equal>
			 <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for  
			 <bean:write name="booklistAdminForm" property="college"/>
	</sakai:heading>
	<p/>
  <sakai:messages/>

	<sakai:flat_list>
		<th><fmt:message key="label.tableheaderauthor"/></th>			
			<th><fmt:message key="label.tableheaderyear"/></th>	
			<th><fmt:message key="label.tableheadertitle"/></th>					
			<th><fmt:message key="label.tableheaderedition"/></th>			
			<th><fmt:message key="label.tableheaderpublisher"/></th>
			<th><fmt:message key="label.tableheaderisbn"/></th>	
		<p/>
		<logic:iterate  name="booklistAdminForm" property="booklist" id="c" indexId="cindex">
			<tr>
			<td><bean:write name="c" property="txtAuthor"/></td>
				<td><bean:write name="c" property="txtYear"/></td>
					<td><bean:write name="c" property="txtTitle"/></td>
					<td><bean:write name="c" property="txtEdition"/></td>
					<td><bean:write name="c" property="txtPublisher"/></td>
					<td><bean:write name="c" property="txtISBN"/></td>		
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	<p/>
	</logic:notEqual>
	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">
		<sakai:heading><p/>
			<fmt:message key="function.booklistforE"/>
			 <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for 
			 <bean:write name="booklistAdminForm" property="college"/> 
	</sakai:heading>
	<p/>
  <sakai:messages/>

	<sakai:flat_list>
		<tr>
			 <th><fmt:message key="label.tableheaderauthor"/></th>
			<th><fmt:message key="label.tableheaderyear"/></th>
			<th><fmt:message key="label.tableheadertitle"/></th>
			<th><fmt:message key="label.tableheaderebookpublicationJ"/></th>
			<th><fmt:message key="label.tableheaderebookvolume" /></th>
			<th><fmt:message key="label.tableheaderebookpages" /></th>	
		</tr>
		<p/>
		<logic:iterate  name="booklistAdminForm" property="booklist" id="c" indexId="cindex">
			<tr>
			<td><bean:write name="c" property="txtAuthor"/></td>
				<td><bean:write name="c" property="txtYear"/></td>
					<td><bean:write name="c" property="txtTitle"/></td>
					<td><bean:write name="c" property="txtPublisher"/></td>
					<td><bean:write name="c" property="ebookVolume"/></td>		
					<td><bean:write name="c" property="ebook_pages"/></td>	
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	<p/>
	</logic:equal>
		<html:hidden property="continueOption" value="CONFIRMBYADMIN"/>
	<html:hidden property="backOption" value="editModule"/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.publishbooklist"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>
