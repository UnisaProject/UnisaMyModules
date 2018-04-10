<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>
<!-- <script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/jquery.blockUI.js" />"></script> 
<script type="text/javascript" language="javascript">
	function testBlock() {		
		
		$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /><br/>Processing SMS Request... Do not close your browser</b>" });
		setTimeout($.unblockUI, 4000);
	}
</script> -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<sakai:html>
  <head>
    <!--    include cascading style sheet here    
    <link rel="stylesheet" href="/cmsys/staff/stylesheets/staff.css">-->
	<title><fmt:message key="page.heading"/></title>

  </head> 

  <body>
  <br/>
  <h1><fmt:message key="page.menu.heading"/></h1>
  <html:form action="/smsbatch.do?act=step1">
  	<html:hidden property="page" value="menu"/>

  	<p><font color="red"><html:errors/></font></p>

  	<table>
		<tr>
			<td colspan="2"><strong><fmt:message key="page.menu.options"/>&nbsp;</strong></td>
		</tr>
		<logic:greaterThan name="smsBatchForm" property="rcCount" value="0">		
		<tr>
			<td colspan="2"><a href="#" onclick="document.forms[0].action='smsbatch.do?act=step1';document.forms[0].submit()"><fmt:message key="page.menu.option.standard"/></a></td> 
			<!-- <td colspan="2"><fmt:message key="page.menu.option.standard"/><style="color:red">...Due to unforeseen technical problems this tool is temporary closed, we apologise for any inconvenience caused.</style></td>-->
		</tr><tr>
			<td colspan="2"><a href="#" onclick="document.forms[0].action='smsFile.do?act=step1';document.forms[0].submit()">
			<logic:equal name="smsBatchForm" property="dcmUser" value="true">
				<fmt:message key="page.menu.option.dcm.standardFile"/>
			</logic:equal>
			<logic:equal name="smsBatchForm" property="dcmUser" value="false">
				<fmt:message key="page.menu.option.standardFile"/>
			</logic:equal>
			</a></td>		
		</tr><tr>
			<td colspan="2">
				<logic:equal name="smsBatchForm" property="smsEnvironment" value="dev">
					<a HREF='<%=application.getInitParameter("customsmspathdev")%>'>
						<fmt:message key="page.menu.option.customised"/></a>
				</logic:equal>
				<logic:equal name="smsBatchForm" property="smsEnvironment" value="qa">
					<a HREF='<%=application.getInitParameter("customsmspathqa")%>'>
						<fmt:message key="page.menu.option.customised"/></a>
				</logic:equal>
				<logic:equal name="smsBatchForm" property="smsEnvironment" value="prod">
					<a HREF='<%=application.getInitParameter("customsmspathprod")%>'>
						<fmt:message key="page.menu.option.customised"/></a>
				</logic:equal>
			</td>		
		</tr>
		</logic:greaterThan>
		<tr>		
			<td colspan="2">
				<logic:equal name="smsBatchForm" property="smsEnvironment" value="dev">
					<a HREF='<%=application.getInitParameter("smsenquirydev")%>'>
						<fmt:message key="page.menu.option.enquiry"/></a>
				</logic:equal>
				<logic:equal name="smsBatchForm" property="smsEnvironment" value="qa">
					<a HREF='<%=application.getInitParameter("smsenquiryqa")%>'>
						<fmt:message key="page.menu.option.enquiry"/></a>
				</logic:equal>
				<logic:equal name="smsBatchForm" property="smsEnvironment" value="prod">
					<a HREF='<%=application.getInitParameter("smsenquiryprod")%>'>
						<fmt:message key="page.menu.option.enquiry"/></a>
				</logic:equal>
			</td>
		</tr><tr>
    		<td>&nbsp;</td>
    	</tr><tr>
			<td colspan="2"><strong><fmt:message key="page.menu.requestor"/>&nbsp;</strong></td>
		</tr><tr>
    		<td><fmt:message key="page.menu.name"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="userName"/>&nbsp;</td>
    	</tr><tr>
    		<td><fmt:message key="page.menu.unit"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="department"/>&nbsp;</td>
    	</tr><tr>
    	<logic:greaterThan name="smsBatchForm" property="rcCount" value="1">
    			<td><fmt:message key="page.menu.rclist"/>&nbsp;</td>
    	    		<bean:define id="rcList" property="rcList" name="smsBatchForm"/>
    	        	<td><html:select name="smsBatchForm" property="rcCode" size="5" style="font-family: Courier New;font-size: 90%">
    	    		<html:options collection="rcList" property="value" labelProperty="label" filter="false" style="font-family: Courier New;font-size: 90%"/>
    	    		</html:select>
    	    	</td>
    	    </logic:greaterThan>
    		<logic:equal name="smsBatchForm" property="rcCount" value="1">
    			<td><fmt:message key="page.menu.rc"/>&nbsp;</td>
				<td><bean:write name="smsBatchForm" property="rcCode"/>&nbsp;:&nbsp;<bean:write name="smsBatchForm" property="rcDescription"/></td>
    		</tr><tr>
    			<td><fmt:message key="page.request.totalbudget"/>:&nbsp;</td>
				<td><bean:write name="smsBatchForm" property="budgetAmount" format="R 0.00"/></td>
    		</tr>
    		<tr>
    			<td><fmt:message key="page.request.availablebudget"/>:&nbsp;</td>
				<td><bean:write name="smsBatchForm" property="availableBudgetAmount" format="R 0.00"/></td>
    		</tr>
    		</logic:equal>
  	</table><br/>
  	<!--<html:button property="act" onclick="javascript:testBlock();">
					<fmt:message key="button.send"/>
	</html:button>-->

  </html:form>
  </body>
</sakai:html>