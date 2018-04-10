<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="org.apache.commons.collections.iterators.ArrayListIterator"%>
<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>
<sakai:html>
<html:form action="/myUnisaHod">
   <table>
        <tr> 
    <td>
            
          <font color="red"><big><fmt:message key="removeStandin.instruction"/></big> </font> 
          
          				<logic:iterate name="hoddisplayform" property="selectedStandin" id="value" indexId="i">
					<bean:write name="value" />
				</logic:iterate><br/> 
				
        <html:radio property="choice" value="Y" ><fmt:message key="removeStandin.yes"/></html:radio> <br/>
	    <html:radio property="choice" value="N" ><fmt:message key="removeStandin.no"/></html:radio>
					
    </td>
    </tr>
      </table>
        <html:submit property="action">
		<fmt:message key="button.deleteStandin"/>
		</html:submit>
		
  
    </html:form>
    </sakai:html>
  
         