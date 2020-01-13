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
		/*
		.ui-dialog {
		    left: 50% !important;
		    top: 100px !important;
		    margin-left: -175px !important; 
		    /*margin-top: -175px !important;*/
		} */
		/* Override jQuery UI theme's padding on buttons: */
		.ui-button-text-only .ui-button-text {
			padding: 0.2em 0.5em;
		}
		input[type='radio'] {
	    	text-align: center;
			/*float: left;*/
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
		
		/**Call when document is ready**/
		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");

			/**Reset Radio Buttons**/
			$('input:radio[name="studentApplication.radioOfferQual1"]').removeAttr('checked');
			$('input:radio[name="studentApplication.radioOfferQual2"]').removeAttr('checked');

			var isQual1Selected = false;
			var isQual2Selected = false;
			$('input[name="studentApplication.radioOfferQual1"]:radio').change(function(){
				var radVal1 = $(this).attr("value");
				if (radVal1 == 'Y') {
					$("input[name='studentApplication.radioOfferQual1']").val(['Y']); //.prop('checked', true);
			    	isQual1Selected = true;
				}else if (radVal1 == 'N') {
					$("input[name='studentApplication.radioOfferQual1']").val(['N']); //.prop('checked', true);
					isQual1Selected = true;
				}
			});
			$('input[name="studentApplication.radioOfferQual2"]:radio').change(function(){
				var radVal2 = $(this).attr("value");
				if (radVal2 == 'Y') {
					$("input[name='studentApplication.radioOfferQual2']").val(['Y']); //.prop('checked', true);
			    	isQual2Selected = true;
				}else if (radVal2 == 'N') {
					$("input[name='studentApplication.radioOfferQual2']").val(['N']); //.prop('checked', true);
					isQual2Selected = true;
				}
			});
			
			//Show correct parts of page depending on status of qualifications
			var qualStatusCode1 = $('#qualStatusCode1').val();
			var qualStatusCode2 = $('#qualStatusCode2').val();
			
			if ((qualStatusCode1 == null || qualStatusCode1 == "" || qualStatusCode1 == "undefined" || qualStatusCode1 == "false"|| qualStatusCode1 == false )  
				&& (qualStatusCode2 == null || qualStatusCode2 == "" || qualStatusCode2 == "undefined" || qualStatusCode2 == "false"|| qualStatusCode2 == false )){
				$(".noOfferQual").show();
				$(".noOfferButtons").show();
				$(".doOfferQual").hide();
				$(".doOfferButtons").hide();
			}else{
				$(".noOfferQual").hide();
				$(".noOfferButtons").hide();
				$(".doOfferQual").show();
				$(".doOfferButtons").show();
			}
			
		});


	 	function validateSelect() {
			//Check Radio selected value
			var radioSelected1 = $("input:radio[name='studentApplication.radioOfferQual1']:checked").val();
			var radioSelected2 = $("input:radio[name='studentApplication.radioOfferQual2']:checked").val();
			var qualCode1 = $("#qualCode1").val();
			var qualCode2 = $("#qualCode2").val();
			
			var isSet1 = false;
			var isSet2 = false;
			if (radioSelected1 == "Y" || radioSelected1 == "N"){
				isSet1 = true;
			}
			if (radioSelected2 == "Y" || radioSelected2 == "N"){
				isSet2 = true;
			}
			if (isSet1 === false && isSet2 === false){
				showError("Error", "Please select an offer of admission to accept, or decline or Cancel to quit");
				return false;
			}else{
				if (radioSelected1 == "Y"){
					var retVal1a = confirm("You have accepted the offer for "+qualCode1+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
					if( retVal1a != true ){
						return false;
					}
				}
				if (radioSelected1 == "N"){
					var retVal1b = confirm("You have declined the offer for "+qualCode1+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
					if( retVal1b != true ){
						return false;
				   	}
				}
				if (radioSelected2 == "Y"){
					var retVal2a = confirm("You have accepted the offer for "+qualCode2+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
					if( retVal2a != true ){
						return false;
					}
				}
				if (radioSelected2 == "N"){
					var retVal2b = confirm("You have declined the offer for "+qualCode2+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
					if( retVal2b != true ){
						return false;
				   	}
				}
			}
		}
	
			
	   	function showError(errorTitle, errorText) {
	   	    // show the actual error modal
	   	    $.unblockUI();		    
	   	    $('#dialogContent').html(errorText);
	   		$('#dialogHolder').dialog({
	   			autoOpen: true,
	   		  	title: errorTitle,
	   		  	modal: true,
	   		 	width: "auto",
	   		  	buttons: {
	   		    	"Ok": function() {
	   		   	   		$(this).dialog("close");
	   		    	}
	   		  	}
	   		});
	   	}
	   	
	   	
		/**Cancel button**/
		function cancel(){
			$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Redirecting...</strong>" });
			var url = "http://applications.unisa.ac.za/index.html";
	  	   	window.top.location.href=url;
	  	   	return false;
		}
	   	
	</script>
</head>
<body>
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="applyOffer"/>
	
	<input type='hidden' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />
	<input type="hidden" id="qualCode1" name="qualCode1" value="<bean:write name="studentRegistrationForm" property="offerQual1"/>" />
	<input type="hidden" id="qualCode2" name="qualCode2" value="<bean:write name="studentRegistrationForm" property="offerQual2"/>" />
	<input type="hidden" id="qualStatusCode1" name="qualStatusCode1" value="<bean:write name='studentRegistrationForm' property='qualStatusCode1' />" />
	<input type="hidden" id="qualStatusCode2" name="qualStatusCode2" value="<bean:write name='studentRegistrationForm' property='qualStatusCode2' />" />

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	
	<br/>
	<div class="container full-width">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.offer.acceptance"/>&nbsp;<bean:write name="studentRegistrationForm" property="student.academicYear"/>&nbsp;<fmt:message key="page.offer.heading.extra"/></h3>
				</div>
				<div class="panel-body">
					<strong><fmt:message key="page.offer.info"/></strong><br/>
					<i><fmt:message key="page.offer.info.note"/></i>
					<br/><br/>
					<div class="noOfferQual" align="center" style="display: none;">
						<h5><font color="red"><fmt:message key="page.offer.none"/></font></h5>
					</div>
					<div class="doOfferQual" style="display: none;">
						<h5><strong><fmt:message key="page.offer.selection"/></strong></h5>
						<table style="width=100%">
							<tr>
								<td><b>Qualification&nbsp;</b></td>
								<td><b>Specializations&nbsp;</b></td>
								<td><b>Status&nbsp;</b></td>
								<td width="5%" align="center"><b><fmt:message key="page.accept"/></b></td>
								<td width="5%" align="center"><b><fmt:message key="page.decline"/></b></td>
							</tr>
							<logic:notEqual name="studentRegistrationForm" property="offerQual1" value="">
								<logic:notEqual name="studentRegistrationForm" property="offerQual1" value="Not Found">
									<tr>
										<td colspan="4"><h5>Primary Qualification&nbsp;</h5></td>
									</tr><tr>
										<td><bean:write name="studentRegistrationForm" property="offerQual1"/></td>
										<logic:notEqual name="studentRegistrationForm" property="offerSpec1" value="">
											<td><bean:write name="studentRegistrationForm" property="offerSpec1"/></td>
										</logic:notEqual>
										<logic:equal name="studentRegistrationForm" property="offerSpec1" value="">
											<td>N/A - Not Applicable</td>
										</logic:equal>
										<td>
											<bean:write name="studentRegistrationForm" property="qualStatus1"/>
										</td>
										<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioOfferQual1" value="Y"/></td>
										<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioOfferQual1" value="N"/></td>
									</tr>	
								</logic:notEqual>
							</logic:notEqual>
							<logic:notEqual name="studentRegistrationForm" property="offerQual2" value="">
								<logic:notEqual name="studentRegistrationForm" property="offerQual2" value="Not Found">
									<tr>
										<td colspan="4">&nbsp;</td>
									</tr><tr>
										<td colspan="4"><h5>Alternative Qualification&nbsp;</h5></td>
									</tr><tr>
										<td><bean:write name="studentRegistrationForm" property="offerQual2"/>
										<logic:notEqual name="studentRegistrationForm" property="offerSpec2" value="">
											<td><bean:write name="studentRegistrationForm" property="offerSpec2"/></td>
										</logic:notEqual>
										<logic:equal name="studentRegistrationForm" property="offerSpec2" value="">
											<td>N/A - Not Applicable</td>
										</logic:equal>
										<td>
											<bean:write name="studentRegistrationForm" property="qualStatus2"/>
										</td>
										<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioOfferQual2" value="Y"/></td>
										<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioOfferQual2" value="N"/></td>
									</tr>
								</logic:notEqual>
							</logic:notEqual>
						</table>
					</div>
					<div style="padding: 16px">
							<div style="float: left; padding-right: 8px;">
								<div class="noOfferButtons" style="display: none;">
									<html:submit property="act" onclick="cancel();"><fmt:message key="button.cancel" /></html:submit>
								</div>
								<div class="doOfferButtons" style="display: none;">
									<html:submit property="act" onclick="cancel();"><fmt:message key="button.cancel" /></html:submit>&nbsp;
									<html:submit property="act" onclick="return validateSelect();"><fmt:message key="button.next" /></html:submit>
								</div>
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
		
</html:form>
</body>
</sakai:html>