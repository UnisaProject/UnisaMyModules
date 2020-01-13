<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>	  
	<html:form action="/booklist.do">	
		<sakai:tool_bar>	   	
       		<html:link href="booklist.do?action=view">
       			<input type="image" align=right src="/library/image/printer.png" onClick="window.print()"/>
       		</html:link> 
		</sakai:tool_bar>
		<sakai:messages/>
    		<sakai:messages message="true"/>
    		
    		<fmt:setBundle basename="za.ac.unisa.lms.tools.booklist.ApplicationResources"/>
		<tr>
			<td><sakai:heading><fmt:message key="booksite.heading1"/>
					<bean:write name="booklistForm" property="college"/> 
					<fmt:message key="pagestotal"/>
					<bean:write name="booklistForm" property="year"/>
					<fmt:message key="statustotal"/>
					 <bean:write name="booklistForm" property="booklistStatus"/>
			         <bean:write name="booklistForm" property="status"/>
    			</sakai:heading>
  			</td>
			<tr>
	  			<sakai:instruction>
	  				<p/><fmt:message key="booksite.school.instruction"/>
	  			</sakai:instruction> 
  	 		</tr>
 		</tr> 
		<sakai:group_table>  
			<tr>
				<td><fmt:message key="schools.disc"/></td>
    			<td><html:select property="school">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="schools" property="value" labelProperty="label" />
					</html:select>         
    			</td>
 			</tr> 
  			<tr> 
 	 			<td> <fmt:message key="departments.disc"/></td>
     			<td><html:select property="department">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="departments" property="value" labelProperty="label" />
    				</html:select>   
	 			</td>
  			</tr> 
  			<tr>
   				<td><fmt:message key="prescribe.disc"/></td>
  				<td><html:select name="booklistForm"  property="viewStudyUnits">
					<html:option value="a">All Study Units</html:option>	 
        		    <html:option value="m">Selected Study Units</html:option>	
    				</html:select> 
				</td>
  			</tr>      
   		</sakai:group_table> 
		<tr>
			<td>
				<sakai:actions>        
		   			<html:submit styleClass="button" property="action=view">
			   		<fmt:message key="sites.display"/>
			  		</html:submit>
				</sakai:actions>
     		</td>
     	</tr>
       	 	<logic:notEmpty name="booklistForm" property="modules">
	 
			<sakai:flat_list>
			
			<td><th><fmt:message key="booksite.field.selectcheckboxes"/></th></td>
			
			<sakai:group_table>
			<tr>
			
	  			<logic:iterate name="booklistForm"  id="d"  property="modules" indexId="t">	
	       			<c:set var="mod" value="d"/>
	       			<jsp:useBean id="mod" type="java.lang.String" />
	     			<c:if test="${t%5==0 and t!=0 }" >	
	   			  <p></p>
			</tr>
			<tr>		  			
				</c:if>    	      
	      	  	<td colspan="3">
			    	<html:multibox  property="selectedModules">
			    		<bean:write name="d"/>
			    	</html:multibox>
				    <bean:write name="d"/>	              
			  	</td>
   
			</logic:iterate>
			</tr>		 
			</sakai:group_table>
	</sakai:flat_list>
	 
	</logic:notEmpty>		
		
	<logic:empty name="booklistForm" property="prescribedBooks">
		<p/>
		<fmt:message key="booksite.notavailable"/>
		<p/>
	</logic:empty>
	  <td>
		<sakai:actions>        
		   	<html:submit styleClass="button" property="action">
			   <fmt:message key="booklist.continue"/>
			  </html:submit>
			  
				   	<html:submit styleClass="button" property="action">
			   <fmt:message key="button.back"/>
			  </html:submit>	  
		</sakai:actions>
       </td>	
        <input type="hidden" name="action" value= "viewbooklist"/>
  </html:form>
</sakai:html> 