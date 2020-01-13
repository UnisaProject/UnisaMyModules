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
	<hr/>
	<sakai:messages/>
	<html:form action="booklistadmin">	
	<logic:notEqual name="booklistAdminForm" property="status" value="No Books prescribed for this course">
	<logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E">
	                 <sakai:tool_bar>
                <html:link href="booklistadmin.do?action=searchBook"><fmt:message key="link.addnew"/></html:link>      
                <html:link href="booklistadmin.do?action=bookReuse"><fmt:message key="link.reusebook"/></html:link>
                <html:link href="booklistadmin.do?action=courseNote"><fmt:message key="link.addcoursenote"/></html:link>
               <html:link href="booklistadmin.do?action=declareNopBooks"><fmt:message key="link.declarenopbooks"/></html:link>         
               </sakai:tool_bar>
               <sakai:instruction>	
		<b><fmt:message key="function.booklistinstr"/>  <bean:write name="booklistAdminForm" property="courseId"/> for 
		 <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/> </b>	<p/>	
		  <fmt:message key="function.displayinstr"/>
			 </sakai:instruction>
		 	<logic:notEmpty name="booklistAdminForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
               		<td><bean:write name="booklistAdminForm" property="lastUpdated.transactionTime"/></td>
           		</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="booklistAdminForm" property="displayAuditTrailName"/></td>
          		</tr>
           		<tr>
           			<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="booklistAdminForm" property="status"/></b></td>
           		</tr>   
       		</sakai:group_table>
  		</logic:notEmpty>
   		<p/>
		 <sakai:group_heading><fmt:message key="label.compilelstmsg"/><br/></sakai:group_heading>
		 		<logic:present name="booklistAdminForm" property="courseNote">
					<sakai:group_table>
						<tr>
							<td><fmt:message key="label.updateviewfrmnote" /></td>
							<td><bean:write name="booklistAdminForm" property="courseNote" filter="none" /></td>
						</tr>
						<tr>
										<td><html:link href="booklistadmin.do?action=courseNote">
										<fmt:message key="function.updateviewfrmedit" />
										</html:link>
									</td>
							</tr>
												</sakai:group_table>
				</logic:present>
		 
		 <sakai:flat_list>
		   <logic:notEmpty name="booklistAdminForm" property="booklist">
			<tr>
			<th><fmt:message key="label.tableheaderauthor"/></th>			
			<th><fmt:message key="label.tableheaderyear"/></th>	
			<th><fmt:message key="label.tableheadertitle"/></th>					
			<th><fmt:message key="label.tableheaderedition"/></th>			
			<th><fmt:message key="label.tableheaderpublisher"/></th>
			<th><fmt:message key="label.tableheaderisbn"/></th>	
			<th><fmt:message key="label.tableheaderremove"/></th>	
			</tr>
			
				<logic:iterate name="booklistAdminForm" property="booklist" id="d" indexId="dindex">				
		    <tr>
		      <td>
				<bean:write name="d" property="txtAuthor"/><br>
				<b><html:link	href="booklistadmin.do?action=addNewBook&continueOption=update&searchOption=edit"
								paramName="d" paramProperty="bookId" paramId="bookId">Edit</html:link></b>
			</td>
			<td>
				<bean:write name="d" property="txtYear"/>
			</td>
		   <td>
				<bean:write name="d" property="txtTitle"/>
			</td>				
			<td>
				<bean:write name="d" property="txtEdition"/>
			</td>
			<td>
				<bean:write name="d" property="txtPublisher"/>
			</td>
			<td>
				<bean:write name="d" property="txtISBN"/>
			</td>
			<td>	
				<html:checkbox name="booklistAdminForm" property='<%="bookIndex["+dindex+"].remove" %>' />
			</td>
			</tr>
			</logic:iterate>			
			</logic:notEmpty>			
		 </sakai:flat_list>
		 </logic:notEqual>		 
		 
		 
		 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">
         <sakai:tool_bar>
                <html:link href="booklistadmin.do?action=searchBook"><fmt:message key="link.addnewitem"/></html:link>      
                <html:link href="booklistadmin.do?action=bookReuse"><fmt:message key="link.reusebookforE"/></html:link>
                <html:link href="booklistadmin.do?action=courseNote"><fmt:message key="link.addcoursenote"/></html:link>
                <html:link href="booklistadmin.do?action=declareNopBooks"><fmt:message key="link.declarenopbooksforE"/></html:link>
        	</sakai:tool_bar>
        	 <sakai:instruction>	
		<b><fmt:message key="function.booklistforE"/>  <bean:write name="booklistAdminForm" property="courseId"/> for 
		 <bean:write name="booklistAdminForm" property="year"/> for <bean:write name="booklistAdminForm" property="college"/></b>	<p/>				 
		 <fmt:message key="function.instructiontext2forE"/>		
		 </sakai:instruction>
		 	<logic:notEmpty name="booklistAdminForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
               		<td><bean:write name="booklistAdminForm" property="lastUpdated.transactionTime"/></td>
           		</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="booklistAdminForm" property="displayAuditTrailName"/></td>
          		</tr>
           		<tr>
           			<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="booklistAdminForm" property="status"/></b></td>
           		</tr>   
       		</sakai:group_table>
  		</logic:notEmpty>
   		<p/>
		 <sakai:group_heading><fmt:message key="label.compilelstmsg"/><br/></sakai:group_heading>
		 		<logic:present name="booklistAdminForm" property="courseNote">
					<sakai:group_table>
						<tr>
							<td><fmt:message key="label.updateviewfrmnote" /></td>
							<td><bean:write name="booklistAdminForm" property="courseNote" filter="none" /></td>
						</tr>
										<tr>
										<td><html:link href="booklistadmin.do?action=courseNote">
										<fmt:message key="function.updateviewfrmedit" />
										</html:link>
									</td>
							</tr>
					</sakai:group_table>
				</logic:present>
		  <sakai:flat_list>
		   <logic:notEmpty name="booklistAdminForm" property="booklist">
			<tr>
			    <th><fmt:message key="label.tableheaderereservetype"/></th>
				<th><fmt:message key="label.tableheaderauthor" /></th>
				<th><fmt:message key="label.tableheaderyear" /></th>
				<th><fmt:message key="label.tableheadertitle" /></th>
				<th><fmt:message key="label.tableheaderebookpublicationJ" /></th>
				<th><fmt:message key="label.tableheaderebookvolume" /></th>
				<th><fmt:message key="label.tableheaderebookpages" /></th>
				<th><fmt:message key="label.tableheaderremove"/></th>	
				</tr>			
				<logic:iterate name="booklistAdminForm" property="booklist" id="d" indexId="dindex">				
		    <tr>
		     <td>
		    <bean:write name="d" property="eReserveType"/><br>
				<b><html:link	href="booklistadmin.do?action=addNewBook&continueOption=update&searchOption=edit"
								paramName="d" paramProperty="bookId" paramId="bookId">Edit</html:link></b>
	        </td>
		   <td>
				<bean:write name="d" property="txtAuthor"/><br>
			</td>
			<td>
				<bean:write name="d" property="txtYear"/>
			</td>
		   <td>
				<bean:write name="d" property="txtTitle"/>
			</td>				
			<td>
				<bean:write name="d" property="txtPublisher"/>
			</td>
			<td><bean:write name="d" property="ebookVolume" /></td>
			<td><bean:write name="d" property="ebook_pages" /></td>
			<td>	
				<html:checkbox name="booklistAdminForm" property='<%="bookIndex["+dindex+"].remove" %>' />
			</td>
			</tr>
			</logic:iterate>			
			</logic:notEmpty>			
		 </sakai:flat_list>
		 </logic:equal>
		 <html:hidden property="cancelOption" value="TOVIEWCOURSE"/>
		 <html:hidden property="continueOption" value="CONFIRMVIEW"/>
		  <logic:equal name="booklistAdminForm" property="fromButton" value="searchCourse">
        	    <html:hidden property="backOption" value="searchCourse"/>
        	</logic:equal>
        	<logic:notEqual name="booklistAdminForm" property="fromButton" value="searchCourse">
        	    <html:hidden property="backOption" value="viewbooklist"/>
        	</logic:notEqual>
		  <sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.publishbooklist"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.remove"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>			
		</sakai:actions>
		 
		 </logic:notEqual>
		 
		 
		 
		 <logic:equal name="booklistAdminForm" property="status" value="No Books prescribed for this course">
		 	<sakai:heading>
    	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="P">	<fmt:message key="function.booklist"/> </logic:equal>
    	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="R">	<fmt:message key="function.booklistforR"/> </logic:equal>
    	<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">	<fmt:message key="function.booklistforE"/> </logic:equal>
    	
    	<bean:write name="booklistAdminForm" property="courseId"/> for <bean:write name="booklistAdminForm" property="year"/></sakai:heading>
    	   	<logic:notEmpty name="booklistAdminForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
               		<td><bean:write name="booklistAdminForm" property="lastUpdated.transactionTime"/></td>
           		</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="booklistAdminForm" property="displayAuditTrailName"/></td>
          		</tr>
           		<tr>
           			<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="booklistAdminForm" property="status"/></b></td>
           		</tr>   
       		</sakai:group_table>
  		</logic:notEmpty>
   		<p/>
        <html:form action="booklistadmin">
			<p/>
        	<html:hidden property="cancelOption" value="TOVIEWCOURSE"/>
        	<logic:equal name="booklistAdminForm" property="fromButton" value="searchCourse">
        	    <html:hidden property="backOption" value="searchCourse"/>
        	</logic:equal>
        	<logic:notEqual name="booklistAdminForm" property="fromButton" value="searchCourse">
        	    <html:hidden property="backOption" value="viewbooklist"/>
        	</logic:notEqual>
        		<sakai:actions>
        			<html:submit styleClass="active" property="action"><fmt:message key="button.unconfirm"/></html:submit>
                	<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		</sakai:actions>
		</html:form>      
		 </logic:equal>
		 </html:form>
		 </sakai:html>
		 
		