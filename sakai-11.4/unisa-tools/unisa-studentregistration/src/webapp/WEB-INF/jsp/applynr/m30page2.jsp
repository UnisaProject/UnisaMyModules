<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<sakai:html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<c:url value='/resources/css/tool_base.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/tool.css' />" rel="stylesheet"  type="text/css" />	
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	 
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) } 
    
		$(document).ready(function() {
		    
			$("input[name='Next']").click(function(){
				//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Continuing...</strong>" });
			}); 
			$("input[name='Back']").click(function(){
				//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Going Back...</strong>" });
			});
		});
	</script>
</head>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">

	<html:hidden property="page" value="m30p2"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<div class="container">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.m30.h0"/></h3>
				</div>
				<div class="panel-body">
					<strong><fmt:message key="page.m30.h1"/></strong><br/>
					<br/>
					<strong><fmt:message key="page.m30.h4"/></strong><br/>
					<br/>
					<sakai:group_table>
					<tr>
						<td colspan="5"><strong><fmt:message key="page.m30.h5"/></strong></td>
					</tr><tr>
						<td><fmt:message key="page.m30.l1"/>&nbsp;</td>
						<td colspan="4"><html:text name="studentRegistrationForm" property="matric.schoolCertificate" maxlength="50" size="60"/></td>
					</tr><tr>
						<td><fmt:message key="page.m30.l2"/>&nbsp;</td>
						<td colspan="4">
							<logic:notEmpty name="studentRegistrationForm" property="matric.schoolName">
								<bean:write name='studentRegistrationForm' property='matric.schoolName' />
							</logic:notEmpty>
							<logic:empty name="studentRegistrationForm" property="matric.schoolName">
								<html:text name="studentRegistrationForm" property="matric.schoolName" maxlength="50" size="60" />
							</logic:empty>
						</td>
					</tr><tr>
						<td><fmt:message key="page.m30.l8"/>&nbsp;</td>
						<td colspan="4"><html:text name="studentRegistrationForm" property="matric.schoolCompleteYr" maxlength="4" size="4"/></td>
					</tr><tr>
						<td><fmt:message key="page.m30.l3"/>&nbsp;</td>
					</tr><tr>
						<td><fmt:message key="page.m30.l4"/>&nbsp;</td>
						<td colspan="4"><html:text name="studentRegistrationForm" property="matric.schoolProvCountry" maxlength="40" size="60"/></td>
					</tr><tr>
						<td><strong><fmt:message key="page.m30.l5a"/></strong></td>
						<td><strong><fmt:message key="page.m30.l5b"/></strong></td>
						<td><strong><fmt:message key="page.m30.l5c"/></strong></td>
						<td><strong><fmt:message key="page.m30.l5d"/></strong></td>
						<td><strong><fmt:message key="page.m30.l5e"/></strong></td>
					</tr><tr>
						<td><html:text name="studentRegistrationForm" property="matric.subject1" maxlength="50" size="40"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subyear1" maxlength="4" size="6"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.submonth1" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subgrade1" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.symbol1" maxlength="1" size="4"/></td>
					</tr><tr>
						<td><html:text name="studentRegistrationForm" property="matric.subject2" maxlength="50" size="40"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subyear2" maxlength="4" size="6"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.submonth2" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subgrade2" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.symbol2" maxlength="1" size="4"/></td>
					</tr><tr>
						<td><html:text name="studentRegistrationForm" property="matric.subject3" maxlength="50" size="40"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subyear3" maxlength="4" size="6"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.submonth3" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subgrade3" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.symbol3" maxlength="1" size="4"/></td>
					</tr><tr>
						<td><html:text name="studentRegistrationForm" property="matric.subject4" maxlength="50" size="40"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subyear4" maxlength="4" size="6"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.submonth4" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subgrade4" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.symbol4" maxlength="1" size="4"/></td>
					</tr><tr>
						<td><html:text name="studentRegistrationForm" property="matric.subject5" maxlength="50" size="40"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subyear5" maxlength="4" size="6"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.submonth5" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subgrade5" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.symbol5" maxlength="1" size="4"/></td>
					</tr><tr>
						<td><html:text name="studentRegistrationForm" property="matric.subject6" maxlength="50" size="40"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subyear6" maxlength="4" size="6"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.submonth6" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subgrade6" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.symbol6" maxlength="1" size="4"/></td>
					</tr><tr>
						<td><html:text name="studentRegistrationForm" property="matric.subject7" maxlength="50" size="40"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subyear7" maxlength="4" size="6"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.submonth7" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.subgrade7" maxlength="2" size="4"/></td>
						<td><html:text name="studentRegistrationForm" property="matric.symbol7" maxlength="1" size="4"/></td>
					</tr><tr>
						<td colspan="5"><strong><fmt:message key="page.m30.h6"/></strong></td>
					</tr><tr>
					</tr><tr>
						<td><fmt:message key="page.m30.l6"/>&nbsp;</td>
						<td colspan="4"><html:text name="studentRegistrationForm" property="matric.qualName" maxlength="50" size="60"/></td>
					</tr><tr>
						<td><fmt:message key="page.m30.l7"/>&nbsp;</td>
						<td colspan="4"><html:text name="studentRegistrationForm" property="matric.qualInstitution" maxlength="50" size="60"/></td>
					</tr><tr>
						<td><fmt:message key="page.m30.l8"/>&nbsp;</td>
						<td colspan="4"><html:text name="studentRegistrationForm" property="matric.qualCompleteYr" maxlength="4" size="6"/></td>
					</tr><tr>
						<td><fmt:message key="page.m30.l9"/>&nbsp;</td>
						<td colspan="4"><html:text name="studentRegistrationForm" property="matric.qualPropose" maxlength="5" size="8"/></td>
					</tr><tr>
						<td><fmt:message key="page.m30.l10"/>&nbsp;</td>
						<td colspan="4"><html:text name="studentRegistrationForm" property="matric.qualFirstYr" maxlength="4" size="6"/></td>
					</tr><tr>
					</sakai:group_table>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="act"><fmt:message key="button.back"/></html:submit>
						<html:submit property="act"><fmt:message key="button.next"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>
	
</html:form>
</body>
</sakai:html>