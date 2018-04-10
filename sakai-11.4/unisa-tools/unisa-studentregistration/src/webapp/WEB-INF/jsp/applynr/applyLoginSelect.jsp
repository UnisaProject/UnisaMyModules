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
		label:hover {
		    cursor:pointer;
		}
		label.inline {
			font-weight: normal;
		}
		table { 
		    border-spacing: 2px;
		    border-collapse: separate;
		}
		td { 
		    padding: 2px;
		}
	</style>
	
    <script type="text/javascript">  
      
		$(document).ready(function() {
			
			radioInit();
			
			//Reset all Radio Buttons (Twice...)
			$('input:radio[name="loginSelectMain"]').removeAttr('checked');
			$('input:radio[name="loginSelectYesNo"]').removeAttr('checked');
			$("input:radio[name='loginSelectMain']").each(function(i) {
			       this.checked = false;
			});
			$("input:radio[name='loginSelectYesNo']").each(function(i) {
			       this.checked = false;
			});
	   	
			$('form,input,select,textarea').attr("autocomplete", "off");
			
			var showNewUD	= false;
			var showRetUD	= false;
			var showNewHON	= false;
			var showRetHON	= false;
			var showSLP		= false;
			var showMD		= false;
			var showDOC		= false;
			var showPAY		= false;
			var showSTAT	= false;
			var showAPL		= false;
			var showOFFER	= false;
			var showOFFICE	= false;
			
			var hideQualSelect	= false;
			var hideDocPay		= false;
			var hideSTO			= false;
			
			if ($("#WAPU").val() == "true"){
				showNewUD = true;
				//alert("check - showNewUD: " + showNewUD);
			}
			if ($("#WAPRU").val() == "true"){
				showRetUD = true;
				//alert("check - showRetUD: " + showRetUD);
			}
			if ($("#WAPH").val() == "true"){
				showNewHON = true;
				//alert("check - showNewHON: " + showNewHON);
			}
			if ($("#WAPRH").val() == "true"){
				showRetHON = true;
				//alert("check - showRetHON: " + showRetHON);
			}
			if ($("#WAPS").val() == "true"){
				showSLP = true;
				//alert("check - showSLP: " + showSLP);
			}
			if ($("#WAPM").val() == "true" || $("#WAPD").val() == "true"){
				showMD = true;
				//alert("check - showMD: " + showMD);
			}
			if ($("#WAPDOC").val() == "true"){
				showDOC = true;
				//alert("check - showDOC: " + showDOC);
			}
			if ($("#WAPPAY").val() == "true"){
				showPAY = true;
				//alert("check - showPAY: " + showPAY);
			}
			if ($("#WAPSTAT").val() == "true"){
				showSTAT = true;
				//alert("check - showSTAT: " + showSTAT);
			}
			if ($("#WAPAPL").val() == "true"){
				showAPL = true;
				//alert("check - showAPL: " + showAPL);
			}
			if ($("#WAPOFFER").val() == "true"){
				showOFFER = true;
				//alert("check - showOFFER: " + showOFFER);
			}
			if ($("#WAPOFFICE").val() == "true"){
				showOFFICE = true;
				//alert("check - showOFFICE: " + showOFFICE);
			}

			if (showNewUD === true || showRetUD === true){ /*All Undergrad Open*/
				$('#doMainUD').show();
				$('#doMainUDNo').hide();
				$('#QualSelect').show();
			}else if (showNewUD === false && showRetUD === false){
				$('#doMainUD').hide();
				$('#doMainUDNo').show();
			}
			if (showNewHON === true || showRetHON === true){ /*All Honours Open*/
				$('#doMainHON').show();
				$('#doMainHONNo').hide();
				$('#QualSelect').show();
			}else if (showNewHON === false && showRetHON === false){
				$('#doMainHON').hide();
				$('#doMainHONNo').show();
			}
			if (showSLP === true){ /*SLP Open*/
				$('#doMainSLP').show();
				$('#doMainSLPNo').hide();
				$('#QualSelect').show();
			}else if (showSLP === false){
				$('#doMainSLP').hide();
				$('#doMainSLPNo').show();
			}
			if (showMD === true){
				$('#doMainMD').show();
				$('#doMainMDNo').hide();
				$('#QualSelect').show();
			}else {
				$('#doMainMD').hide();
				$('#doMainMDNo').show();
			}
			if (showNewUD === false && showRetUD === false && showNewHON === false && showRetHON === false && showSLP === false && showMD === false){
				$('#QualSelect').hide();
				hideQualSelect = true;
			}
			
			if (showDOC === true){
				$('#doMainDOC').show();
				$('#doMainDOCNo').hide();
				$('#DocPay').show();
			}else {
				$('#doMainDOC').hide();
				$('#doMainDOCNo').show();
			}
			if (showPAY === true){
				$('#doMainPAY').show();
				$('#doMainPAYNo').hide();
				$('#DocPay').show();
			}else {
				$('#doMainPAY').hide();
				$('#doMainPAYNo').show();
			}
			if (showDOC === false && showPAY === false){
				$('#DocPay').hide();
				hideDocPay	= true;
			}
			
			if (showSTAT === true){
				$('#showSTAT').show();
				$('#showSTATNo').hide();
				$('#STO').show();
			}else {
				$('#showSTAT').hide();
				$('#showSTATNo').show();
			}
			if (showAPL === true){
				$('#showAPL').show();
				$('#showAPLNo').hide();
				$('#STO').show();
			}else {
				$('#showAPL').hide();
				$('#showAPLNo').show();
			}
			if (showOFFER === true){
				$('#showOFFER').show();
				$('#showOFFERNo').hide();
				$('#STO').show();
			}else {
				$('#showOFFER').hide();
				$('#showOFFERNo').show();
			}
			if (showSTAT === false && showAPL === false && showOFFER === false){
				$('#STO').hide();
				hideSTO = true;
			}
			
			if (hideQualSelect === true && hideDocPay === true && hideSTO === true){
				$('#hideAllClosed').show();
			}else{
				$('#hideAllClosed').hide();
			}
			
			if (showOFFICE === true){
				$('#Office').show();
			}else {
				$('#Office').hide();
			}
			
			if (showNewUD === true || showRetUD === true || showNewHON === true || showRetHON === true || showMD === true){
				//ToDo Close options
				var webLogMsg = $("#webLogMsg").val();
				if(webLogMsg != null && webLogMsg != "" && webLogMsg != " " && webLogMsg != "undefined"){
					webLogMsg = webLogMsg.replace(/newLine /g,"\n");
					var retMsg = confirm(webLogMsg);
			    	if( retMsg == true ){
			    		/**alert("User wants to retry!");**/
			  	      	return false;
			  	   	}else{
			  	      	/**alert("User does not want to retry!");**/
			  	      	window.top.location.href = "http://applications.unisa.ac.za/index.html";
			  		  	return false;
			  	   	}
				}
			}
		});
		
	function mainCheck(){
		var mainSelect = $('input:radio[name="loginSelectMain"]:checked').val();
		if (mainSelect === "DOC"){
			var url = "/unisa-studentupload/default.do";
		    window.location.href = url;
		    return false;
		}else if (mainSelect === "STATUS"){
			var url = "/unisa-studentstatus/default.do";
		    window.location.href = url;
		    return false;
		}else if (mainSelect === "APPEAL"){
			var url = "/unisa-studentappeal/default.do";
		    window.location.href = url;
		    return false;
		}else if (mainSelect === "OFFER"){
			var url = "/unisa-studentoffer/default.do";
		    window.location.href = url;
		    return false;
		}else{
			var url = "/unisa-studentregistration/applyForStudentNumber.do?act=stepLoginSelect&mainSelection="+mainSelect;
	    	window.location.href = url;
	    	return false;
		}
	}
	
	function doOffice(){
		document.studentRegistrationForm.action='applyForStudentNumber.do?act=loginAdmin';
		document.studentRegistrationForm.submit();
	}
	   
	function radioInit() {
		var checkMain = $("input:radio[name=loginSelectMain]");
       	for( var i=0; i<checkMain.length; i++ ){
       	   		checkMain[i].checked = false;
       	}
	}
	
	</script>	
</head>
<body>
 	<noscript>
	    <style type="text/css">
	        .container {display:none;}
	    </style>
	    <div class="noscriptmsg">
	    	Your browser does not support JavaScript or it is disabled. Please enable JavaScript in your Browser to continue.<br/>
	    	Here are the <a href="http://www.enable-javascript.com" target="_blank"> instructions how to enable JavaScript in your web browser</a>
	    </div>
	</noscript>

<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="applyLoginSelect"/>
	<input type="hidden" name="webOpenDate" id="webOpenDate" value="<bean:write name='studentRegistrationForm' property='student.webOpenDate' />"/>
	<input type="hidden" name="selectReset" id="selectReset" value="<bean:write name='studentRegistrationForm' property='selectReset'/>"/>
	<input type="hidden" name="webLogMsg" id="webLogMsg" value="<bean:write name='studentRegistrationForm' property='webLoginMsg'/>"/>
	
	<input type="hidden" name="WAPU" id="WAPU" value="<bean:write name='studentRegistrationForm' property='student.dateWAPU'/>"/>
	<input type="hidden" name="WAPRU" id="WAPRU" value="<bean:write name='studentRegistrationForm' property='student.dateWAPRU'/>"/>
	<input type="hidden" name="WAPH" id="WAPH" value="<bean:write name='studentRegistrationForm' property='student.dateWAPH'/>"/>
	<input type="hidden" name="WAPRH" id="WAPRH" value="<bean:write name='studentRegistrationForm' property='student.dateWAPRH'/>"/>
	<input type="hidden" name="WAPS" id="WAPS" value="<bean:write name='studentRegistrationForm' property='student.dateWAPS'/>"/>
	<input type="hidden" name="WAPM" id="WAPM" value="<bean:write name='studentRegistrationForm' property='student.dateWAPM'/>"/>
	<input type="hidden" name="WAPD" id="WAPD" value="<bean:write name='studentRegistrationForm' property='student.dateWAPD'/>"/>

	<input type="hidden" name="WAPADMU" id="WAPADMU" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMU'/>"/>
	<input type="hidden" name="WAPADMH" id="WAPADMH" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMH'/>"/>
	<input type="hidden" name="WAPADMM" id="WAPADMM" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMM'/>"/>
	<input type="hidden" name="WAPADMD" id="WAPADMD" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMD'/>"/>
	<input type="hidden" name="WAPADMNEW" id="WAPADMNEW" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMNEW'/>"/>
	<input type="hidden" name="WAPADMRET" id="WAPADMRET" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMRET'/>"/>

	<input type="hidden" name="WAPDOC" id="WAPDOC" value="<bean:write name='studentRegistrationForm' property='student.dateWAPDOC'/>"/>
	<input type="hidden" name="WAPPAY" id="WAPPAY" value="<bean:write name='studentRegistrationForm' property='student.dateWAPPAY'/>"/>
	<input type="hidden" name="WAPSTAT" id="WAPSTAT" value="<bean:write name='studentRegistrationForm' property='student.dateWAPSTAT'/>"/>
	<input type="hidden" name="WAPAPL" id="WAPAPL" value="<bean:write name='studentRegistrationForm' property='student.dateWAPAPL'/>"/>
	<input type="hidden" name="WAPOFFER" id="WAPOFFER" value="<bean:write name='studentRegistrationForm' property='student.dateWAPOFFER'/>"/>
	<input type="hidden" name="WAPOFFICE" id="WAPOFFICE" value="<bean:write name='studentRegistrationForm' property='student.dateWAPOFFICE'/>"/>
	
	<sakai:messages/>
	<sakai:messages message="true"/>

	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
    		<fmt:message key="page.apply.select.info1"/><br/>
    		<br style="height:5px;">
    	</div>
    	
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.apply.select.info3"/></h3>
				</div>
			</div>
		</div>
		<div id="hideAllClosed" class="col-md-12 col-sm-12 col-xs-12" style="display: none;">
			<div class="panel panel-default">
				<div class="light" align="center">
					<br/>
					<font color="#A90E13">The application period is closed.</font>
					<br/><br/>
				</div>
			</div>
		</div>						
		<div id="QualSelect" class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<fmt:message key="page.apply.select.apply"/><br/><br/>
					</div>
					<div class="col-xs-3">
						<div id="doMainUD" style="display: none;">
							<input type="radio" name="loginSelectMain" value="UD" id="UD" onclick="mainCheck();" />
							<label for="UD" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoUD1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoUD2"/>
							</div>
						</div>
						<div id="doMainUDNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoUD1"/></font><br/>
							<div class="small">
								<font color="#A90E13">The application period for this option is closed.</font>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
						<div id="doMainHON" style="display: none;">
							<input type="radio" name="loginSelectMain" value="HON" id="HON" onclick="mainCheck();" />
							<label for="HON" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoHON1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoHON2"/>
							</div>
						</div>
						<div id="doMainHONNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoHON1"/></font><br/>
							<div class="small">
								<font color="#A90E13">The application period for this option is closed.</font>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
						<div id="doMainSLP" style="display: none;">
							<input type="radio" name="loginSelectMain" value="SLP" id="SLP" onclick="mainCheck();" />
							<label for="SLP" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoSLP1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoSLP2"/>
							</div>
						</div>
						<div id="doMainSLPNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoSLP1"/></font><br/>
							<div class="small">
								<font color="#A90E13">The application period for this option is closed.</font>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
						<div id="doMainMD" style="display: none;">
							<input type="radio" name="loginSelectMain" value="MD" id="MD" onclick="mainCheck();" />
							<label for="MD" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoMD1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoMD2"/>
							</div>
						</div>
						<div id="doMainMDNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoMD1"/></font><br/>
							<div class="small">
								<font color="#A90E13">The application period for this option is closed.</font>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="DocPay" class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<fmt:message key="page.apply.select.docPay"/><br/><br/>
					<div class="col-xs-3">
						<div id="doMainDOC" style="display: none;">
							<input type="radio" name="loginSelectMain" value="DOC" id="DOC" onclick="mainCheck();" />
							<label for="DOC" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoDOC1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoDOC2"/>
							</div>
						</div>
						<div id="doMainDOCNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoDOC1"/></font><br/>
							<div class="small">
								<font color="#A90E13">This option is closed.</font>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
						<div id="doMainPAY" style="display: none;">
							<input type="radio" name="loginSelectMain" value="PAY" id="PAY" onclick="mainCheck();" />
							<label for="PAY" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoPAY1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoPAY2"/>
							</div>
						</div>
						<div id="doMainPAYNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoPAY1"/></font><br/>
							<div class="small">
								<font color="#A90E13">This option is closed.</font>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
					</div>
					<div class="col-xs-3">
					</div>
					
				</div>										
			</div>
		</div>
		<div id="STO" class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<fmt:message key="page.apply.select.sto"/><br/><br/>
					<div class="col-xs-3">
						<div id="showSTAT" style="display: none;">
							<input type="radio" name="loginSelectMain" value="STATUS" id="STATUS" onclick="mainCheck();" />
							<label for="STATUS" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoSTATUS1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoSTATUS2"/>
							</div>
						</div>
						<div id="showSTATNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoSTATUS1"/></font><br/>
							<div class="small">
								<font color="#A90E13">This option is closed.</font>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
						<div id="showAPL" style="display: none;">
							<input type="radio" name="loginSelectMain" value="APPEAL" id="APPEAL" onclick="mainCheck();" />
							<label for="APPEAL" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoAPPEAL1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoAPPEAL2"/>
							</div>
						</div>
						<div id="showAPLNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoAPPEAL1"/></font><br/>
							<div class="small">
								<font color="#A90E13">This option is closed.</font>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
						<div id="showOFFER" style="display: none;">
							<input type="radio" name="loginSelectMain" value="OFFER" id="OFFER" onclick="mainCheck();" />
							<label for="OFFER" class="inline"><span>&nbsp;<fmt:message key="page.apply.select.infoOFFER1"/></span></label><br/>
							<div class="small">
								<fmt:message key="page.apply.select.infoOFFER2"/>
							</div>
						</div>
						<div id="showOFFERNo" class="light" style="display: none;">
							<font color="#D3D3D3"><fmt:message key="page.apply.select.infoOFFER1"/></font><br/>
							<div class="small">
								<font color="#A90E13">This option is closed.</font>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
					</div>
				</div>										
			</div>
		</div>
		<br/>&nbsp;<br/>
		<div id="Office" class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.apply.select.administration"/></h3>
				</div>
				<div class="panel-body">
					<div  class="small">
						<fmt:message key="page.apply.select.office"/><br/>
					</div>
				</div>	
				<div class="panel-footer clearfix">
					<button class="btn btn-default btn-block" type="button" onclick="doOffice();"><fmt:message key="button.submitAdminLogin" /></button>
				</div> 									
			</div>
		</div>
	</div>
	
	<div id="SpaceMaker">
		<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
	</div>
	
	<div id="hiddenRadio" style="display: none;">
		<input type="radio" name="loginSelectMain" value="xyz" checked="checked" />
		<input type="radio" name="loginSelectYesNo" value="xyz" checked="checked" />
		<input type="radio" name="loginSelectAPS" value="xyz" checked="checked" />
	</div>

	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
  </body>
</sakai:html>