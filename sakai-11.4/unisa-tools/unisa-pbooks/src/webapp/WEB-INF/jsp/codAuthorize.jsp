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
    	<sakai:messages message="true"/>
     <html:form action="authorisation">
    <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
     <logic:notEqual name="bookMenuForm" property="schDirector" value="SD">
    <sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklistinstruction"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistinstructionforR"/></logic:equal>
       <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
       </logic:notEqual>
       <logic:equal name="bookMenuForm" property="schDirector" value="SD">
        <sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.sddbooklistinstruction"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.sdbooklistinstructionforR"/></logic:equal>
       <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
       </logic:equal>
    	
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
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
        	</logic:notEmpty>
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

  </p></logic:notEqual>
  
  
  
  
   <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
     <sakai:heading>
          <logic:notEqual name="bookMenuForm" property="schDirector" value="SD"> <fmt:message key="function.booklistinstructionforE"/>  </logic:notEqual>
    <logic:equal name="bookMenuForm" property="schDirector" value="SD"><fmt:message key="function.sdbooklistinstructionforE"/> </logic:equal>
    <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/>
   
    
     </sakai:heading>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
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
        	</logic:notEmpty>
        	<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>					
						<th><fmt:message key="label.tableheaderpublisher" /></th>	
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
        
    
    
    
    
     <sakai:group_table>    
        <tr>
            <td><fmt:message key="function.college"/> </td> <td><bean:write name="bookMenuForm" property="college"/></td>
   		</tr>
   			<tr>
    		<td><fmt:message key="function.department"/> </td><td><bean:write name="bookMenuForm" property="department"/></td>
        </tr></br>
          <tr>
            <td><fmt:message key="function.instuctionselect"/></td>
   		</tr>
   		
   		
   		 <logic:equal name="bookMenuForm" property="schDirector" value="COD">
   		 <tr>
            <td><fmt:message key="function.schooldirector"/></td></tr>
            
      		<tr><td><html:radio name="bookMenuForm" property = "selectedPerson" value="SchDirector"/>
      		<bean:write name="bookMenuForm" property="schDirSurname"/></td>
   		</tr>
   	
   		     <tr><td><fmt:message key="function.departmentdeputydean2"/></td></tr>
   		
              <logic:iterate  name="bookMenuForm" property="standInSchools" id="c" indexId="cindex">
   			<tr>
			<td><html:radio name="c" property = "selectedPerson" value='${c.standInNovellcode}'/><bean:write name="c" property="standInName"/>&nbsp;
				<bean:write name="c" property="standInSurname"/></td>
				</tr>
   		</logic:iterate>
              
              </logic:equal>
            
             <logic:equal name="bookMenuForm" property="schDirector" value="SD">
   		 <tr>
            <td><fmt:message key="function.departmentdean"/></td></tr>            
      		<tr><td><html:radio name="bookMenuForm" property = "selectedPerson" value="Dean"/>
      		<bean:write name="bookMenuForm" property="deanSurname"/></td>
   		</tr>
   		  <tr><td><fmt:message key="function.departmentdeputydean"/></td></tr> 		
   		       		  
              <logic:iterate  name="bookMenuForm" property="deputyDeanList" id="c" indexId="cindex">
   			<tr>
			<td><html:radio name="c" property = "selectedPerson" value='${c.standInNovellcode}'/><bean:write name="c" property="standInName"/>&nbsp;
				<bean:write name="c" property="standInSurname"/></td>
				</tr>
   		</logic:iterate>
              
            </logic:equal>
   		</sakai:group_table>
   		
     
    <html:hidden property="cancelOption" value="TOMAINVIEW"/>
        <sakai:actions>
        <html:submit styleClass="active" property="action"><fmt:message key="button.authorize"/></html:submit>
        <html:submit styleClass="active" property="action"><fmt:message key="button.sendback"/></html:submit>
        <html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
     </sakai:actions>
  </html:form>
  </sakai:html>      	