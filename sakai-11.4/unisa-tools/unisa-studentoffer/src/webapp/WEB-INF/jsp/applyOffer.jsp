<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentoffer.forms.StudentOfferForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentoffer.ApplicationResources"/>

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
	
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script> 
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
			$('input:radio[name="studentApplication.radioOfferAccept"]').removeAttr('checked');	
			
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


// 	 	function validateSelectOld() {
// 			//Check Radio selected value
// 			var radioSelected1 = $("input:radio[name='studentApplication.radioOfferQual1']:checked").val();
// 			var radioSelected2 = $("input:radio[name='studentApplication.radioOfferQual2']:checked").val();
// 			var qualCode1 = $("#qualCode1").val();
// 			var qualCode2 = $("#qualCode2").val();
			
// 			var isSet1 = false;
// 			var isSet2 = false;
// 			if (radioSelected1 == "Y" || radioSelected1 == "N"){
// 				isSet1 = true;
// 			}
// 			if (radioSelected2 == "Y" || radioSelected2 == "N"){
// 				isSet2 = true;
// 			}
// 			if (isSet1 === false && isSet2 === false){
// 				showError("Error", "Please select an offer of admission to accept, or decline or Cancel to quit");
// 				return false;
// 			}else{
// 				if (radioSelected1 == "Y"){
// 					showModal("Confirmation", "You have accepted the offer for "+qualCode1+". Click 'OK' to confirm or on 'Cancel' to change your selection.")
// 				}
// 				if (radioSelected1 == "N"){
// 					showModal("Confirmation", "You have declined the offer for "+qualCode1+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
// 				}
// 				if (radioSelected2 == "Y"){
// 					showModal("Confirmation", "You have accepted the offer for "+qualCode2+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
// 				}
// 				if (radioSelected2 == "N"){
// 					showModal("Confirmation", "You have declined the offer for "+qualCode2+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
// 				}
// 			}
// 		}	 	
	 	
	 	function validateSelect() {
			//Check Radio selected value
			var radioAccept = $("input:radio[name='studentApplication.radioOfferAccept']:checked").val();	
			var qualCode1 = $("#qualCode1").val();
			var qualCode2 = $("#qualCode2").val();
			var pendingApp = $("#pendingApp").val();	
			
			if (radioAccept == "1" || radioAccept =="2"){
				if (pendingApp == "true"){
					showPending("Confirmation", "One application is still being processed. Are you sure you want to accept the offer?");
				}
				if (radioAccept == "1"){
					showModal("Confirmation", "You have accepted the offer for "+qualCode1+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
				}
				if (radioAccept == "2"){
					showModal("Confirmation", "You have accepted the offer for "+qualCode2+". Click 'OK' to confirm or on 'Cancel' to change your selection.");
				}	
			}else{
				showError("Error", "Please select an offer of admission to accept, or decline or Cancel to quit");
				return false;
			}
		}
	 	
	
		function doSubmit(button){
			if (button === "Continue"){
				document.studentOfferForm.action='studentOffer.do?act=applyOffer';
			}else if (button === "Back"){
				document.studentOfferForm.action='studentOffer.do?act=back';
			}else if (button === "TrackStatus"){
				document.studentOfferForm.action='studentOffer.do?act=trackStatus';
			}else if (button === "Cancel"){
				window.top.location.href = "http://applications.unisa.ac.za/index.html";
	  		  	return false;
			}
			$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Submitting...</strong>" });
			document.studentOfferForm.submit();
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
		
		function showPending(errorTitle, errorText) {
	   	    // show the actual error modal
	   	    $.unblockUI();		    
	   	    $('#dialogContent').html(errorText);
	   		$('#dialogHolder').dialog({
	   			autoOpen: true,
	   		  	title: errorTitle,
	   		  	modal: true,
	   		 	width: "auto",
	   		  	buttons: {
	   		    	"Yes": function() {
	   		   	  		doSubmit("Continue");
	   		   	  	},
	   		   	  	"No": function(){
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
	   		 	width: "auto",
	   		 	position:['middle',20],
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
<html:form action="/studentOffer">
	<html:hidden property="page" value="applyOffer"/>
	
	<input type='hidden' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />
	<input type='hidden' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />
	<input type="hidden" id="qualCode1" name="qualCode1" value="<bean:write name="studentOfferForm" property="offerQual1"/>" />
	<input type="hidden" id="qualCode2" name="qualCode2" value="<bean:write name="studentOfferForm" property="offerQual2"/>" />
	<input type="hidden" id="qualStatusCode1" name="qualStatusCode1" value="<bean:write name='studentOfferForm' property='qualStatusCode1' />" />
	<input type="hidden" id="qualStatusCode2" name="qualStatusCode2" value="<bean:write name='studentOfferForm' property='qualStatusCode2' />" />
	<input type="hidden" id="pendingApp" name="pendingApp" value="<bean:write name='studentOfferForm' property='pendingApp' />" />


	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	
	<br/>
	<div class="container full-width">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.offer.acceptance"/>&nbsp;<bean:write name="studentOfferForm" property="student.academicYear"/>&nbsp;<fmt:message key="page.offer.heading.extra"/></h3>
				</div>
				<div class="panel-body">
					<strong><fmt:message key="page.offer.info"/></strong><br/>
					<i><fmt:message key="page.offer.info.note"/></i><br/>
					<table style="width=90%">
						<tr><td><i><fmt:message key="page.offer.info.note.a"/></i></td></tr>
						<tr><td><i><fmt:message key="page.offer.info.note.b"/></i></td></tr>
						<tr><td><i><fmt:message key="page.offer.info.note.c"/></i></td></tr>
					</table>
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
								<!-- <td width="5%" align="center"><b><fmt:message key="page.decline"/></b></td> -->
							</tr>
							<logic:notEqual name="studentOfferForm" property="offerQual1" value="">
								<logic:notEqual name="studentOfferForm" property="offerQual1" value="Not Found">
									<tr>
										<td colspan="3"><h5>Primary Qualification&nbsp;</h5></td>
									</tr><tr>
										<td><bean:write name="studentOfferForm" property="offerQual1"/></td>
										<logic:notEqual name="studentOfferForm" property="offerSpec1" value="">
											<td><bean:write name="studentOfferForm" property="offerSpec1"/></td>
										</logic:notEqual>
										<logic:equal name="studentOfferForm" property="offerSpec1" value="">
											<td>N/A - Not Applicable</td>
										</logic:equal>
										<td>
											<bean:write name="studentOfferForm" property="qualStatus1"/>
										</td>
										<td align="center"><html:radio name="studentOfferForm" property="studentApplication.radioOfferAccept" value="1"/></td>
										<!-- <td align="center"><html:radio name="studentOfferForm" property="studentApplication.radioOfferQual1" value="N"/></td> -->
									</tr>	
								</logic:notEqual>
							</logic:notEqual>
							<logic:notEqual name="studentOfferForm" property="offerQual2" value="">
								<logic:notEqual name="studentOfferForm" property="offerQual2" value="Not Found">
									<tr>
										<td colspan="3">&nbsp;</td>
									</tr><tr>
										<td colspan="3"><h5>Alternative Qualification&nbsp;</h5></td>
									</tr><tr>
										<td><bean:write name="studentOfferForm" property="offerQual2"/>
										<logic:notEqual name="studentOfferForm" property="offerSpec2" value="">
											<td><bean:write name="studentOfferForm" property="offerSpec2"/></td>
										</logic:notEqual>
										<logic:equal name="studentOfferForm" property="offerSpec2" value="">
											<td>N/A - Not Applicable</td>
										</logic:equal>
										<td>
											<bean:write name="studentOfferForm" property="qualStatus2"/>
										</td>
										<td align="center"><html:radio name="studentOfferForm" property="studentApplication.radioOfferAccept" value="2"/></td>
										<!-- <td align="center"><html:radio name="studentOfferForm" property="studentApplication.radioOfferQual2" value="N"/></td> -->
									</tr>
								</logic:notEqual>
							</logic:notEqual>
						</table>
					</div>
				</div>
			</div>
			<div class="panel-footer clearfix">
				<div style="float: left; padding-right: 8px;">
					<div class="noOfferButtons" style="display: none;">
						<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');"><fmt:message key="button.cancel" /></button>
					</div>
					<div class="doOfferButtons" style="display: none;">
						<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');"><fmt:message key="button.cancel" /></button>
						<button class="btn btn-default" type="button" onclick="validateSelect();"><fmt:message key="button.next" /></button>
						<button class="btn btn-default" type="button" onclick="doSubmit('TrackStatus');"><fmt:message key="button.trackStatus" /></button>												
					</div>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentOfferForm" property="version"/></div>
		
</html:form>
</body>
</sakai:html>