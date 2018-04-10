<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentappeal.forms.StudentAppealForm"%>
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
	
		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");
			
			//Reset all Radio Buttons
			$('input:radio[name="agree"]').removeAttr('checked');
 
		});
	
		function validateSelect(){
			var onToday = $("#onToday").val();
		    //Declare Radio
			var agreeVal = $('input[name=agree]:checked').val();
			if(agreeVal != "Y"){
				showError("Error","Please indicate your agreement to the declaration and undertaking.");
				return false;
		    }
			doSubmit("Continue");
		}
		
		function doSubmit(input,check){
			if (input === "Continue"){
				document.studentAppealForm.action='studentAppeal.do?act=appealDeclare';
			}else if (input === "Back"){
				document.studentAppealForm.action='studentAppeal.do?act=Back';
			}else if (input === "Cancel"){
				document.studentAppealForm.action='studentAppeal.do?act=Cancel'; 
			}
			document.studentAppealForm.submit();
		}
		
		function showModal(errorTitle, errorText) {
	   	    // show the actual error modal
	   	    $.unblockUI();		    
	   	    $('#dialogContent').html(errorText);
	   		$('#dialogHolder').dialog({
	   			autoOpen: true,
	   		  	title: errorTitle,
	   		  	modal: true,
	   		 	width: "auto",
	   		 	position:['middle',20],
	   		  	buttons: {
	   		    	"Ok": function() {
	   		   	  		doSubmit("Continue");
	   		   	  	},
	   		   	  	"Cancel": function(){
	   		   	   		$(this).dialog("close");
	   		    	}
	   		  	}
	   		});
	   	}
		
    	function showError(errorTitle, errorText) {

    	    // show the actual error modal
    	    $.unblockUI();		    
    	    $('#dialogContent').html(errorText);
    		$('#dialogHolder').dialog({
    		  autoOpen: true,
    		  title: errorTitle,
    		  modal: true,
    		  position:['middle',20],
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
<html:form action="/studentAppeal">

	<html:hidden property="page" value="appealDeclare"/>
	
	<input type='hidden' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />
	<input type="hidden" id="docMsg" name="docMsg" value="<bean:write name='studentAppealForm' property='webUploadMsg' />" />
	<input type="hidden" id="qualStatus1" name="qualStatus1" value="<bean:write name='studentAppealForm' property='qualStatusCode1' />" />
	<input type="hidden" id="qualStatus2" name="qualStatus2" value="<bean:write name='studentAppealForm' property='qualStatusCode2' />" />
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<div class="container full-width">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.appeal.declare.heading"/></h3>
				</div>
				<div class="panel-body">
					<logic:equal name="studentAppealForm" property="appealSelect1" value="Y">
						<sakai:group_heading><fmt:message key="page.appeal.declare.subheading"/></sakai:group_heading>
					</logic:equal>
					<logic:notEqual name="studentAppealForm" property="appealSelect1" value="Y">
						<logic:equal name="studentAppealForm" property="appealSelect2" value="Y">
							<sakai:group_heading><fmt:message key="page.appeal.declare.subheading"/></sakai:group_heading>
						</logic:equal>
					</logic:notEqual>
			
						<sakai:group_table>
							<logic:equal name="studentAppealForm" property="appealSelect1" value="Y">
								<tr>
									<td><b>Primary Qualification&nbsp;</b></td>
									<td><b>Specializations&nbsp;</b></td>
								</tr><tr>
									<td>
										&nbsp;<bean:write name="studentAppealForm" property="selQualCode1"/>
									</td>
									<logic:notEqual name="studentAppealForm" property="selSpecCode1" value="0">
										<td><bean:write name="studentAppealForm" property="selSpecCode1"/></td>
									</logic:notEqual>
									<logic:equal name="studentAppealForm" property="selSpecCode1" value="0">
										<td>N/A - Not Applicable</td>
									</logic:equal>
								</tr>	
							</logic:equal>
							<logic:equal name="studentAppealForm" property="appealSelect2" value="Y">
								<logic:notEqual name="studentAppealForm" property="selQualCode2" value="0">
									<tr>
										<td><b>Secondary Qualification&nbsp;</b></td>
										<td><b>Specializations&nbsp;</b></td>
									</tr><tr>
										<td>
											&nbsp;<bean:write name="studentAppealForm" property="selQualCode2"/>
										</td>
										<logic:notEqual name="studentAppealForm" property="selSpecCode2" value="0">
											<td><bean:write name="studentAppealForm" property="selSpecCode2"/></td>
										</logic:notEqual>
										<logic:equal name="studentAppealForm" property="selSpecCode2" value="0">
											<td>N/A - Not Applicable</td>
										</logic:equal>
									</tr>
								</logic:notEqual>	
							</logic:equal>	
							<tr>
								<td colspan="2"><strong><fmt:message key="page.appeal.declare.motivation"/></strong></td>
							</tr><tr>
								<td colspan="2"><bean:write name="studentAppealForm" property="appealText"/></td>
							</tr>
							<logic:notEmpty name="studentAppealForm" property="appealSourceFiles">
								<tr>
									<td colspan="2"><strong><fmt:message key="page.appeal.declare.files"/></strong></td>
								</tr><tr>
									<td colspan="2">
										<div>
										   	<logic:notEmpty name="studentAppealForm" property="appealSourceFiles">
												<logic:iterate name="studentAppealForm" property="appealSourceFiles" id="listMsgId">
													&nbsp;<bean:write name="listMsgId"/><br/>
												</logic:iterate>
											</logic:notEmpty>
										</div>
									</td>
								</tr>
							</logic:notEmpty>
							<tr>
								<td colspan="2"><html:radio  property ="agree" value="Y"/>&nbsp;&nbsp;
									<fmt:message key="page.appeal.declare.declaration1"/><br/>
									<fmt:message key="page.appeal.declare.declaration2"/><br/>
									<fmt:message key="page.appeal.declare.declaration3"/><br/>
									<fmt:message key="page.appeal.declare.declaration4"/><br/>
									<fmt:message key="page.appeal.declare.declaration5"/><br/>
									<fmt:message key="page.appeal.declare.declaration6"/><br/>
								</td>
							</tr>
					</sakai:group_table>
				</div>
				<div class="panel-footer clearfix">
					<button class="btn btn-default" type="button" onclick="doSubmit('Back');"><fmt:message key="button.back"/></button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');"><fmt:message key="button.cancel"/></button>
					<button class="btn btn-default" type="button" onclick="validateSelect();"><fmt:message key="button.next"/></button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentAppealForm" property="version"/></div>

</html:form>
  </body>
</sakai:html>
