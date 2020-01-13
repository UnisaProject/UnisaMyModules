
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>
	<sakai:heading><fmt:message key="admin.heading"/></sakai:heading>
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
				<%-- <logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">	
     			            <fmt:message key="label.ereservetype"/><br>
					          <html:radio name="booklistAdminForm" property="eReserveType"  value="Journal">
					         </html:radio> <fmt:message key="label.journal"/> 
					        <html:radio name="booklistAdminForm" property="eReserveType"     value="LawReport">
					        </html:radio> <fmt:message key="label.lawReport"/> 
					        <html:radio name="booklistAdminForm" property="eReserveType"     value="BookChapter" >
					        </html:radio> <fmt:message key="label.bookChapter"/><br>
				</logic:equal> --%>
		
			<tr>
			
				<td><fmt:message key="label.tableheadertitleofReport"/> <sakai:required/></td>				
				<td><html:text property="txtTitle" size="60" maxlength="256"></html:text></td>
			</tr>
			<tr>			
			<td><fmt:message key="label.tableheadercasenr"/><sakai:required/></td>
			<td><html:text property="ebookVolume" size="8" maxlength="10"></html:text> <fmt:message key="label.tableheaderebookcasenrrule"/> </td>
			</tr>
			<tr>
			<td><fmt:message key="label.tableheaderebookpages"/></td>
			<td><html:text property="ebook_pages" size="8" maxlength="10"></html:text> <fmt:message key="label.tableheaderebookpagesrule"/></td></tr>
			<tr>			
			<td><fmt:message key="label.tableheaderebookurl"/> </td>
			<td><html:text property="url" size="40" maxlength="100"><fmt:message key="label.tableheaderebookurlrule"/></html:text></td>
			</tr>
			<tr>			
			<td><fmt:message key="label.tableheadermastarcopy"/>  </td>
			<td>
					<html:radio property="masterCopy" value="Y"> <fmt:message key="label.mastercopyYes"/></html:radio>
					<html:radio property="masterCopy" value="N"><fmt:message key="label.mastercopyNo"/></html:radio>
			</td>
			</tr>
			<tr>			
			<td><fmt:message key="label.tableheadermastarcopyformate"/> </td>
		     <td>
					 <html:radio property="masterCopyFormat" value="Print"> <fmt:message key="label.mastercopyformatprint"/></html:radio>
					<html:radio property="masterCopyFormat" value="Electronic"><fmt:message key="label.mastercopyfomateelectronic"/></html:radio>
					 <html:radio property="masterCopyFormat" value="Unknown"> <fmt:message key="label.mastercopyformatunknown"/></html:radio></td>
			</tr>
			<tr> 
				<td><fmt:message key="label.coursenote"/></td>
				<td><html:textarea property="noteToLibrary" rows="4" cols="60"></html:textarea></td>
			</tr>
					
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
	
	