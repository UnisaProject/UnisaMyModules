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

		//Click button
		function doSubmit(button){
			if (button === "Quit"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Quit';
			}else if (button === "Back"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
			}else if (button === "Cancel"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel';
			}
			document.studentRegistrationForm.submit();
		}
		
    	function showError(errorTitle, errorText) {

    	    // show the actual error modal
    	    $.unblockUI();		    
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

	<html:hidden property="page" value="applyStatus"/>
	
	<input type='hidden' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<br/>
	<div class="container full-width">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.status.heading"/>&nbsp;<bean:write name="studentRegistrationForm" property="student.academicYear"/>&nbsp;<fmt:message key="page.status.heading.extra"/></h3>
				</div>
				<div class="panel-body">
					<sakai:group_table>
					<tr>
						<td colspan="3"><h4>Primary Qualification&nbsp;</h4></td>
					</tr><tr>
						<td style="width:40%"><b>Qualification&nbsp;</b></td>
						<td style="width:30%"><b>Specializations&nbsp;</b></td>
						<td style="width:30%"><b>Status&nbsp;</b></td>
					</tr><tr>
						<td class="dispFont"><bean:write name="studentRegistrationForm" property="selQualCode1"/></td>
						<logic:notEqual name="studentRegistrationForm" property="selSpecCode1" value="">
							<td class="dispFont"><bean:write name="studentRegistrationForm" property="selSpecCode1"/></td>
						</logic:notEqual>
						<logic:equal name="studentRegistrationForm" property="selSpecCode1" value="">
							<td class="dispFont">N/A - Not Applicable</td>
						</logic:equal>
						<td class="dispFont">
							<bean:write name="studentRegistrationForm" property="qualStatus1"/>
						</td>
					</tr>	
					<logic:notEqual name="studentRegistrationForm" property="selQualCode2" value="">
						<logic:notEqual name="studentRegistrationForm" property="selQualCode2" value="Not Found">
							<tr>
								<td colspan="3"><h4>Alternative Qualification&nbsp;</h4></td>
							</tr><tr>
								<td style="width:40%"><b>Qualification&nbsp;</b></td>
								<td style="width:30%"><b>Specializations&nbsp;</b></td>
								<td style="width:30%"><b>Status&nbsp;</b></td>
							</tr><tr>
								<td class="dispFont"><bean:write name="studentRegistrationForm" property="selQualCode2"/></td>
								<logic:notEqual name="studentRegistrationForm" property="selSpecCode2" value="">
									<td class="dispFont"><bean:write name="studentRegistrationForm" property="selSpecCode2"/></td>
								</logic:notEqual>
								<logic:equal name="studentRegistrationForm" property="selSpecCode2" value="">
									<td class="dispFont">N/A - Not Applicable</td>
								</logic:equal>
								<td class="dispFont">
									<bean:write name="studentRegistrationForm" property="qualStatus2"/>
								</td>
							</tr>
						</logic:notEqual>
					</logic:notEqual>
					<tr>
					</sakai:group_table>
					<br/>
					<logic:equal name="studentStatusForm" property="qualStatus1" value="NP">
						<sakai:group_table>
							<tr>
								<td colspan="2">
									<strong>Payment Status: </strong><br/>
								</td>
							</tr><tr>
								<td>
									<strong>Payment Date: </strong><br/>
								</td><td>
									<bean:write name="studentStatusForm" property="status.payDate"/>
								</td>
							</tr><tr>
								<td>
									<strong>Payment Received: </strong><br/>
								</td><td>
									R&nbsp;<bean:write name="studentStatusForm" property="status.payFee"/>
								</td>
							</tr><tr>
								<td>
									<strong>Payment Complete: </strong><br/>
								</td>
								<logic:equal name="studentStatusForm" property="status.payFull" value="true">
									<td>"Y"</td>
								</logic:equal>
								<logic:notEqual name="studentStatusForm" property="status.payFull" value="true">
									<td>"N"</td>
								</logic:notEqual>
							</tr>
							<logic:notEqual name="studentStatusForm" property="status.payComment" value="">
								<tr>
									<td>
										<strong>Payment Comment: </strong><br/>
									</td><td>
										<bean:write name="studentStatusForm" property="status.payComment"/>
									</td>
								</tr>
							</logic:notEqual>
						</sakai:group_table>
					</logic:equal>
				</div>	
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="act"><fmt:message key="button.quit"/></html:submit>
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