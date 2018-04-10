<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>

<head>

	<script type="text/javascript" src="/unisa-studentregistration/js/jquery.js"></script>
    <script language="JavaScript" type="text/javascript">  
    
	$(document).ready(function() {
	    
		//Check Courier Address   
		$("input[value='Continue']").click(function(){
			var courierLine1 = $('[name="courierLine1"]').val();
			if (courierLine1 == 0 || courierLine1 == null){
				alert("The courier address is compulsory. \nPlease ensure that someone is available at the address to receive your study material.\n\nPlease fill in a Courier address and then click <Continue>");
				return false;
			}
        }); 
	});
  
     </script>  
     
</head> 

<!-- Form -->
	<html:form action="/addressDetails">		
		<!-- first part of page 2 -->
		<html:hidden property="goto" value="courier"/>
		
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="page.heading.address"/></sakai:heading>	
		<sakai:instruction>
			<fmt:message key="page.address.instruction10"/><br/>
			<fmt:message key="page.address.instruction6"/><br/>
			<fmt:message key="page.required.instruction"/>&nbsp;<sakai:required/>
		</sakai:instruction>
		
		<sakai:group_heading><fmt:message key="page.subheading.courier"/></sakai:group_heading>
		<font color="red"><fmt:message key="page.subheading.courier.note"/>&nbsp;</font>
		
		<sakai:group_table>
		<tr>
			<td><fmt:message key="page.address.adv.courier"/>&nbsp;<sakai:required/></td>
			<td><html:text name="bioDetailsForm" property="courierLine1" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="courierLine2" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="courierLine3" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="courierLine4" size="30" maxlength="28"/></td>
		</tr>
	<!-- <tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="courierLine5" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="courierLine6" size="30" maxlength="28"/></td>
		</tr>-->
		<tr>
			<td><fmt:message key="page.address.suburb"/>&nbsp;<sakai:required/></td>
			<td colspan="2"><html:text name="bioDetailsForm" property="courierSuburb" size="30" maxlength="28" readonly="true"/></td>
		</tr>
		<logic:notEmpty name="bioDetailsForm" property="courierTown">
			<logic:notEqual name="bioDetailsForm" property="courierTown" value="">	
		<tr>
			<td><fmt:message key="page.address.town"/>&nbsp;<sakai:required/></td>
			<td colspan="2"><html:text name="bioDetailsForm" property="courierTown" size="30" maxlength="28" readonly="true"/></td>
		</tr>
			</logic:notEqual>
		</logic:notEmpty>
		<tr>
			<td><fmt:message key="page.postalCode"/>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="courierPostalCode" maxlength="4" size="6" readonly="true"/>&nbsp;
			<html:submit property="action"><fmt:message key="button.searchPostSub"/></html:submit></td>
			<td align="left">&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="page.contact"/>&nbsp;<sakai:required/></td>
			<td><html:text name="bioDetailsForm" property="contactNr" size="40" maxlength="28"/></td>
		</tr>
	</sakai:group_table>	
	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		<html:submit property="action"><fmt:message key="button.advisor"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
	
	</html:form>
	
</sakai:html>