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
		.ui-dialog {
		    left: 50% !important;
		    top: 100px !important;
		    margin-left: -175px !important; 
		    /*margin-top: -175px !important;*/
		} 
		.dialog .ui-icon {
		    background-image: url("<c:url value='/resources/images/ui-icons_222222_256x240.png' />");
		}
		.ui-state-default .ui-icon {
			background-image: url("<c:url value='/resources/images/ui-icons_222222_256x240.png' />");
		}
		/* Override jQuery UI theme's padding on buttons: */
		.ui-button-text-only .ui-button-text {
			padding: 0.2em 0.5em;
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
		    border-collapse: separate;
		}
		td { 
		    padding: 2px;
		}
		.buttonFooter {
		    background-color: #333;
		    padding: 16px;
		}
	</style>
	
	<script type="text/javascript">
	
		function disableBackButton(){window.history.forward()} 
		disableBackButton(); 
		window.onload=disableBackButton(); 
		window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
		window.onunload=function() { void(0) }  
		
		/**Call when document is ready**/
		$(document).ready(function() {
			
			/**Reset Radio Buttons **/
			window.radioReset = function radioReset() {
				var checkMain = $("input:radio[name=selectMatric]");
		       	for( var i=0; i<checkMain.length; i++ ){
		       	   		checkMain[i].checked = false;
		       	}
		    };       	
	       	
			radioReset();
			
			$('form,input,select,textarea').attr("autocomplete", "off");	
		});
		
		function validateSelect(){
			var matricVal = $('input[name="selectMatric"]:checked').val();
			if(matricVal == null || matricVal == "" || matricVal == "undefinded"){
				showError("Error", "Please select a Higher Education option");
				return false;
			}
		}
		
		//Click button
		function doSubmit(button){
			if (button === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepMatric';
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
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">

	<html:hidden property="page" value="applyMatric"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.aps.heading"/></h3>
				</div>
				<div class="panel-body">
					<fmt:message key="page.aps.info"/><br/>
					<h4><fmt:message key="page.aps.matric.heading"/></h4>
						
					<table>
						<tr>
							<td style="width:50%"><h4><fmt:message key="page.aps.matric.matricSC"/></h4></td>
							<td style="width:50%"><h4><fmt:message key="page.aps.matric.matricNSC"/></h4></td>
						</tr><tr>
							<td><input type="radio" name="selectMatric" value="MATR" id="MATR"/><label for="MATR" class="inline"><span>&nbsp;<fmt:message key="page.aps.matric.MATR"/></span></label></td>
							<td><input type="radio" name="selectMatric" value="DEGR" id="DEGR"/><label for="DEGR" class="inline"><span>&nbsp;<fmt:message key="page.aps.matric.DEGR"/></span></label></td>
						</tr><tr>
							<td><input type="radio" name="selectMatric" value="SENR" id="SENR"/><label for="SENR" class="inline"><span>&nbsp;<fmt:message key="page.aps.matric.SENR"/></span></label></td>
							<td><input type="radio" name="selectMatric" value="DIPL" id="DIPL"/><label for="DIPL" class="inline"><span>&nbsp;<fmt:message key="page.aps.matric.DIPL"/></span></label></td>
						</tr><tr>
							<td><input type="radio" name="selectMatric" value="ONBK" id="ONBK"/><label for="ONBK" class="inline"><span>&nbsp;<fmt:message key="page.aps.matric.ONBK"/></span></label></td>
							<td><input type="radio" name="selectMatric" value="HCER" id="HCER"/><label for="HCER" class="inline"><span>&nbsp;<fmt:message key="page.aps.matric.HCER"/></span></label></td>
						</tr>
					</table>
					<br/>
					<fmt:message key="page.aps.matric.not"/><br/>
				</div>	
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="act" onclick="return validateSelect();"><fmt:message key="button.continue"/></html:submit>
						<html:submit property="act"><fmt:message key="button.back"/></html:submit>
						<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
</body>
</sakai:html>