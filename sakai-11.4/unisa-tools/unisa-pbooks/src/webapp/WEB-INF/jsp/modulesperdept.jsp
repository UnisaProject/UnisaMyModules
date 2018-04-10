<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

 <sakai:html>
 <sakai:messages/>
  <html:form action="authorisation">
     <sakai:heading>
    <b><fmt:message key="function.booklist"/></b>
    </sakai:heading>	
      <sakai:heading>
    <logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	<fmt:message key="function.booklists"/> </logic:equal>
    	<logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	<fmt:message key="function.booklistsforR"/> </logic:equal>
    	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	<fmt:message key="function.booklistsforE"/> </logic:equal></b>
    	for </sakai:heading>
    			<sakai:group_table>  
		<tr> <td> <fmt:message key="function.deandepartments" /></td>
     <td><html:select name="bookMenuForm"  property="department">
     	           <html:option value="-1">Select a Department</html:option>
        		    <html:options  collection="schdirdepts" property="value" labelProperty="label" />
    	</html:select>   
	 	</td></tr> 
	 	</sakai:group_table>
	 	<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="sites.display"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.backtomain"/></html:submit>
		</sakai:actions>
	 	<hr>
	 	 <sakai:instruction>
		<fmt:message key="function.allmodulesinstr"/><br/>
		
		 <logic:notEmpty name="bookMenuForm" property="bookList">
		      <sakai:flat_list>
		 		<tr>        <th><fmt:message key="label.tableheadercourse"/></th>
		 		           <th><fmt:message key="function.acceptreject"/></th>
			                <th><fmt:message key="label.tableheaderauthor" /></th>
							<th><fmt:message key="label.tableheaderyear" /></th>
							<th><fmt:message key="label.tableheadertitle" /></th>
							<th><fmt:message key="label.tableheaderedition" /></th>
							<th><fmt:message key="label.tableheaderpublisher" /></th>
							<th><fmt:message key="label.tableheaderISBN" /></th>
			</tr>
			
			   <logic:iterate name="bookMenuForm" property="bookList" id="c" indexId="cindex">				
	          	<tr>	  
	          	   <td><bean:write name="c" property="course" /> </td>        	
	          	<td><logic:notEqual name="c" property="course" value="">
					<html:radio name="bookMenuForm" property='<%= "recordIndexed2["+cindex+"].selectedAuthorize" %>' value="true">
					</html:radio> &nbsp; &nbsp;&nbsp;&nbsp;
										
					<html:radio name="bookMenuForm" property='<%= "recordIndexed2["+cindex+"].selectedAuthorize" %>' value="">
					</html:radio>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
				  </td>	          	
	                     <td><bean:write name="c" property="txtAuthor" /> </td>
					    	<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
							</tr>
		
		</logic:iterate>
		</sakai:flat_list>
		 
		 </logic:notEmpty>
		
		
		</sakai:instruction>
				<html:hidden property="continueOption" value="booklist"/>
			    <html:hidden property="cancelOption" value="TOMAINVIEW"/>
	 	</html:form>
	 	</sakai:html>