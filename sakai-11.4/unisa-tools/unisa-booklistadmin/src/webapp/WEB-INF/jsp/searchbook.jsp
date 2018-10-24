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
<sakai:heading><fmt:message key="admin.heading"/></sakai:heading>
	<hr/><br/>
	<logic:notEqual name="booklistAdminForm" property="searchOption" value="publisher">	
		<sakai:instruction>
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E"><b><fmt:message key="label.addnewbookinstructionforE"/>
		 <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for 
		 <bean:write name="booklistAdminForm" property="college"/> </b><p/></p></logic:equal>
		 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><b><fmt:message key="label.addnewbookinstructionforR"/>
		 <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/> </b><p/></p></logic:equal>
		 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><b><fmt:message key="label.addnewbookinstruction"/>
		 <bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/> </b><p/></p></logic:equal>
		 </sakai:instruction>
	   <sakai:messages/>
	   <sakai:instruction>
	            <logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E">	
		              <fmt:message key="function.searchbookmsg"/>		
		              <sakai:group_heading>
			               <fmt:message key="label.searchbookinstruction"/>
		              </sakai:group_heading>
                </logic:notEqual>
	            <logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">	
			          <fmt:message key="function.searchbookmsgforE"/>	
		              <sakai:group_heading>
			              <fmt:message key="label.searchbookinstructionforereserves"/>
	                  </sakai:group_heading>
	            
	                       <fmt:message key="label.ereservetype"/><br>
					          <html:radio name="booklistAdminForm" property="eReserveType"  value="Journal">
					         </html:radio> <fmt:message key="label.journal"/> 
					        <html:radio name="booklistAdminForm" property="eReserveType"     value="LawReport">
					        </html:radio> <fmt:message key="label.lawReport"/> 
					        <html:radio name="booklistAdminForm" property="eReserveType"     value="BookChapter" >
					        </html:radio> <fmt:message key="label.bookChapter"/><br>
				</logic:equal>
		         <fmt:message key="function.searchbookinstruction2"/>
	    </sakai:instruction>
	
	
		<p/>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="label.tableheadertitle"/></td>
				<td><html:text property="txtTitle"></html:text></td>
			</tr>
			<tr>
				<td><fmt:message key="label.tableheaderauthor"/></td>
				<td><html:text property="txtAuthor"></html:text></td>
			</tr>
			
		</sakai:group_table>
		<sakai:instruction>
			<fmt:message key="function.addnewbookeginstruction"/>
			<fmt:message key="function.addnewbookeginstruction1"/>
		</sakai:instruction>
		
		<input type="hidden" name="cancelOption" value="TOCONFIRMBOOKS">
		
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.search"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</p>
	</logic:notEqual>
	<logic:equal name="booklistAdminForm" property="searchOption" value="publisher">	
	<sakai:messages/>
	
          <sakai:instruction><b>
			<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P"><fmt:message key="label.searchbookinstruction2"/></logic:equal>
		 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="R"><fmt:message key="label.searchbookinstructionforR"/></logic:equal>
		 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="E"><fmt:message key="label.searchbookinstructionforE"/></logic:equal>
		</b></sakai:instruction><p/>
		<sakai:instruction>
			<fmt:message key="label.searchbookinstruction3"/>
		</sakai:instruction>
		 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">
		   
					          <fmt:message key="label.ereservetype"/><br>
					          <html:radio name="booklistAdminForm" property="eReserveType"  value="Journal">
					         </html:radio> <fmt:message key="label.journal"/> 
					        <html:radio name="booklistAdminForm" property="eReserveType"     value="LawReport">
					        </html:radio> <fmt:message key="label.lawReport"/> 
					        <html:radio name="booklistAdminForm" property="eReserveType"     value="BookChapter">
					        </html:radio> <fmt:message key="label.bookChapter"/><br>
					    
			</logic:equal>
			<sakai:group_heading>		
			<fmt:message key="label.searchforbookheading"/>
		</sakai:group_heading><br>
	<sakai:group_table>
			<tr>
				<td><fmt:message key="label.tableheaderbooktitle"/></td>
				<td><html:text property="txtTitle"></html:text></td>
			</tr>
			<tr>
				<td><fmt:message key="label.tableheaderbookauthor"/></td>
				<td><html:text property="txtAuthor"></html:text></td>
			</tr>
			<tr>
				<td><fmt:message key="label.tableheaderbookpublisher"/></td>
				<td><html:text property="publisherName"></html:text></td>
			</tr>			
		</sakai:group_table>
			<input type="hidden" name="cancelOption" value="TOCOURSEVIEW">
			<html:hidden property="fromButton" value="searchbook"/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.search"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.clear"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</logic:equal>
	</html:form>

</sakai:html>
	
	