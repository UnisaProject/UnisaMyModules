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
	  <sakai:tool_bar>
                <html:link href="booklistadmin.do?action=searchBooks&searchOption=publisher"><fmt:message key="link.searchbook"/></html:link>    
               &nbsp;&nbsp;&nbsp;&nbsp;<html:link href="booklistadmin.do?action=searchBooks&searchOption=searchCourse"><fmt:message key="link.searchCourse"/></html:link>   
                &nbsp;&nbsp;&nbsp;&nbsp;<html:link href="booklistadmin.do?action=selectYearforList"><fmt:message key="link.exportbooklist"/></html:link>
                &nbsp;&nbsp;&nbsp;&nbsp;<html:link href="booklistadmin.do?action=manageDates"><fmt:message key="link.managesubmissiondates"/></html:link>  
                &nbsp;&nbsp;&nbsp;&nbsp;<html:link href="booklistadmin.do?action=manageReleaseDates"><fmt:message key="link.managereleasedates"/></html:link>
                &nbsp;&nbsp;&nbsp;&nbsp;</sakai:tool_bar>
	<sakai:messages/>
	<html:form action="booklistadmin">	
	<sakai:instruction>
		<fmt:message key="function.instructiontext"/>
		</sakai:instruction> <p/>
		
		<sakai:group_table>
	 <tr><td><fmt:message key="function.collegeinstr"/></td>
	 <td><html:select property="colleg">
			<html:option value="">Select college to view</html:option>	 
        	<html:options  collection="colleges" property="value" labelProperty="label"/></html:select></td></tr>
        	
        	<tr><td><fmt:message key="function.yearinstr"/> </td>
			<td><html:select property="year">
			<html:option value="${booklistAdminForm.lastYear}">
				<bean:write name="booklistAdminForm" property="lastYear"/>
			</html:option><html:option value="${booklistAdminForm.currentYear}">
				<bean:write name="booklistAdminForm" property="currentYear"/>
			</html:option><html:option value="${booklistAdminForm.nextYear}">
				<bean:write name="booklistAdminForm" property="nextYear"/>
			</html:option></html:select></td></tr>
			
			<tr><td><fmt:message key="function.statusoption"/></td>
	        <td><html:select property="statusOption">
					<html:option value="">Select view</html:option>        		    
        		    <html:option value="5">Book list authorized by Dean</html:option>        
        		    <html:option value="4">Booklist authorized by School Director</html:option>     
        		    <html:option value="2">Booklist authorized by COD</html:option>     
        		    <html:option value="1">Booklist submitted but not authorized</html:option>     
        		    <html:option value="0">Booklist started but not submitted</html:option>         
        		     <html:option value="-1">Book list with no activity</html:option>  	
        		      <html:option value="3">No books prescribed</html:option> 	
        		     <html:option value="7">Booklist open for editing by administrator</html:option>  	
        		     <html:option value="6">Book list published by administrator</html:option>  	     	
    	        </html:select></td></tr>
		 
		  <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="button.display"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
			
        	</sakai:group_table>
        	
        			<p/>
		
		<html:hidden property="backOption" value="adminmenu"/>
		<html:hidden property="fromButton" value="adminmenu"/>
		</p></html:form>
		</sakai:html>
		
