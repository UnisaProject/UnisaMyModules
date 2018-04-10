<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>
	<logic:notEqual name="booklistAdminForm" property="searchOption" value="publisher">
<h3><fmt:message key="admin.heading"/></h3>
	<hr/><br/>
	<html:form action="booklistadmin">
	<logic:notEmpty  name="booklistAdminForm" property="booklist">
	<logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E">	
	<sakai:heading>
	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><fmt:message key="label.addnewbookinstruction"/> </logic:equal>
	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><fmt:message key="label.addnewbookinstructionforR"/> </logic:equal>
	<bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/></sakai:heading>
	<p/>
	<sakai:messages/>
	<p/>
	<sakai:instruction><fmt:message key="function.foundmatchinstr"/></sakai:instruction> 
	<sakai:group_heading>
		<fmt:message key="function.selectbookmessage"/>
	</sakai:group_heading>
	<sakai:instruction>
	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P">	<fmt:message key="function.foundmatchinstruction"/></logic:equal>
	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R">	<fmt:message key="function.foundmatchinstructionforR"/></logic:equal>
	
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
			<logic:iterate  name="booklistAdminForm" property="booklist" id="c" indexId="cindex">
			<tr>
				<td><bean:write name="c" property="txtAuthor"/></td>
				<td><bean:write name="c" property="txtYear"/></td>
				<td><bean:write name="c" property="txtTitle"/></td>
				<td><bean:write name="c" property="txtEdition"/></td>
				<td><bean:write name="c" property="txtPublisher"/></td>
				<td><bean:write name="c" property="txtISBN"/></td>
				<td><html:radio name="booklistAdminForm" property = "copyBook" value='${c.bookId}'/></td>
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
		
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">	
			<sakai:heading><fmt:message key="label.addnewbookinstructionforE"/> <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/></sakai:heading>
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
			    <logic:notEqual name="booklistAdminForm"  property="eReserveType" value="LawReport">
				     <th><fmt:message key="label.tableheaderauthor"/></th>
				     <th><fmt:message key="label.tableheaderyear"/></th>
				 </logic:notEqual>
				 <th><fmt:message key="label.tableheadertitle"/></th>
				 <logic:notEqual name="booklistAdminForm"  property="eReserveType" value="LawReport">
				          <th><fmt:message key="label.tableheaderebookpublication"/></th>	
				 </logic:notEqual>
				<th>
				<logic:notEqual name="booklistAdminForm"  property="eReserveType" value="LawReport"> 
			            <fmt:message key="label.tableheaderebookvolume" />
			    </logic:notEqual>
			    <logic:equal name="booklistAdminForm"  property="eReserveType" value="LawReport"> 
			               <fmt:message key="label.tableheadercasenr" />
			    </logic:equal>
			    </th>
				<th><fmt:message key="label.tableheaderebookpages" /></th>			
				<th><fmt:message key="label.tableheadercopy"/></th>
			</tr>			
			<logic:iterate  name="booklistAdminForm" property="booklist" id="c" indexId="cindex">
			<tr>
			    <td>
			         <bean:write name="c" property="eReserveType"/><br>
				</td>
				 <logic:notEqual name="booklistAdminForm"  property="eReserveType" value="LawReport">
				     <td><bean:write name="c" property="txtAuthor"/></td>
				     <td><bean:write name="c" property="txtYear"/></td>
				 </logic:notEqual>
				<td><bean:write name="c" property="txtTitle"/></td>
				<logic:notEqual name="booklistAdminForm"  property="eReserveType" value="LawReport">
				      <td><bean:write name="c" property="txtPublisher"/></td>
				 </logic:notEqual>
				<td><bean:write name="c" property="ebookVolume" /></td>
				<td><bean:write name="c" property="ebook_pages" /></td>
				<td><html:radio name="booklistAdminForm" property = "copyBook" value='${c.bookId}'/></td>
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
		
	<logic:empty  name="booklistAdminForm" property="booklist">
	<logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E">
		<sakai:heading><logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><fmt:message key="label.addnewbookinstruction"/> </logic:equal>
	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><fmt:message key="label.addnewbookinstructionforR"/> </logic:equal>
	 <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/></sakai:heading>
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
		
		
		
	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">		
	<sakai:heading><fmt:message key="label.addnewbookinstructionforE"/> <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/></sakai:heading>
	<p/>
	<sakai:messages/>
	<p/>
	
	<sakai:group_heading>
		<fmt:message key="function.selectbookmsgforE"/>
	</sakai:group_heading>
	
		<sakai:instruction>
			<html:radio property="searchOption" value="searchform"><fmt:message key="function.searchmsgforE"/></html:radio>   
			<fmt:message key="label.booknotfoundseparator"/>
			<html:radio property="searchOption" value="addform"><fmt:message key="function.addnewbookmsgforE"/></html:radio>
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
	</logic:notEqual>
	
	
	
	
	
	
	<logic:equal name="booklistAdminForm" property="searchOption" value="publisher">
	<h3><fmt:message key="admin.heading"/></h3>
	<hr/><br/>
	<html:form action="booklistadmin">
	<logic:notEmpty  name="booklistAdminForm" property="booklist">
	<sakai:heading>
	<fmt:message key="label.searchbookinfo1"/>
	<logic:notEmpty  name="booklistAdminForm" property="txtTitle">
	<bean:write name="booklistAdminForm" property="txtTitle"/> </logic:notEmpty> &nbsp;
	<logic:notEmpty  name="booklistAdminForm" property="txtAuthor">
	<bean:write name="booklistAdminForm" property="txtAuthor"/> </logic:notEmpty>&nbsp;
	<logic:notEmpty  name="booklistAdminForm" property="publisherName">
	<bean:write name="booklistAdminForm" property="publisherName"/></logic:notEmpty>
	</sakai:heading>
	<p/>
	<sakai:messages/>
	<p/>
	<logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E">	
	<sakai:instruction><fmt:message key="function.booksearchinfo2"/></sakai:instruction> 	
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="label.tableheadercourse"/></th>
				<th><fmt:message key="label.tableheaderacadyear"/></th>
				<th><fmt:message key="label.tableheaderauthor"/></th>
				<th><fmt:message key="label.tableheaderotherauthor"/></th>
				<th><fmt:message key="label.tableheaderyear"/></th>
				<th><fmt:message key="label.tableheadertitle"/></th>
				<th><fmt:message key="label.tableheaderedition"/></th>
				<th><fmt:message key="label.tableheaderpublisher"/></th>
				<th><fmt:message key="label.tableheaderISBN"/></th>
				</tr>			
			<logic:iterate  name="booklistAdminForm" property="booklist" id="c" indexId="cindex">
			<tr>
		    	<td><bean:write name="c" property="course"/></td>
		    	<td><bean:write name="c" property="academicYear"/></td>
				<td><bean:write name="c" property="txtAuthor"/><br>
				<b><html:link	href="booklistadmin.do?action=addNewBook&continueOption=updatebookforcourses&searchOption=edit"
								paramName="c" paramProperty="bookId" paramId="bookId">[Edit]</html:link></b></td>
				<td><bean:write name="c" property="txtOtherAuthor"/></td>
				<td><bean:write name="c" property="txtYear"/></td>
				<td><bean:write name="c" property="txtTitle"/></td>
				<td><bean:write name="c" property="txtEdition"/></td>
				<td><bean:write name="c" property="txtPublisher"/></td>
				<td><bean:write name="c" property="txtISBN"/></td>				
			</tr>
		</logic:iterate>
		</sakai:flat_list>
		</logic:notEqual>
		
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">	
		<sakai:flat_list>
			<tr>
			    <th><fmt:message key="label.tableheadercourse"/></th>
				<th><fmt:message key="label.tableheaderacadyear"/></th>
				<th><fmt:message key="label.tableheaderauthor"/><br>
				<th><fmt:message key="label.tableheadertitle"/></th>
				 <logic:notEqual name="booklistAdminForm"  property="eReserveType" value="LawReport">
				          <th><fmt:message key="label.tableheaderebookpublication"/></th>	
				 </logic:notEqual>
				<th>
				<logic:notEqual name="booklistAdminForm"  property="eReserveType" value="LawReport"> 
			            <fmt:message key="label.tableheaderebookvolume" />
			    </logic:notEqual>
			    <logic:equal name="booklistAdminForm"  property="eReserveType" value="LawReport"> 
			               <fmt:message key="label.tableheadercasenr" />
			    </logic:equal>
			    </th>
				<th><fmt:message key="label.tableheaderebookpages" /></th>			
			</tr>			
			<logic:iterate  name="booklistAdminForm" property="booklist" id="c" indexId="cindex">
			<tr>
			    <td><bean:write name="c" property="course"/></td>
		    	<td><bean:write name="c" property="academicYear"/></td>
				<td><bean:write name="c" property="txtAuthor"/><br>
				     <td><bean:write name="c" property="txtTitle"/><br>
				     <b><html:link	href="booklistadmin.do?action=addNewBook&continueOption=updatebookforcourses&searchOption=edit"
								paramName="c" paramProperty="bookId" paramId="bookId">[Edit]</html:link></b>
				</td>
				<logic:notEqual name="booklistAdminForm"  property="eReserveType" value="LawReport">
				      <td><bean:write name="c" property="txtPublisher"/></td>
				 </logic:notEqual>
				<td><bean:write name="c" property="ebookVolume" /></td>
				<td><bean:write name="c" property="ebook_pages" /></td>
				
			</tr>
		</logic:iterate>
		</sakai:flat_list>

		</logic:equal>
			<input type="hidden" name="cancelOption" value="TOCOURSEVIEW">
			<html:hidden property="backOption" value="searchBook"/>
			<sakai:actions>
		    <html:cancel styleClass="active" property="action"><fmt:message key="button.back"/></html:cancel>
			<html:cancel styleClass="active" property="action"><fmt:message key="button.cancel"/></html:cancel>
		</sakai:actions>
		
	</logic:notEmpty>	
	</html:form>
	</logic:equal>

</sakai:html>
	
	