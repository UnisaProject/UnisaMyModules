<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Enumeration"%>
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
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- Sakai Themes -->
	<link href="<c:url value='/resources/css/tool_base.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/tool.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/portal.css' />" rel="stylesheet"  type="text/css" />

	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.css" />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
        
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style>
		#color {
		  /**background-color: red;**/
		  float: left;
		}
		#color2 {
		  /**background-color: red;**/
		  float: left;
		}
		#doMainUDNo,#doMainUDNo,#doMainHONNo,#doMainHONNo,#doMainMDNo,#doMainDOCNo,#doMainPAYNo {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}
		.itemSummary, .itemSummaryLite {
		    margin: 0;
		}
	
	   <!-- CSS code from Bootply.com editor -->
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		.panel {
	        /*height : 300px;*/
	        margin-bottom:1px;
	        position: relative;
	    }
	    .panel .panel-footer {
	        position : relative;
	        /*bottom : 0;*/
	        margin-bottom:1px;
	        width : 100%;
	    }
	    <!-- Bootply end -->
	    input[type="text"] {
		    width: 100%;
		    padding: 0 5px;
		}
	</style>
 
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) } 

	//Call when document is ready
	$(document).ready(function() {
		
		// This will hold our timer
		var myTimer = {};
		  	// delay 45 seconds
			myTimer = $.timer(60000, function() {
				var isAdmin = $('#webADM').val();
				if (isAdmin === false || isAdmin === "false"){
					//redirect to home page
			  		window.top.location.href = "http://applications.unisa.ac.za/index.html";
				}else{
					//redirect to Admin home page
					window.top.location.href = "/unisa-studentregistration/applyForStudentNumber.do?act=loginAdmin";
		  		}
		  });
	});
</script>

</head>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="complete"/>
	<input type="hidden" name="webADM" id="webADM" value="<bean:write name='studentRegistrationForm' property='adminStaff.admin' />"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<div class="container full-width">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading"/></h3>
				</div>
				<div class="panel-body">
					<sakai:group_heading><fmt:message key="page.studentnr.completeRet.info1"/></sakai:group_heading>
					<br>
					<table style="width:100%">
						<tr>
							<td style="height:30px"><strong><font size="2"><fmt:message key="page.studentnr.completeRet.info2"/></font></strong>&nbsp;</td>
						</tr><tr>
							<td style="height:30px"><fmt:message key="page.studentnr.completeRet.info3"/>&nbsp;
							<bean:write name="studentRegistrationForm" property="student.appTime"/>
							</td>
						</tr><tr>
							<td>
								<table style="width:100%">
									<tr>
										<td colspan="2" style="height:30px"><strong>These are the qualifications you have selected. &nbsp;</strong></td>
									</tr><tr>
										<td colspan="2" style="height:30px">
											<fmt:message key="page.studentnr.completeRet.contact1"/><br/>
											<fmt:message key="page.studentnr.completeRet.contact2"/><br/>
											<fmt:message key="page.studentnr.completeRet.contact3"/><br/>
											<fmt:message key="page.studentnr.completeRet.contact4"/><br/>
										</td>
									</tr>
									<logic:notEqual name='studentRegistrationForm' property='student.stuSLP' value="true">
										<logic:notEmpty name="studentRegistrationForm" property="student.retQualPrevFinal">
											<tr>
												<td style="width:200px; height:30px"><b>Current Qualification:</b></td>
												<td>&nbsp;</td>
											<tr>
												<td style="width:200px; height:30px">Qualification:</td>
												<td><bean:write name="studentRegistrationForm" property="student.retQualPrevFinal"/></td>
											</tr><tr>
												<td style="width:200px; height:30px">Specialization:</td>
												<td><bean:write name="studentRegistrationForm" property="student.retSpecPrevFinal"/></td>
											</tr>
										</logic:notEmpty>
									</logic:notEqual>
									<logic:notEmpty name="studentRegistrationForm" property="student.retQualOneFinal">
										<tr>
											<td style="width:200px; height:30px"><b>Proposed Qualification:</b></td>
											<td>&nbsp;</td>
										<tr><tr>
											<td style="width:200px; height:30px">Primary Qualification:</td>
											<td><bean:write name="studentRegistrationForm" property="student.retQualOneFinal"/></td>
										</tr><tr>
											<td style="width:200px; height:30px">Primary Specialization:</td>
											<td><bean:write name="studentRegistrationForm" property="student.retSpecOneFinal"/></td>
										</tr>
									</logic:notEmpty>
									<logic:notEmpty name="studentRegistrationForm" property="student.retQualTwoFinal">
										<tr>
											<td style="width:200px; height:30px">Secondary Qualification:</td>
											<td><bean:write name="studentRegistrationForm" property="student.retQualTwoFinal"/></td>
										</tr><tr>
											<td style="width:200px; height:30px">Secondary Specialization:</td>
											<td><bean:write name="studentRegistrationForm" property="student.retSpecTwoFinal"/></td>
										</tr>
									</logic:notEmpty>
									 
								</table>
							</td>
						</tr>
						<tr>
							<td style="height:30px"><fmt:message key="page.studentnr.completeRet.info4"/>&nbsp;
							<bean:write name="studentRegistrationForm" property="student.emailAddress"/></td>
						</tr>
						<tr>
							<td style="height:20px">&nbsp;</td>
						</tr>
						<tr>
							<td style="height:30px"><strong><font size="2"><fmt:message key="page.studentnr.completeRet.info6"/></font></strong>&nbsp;</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
		
</html:form>
</body>
</sakai:html>