<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mylife.ApplicationResources"/>

<script language="JavaScript">
function setAction() {
		document.getElementById("sbmt").style.visibility='hidden';
		document.getElementById("process").style.display="block";  
	}
</script>
<sakai:html>
	<html:form action="/myUnisaMylife.do" >
	<html:hidden property="atstep" value="stepUnisaAcknowledge"/>

	<sakai:group_heading> <fmt:message key="mylife.headingstep3"/> </sakai:group_heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<br>
	<fmt:message key="mylife.mylifeAccept.info1"/>	
	<table>			
		
		<tr>
			<td><html:checkbox property="agreeCheck1"></html:checkbox></td>
			<td><fmt:message key="mylife.mylifeAccept.info2"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td><html:checkbox property="agreeCheck2"></html:checkbox></td>
			<td><fmt:message key="mylife.mylifeAccept.info3"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td><html:checkbox property="agreeCheck3"></html:checkbox></td>
			<td><fmt:message key="mylife.mylifeAccept.info4"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
			<tr>
			<td><html:checkbox property="agreeCheck4"></html:checkbox></td>
			<td><fmt:message key="mylife.mylifeAccept.info5"/></td>
		</tr>
			<tr><td>&nbsp;</td></tr>
		<%-- 	<tr>
			<td><html:checkbox property="agreeCheck5"></html:checkbox></td>
			<td><fmt:message key="mylife.mylifeAccept.info6"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr> --%>
						
	</table>
	<fmt:message key="mylife.mylifeAccept.info7"/>
	
	   		<sakai:actions>
			<html:submit styleClass="active" property="act" onclick="setAction()" styleId="sbmt"><fmt:message key="button.acknowledge"/></html:submit>
			<div id="process" style="display:none">Processing..</div>
		    <html:submit property="action"> <fmt:message key="button.back"/> </html:submit>
		</sakai:actions>
	
	</html:form>
</sakai:html>