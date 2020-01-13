<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
<html:form action="/additions" >
	<html:hidden property="goto" value="7"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
		
		<sakai:group_heading><fmt:message key="page.selfhelp.success"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td><strong><font color="red"><bean:write name="successMsg"/>&nbsp;</font></strong></td>
		</tr>
		</sakai:group_table>
		
	    <sakai:group_heading><fmt:message key="page.payment"/></sakai:group_heading>
		<sakai:group_table>
	   <tr>
            <td></br><strong>Your registration of additional study units will only be finalised once the above minimum fees has been paid.</strong></td>
		</tr><tr>
			<td>For online credit card payments, click the credit card payment button below.</td>
		</tr><tr>
			<td>For offline payments a form must be downloaded, printed,
						signed and either faxed or mailed to Unisa.</td>
       </tr><tr>
			<td>For general information regarding payment, <a HREF='http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17190' onClick="window.open('http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17190', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">click here.</a></td>
		</tr>

	</sakai:group_table>

	<sakai:actions>
		    <html:submit property="action"><fmt:message key="button.creditcard"/></html:submit>
			<html:submit property="action"><fmt:message key="button.otherpayment"/></html:submit>
			<html:submit property="action"><fmt:message key="button.regdetails"/></html:submit>
		</sakai:actions>
</html:form>

</sakai:html>