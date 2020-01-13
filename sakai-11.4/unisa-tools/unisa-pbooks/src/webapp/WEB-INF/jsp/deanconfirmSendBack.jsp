<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

    <sakai:html>
 
     <html:form action="deanAuthorisation">
      <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
    <sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.deanbooklistinstruction"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.deanbooklistinstructionforR"/></logic:equal>
       <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
   
    	<sakai:messages/>
    	<sakai:messages message="true"/>
    	<p/>
     <logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderedition" /></th>
						<th><fmt:message key="label.tableheaderpublisher" /></th>
						<th><fmt:message key="label.tableheaderISBN" /></th>
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>   	
     
     </logic:notEqual>
     
     
      <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
     <sakai:heading>
          <fmt:message key="function.deanbooklistinstructionforE"/>
    <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
      <sakai:messages/>
    	<sakai:messages message="true"/>
    	<p/>
     <logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>					
						<th><fmt:message key="label.tableheaderebookpublicationJ" /></th>	
						<th><fmt:message key="label.tableheaderebookvolume" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>						
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>							
							<td><bean:write name="c" property="txtPublisher" /></td>	
							<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>						
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>    	
    </logic:equal>
     
     <sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.athorizationcomment"/></td>
                	<td><bean:write name='bookMenuForm' property="authComment" /></td>
            	</tr>
    </sakai:group_table>
    	<html:hidden property="cancelOption" value="requestedList"/>
    <sakai:actions>
        <html:submit styleClass="active" property="action"><fmt:message key="button.unAuthorize"/></html:submit>
        <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
    </sakai:actions>
     </html:form>
     </sakai:html>
    
    
  