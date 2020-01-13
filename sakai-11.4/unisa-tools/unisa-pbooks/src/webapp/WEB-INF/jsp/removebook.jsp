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
			<logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.rembookfrmtitle"/></logic:equal>
				<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.rembookfrmtitleforR"/></logic:equal>
					<logic:equal name="bookMenuForm" property="typeOfBookList" value="E"><fmt:message key="function.rembookfrmtitleforE"/></logic:equal>
			 <bean:write name="bookMenuForm" property="acadyear"/> <fmt:message key="function.rembookfrmtitlelist"/>
	</sakai:heading>
	<p/>
	<sakai:messages/>
	<html:form action="prebook">
	<logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">	
	<sakai:flat_list>
		<tr>
			<th><fmt:message key="label.tableheaderauthor"/></th>
			<th><fmt:message key="label.tableheaderyear"/></th>
			<th><fmt:message key="label.tableheadertitle"/></th>
			<th><fmt:message key="label.tableheaderedition"/></th>
			<th><fmt:message key="label.tableheaderlastmodified"/></th>
		</tr>
		<p/>
		<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<logic:equal name ="c" property="remove" value="true">
				<tr>
					<td><bean:write name="c" property="txtAuthor"/></td>
					<td><bean:write name="c" property="txtYear"/></td>
					<td><bean:write name="c" property="txtTitle"/></td>
					<td><bean:write name="c" property="txtEdition"/></td>		
				   <td><bean:write name="bookMenuForm" property="lastmodifiedforbook"/></td>						
				</tr>
			</logic:equal>
		</logic:iterate>
	</sakai:flat_list>
	</logic:notEqual>
	
			<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	
				<sakai:flat_list>
		<tr>
			<th><fmt:message key="label.tableheaderauthor"/></th>
			<th><fmt:message key="label.tableheaderyear"/></th>
			<th><fmt:message key="label.tableheadertitle"/></th>
			<th><fmt:message key="label.tableheaderebookpublicationJ"/></th>
			<th><fmt:message key="label.tableheaderebookvolume" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>	
			<th><fmt:message key="label.tableheaderlastmodified"/></th>
		</tr>
		<p/>
		<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<logic:equal name ="c" property="remove" value="true">
				<tr>
					<td><bean:write name="c" property="txtAuthor"/></td>
					<td><bean:write name="c" property="txtYear"/></td>
					<td><bean:write name="c" property="txtTitle"/></td>
					<td><bean:write name="c" property="txtPublisher"/></td>
					<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
					<td><bean:write name="bookMenuForm" property="lastmodifiedforbook"/></td>					
				</tr>
			</logic:equal>
		</logic:iterate>
	</sakai:flat_list>
			</logic:equal>
	
	
	<input type="hidden" name="cancelOption" value="TOCONFIRMBOOKS">
	
		<p/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.delete"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		</html:form>

</sakai:html>
	
	