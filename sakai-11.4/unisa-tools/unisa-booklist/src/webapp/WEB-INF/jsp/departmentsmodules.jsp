<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>	  
	<html:form action="/booklist.do">	
	<sakai:messages/>
	    <sakai:messages message="true"/>
<sakai:tool_bar>	   	
       			<html:link href="booklist.do?action=view"><input type="image" align=right src="/library/image/printer.png" onClick="window.print()"/></html:link> 
			</sakai:tool_bar>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklist.ApplicationResources"/>

	<sakai:group_table>  

  	<tr> 
		<td><sakai:heading>
			<fmt:message key="booksite.heading1"/>
			<bean:write name="booklistForm" property="college"/> 
			<fmt:message key="pagestotal"/>
			<bean:write name="booklistForm" property="year"/>
	    	</sakai:heading>
  		</td>   
 	</tr>
 	
 	<tr>
 		<td><sakai:instruction><fmt:message key="booksite.school.instruction"/></sakai:instruction></td>
 	</tr>
</sakai:group_table>

<sakai:group_table>  

<tr>
	<td><fmt:message key="schools.disc"/></td>	
    <td>
    	<html:select property="school" onchange="submit()">
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
  	<td>
		<html:select name="booklistForm"  property="viewStudyUnits">
					<html:option value="a">All Study Units</html:option>						 
        		    <html:option value="m">Selected Study Units</html:option>	
    	</html:select> 
	</td>
  </tr>      
    
</sakai:group_table> 
<sakai:flat_list>
<tr>
	<td>
		<sakai:actions>        
		   	<html:submit styleClass="button" property="action=view">
			   <fmt:message key="sites.display"/>
			  </html:submit>
		</sakai:actions>
       </td>
  
   	<td align="right"> 
		 	<fmt:message key="viewdescr"/>
		    <bean:write name="booklistForm" property="start"/>
			<fmt:message key="pagestotal"/>
			<bean:write name="booklistForm" property="end"/>
			<fmt:message key="pagesof"/>
			<bean:write name="booklistForm" property="numitems"/>
			<fmt:message key="numofitems"/>
   	
		<sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="verystart"/>
			   </html:submit>
			
			<html:submit styleClass="button" property="action">
			    <fmt:message key="goback"/>
			   </html:submit>	
			      
		    
		    <html:select name="booklistForm" property="records" onchange = "submit();">
					<html:option value="20">Show 20 items </html:option>
					<bean:write name="booklistForm" property="records"/>					
					<html:option value="50">Show 50 items </html:option>
					<bean:write name="booklistForm" property="records"/>	
					<html:option value="100">Show 100 items </html:option>
					<bean:write name="booklistForm" property="records"/>		
				</html:select>
				
			<html:submit styleClass="button" property="action">
			    <fmt:message key="gofoward"/>
			   </html:submit>

		 	<html:submit styleClass="button" property="action">
			    <fmt:message key="verylast"/>
			   </html:submit>
		     </sakai:actions>		   
	</td>   
   </tr>  
	 </sakai:flat_list> 
	 
	 
		  <sakai:flat_list>
		

<logic:notEqual name="booklistForm" property="booklistStatus" value="Ereserves">

		<logic:notEmpty name="booklistForm" property="someprescribedBooks">
			<tr>
			<th><fmt:message key="booksite.field.studyunit"/></th>
			<th><fmt:message key="booksite.field.title"/></th>
			<th><fmt:message key="booksite.field.author"/></th>
			<th><fmt:message key="booksite.field.ed"/></th>
			<th><fmt:message key="booksite.field.publisher"/></th>
			<th><fmt:message key="booksite.field.publ"/></th>
			<th><fmt:message key="booksite.field.isbn"/></th>
			 
			</tr>
		<logic:iterate name="booklistForm" property="someprescribedBooks" id="d" indexId="dindex">				
		<tr>
			<td>
				<bean:write name="d" property="studyUnit"/>
			</td>	
			<td>
				<bean:write name="d" property="title"/>
			</td>	
			<td>
				<bean:write name="d" property="author"/>
			</td>
			<td>
				<bean:write name="d" property="edition"/>
			</td>
			<td>
				<bean:write name="d" property="publication"/>
			</td>	
			<td>
				<bean:write name="d" property="publYear"/>
			</td>										
			<td>
				<bean:write name="d" property="isbn"/>
			</td>						                 				
		</tr>		
		</logic:iterate>
						
	</logic:notEmpty>		
		
	<logic:empty name="booklistForm" property="someprescribedBooks">
				<fmt:message key="booksite.notavailable"/>
	</logic:empty>	
		</logic:notEqual>
	<logic:equal name="booklistForm" property="booklistStatus" value="Ereserves">
	<logic:notEmpty name="booklistForm" property="someprescribedBooks">
			<tr>
			<th><fmt:message key="booksite.field.studyunit"/></th>
			<th><fmt:message key="booksite.field.title"/></th>
			<th><fmt:message key="booksite.field.author"/></th>
			<th><fmt:message key="booksite.field.publisher1"/></th>
			<th><fmt:message key="booksite.field.vol"/></th>
			<th><fmt:message key="booksite.field.pages"/></th>
			 
			</tr>
		<logic:iterate name="booklistForm" property="someprescribedBooks" id="d" indexId="dindex">				
		<tr>
			<td>
				<bean:write name="d" property="studyUnit"/>
			</td>	
			<td>
				<bean:write name="d" property="title"/>
			</td>	
			<td>
				<bean:write name="d" property="author"/>
			</td>
			<td>
				<bean:write name="d" property="publication"/>
			</td>	
			<td>
				<bean:write name="d" property="ebookvolume"/>
			</td>										
			<td>
				<bean:write name="d" property="ebookpages"/>
			</td>						                 				
		</tr>		
		</logic:iterate>
						
	</logic:notEmpty>		
		
	<logic:empty name="booklistForm" property="someprescribedBooks">
				<fmt:message key="booksite.notavailable"/>
	</logic:empty>	
	</logic:equal>
	</sakai:flat_list>
	    <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="button.back"/>
			   </html:submit>
			  
		     </sakai:actions>
	        <input type="hidden" name="action" value= "viewselectedmodules"/>
	</html:form>
</sakai:html> 