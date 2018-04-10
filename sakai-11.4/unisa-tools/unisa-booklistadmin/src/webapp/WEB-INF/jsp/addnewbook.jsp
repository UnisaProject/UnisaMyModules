
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
	<sakai:messages/>

	<logic:equal name="booklistAdminForm" property="bookId" value="">
		<html:hidden property="submitOption" value="NEWBOOK"/> 
		<sakai:instruction>
		<b>	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><fmt:message key="label.addnewbookinstruction"/></logic:equal>
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><fmt:message key="label.addnewbookinstructionforR"/></logic:equal>
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E"><fmt:message key="label.addnewbookinstructionforE"/></logic:equal>
		 <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/></b><p/>
			 <logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E"><fmt:message key="function.enterdetailsmsg"/><p/></logic:notEqual>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E"><fmt:message key="function.enterdetailsmsgforE"/><p/></logic:equal>
			<sakai:group_heading><logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><fmt:message key="function.addnewbookmsg"/></logic:equal>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><fmt:message key="function.addnewbookmsgforR"/></logic:equal>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E"><fmt:message key="function.addnewbookmsgforE"/></logic:equal>
			</sakai:group_heading>
			<p/>
		</sakai:instruction>
	</logic:equal>
	
	<logic:notEqual name="booklistAdminForm" property="bookId" value="">
		<input  name="bookId" type="hidden" value='${booklistAdminForm.bookId}'/>
		<sakai:instruction>
			<b>	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><fmt:message key="function.editbookfrmtitle"/></logic:equal>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><fmt:message key="function.editbookfrmtitleforR"/></logic:equal>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E"><fmt:message key="function.editbookfrmtitleforE"/></logic:equal>
			<logic:notEqual name="booklistAdminForm" property="searchOption" value="publisher">
		  for <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/> </logic:notEqual></b><p/>
			<fmt:message key="function.editbookfrminstruction"/><p/>
		</sakai:instruction>
	</logic:notEqual>

	<html:form action="booklistadmin">

		<p/>
		<sakai:group_table>
					<tr>
			
				<td><fmt:message key="label.tableheadertitle"/> <sakai:required/></td>				
				<td><html:text property="txtTitle" size="60" maxlength="256"></html:text></td>
			</tr>
			
			<tr>
				<td><fmt:message key="label.tableheaderauthor"/> <sakai:required/></td>
				<td><html:text property="txtAuthor" size="60" maxlength="256"></html:text></td>
			</tr>
			<tr>			<td><fmt:message key="label.tableheaderotherauthor"/></td>
				<td><html:text property="txtOtherAuthor" size="60" maxlength="256"></html:text> <fmt:message key="label.tableheaderauthorrule"/></td>
			</tr>
			
				
			<tr>
				<td><fmt:message key="label.tableheaderedition"/> <sakai:required/></td>
				<td><html:text property="txtEdition" size="6" maxlength="10"></html:text>   <fmt:message key="label.tableheadereditionrule"/></td>
			<tr>
				<td><fmt:message key="label.tableheaderyear"/> <sakai:required/></td>
				<td><html:text property="txtYear" size="4" maxlength="4"></html:text>    <fmt:message key="label.tableheaderyearrule"/></td>
			</tr>			
			<tr>
				<td><fmt:message key="label.tableheaderpublisher"/> <sakai:required/></td>
				<td><html:text property="txtPublisher" size="40" maxlength="256"></html:text>    <fmt:message key="label.tableheaderpublisherrule"/></td>
			</tr>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P">
			<tr>
				<td><fmt:message key="label.tableheaderISBN"/><sakai:required/></td>				
				<td><html:text property="txtISBN" size="20" maxlength="25"></html:text> 			
				 </td>
			</tr>
			</logic:equal>
				<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R">
			<tr>
				<td><fmt:message key="label.tableheaderISBN1"/><sakai:required/></td>				
				<td><html:text property="txtISBN" size="20" maxlength="25"></html:text> 			
				 </td>
			</tr>
			<tr>
				<td><fmt:message key="label.tableheaderISBN2"/></td>				
				<td><html:text property="txtISBN1" size="20" maxlength="25"></html:text> 			
				 </td>
			</tr>
			<tr>
				<td><fmt:message key="label.tableheaderISBN3"/></td>				
				<td><html:text property="txtISBN2" size="20" maxlength="25"></html:text> 			
				 </td>
			</tr>
			<tr>
				<td><fmt:message key="label.tableheaderISBN4"/></td>				
				<td><html:text property="txtISBN3" size="20" maxlength="25"></html:text> 			
				 </td>
			</tr>
			</logic:equal>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P">		
			<tr>
				<td><fmt:message key="label.booknote"/></td>
				<td><html:textarea property="txtBookNote" rows="2" cols="60"></html:textarea></td>
			</tr>
			</logic:equal>
			<tr>
				<td><fmt:message key="label.languagemsg"/></td>
				<td>
					<html:radio property="courseLang" value="B"> <fmt:message key="label.optBoth"/></html:radio>
					<html:radio property="courseLang" value="E"><fmt:message key="label.optEng"/></html:radio>
					<html:radio property="courseLang" value="A"> <fmt:message key="label.optAfr"/></html:radio>	
				</td>
			</tr>
			<tr><td><fmt:message key="label.editbookfrmmsg"/></td>
				<td><html:radio property="publishStatus" value="1"><fmt:message key="label.editbookfrmoptionyes"/></html:radio>
					<html:radio property="publishStatus" value="0"> <fmt:message key="label.editbookfrmoptionno"/></html:radio></td></tr>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R">	
			<tr><td><fmt:message key="label.availableasebookmsg"/></td>
					<td><html:radio property="availableAsEbook" value="1"><fmt:message key="label.availableAsEbookyes"/></html:radio>
					<html:radio property="availableAsEbook" value="0"> <fmt:message key="label.availableAsEbookno"/></html:radio>
					<html:radio property="availableAsEbook" value="unknown"> <fmt:message key="label.availableAsEbookunknown"/> </html:radio></td></tr>
					</logic:equal>
				
				<logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="P">			
				<tr> 
				<td><fmt:message key="label.coursenote"/></td>
				<td><html:textarea property="noteToLibrary" rows="4" cols="60"></html:textarea></td>
			</tr>
				</logic:notEqual>
			
		</sakai:group_table>
			
		<p/>
		<logic:equal name="booklistAdminForm" property="bookId" value="">
		<html:hidden property="cancelOption" value="editModule"/>
		<html:hidden property="submitOption" value="NEWBOOK"/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.submit"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		</logic:equal>
		
		<logic:notEqual name="booklistAdminForm" property="bookId" value="">
			<html:hidden property="submitOption" value="UPDATEBOOK"/>
			<input  name="bookId" type="hidden" value='${booklistAdminForm.bookId}'/>
			<html:hidden property="cancelOption" value="editModule"/>
				
			<sakai:actions>
				<html:submit styleClass="active" property="action"><fmt:message key="button.submit"/></html:submit>
				<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
			</sakai:actions>
		</logic:notEqual>
	<html:hidden property="addNewBookErrorMsg" value="No book entries found that match your search criteria"/>
	</html:form>

</sakai:html>
	
	