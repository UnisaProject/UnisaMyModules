<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>

<script type="text/JavaScript" language="JavaScript">
		<!--
			function textCounter(field, maxlimit) {
				if (field.value.length > maxlimit)
					field.value = field.value.substring(0, maxlimit);
			}
-->
		</script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/jquery.blockUI.js" />"></script> 
<script language="javascript">
	function doAction() {
		document.smsBatchForm.action = 'smsFile.do?act=send';  
		$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /><br/>Processing SMS Request... Do not close your browser</b>" });
		document.smsBatchForm.submit();
	}
</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<sakai:html>
  <body>
  <br/>
  <h1><fmt:message key="page.file.heading"/></h1>
  <html:form action="/smsFile">
  	<html:hidden property="page" value="fileStep2"/>
	<sakai:messages/>
	<sakai:messages message="true"/>
  	<table>
  		<tr>
  			<td><i><fmt:message key="page.send.instruction"/></i></td></tr>
    	<tr>
    </table>
  	<table>
  		<tr>
			<td valign="top"><fmt:message key="page.control.cell"/>&nbsp;</td>
			<td>				
				<logic:iterate name="smsBatchForm" property="controlCellNumberList" id="record">
					<logic:notEmpty name="record">
						<bean:write name="record"/><br/>
					</logic:notEmpty>
				</logic:iterate>
			</td>			
		</tr>
		<tr>
			<td><fmt:message key="page.request.reason"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="reasonGc27"/></td>
		</tr>
		<tr>
			<td><fmt:message key="page.message"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="message"/></td>
		</tr>
		<tr>
    		<td colspan="2">&nbsp;</td>
    	</tr>	
		<tr>
    		<td colspan="2"><strong><fmt:message key="page.fileUpload"/>&nbsp;</strong></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.fileUpload.name"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="fileName"/></td>
    	</tr>	
    	<tr>
    		<td><fmt:message key="page.fileUpload.content"/>&nbsp;</td>
    		<logic:equal name="smsBatchForm" property="fileContentType" value="STUDNUM">
				<td><fmt:message key="page.fileUpload.content.studentNumbers"/></td>
			</logic:equal>
			<logic:equal name="smsBatchForm" property="fileContentType" value="CELLNUM">
				<td><fmt:message key="page.fileUpload.content.cellPhoneNumbers"/></td>
			</logic:equal>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.fileUpload.size"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="fileSize"/></td>
    	</tr>	
    	<tr>
    		<td><fmt:message key="page.fileUpload.records"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="recordNr"/></td>
    	</tr>	
    	<tr>
    		<td><fmt:message key="page.fileUpload.invalid.records"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="invalidRecordNr"/></td>
    	</tr>		
    	<tr>
    		<td colspan="2">&nbsp;</td>
    	</tr>
    	<tr>
    		<td colspan="2"><strong><fmt:message key="page.request"/>&nbsp;</strong></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.messagesnr"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="messageCount"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.rccode"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="rcCode"/>&nbsp;:&nbsp;<bean:write name="smsBatchForm" property="rcDescription"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.smscost"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="costPerSms" format="0.000"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.totalbudget"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="budgetAmount" format="0.00"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.availablebudget"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="availableBudgetAmount" format="0.00"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.totalcost"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="totalCost"/></td>
    	</tr>
  	</table>
  	<br/>
  	<logic:greaterThan name="smsBatchForm" property="invalidRecordNr" value="0">
  		<table><tr><td style="color:red">
	  		<fmt:message key="note.invalidRedords1"/>
	  		<bean:write name="smsBatchForm" property="invalidRecordNr"/>
	  		<fmt:message key="note.invalidRedords2"/>
	  		</td></tr>
  		</table>
  		<br/>  		
  	</logic:greaterThan>
  	<html:button property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.send"/>
			</html:button>
  	<html:submit property="act"><fmt:message key="button.viewErrors"/></html:submit>
  	<html:submit property="act"><fmt:message key="button.back"/></html:submit>
	<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
  </html:form>
  </body>
</sakai:html>