<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

<sakai:html>
	

	<p/>
	<html:form action="prebook">
	<logic:notEmpty  name="bookMenuForm" property="bookList">
	<logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">	
	<sakai:heading>
	<logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="label.addnewbookinstruction"/> </logic:equal>
	<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="label.addnewbookinstructionforR"/> </logic:equal>
	<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
	<p/>
	<sakai:messages/>
	<p/>
	<sakai:instruction><fmt:message key="function.foundmatchinstr"/></sakai:instruction> 
	<sakai:group_heading>
		<fmt:message key="function.selectbookmessage"/>
	</sakai:group_heading>
	<sakai:instruction>
	<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	<fmt:message key="function.foundmatchinstruction"/></logic:equal>
	<logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	<fmt:message key="function.foundmatchinstructionforR"/></logic:equal>
	
		<fmt:message key="label.foundmatch"/>
	</sakai:instruction> 
		<sakai:flat_list>
			<tr>
				
				<th><fmt:message key="label.tableheaderauthor"/></th>
				<th><fmt:message key="label.tableheaderyear"/></th>
				<th><fmt:message key="label.tableheadertitle"/></th>
				<th><fmt:message key="label.tableheaderedition"/></th>
				<th><fmt:message key="label.tableheaderpublisher"/></th>
				<th><fmt:message key="label.tableheaderISBN"/></th>
				<th><fmt:message key="label.tableheaderselect"/></th>
			</tr>			
			<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<tr>
				
				<td><bean:write name="c" property="txtAuthor"/></td>
				<td><bean:write name="c" property="txtYear"/></td>
				<td><bean:write name="c" property="txtTitle"/></td>
				<td><bean:write name="c" property="txtEdition"/></td>
				<td><bean:write name="c" property="txtPublisher"/></td>
				<td><bean:write name="c" property="txtISBN"/></td>
				<td><html:radio name="bookMenuForm" property = "copyBook" value='${c.bookId}'/></td>
			</tr>
		</logic:iterate>
		</sakai:flat_list>
		<html:hidden property="copyExistingBookOption" value="COPYEXISTINGBOOKS"/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.copy"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.newsearch"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.addnewbook"/></html:submit>
			<html:cancel styleClass="active" property="action"><fmt:message key="button.cancel"/></html:cancel>
		</sakai:actions>
		</logic:notEqual>
		
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	
			<sakai:heading><fmt:message key="label.addnewbookinstructionforE"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
	<p/>
	<sakai:messages/>
	<p/>
	<sakai:instruction><fmt:message key="function.foundmatchinstr"/></sakai:instruction> 
	<sakai:group_heading>
		<fmt:message key="function.selectbookmessageforE"/>
	</sakai:group_heading>
	<sakai:instruction>
		<fmt:message key="function.foundmatchinstructionforE"/>
		<fmt:message key="label.foundmatch"/>
	</sakai:instruction> 
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="label.tableheaderereservetype"/></th>
				
				<logic:notEqual name="bookMenuForm" property="eReserveType" value="Law Report">	
					<th><fmt:message key="label.tableheaderauthor"/></th>
				</logic:notEqual>	
				<logic:notEqual name="bookMenuForm" property="eReserveType" value="Law Report">	
					<th><fmt:message key="label.tableheaderyear"/></th>
				</logic:notEqual>
				<th><fmt:message key="label.tableheadertitle"/></th>
				<logic:notEqual name="bookMenuForm" property="eReserveType" value="Law Report">	
					<th><fmt:message key="label.tableheaderebookpublicationJ"/></th>
				</logic:notEqual>
				<logic:notEqual name="bookMenuForm" property="eReserveType" value="Law Report">	
					<th><fmt:message key="label.tableheaderebookvolume" /></th>
				</logic:notEqual>
				<logic:equal name="bookMenuForm" property="eReserveType" value="Law Report">	
					<th><fmt:message key="label.tableheaderebookvolumeforLawReport" /></th>
				</logic:equal>
				<th><fmt:message key="label.tableheaderebookpages" /></th>		
				<th><fmt:message key="label.tableheaderselect"/></th>
			</tr>			
			<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<tr>
				<td><bean:write name="c" property="eReserveType"/></td>
				
				<logic:notEqual name="bookMenuForm" property="eReserveType" value="Law Report">
					<td><bean:write name="c" property="txtAuthor"/></td>
				</logic:notEqual>
				<logic:notEqual name="bookMenuForm" property="eReserveType" value="Law Report">
					<td><bean:write name="c" property="txtYear"/></td>
				</logic:notEqual>
				<td><bean:write name="c" property="txtTitle"/></td>
				<logic:notEqual name="bookMenuForm" property="eReserveType" value="Law Report">
					<td><bean:write name="c" property="txtPublisher"/></td>
				</logic:notEqual>
				<td><bean:write name="c" property="ebookVolume" /></td>
				<td><bean:write name="c" property="ebook_pages" /></td>
				<td><html:radio name="bookMenuForm" property = "copyBook" value='${c.bookId}'/></td>
			</tr>
		</logic:iterate>
		</sakai:flat_list>
		<html:hidden property="copyExistingBookOption" value="COPYEXISTINGBOOKS"/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.copy"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.newsearch"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.addnewbook"/></html:submit>
			<html:cancel styleClass="active" property="action"><fmt:message key="button.cancel"/></html:cancel>
		</sakai:actions>
		</logic:equal>
	</logic:notEmpty>
		
	<logic:empty  name="bookMenuForm" property="bookList">
	<logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
		<sakai:heading><fmt:message key="label.addnewbookinstruction"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
	<p/>
	<sakai:messages/>
		
	<sakai:group_heading>
		<fmt:message key="function.selectbookmsg"/>
	</sakai:group_heading>
	
		<sakai:instruction>
			<html:radio property="searchOption" value="searchform"><fmt:message key="function.searchmsg"/></html:radio>   
			<fmt:message key="label.booknotfoundseparator"/>
			<html:radio property="searchOption" value="addform"><fmt:message key="function.addnewbookmsg"/></html:radio>
			<input  name="bookId" type="hidden" value=""/>
			<p/>
		</sakai:instruction>
				<p/>
		<html:hidden property="continueOption" value="SEARCHBOOK"/>
		
		<input type="hidden" name="cancelOption" value="TOCONFIRMBOOKS">
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.continue"/></html:submit>
			<html:cancel styleClass="active" property="action"><fmt:message key="button.cancel"/></html:cancel>
		</sakai:actions>
	</p></logic:notEqual>
		
		
		
	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">		
	<sakai:heading><fmt:message key="label.addnewbookinstructionforE"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
	<p/>
	<sakai:messages/>
	<p/>
	
	<sakai:group_heading>
		<fmt:message key="function.selectbookmsgforE"/>
	</sakai:group_heading>
	
		<sakai:instruction>
			<html:radio property="ereserveSearchOption" value="searchform"><fmt:message key="function.searchmsgforE"/></html:radio>   
			<fmt:message key="label.booknotfoundseparator"/>
			
			
			
			<%-- <fmt:message key="label.choosetype"/> --%>
			<html:radio property="ereserveSearchOption" value="addNew"><fmt:message key="function.addnewbookmsgforE"/></html:radio>
			<%-- <html:radio property="searchOption" value="LawReport"><fmt:message key="function.lawreport"/></html:radio>
			<html:radio property="searchOption" value="Extract"><fmt:message key="function.extract"/></html:radio> --%>
			<input  name="bookId" type="hidden" value=""/>
			<p/>
		</sakai:instruction>
				<p/>
		<html:hidden property="continueOption" value="SEARCHBOOK"/>
		
		<input type="hidden" name="cancelOption" value="TOCONFIRMBOOKS">
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.continue"/></html:submit>
			<html:cancel styleClass="active" property="action"><fmt:message key="button.cancel"/></html:cancel>
		</sakai:actions>
			</logic:equal>
	</logic:empty>
	
	</html:form>

</sakai:html>
	
	