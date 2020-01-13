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
  <html:form action="deanAuthorisation">
   <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
 <sakai:instruction>
		<fmt:message key="function.deaninstructiontext"/> <logic:equal name="bookMenuForm" property="typeOfBookList" value="P"> <b><fmt:message key="function.booklist"/>
		<bean:write name="bookMenuForm" property="courseId"/></b></logic:equal>
<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><b> <fmt:message key="function.booklistforR"/><bean:write name="bookMenuForm" property="courseId"/></b></logic:equal>
<br/><p/></sakai:instruction><p/>
<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
            		<logic:present name="bookMenuForm" property="courseNote">
            			<tr>
						<td><fmt:message key="label.updateviewfrmnote" /></td>
						<td><bean:write name="bookMenuForm" property="courseNote" filter="none" /></td>
					</tr>      
					</logic:present>      	
        	</sakai:group_table>    
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
        	</p>
        	</logic:notEqual>
        
		
		
		 <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
     <sakai:instruction>
		<fmt:message key="function.deaninstructiontext"/> <b> 
		<fmt:message key="function.booklistforE"/><bean:write name="bookMenuForm" property="courseId"/></b><br/><p/>
		<fmt:message key="function.deanauthorizeinstr1"/> <br/></sakai:instruction><p/>
<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
            		<logic:present name="bookMenuForm" property="courseNote">
            			<tr>
						<td><fmt:message key="label.updateviewfrmnote" /></td>
						<td><bean:write name="bookMenuForm" property="courseNote" filter="none" /></td>
					</tr>      
					</logic:present>      	
        	</sakai:group_table>    
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
                	<td><html:textarea name='bookMenuForm' property="authComment" rows="2" cols="60"></html:textarea></td>
            	</tr>
    </sakai:group_table>
    
    <html:hidden property="cancelOption" value="TOMAINVIEW"/>
        <sakai:actions>
        <html:submit styleClass="active" property="action"><fmt:message key="button.confirmAuthorize"/></html:submit>
        <html:submit styleClass="active" property="action"><fmt:message key="button.sendback"/></html:submit>
        <html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
     </sakai:actions>
</html:form>
</sakai:html>