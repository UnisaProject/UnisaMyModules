<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

<sakai:html>

		<sakai:heading>
			
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/>	</logic:equal>
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/>	</logic:equal>
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="E"><fmt:message key="function.booklistforE"/>	</logic:equal>
			 <bean:write name="bookMenuForm" property="courseId"/><p/> 
		</sakai:heading>
		<sakai:messages/>		
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="label.reusefrmmsg"/>	</logic:equal>
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="label.reusefrmmsgforR"/>	</logic:equal>
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="E"><fmt:message key="label.reusefrmmsgforE"/>	</logic:equal>
		
		
		<sakai:group_heading>
			<logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E"> <fmt:message key="label.reusefrminstruction"/><br/></logic:notEqual>
			<logic:equal name="bookMenuForm" property="typeOfBookList" value="E"><fmt:message key="label.reusefrminstructionforE"/>	</logic:equal>
               
        </sakai:group_heading>

	<html:form action="prebook">
	<logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
		<p/>
		<sakai:flat_list>
			<tr>				
				<th><fmt:message key="label.tableheaderauthor"/></th>
				<th><fmt:message key="label.tableheaderyear"/></th>
				<th><fmt:message key="label.tableheadertitle"/></th>
				<th><fmt:message key="label.tableheaderedition"/></th>
				<th><fmt:message key="label.tableheaderlanguage"/></th>
				<th><fmt:message key="label.tableheadercopy"/></th>
			</tr>			
			<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<tr>
				<td><bean:write name="c" property="txtAuthor"/></td>
				<td><bean:write name="c" property="txtYear"/></td>
				<td><bean:write name="c" property="txtTitle"/></td>
				<td><bean:write name="c" property="txtEdition"/></td>
				<td><bean:write name="c" property="courseLang"/></td>
				<td><html:checkbox name="bookMenuForm" property = '<%="bookIndex["+cindex+"].remove" %>' /></td>
			</tr>
		</logic:iterate>
		</sakai:flat_list>
		</p></logic:notEqual>
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
		<p/>
		<sakai:flat_list>
			<tr>				
			    <th><fmt:message key="label.tableheaderereservetype"/></th>
				<th><fmt:message key="label.tableheaderauthor"/></th>
				<th><fmt:message key="label.tableheaderyear"/></th>
				<th><fmt:message key="label.tableheadertitle"/></th>
				<th><fmt:message key="label.tableheaderebookpub"/></th>	
				<th><fmt:message key="label.tableheaderebookvolume" /></th>
				<th><fmt:message key="label.tableheaderebookpages" /></th>			
				<th><fmt:message key="label.tableheadercopy"/></th>
			</tr>			
			<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<tr>
			    <td><bean:write name="c" property="eReserveType"/></td>
				<td><bean:write name="c" property="txtAuthor"/></td>
				<td><bean:write name="c" property="txtYear"/></td>
				<td><bean:write name="c" property="txtTitle"/></td>
				<td><bean:write name="c" property="txtPublisher"/></td>
				<td><bean:write name="c" property="ebookVolume" /></td>
				<td><bean:write name="c" property="ebook_pages" /></td>
				<td><html:checkbox name="bookMenuForm" property = '<%="bookIndex["+cindex+"].remove" %>' /></td>
			</tr>
		</logic:iterate>
		</sakai:flat_list>
		</logic:equal>
	
		<html:hidden property="copyExistingBookOption" value="COPYALLBOOKS"/>
		<html:hidden property="cancelOption" value="BOOKLISTSTATUSVIEW"/>

		<p/>		
		
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.copy"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
		</html:form>
</sakai:html>
