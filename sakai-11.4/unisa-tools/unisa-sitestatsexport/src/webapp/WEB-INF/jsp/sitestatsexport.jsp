<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<!-- document.sitestatsexportForm.action = 'sitestatsexport.do?act=mainview'; -->
<fmt:setBundle basename="za.ac.unisa.lms.tools.sitestatsexport.ApplicationResources"/>
<script language="JavaScript">
	function setAction() {
		document.sitestatsexportForm.action = 'sitestatsexport.do?act=mainview';
		document.sitestatsexportForm.submit();
	}
</script>

<sakai:html>
	<html:form action="/sitestatsexport">
 	
    <sakai:tool_bar>	   	
       	<html:link href="sitestatsexport.do?act=view"><input type="image" align=right src="/library/image/printer.png" onClick="window.print()"/></html:link> 
	</sakai:tool_bar>
		     	 
		
	<sakai:messages/>
    <sakai:messages message="true"/>


 
  	  <sakai:heading><fmt:message key="sitestatsexport.heading1"/></sakai:heading>
  	   <sakai:instruction><fmt:message key="sitestatsexport.instruction"/></sakai:instruction>
      <sakai:instruction><fmt:message key="sitestatsexport.instruction1"/></sakai:instruction>
<sakai:group_table>
 <tr>
	 <td><fmt:message key="college.disc"/></td>
			
			<td>
			<html:select property="collegeCode" onchange="setAction()" style="width: 420px"  >
					<html:option value="-1">Please Select...</html:option>	 
        		    <html:options  collection="colleges" property="value" labelProperty="label" />
    		</html:select> 
			</td>					
	    </tr>	   
<tr>
	<td><fmt:message key="schools.disc"/></td>
    <td>
    	<html:select property="school" onchange="setAction()" style="width: 420px">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="schools" property="value" labelProperty="label" />
    	</html:select>         
    </td>

 </tr> 
  <tr> 
 	 <td> <fmt:message key="departments.disc"/></td>
     <td><html:select property="department"  onchange="setAction()" style="width: 350px">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="departments" property="value" labelProperty="label" />
    	</html:select>   
	 	</td>
  </tr> 
       <tr> 
 	 <td> <fmt:message key="course.code"/></td>
     <td><html:select property="courseCode1" style="width: 125px">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="courses" property="value" labelProperty="label" />
    	</html:select>   
	 	</td>
  </tr> 
  <tr> 
 	 <td> <fmt:message key="year"/></td>
     <td><html:select property="year" style="width: 125px">
     <html:options collection="yearsList" property="value" labelProperty="label"/>
           </html:select> 
	 	</td>
  </tr> 
  <tr> 
 	 <td> <fmt:message key="semister"/></td>
 <td> <html:select property="semister" style="width: 125px">
 	                <html:option value="-1">All</html:option>
					<html:option value="1">Semester 1</html:option>
        		    <html:option value="2">Semester 2 </html:option> 
        		    <html:option value="0">Year 1</html:option> 
        		    <html:option value="6">Year 2</html:option>  	
    	</html:select> </td>
    </tr>
    <tr>
		<td><fmt:message key = "sitestat.role"/></td>
		<td> <html:select property="role" style="width: 125px">
		            <html:option value="-1">All</html:option>
					<html:option value="1">PRIML</html:option>
        		    <html:option value="2">SECDL</html:option> 
        		    <html:option value="3">CADMN</html:option> 
        		    <html:option value="4">TUTOR</html:option>  	
    	</html:select> </td>
	</tr>
	<tr>
		<td><fmt:message key = "sitestat.person"/></td>
		<td><html:text property="person"  style="width: 125px"></html:text></td>
	</tr>
	<tr>
		<td><fmt:message key = "sitestat.event"/></td>
		  <td><html:select property="event" style="width: 125px">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="events" property="value" labelProperty="label" />
    	</html:select>   
	 	</td>
	</tr>
	
	

		 
</sakai:group_table>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<tr>
		<td><fmt:message key = "sitestat.month"/></td>&nbsp;
		 <td><html:select property="year1" style="width: 60px">
		 <html:options collection="yearsList1" property="value" labelProperty="label"/>
		 </html:select></td>
	  <td><html:select property="month" style="width: 75px">
	      <html:option value="-1">ALL</html:option>
          <html:options  property="months"/>
    	  </html:select> 
	   </td></tr>
	   <sakai:group_table>
     <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act">
			    <fmt:message key="button.export"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
		 </sakai:group_table>


	         <input type="hidden" name="act" value= "viewbooklist"/>  
	</html:form>
</sakai:html> 