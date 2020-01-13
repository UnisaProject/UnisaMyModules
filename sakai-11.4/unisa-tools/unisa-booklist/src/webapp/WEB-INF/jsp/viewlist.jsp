<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.booklist.ApplicationResources"/>

<sakai:html>	

	 <sakai:tool_bar>	   	
       	<html:link href="booklist.do?action=view"><input type="image" align=right src="/library/image/printer.png" onClick="window.print()"/></html:link> 
	</sakai:tool_bar>  
	<html:form action="/booklist.do">	
	<sakai:messages/>

		     	 

	<sakai:heading><fmt:message key="booksite.heading"/>
	                <fmt:message key="date.space"/>
	                 <bean:write name="booklistForm" property="today"/>   
	</sakai:heading>
	 <sakai:instruction><fmt:message key="booksite.college.instruction"/></sakai:instruction>
	  <sakai:instruction><fmt:message key="booksite.school.instruction1"/></sakai:instruction>
	 <sakai:group_table>
	 <tr>
	 <td><fmt:message key="colleges.disc"/></td>
			
			<td>
			<html:select property="collegeCode">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="colleges" property="value" labelProperty="label" />
    		</html:select> 
			</td>					
	    </tr>	
		<tr>
			<td><fmt:message key="booksite.field.year"/> </td>
			<td>
			<html:select property="year">
			<html:option value="${booklistForm.lastYear}">
				<bean:write name="booklistForm" property="lastYear"/>
			</html:option>
			<html:option value="${booklistForm.currentYear}">
				<bean:write name="booklistForm" property="currentYear"/>
			</html:option>
			<html:option value="${booklistForm.nextYear}">
				<bean:write name="booklistForm" property="nextYear"/>
			</html:option>
			</html:select></td>
			
		</tr>
			 <tr>
	 <td><fmt:message key="booklist.type"/></td>
	 <td>
		<html:select property="booklistTypes">
					<html:option value="a">Please select...</html:option>
        		    <html:option value="P">Prescribed </html:option> 
        		    <html:option value="R">Recommended</html:option> 
        		    <html:option value="E">Ereserves</html:option> 
        		   
        		    	
    	</html:select> 			
			</td>
	 
	   </tr>	
		<tr>
	 <td><fmt:message key="statusoptions.disc"/></td>
	 <td>
		<html:select property="statusOptions">
		             <html:option value="-1">Please select...</html:option>  	
					 <html:option value="5">Book list authorized by Dean</html:option>        
        		     <html:option value="4">Booklist authorized by School Director</html:option>     
        		     <html:option value="2">Booklist authorized by COD</html:option>     
        		     <html:option value="1">Booklist submitted but not authorized</html:option>     
        		     <html:option value="0">Booklist started but not submitted</html:option>         
        		     <html:option value="3">No Books declared</html:option>  		
        		     <html:option value="7">Booklist open for editing by administrator</html:option>  	
        		     <html:option value="6">Book list published by administrator</html:option>   
        		    	
    	</html:select> 			
			</td>
	 
	   </tr>
	
		  <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="button.display"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
		 
	 	  <sakai:flat_list>
	 	  <tr><td>
	 	   <fmt:message key="header.status"/><b><bean:write name="booklistForm" property="year"/></b><fmt:message key="header.status1"/>  
	 	   </td></tr><tr><td></td></tr>
	 	   	<th><fmt:message key="booksite.field.college"/></th>
	 	   	<th><fmt:message key="booksite.field.openbooklist"/></th>
	 	   	<th><fmt:message key="booksite.field.confirmedbooklist"/></th>
	 	   	<th><fmt:message key="booksite.field.authorizedbooklist"/></th>
            <th><fmt:message key="booksite.field.nobooks"/></th>	 	  
			
		<logic:notEmpty name="booklistForm" property="collegeinfo">
		<logic:iterate name="booklistForm" property="collegeinfo" id="d" indexId="dindex">
			<tr>
			<td>
				<bean:write name="d" property="college"/>
			</td>	
			<td>
				<bean:write name="d" property="booklistOpen"/>
			</td>	
			<td>
				<bean:write name="d" property="booklistSubmitted"/>
			</td>	
			<td>
				<bean:write name="d" property="booklistAuthorized"/>
			</td>
			<td>
				<bean:write name="d" property="nobooklist"/>
			</td>							
	        </tr>
			

			
		</logic:iterate>				
	</logic:notEmpty>
	</sakai:flat_list>
	</sakai:group_table>
	<logic:empty name="booklistForm" property="collegeinfo">
		<fmt:message key="booksite.notavailable"/>
	</logic:empty>	 
	

    <input type="hidden" name="action" value="view"/>
	</html:form>
</sakai:html>


