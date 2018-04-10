<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.brochures.ApplicationResources"/>
<script language="JavaScript">
	function setAction() {
		document.brochuresForm.action = 'brochures.do?act=myModules';
		document.brochuresForm.submit();
	}		
</script>

<sakai:html>
	<html:form action="/brochures">
	<html:hidden property="atStep" value="3"/>
	
	<html:hidden property="type" value="${brochuresForm.type}"/>
   <sakai:group_heading>Brochure Output <bean:write name="brochuresForm"  property="type"/></sakai:group_heading>
   	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
<sakai:group_table>
<tr> 
 	 <td>Year</td>
     <td><html:select property="year" style="width: 350px">
     <html:options collection="yearsList" property="value" labelProperty="label"/>
           </html:select> 
	 	</td>
  </tr>
  <tr>
	 <td><fmt:message key="college.disc"/></td>
			
			<td>
			<html:select property="collegeCode"   onchange="setAction()" style="width: 350px; overflow:auto;">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="colleges" property="value" labelProperty="label" />
    		</html:select> 
			</td>					
	    </tr> 	   
   <tr> 
         <td>School</td>
     <td><html:select property="schCode"   onchange="setAction()" style="width: 350px">
       <html:option value="-1">All</html:option>
        <html:options collection="schList" property="value" labelProperty="label"/>
           </html:select> 
                </td>
  </tr> 
            <tr> 
         <td>Department</td>
     <td><html:select property="dptCode"  onchange="setAction()" style="width: 350px">
         <html:option value="-1">All</html:option>
         <html:options collection="dptList" property="value" labelProperty="label"/>
           </html:select> 
                </td>
  </tr> 
  <tr> 
         <td>Module Code</td>
 <td> <html:select property="module"  onchange="setAction()" style="width: 350px">
            <html:option value="-1">All</html:option>
             <html:options collection="moduleList" property="value" labelProperty="label"/>                        
        </html:select> </td>
    </tr>
    <tr> 
        <td>Subject Code</td>
 <td> <html:select property="subCode"  onchange="setAction()" style="width: 350px">
            <html:option value="-1">All</html:option>
              <html:options collection="subjectsList" property="value" labelProperty="label"/>             
        </html:select> </td>
    </tr>
     <tr> 
 	 <td>Output Format</td>
 <td> <html:select property="format" style="width: 350px">
 	    <html:option value="1">Word Format</html:option>	
 	    <html:option value="2">XML Format</html:option>		         	
    	</html:select> </td>
    </tr>
</sakai:group_table>
<sakai:group_table>
     <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act">
			    <fmt:message key="button.export"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		     <td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act">
			    <fmt:message key="button.back"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		     <td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act">
			    <fmt:message key="button.audit"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		     <td>
		   <sakai:actions>
			<html:submit styleClass="button" property="act">
			    <fmt:message key="button.clear"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
		 
  </sakai:group_table>
	</html:form>
</sakai:html> 