<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<!-- document.sitestatsexportForm.action = 'sitestatsexport.do?act=mainview'; -->
<fmt:setBundle basename="za.ac.unisa.lms.tools.brochures.ApplicationResources"/>
<script language="JavaScript">
	function setAction() {
		document.brochuresForm.action = 'brochures.do?act=myRegistration';
		document.brochuresForm.submit();
	}		
</script>

<sakai:html>
	<html:form action="/brochures">
	<html:hidden property="atStep" value="2"/>
	
	<html:hidden property="type" value="${brochuresForm.type1}"/>
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
	 <td><fmt:message key="schools.disc"/></td>
			
			<td>
			<html:select property="schCode" onchange="setAction()"  style="width: 350px; overflow:auto;">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="schools" property="value" labelProperty="label" />
    		</html:select> 
			</td>					
	    </tr>

	      <tr>
	 <td><fmt:message key="dpt.disc"/></td>
			
			<td>
			<html:select property="dptCode" onchange="setAction()"  style="width: 350px; overflow:auto;">
					<html:option value="-1">All</html:option>	 
        		    <html:options  collection="departmentL" property="value" labelProperty="label" />
    		</html:select> 
			</td>					
	    </tr>
    <tr> 
 	 <td>Research Flag</td>
     <td> <html:select property="researchFlag" style="width: 350px">
 	   <html:option value="-1">All</html:option>
       <html:option value="Y">Yes</html:option>
       <html:option value="N">No</html:option>	         	
    </html:select> </td>
    </tr>
    
   <tr> 
         <td>Qualification</td>
     <td><html:select property="qualCode"  onchange="setAction()" style="width: 350px">
       <html:option value="-1">All</html:option>
       <html:options collection="quallist" property="value" labelProperty="label"/>
           </html:select> 
                </td>
  </tr> 
            <tr> 
         <td>Specialization</td>
     <td><html:select property="spec"  onchange="setAction()" style="width: 350px">
         <html:option value="-1">All</html:option>
         <html:options collection="speclist" property="value" labelProperty="label"/>
           </html:select> 
                </td>
  </tr> 
  <tr> 
         <td>Category</td>
 <td> <html:select property="category" style="width: 350px">
            <html:option value="-1">All</html:option>
             <html:options collection="catlist" property="value" labelProperty="label"/>                        
        </html:select> </td>
    </tr>
    <tr> 
         <td>'Include'Specialization Repeat</td>
     <td><html:select property="specRepeat" style="width: 350px">
      <html:option value="-1">Please choose..</html:option>
       <html:option value="1">Yes</html:option>
       <html:option value="2">No</html:option>
           </html:select> 
                </td>
  </tr>
   <tr> 
         <td>Specialization Repeat From Year</td>
     <td><html:select property="repeatYear" style="width: 350px">
       <html:options collection="repeatYearList" property="value" labelProperty="label"/>
           </html:select> 
                </td>
  </tr>
   <tr> 
         <td>HEQF Compliant</td>
     <td><html:select property="heqf" style="width: 350px">
        <html:option value="B">Both</html:option>
          <html:option value="Y">Yes</html:option>
            <html:option value="N">No</html:option>
           </html:select> 
                </td>
  </tr>  
    <tr> 
 	 <td>Output Format</td>
 <td> <html:select property="format" style="width: 350px">
 	    <html:option value="1">Word Format</html:option>	
 	    <html:option value="2">XML Format</html:option>	
 	     <html:option value="3">CCM Output</html:option>		         	
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