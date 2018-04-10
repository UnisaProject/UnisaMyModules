<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>	  
	<html:form action="/nosite.do">	
	
	  <sakai:tool_bar>	 
       	<html:link href="nosite.do?action=view"><input type="image" align="right" src="/library/image/printer.png" onClick="window.print()"/></html:link> 
	</sakai:tool_bar>
	
	<sakai:messages/>
    <sakai:messages message="true"/>

<fmt:setBundle basename="za.ac.unisa.lms.tools.nosite.ApplicationResources"/>
<tr>
	<td><sakai:heading>
		<bean:write name="nositeForm" property="college"/> 
		<fmt:message key="pagestotal"/>
		<bean:write name="nositeForm" property="year"/>
    	</sakai:heading>
  	</td> 
  	<tr>
  	 <sakai:instruction><fmt:message key="nosite.school.instruction"/></sakai:instruction> 
  	 </tr>
 <sakai:group_table>  
<tr>
	<td>
<fmt:message key="schools.disc"/></td>
<td>
<html:select name="nositeForm" property="school">
					<html:option value="-1">All</html:option>
					<html:options property="schools"/>
    </html:select>
    </td>
 </tr> 
  <tr> 
 	 <td>
 <fmt:message key="departments.disc"/></td>
 <td><html:select name="nositeForm" property="department">
					<html:option value="-1">All</html:option>
					<html:options  property="departments"/>
					
				</html:select>   
	 	</td>
    </tr> 
    
</sakai:group_table>  
     
<sakai:flat_list>
<tr>
<!--	<tr>
<td><sakai:heading><bean:write name="nositeForm" property="college"/> 
    </sakai:heading>
</td>
</tr>	
	 <td colspan="3"><fmt:message key="schools.disc"/>
		<html:select name="nositeForm" property="school">
					<html:option value="-1">All</html:option>
					<html:options property="schools"/>
    </html:select>
	</td>
 </tr>	
	 <tr>
	 <td colspan="3"><fmt:message key="departments.disc"/>
		<html:select name="nositeForm" property="department">
					<html:option value="-1">All</html:option>
					<html:options property="departments"/>
					
				</html:select>
	  </td>
		 </tr>	

		<tr>
		-->
		<td>
			<sakai:actions>        
		   	<html:submit styleClass="button" property="action=view">
			   <fmt:message key="sites.display"/>
			  </html:submit>
		</sakai:actions>
	  </td>
	   <td align="right"> 
		 	<fmt:message key="viewdescr"/>
		    <bean:write name="nositeForm" property="start"/>
			<fmt:message key="pagestotal"/>
			<bean:write name="nositeForm" property="end"/>
			<fmt:message key="pagesof"/>
			<bean:write name="nositeForm" property="numitems"/>
			<fmt:message key="numofitems"/>

		<!--</td>-->
			

		
		 <!--<td width="15">-->
		<sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="verystart"/>
			   </html:submit>
		    <!-- </sakai:actions> -->
 		<!--</td> -->
		 <!--<td> -->
			<!--<sakai:actions>-->
			<html:submit styleClass="button" property="action">
			    <fmt:message key="goback"/>
			   </html:submit>
		<!--     </sakai:actions>-->
		<!-- </td>-->
		 <!--<td>-->

		    <html:select name="nositeForm" property="records" onchange = "submit();">
					<html:option value="20">Show 20 items </html:option>
					<bean:write name="nositeForm" property="records"/>					
					<html:option value="50">Show 50 items </html:option>
					<bean:write name="nositeForm" property="records"/>					
				
				</html:select>
		<!--	 </td> -->
		<!--<td>-->
        		    

		 <!--   <sakai:actions> -->
			<html:submit styleClass="button" property="action">
			    <fmt:message key="gofoward"/>
			   </html:submit>
		<!--     </sakai:actions>-->
		<!--	 </td> -->
		<!--<td>-->
        		    
		 <!--   <sakai:actions>-->
		 	<html:submit styleClass="button" property="action">
			    <fmt:message key="verylast"/>
			   </html:submit>
		     </sakai:actions>
		   
		  
		  
		
		     </td>
		
		</tr>
	 </sakai:flat_list>
	 	 <!--<tr>-->
		  <sakai:flat_list>

		
		<tr>
			<th><fmt:message key="nosite.field.studyunit"/></th>
			<th><fmt:message key="nosite.field.period"/></th>
			<th><fmt:message key="nosite.field.total"/></th>
			 
		</tr>

		
		<logic:notEmpty name="nositeForm" property="someinactives">
		<logic:iterate name="nositeForm" property="someinactives" id="d" indexId="dindex">
			<tr>
			<td>
				<bean:write name="d" property="studyunit"/>
			</td>	
			<td>
				<bean:write name="d" property="period"/>
			</td>	
			<td>
				<bean:write name="d" property="total"/>
			</td>	
									
			
						                 				
			</tr>
			
		</logic:iterate>				
	</logic:notEmpty>		
		
	<logic:empty name="nositeForm" property="someinactives">
		<fmt:message key="nosite.notavailable"/>
	</logic:empty>	
		

	</sakai:flat_list>


	    <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="button.back"/>
			   </html:submit>
		     </sakai:actions>
		<!--
    	   <html:link href="nosite.do?action=back">
			<fmt:message key="button.back"/>
		</html:link>
		-->



	

	       <input type="hidden" name="action" value= "viewnosites"/>
	</html:form>
</sakai:html> 