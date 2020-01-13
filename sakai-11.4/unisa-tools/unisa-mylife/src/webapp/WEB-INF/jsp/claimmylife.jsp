<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mylife.ApplicationResources"/>

<sakai:html>


<html:form action="/myUnisaMylife.do">
	

	<script language="javascript">
		function windowOpen(){
			var url="<bean:write name="myUnisaMylifeForm" property="mylifePath"/>";
			window.open(url,'mywindow');
			}
		function windowOpen2(){
			var url="<bean:write name="myUnisaMylifeForm" property="myUnisaPath"/>";
			window.open(url,'mywindow');
			}
	</script>
	<sakai:group_heading> <fmt:message key="mylife.headingstep4"/> </sakai:group_heading><br><br>
	
		<fmt:message key="mylife.pers.stno"/>
		<b><bean:write name="myUnisaMylifeForm" property="studentNr"/></b> <br> <br>
		<fmt:message key="mylife.claim.info1"/><br>
				
		<fmt:message key="mylife.claim.info2"/>		
		 <b><bean:write name="myUnisaMylifeForm" property="email"/></b><br> <br>
		
		<fmt:message key="mylife.claim.info3"/>	 <b><bean:write name="myUnisaMylifeForm" property="cellNr"/></b>
		<br><br><br>		
			
			
			<table border=1 bgcolor="LightGoldenRodYellow">
			<tr><td>				
				<fmt:message key="mylife.claim.info4"/>	<br><br>				
				<fmt:message key="mylife.claim.info5"/>	<b> <html:text property="myLifePwd" readonly="true" size="20"/> </b> <br>
		        <fmt:message key="mylife.claim.info6"/>	<br><br>
		        <fmt:message key="mylife.claim.info7"/>	<br>
				</td></tr>
				</table>
				<br><br>		
		<fmt:message key="mylife.claim.info10"/> <br> <br>
		<fmt:message key="mylife.claim.info11"/><a href="javascript:windowOpen()"> <b><bean:write name="myUnisaMylifeForm" property="mylifePath" filter="false"/></b> </a> <br>
		  <fmt:message key="mylife.claim.info12"/> <a href="javascript:windowOpen2()"> <b><bean:write name="myUnisaMylifeForm" property="myUnisaPath" filter="false"/></b> </a> <br><br>
	
</html:form>

</sakai:html>