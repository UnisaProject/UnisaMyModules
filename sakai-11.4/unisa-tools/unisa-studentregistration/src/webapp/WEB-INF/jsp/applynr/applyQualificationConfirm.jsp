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
			doSubmit("Continue");
		}
		
		//Click button
		function doSubmit(button){
			if (button === "Continue"){
				//Go to SLP if SLP student, otherwise continue as normal student.
				var isSTUSLP = $("#isSTUSLP").val();
				var isSTUAPQ = $("#isSTUAPQ").val();
				//alert("stepQualConfirm - SLP - isSTUSLP=" + isSTUSLP+", isSTUAPQ=" + isSTUAPQ);
				if (isSTUSLP == "true" && isSTUAPQ == "true"){
					document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepSLPConfirm';
				}else{
					document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepQualConfirm';
				}
			}else if (button === "Back"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
			}else if (button === "Cancel"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel';
			}
			$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br></strong>" });
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
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="stepQualConfirm"/>

	<input type="hidden" id="isSTUSLP" name="isSTUSLP" value="<bean:write name='studentRegistrationForm' property='student.stuSLP' />" />
	<input type="hidden" id="isSTUAPQ" name="isSTUAPQ" value="<bean:write name='studentRegistrationForm' property='student.stuapq' />" />
	
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
							<td colspan="3"><strong><fmt:message key="page.studentnr.apply.confirm"/></strong>&nbsp;</td>
						</tr><tr>
							<td colspan="3">&nbsp;</td>
						</tr>
						<logic:notEqual name='studentRegistrationForm' property='student.stuSLP' value="true">
							<logic:notEmpty name="studentRegistrationForm" property="existQual">
								<tr>
									<td>Current Qualification&nbsp;</td>
									<td colspan="2"><bean:write name="studentRegistrationForm" property="student.retQualPrevFinal"/></td>
								</tr><tr>
									<td>Current Specialization&nbsp;</td>	
									<td colspan="2"><bean:write name="studentRegistrationForm" property="student.retSpecPrevFinal"/></td>
								</tr><tr>
									<td colspan="3">&nbsp;</td>
								</tr>
							</logic:notEmpty>
						</logic:notEqual>
						<tr>
							<td>Primary Qualification&nbsp;</td>
							<td colspan="2"><bean:write name="studentRegistrationForm" property="selQualCode1"/>&nbsp;-&nbsp;<bean:write name="studentRegistrationForm" property="selQualCode1Desc"/></td>
						</tr>
						<tr>
							<td>Primary Specializations&nbsp;</td>
							<logic:notEqual name="studentRegistrationForm" property="selSpecCode1" value="0">
								<td colspan="2"><bean:write name="studentRegistrationForm" property="selSpecCode1"/>&nbsp;-&nbsp;<bean:write name="studentRegistrationForm" property="selSpecCode1Desc"/></td>
							</logic:notEqual>
							<logic:equal name="studentRegistrationForm" property="selSpecCode1" value="0">
								<td colspan="2">N/A - Not Applicable</td>
							</logic:equal>
						</tr><tr>
							<td colspan="3">&nbsp;</td>
						</tr>	
						<logic:notEqual name="studentRegistrationForm" property="selQualCode2" value="0">
							<tr>
								<td>Secondary Qualification&nbsp;</td>
								<td colspan="2"><bean:write name="studentRegistrationForm" property="selQualCode2"/>&nbsp;-&nbsp;<bean:write name="studentRegistrationForm" property="selQualCode2Desc"/></td>
							</tr>
							<tr>
								<td>Secondary Specializations&nbsp;</td>
								<logic:notEqual name="studentRegistrationForm" property="selSpecCode2" value="0">
									<td colspan="2"><bean:write name="studentRegistrationForm" property="selSpecCode2"/>&nbsp;-&nbsp;<bean:write name="studentRegistrationForm" property="selSpecCode2Desc"/></td>
								</logic:notEqual>
								<logic:equal name="studentRegistrationForm" property="selSpecCode2" value="0">
									<td colspan="2">N/A - Not Applicable</td>
								</logic:equal>
							</tr>
						</logic:notEqual>
						<tr>
							<td colspan="3">&nbsp;</td>
						</tr><tr>
							<td><fmt:message key="page.studentnr.apply.acadyear"/>&nbsp;</td>
							<td colspan="2"><bean:write name="studentRegistrationForm" property="student.academicYear"/></td>
						</tr><tr>
							<td colspan="3">&nbsp;</td>
						</tr><tr>
							<td colspan="3"><i><fmt:message key="page.studentnr.apply.verify"/></i></td>
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
	
	<div id="hiddenRadio" style="display: none;">
		<input type="radio" name="studentApplication.completeQual" value="0" checked="checked" />
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
</body>
</sakai:html>
