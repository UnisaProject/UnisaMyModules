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
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style type="text/css">
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
	        /*font-size: 12px !important;*/
			font-weight: normal !important;
	    }
	    .panel .panel-footer {
	        position : relative;
	        /*bottom : 0;*/
	        margin-bottom:1px;
	        width : 100%;
	    }
	    <!-- Bootply end -->
	    
		.light {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}
		input[type='radio'] {
			float: left;
		}
		label:hover {
		    cursor:pointer;
		}
		label.inline {
			font-weight: normal;
		    display: table-cell;
		}
		table { 
		    border-spacing: 2px;
		    <!-- border-collapse: separate; -->
		    /*font-size: 12px !important;
			font-weight: bold !important;*/
		}
		table.itemSummary td, td.itemSummaryLite {
			padding: .1em;
			/*font-size: 12px !important;*/
			font-weight: normal !important;
		}
		td { 
		    padding: 2px;
		}
		.nav-tabs { 
			border-bottom: none;
		}
		.nav-tabs > li {
		    position:relative;
		    color : white;
		    background-color:#ededed;
		    padding : 2px 2px;
		    border-radius: 5px 5px 0px 0px ;
		    border:0;
		    display: inline-block;
		}
		.nav-tabs > li .close {
		    margin: -3px 0 0 10px;
		    font-size: 18px;
		    padding: 5px 0;
		    float: right;
		}
		.nav-tabs > li > a	{
		    /* adjust padding for height*/
		    padding-top: 4px; 
		    padding-bottom: 4px;
		}
		.nav-tabs > li a[data-toggle=tab] {
		    float: left!important;
		}
		li span{
			display:block;
			white-space: nowrap;
		}
		.stretch {
		    width: 100%;
		
		    padding: 0 5px;
		    /*border: 0;    */
		    /*margin: 0 -5px;  compensate horizontal padding and border-width with negative horizontal marfin */
		
		    /* small styling 
		    border-radius: 5px; 
		    box-shadow: 0 0 5px #555 inset;*/
		}
	</style>
	
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) }  

		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");
			
		});
		
		function validateSelect(){
			//alert("In Validate");
			var cellNr1 = $("input[name='student.cellNr']").val();
			if (cellNr1 == null || cellNr1 == "" || cellNr1 == "undefined"){
				showError("Error", "Please enter your mobile number");
				return false;
			}
			var cellNr2 = $("input[name='student.cellNr2']").val();
			if (cellNr2 == null || cellNr2 == "" || cellNr2 == "undefined"){
				showError("Error", "Please confirm your mobile number");
				return false;
			}
			if (cellNr1 != cellNr2){
				showError("Error", "Entered Mobile numbers do not match. Please try again.");
				return false;
			}
			
			var email1 = $("input[name='student.emailAddress']").val();
			if (email1 == null || email1 == "" || email1 == "undefined"){
				showError("Error", "Please enter your email address");
				return false;
			}
			var email2 = $("input[name='student.emailAddress2']").val();
			if (email2 == null || email2 == "" || email2 == "undefined"){
				showError("Error", "Please confirm your email address");
				return false;
			}
			if (email1 != email2){
				showError("Error", "Entered Email addresses do not match. Please try again.");
				return false;
			}
			doSubmit("Continue");
		}
		
		//Click button
		function doSubmit(button){
			if (button === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepRetContact';
			}else if (button === "Back"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
			}else if (button === "Cancel"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel';
			}
			document.studentRegistrationForm.submit();
		}
		
		
		function showError(errorTitle, errorText) {
			// show the actual error modal
			$('#dialogContent').html(errorText);
			$('#dialogHolder').dialog({
				autoOpen: true,
			  	title: errorTitle,
			  	modal: true,
			  	buttons: {
			  		"Ok": function() {
			  			$(this).dialog("close");
			  		}
			  	}
			});
		}

	</script>
</head>
<%
/**Changes**/
StudentRegistrationForm studentRegistrationForm = (StudentRegistrationForm)session.getAttribute("studentRegistrationForm");
String email = studentRegistrationForm.getStudent().getEmailAddress();
boolean emailAddressGood = studentRegistrationForm.getStudent().getEmailAddressGood();
boolean readOnly = false;
boolean hidden = false;
if(emailAddressGood){
	if(email!=null&&email.indexOf("mylife")!=-1){
		readOnly = true;
		hidden = true;
	}
}

%>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="applyRetContact"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading"/></h3>
				</div>
				<div class="panel-body">	
					<sakai:group_table>
						<tr>
							<td colspan="3"><fmt:message key="page.required.instruction"/>&nbsp;</td>
						</tr><tr>
							<td colspan="3">&nbsp;</td>
						</tr><tr>
							<td valign="middle"><fmt:message key="page.studentnr.apply.surname"/>&nbsp;</td>
							<td colspan="2"><bean:write name="studentRegistrationForm" property="student.surname" /></td>
						</tr><tr>
							<td valign="middle"><fmt:message key="page.studentnr.apply.firstnames"/>&nbsp;</td>
							<td colspan="2"><bean:write name="studentRegistrationForm" property="student.firstnames" /></td>
						</tr>
						<logic:notEmpty name="studentRegistrationForm" property="student.maidenName">
							<tr>
								<td valign="middle"><fmt:message key="page.studentnr.apply.maiden.return"/></td>
								<td colspan="2"><bean:write name="studentRegistrationForm" property="student.maidenName" /></td>
							</tr>
						</logic:notEmpty>
						<tr>
							<td><fmt:message key="page.studentnr.apply.birthshort"/>&nbsp;</td>
							<td colspan="2"><bean:write name="studentRegistrationForm" property="student.birthYear" />&nbsp;/
							<bean:write name="studentRegistrationForm" property="student.birthMonth" />&nbsp;/
							<bean:write name="studentRegistrationForm" property="student.birthDay" /></td>
						</tr>
						<logic:notEmpty name="studentRegistrationForm" property="student.idNumber">
							<logic:notEqual name="studentRegistrationForm" property="student.idNumber" value=" ">
								<tr>
									<td><fmt:message key="page.studentnr.apply.id"/></td>
									<td colspan="2"><bean:write name="studentRegistrationForm" property="student.idNumber"/></td>
								</tr>
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="studentRegistrationForm" property="student.passportNumber">
							<logic:notEqual name="studentRegistrationForm" property="student.passportNumber" value=" ">
								<tr>
									<td><fmt:message key="page.display.studentNumber.passport"/></td>
									<td colspan="2"><bean:write name="studentRegistrationForm" property="student.passportNumber"/></td>
								</tr>
							</logic:notEqual>
						</logic:notEmpty>
						<tr>
							<td colspan="3">&nbsp;</td>
						</tr><tr>
							<td><fmt:message key="page.cell"/>&nbsp;</td>
							<td><html:text name="studentRegistrationForm" property="student.cellNr" maxlength="20" size="30"/></td>
							<td align="left" class="small"><fmt:message key="page.studentnr.apply.eg2"/></td>
						</tr><tr>
							<td><fmt:message key="page.cell2"/>&nbsp;</td>
							<td><html:text name="studentRegistrationForm" property="student.cellNr2" maxlength="20" size="30"/></td>
							<td align="left" class="small"><fmt:message key="page.studentnr.apply.eg3"/></td>
						</tr><tr>
							<td><fmt:message key="page.emailaddress"/>&nbsp;</td>
							<td colspan="2"> 
								<html:text name="studentRegistrationForm" property="student.emailAddress" maxlength="60" size="30" readonly="<%=readOnly %>"/>
							</td>
						</tr><tr>
							<td>
								<% if(!hidden){ %>
									<fmt:message key="page.emailaddress2"/>&nbsp;</td>
								<% } %>
							<td colspan="2">
								<!-- changes -->
								<% if(hidden){%>
									<html:hidden name="studentRegistrationForm" property="student.emailAddress2" value="<%=email%>"/>
								<% } else {%>
									<html:text name="studentRegistrationForm" property="student.emailAddress2" maxlength="60" size="30"/>
								<% } %>
								
							</td>
						</tr>
					</sakai:group_table>
				</div>
				<div class="panel-footer clearfix">
					<button class="btn btn-default" type="button" onclick="validateSelect();">Continue</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Back');">Back</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
</body>
</sakai:html>
