<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>
<sakai:html>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/jquery.blockUI.js" />"></script> 
<script language="javascript">
	function doAction() {
		document.smsBatchForm.action = 'smsbatch.do?act=send';
		$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /><br/>Processing SMS Request... Do not close your browser</b>" });
		document.smsBatchForm.submit();
	}
</script>

  <sakai:heading><fmt:message key="page.heading"/></sakai:heading>
  <html:form action="/smsbatch">
  	<html:hidden property="page" value="3"/>
  	
  	<table>
  		<tr>
  			<td><i><fmt:message key="page.display.instruction"/></i></td></tr>
    	<tr>
    </table><br/>
  	<table>
  		<tr>
			<td><fmt:message key="page.academic.year"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="academicYear"/></td>
		</tr>
		<logic:equal name="smsBatchForm" property="regCriteriaType" value="M">
		<tr>
    		<td><fmt:message key="page.registration.period"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="registrationPeriod"/>
			</td>
    	</tr>
    	</logic:equal>
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
		<!-- reg criteria -->
		<tr>
    		<td colspan="2"><strong><fmt:message key="page.registration.criteria"/>&nbsp;</strong></td>
    	</tr>
		<logic:equal name="smsBatchForm" property="regCriteriaType" value="A">
		<tr>
    		<td colspan="2"><fmt:message key="page.all"/></td>
    	</tr>
    	</logic:equal>
    	<logic:notEqual name="smsBatchForm" property="regCriteriaType" value="A">
    		<logic:iterate name="smsBatchForm" property="regList" id="record" indexId="i">
    			<logic:equal name="i" value="0">
    			<tr>    			
    				<logic:equal name="smsBatchForm" property="regCriteriaType" value="M">
    					<td><fmt:message key="page.module.code"/>&nbsp;</td>
					</logic:equal>
					<logic:equal name="smsBatchForm" property="regCriteriaType" value="Q">
    					<td><fmt:message key="page.qualification.code"/>&nbsp;</td>
    				</logic:equal> 
    				<td><bean:write name="record" property="label"/>&nbsp;</td>
    			</tr>
    			</logic:equal>
				<logic:notEqual name="i" value="0">
				<tr>
					<td>&nbsp;</td>
    				<td><bean:write name="record" property="label"/>&nbsp;</td>
    			</tr>
				</logic:notEqual>
			</logic:iterate>
    	</logic:notEqual>
		<!-- geo criteria -->
    	<tr>
    		<td colspan="2"><strong><fmt:message key="page.geo.criteria"/>&nbsp;</strong></td>
    	</tr>
    	<logic:equal name="smsBatchForm" property="geoCriteriaType" value="A">
    	<tr>
    		<td colspan="2"><fmt:message key="page.all"/></td>
    	</tr>
    	</logic:equal>
    	<logic:equal name="smsBatchForm" property="geoCriteriaType" value="S">
    	<tr>
    		<td colspan="2"><fmt:message key="page.all.rsa"/></td>
    	</tr>
    	</logic:equal>     	
    	<logic:notEqual name="smsBatchForm" property="geoCriteriaType" value="A">
    	<logic:notEqual name="smsBatchForm" property="geoCriteriaType" value="S">
    		<logic:iterate name="smsBatchForm" property="geoSelection" id="record" indexId="i">
    			<logic:equal name="i" value="0">
    			<tr>
    				<logic:equal name="smsBatchForm" property="geoCriteriaType" value="C">
    					<td><fmt:message key="page.countries"/></td>
    				</logic:equal>
    				<logic:equal name="smsBatchForm" property="geoCriteriaType" value="R">
    					<td><fmt:message key="page.regions"/></td>
    				</logic:equal>
    				<logic:equal name="smsBatchForm" property="geoCriteriaType" value="M">
    					<td><fmt:message key="page.mag.districts"/></td>
    				</logic:equal>
    				<td><bean:write name="record"/>&nbsp;</td>
    			</tr>
    			</logic:equal>
				<logic:notEqual name="i" value="0">
					<tr>
						<td>&nbsp;</td>
    					<td><bean:write name="record"/>&nbsp;</td>
    				</tr>
				</logic:notEqual>
			</logic:iterate>
    	</logic:notEqual>	
    	</logic:notEqual>
  	</table>
  	<br/>
  	<html:submit property="act"><fmt:message key="button.continue"/></html:submit>	
  	<logic:equal name="canSendSms" value="true">
  		<!--<html:submit property="action"><fmt:message key="button.send"/></html:submit>-->
  		<html:button property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.send"/>	
		</html:button>			
  	</logic:equal>
	<html:submit property="act"><fmt:message key="button.back"/></html:submit>
	<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>	
  </html:form>
  </body>
</sakai:html>