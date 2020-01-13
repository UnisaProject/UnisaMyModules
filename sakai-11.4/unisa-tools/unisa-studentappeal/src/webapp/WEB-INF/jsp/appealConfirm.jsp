<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentappeal.forms.StudentAppealForm"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Enumeration"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentappeal.ApplicationResources"/>

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
    <link href="<c:url value='/resources/css/jquery-ui.min.css' />" rel="stylesheet"  type="text/css" />
	
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.min.js' />"></script> 
	
	<style>
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
	    
		.light {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
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
		#stretch {
		    width: 100%;
		    padding: 0 5px;
		}
		input[type="text"] {
			width: 100%;
			padding: 0 5px;
		}
		textarea {
			/*border: none;*/
			width: 100%;
			-webkit-box-sizing: border-box; /* <=iOS4, <= Android  2.3 */
		    -moz-box-sizing: border-box; /* FF1+ */
		     box-sizing: border-box; /* Chrome, IE8, Opera, Safari 5.1*/
		}
		.full-width {
		  width: 100vw;
		  position: relative;
		  left: 50%;
		  right: 50%;
		  margin-left: -50vw;
		  margin-right: -50vw;
		}
		.dispFont {
			font-size:14px;
			//transform: scale(0.833);/*10/12=0.833, font-size:10px*/
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
				//redirect to home page
			  	window.top.location.href = "http://applications.unisa.ac.za/index.html";
			});
		});
			
		function doSubmit(input){
			if (input === "Cancel"){
				document.studentAppealForm.action='studentAppeal.do?act=Cancel'; 
			}
			document.studentAppealForm.submit();
		}
		</script>
</head>
 
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/studentAppeal">
	<html:hidden property="page" value="appealConfirm"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<div class="container full-width">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.appeal.complete.heading"/></h3>
				</div>
				<div class="panel-body">
					<sakai:group_heading><fmt:message key="page.appeal.complete.info1"/></sakai:group_heading>
					<br>
					<table class="main">
						<tr>
							<td colspan="2" style="height:30px"><font size="2"><fmt:message key="page.appeal.complete.info2"/></font>&nbsp;</td>
						</tr><tr>
							<td colspan="2">&nbsp;</td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.appeal.complete.info3"/>&nbsp;
								<strong><bean:write name="studentAppealForm" property="student.appTime"/></strong>
							</td>
						</tr><tr>
							<td colspan="2">&nbsp;</td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.appeal.complete.info4"/>&nbsp;
								<strong><bean:write name="studentAppealForm" property="student.emailAddress"/></strong>
							</td>
						</tr><tr>
							<td colspan="2">&nbsp;</td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.appeal.complete.info5"/>&nbsp;
								<strong><bean:write name="studentAppealForm" property="student.cellNr"/></strong>
							</td>
						</tr>
					</table>
				</div>
				<div class="panel-footer clearfix">
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');"><fmt:message key="button.quit"/></button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentAppealForm" property="version"/></div>

</html:form>
 </body>
</sakai:html>
		