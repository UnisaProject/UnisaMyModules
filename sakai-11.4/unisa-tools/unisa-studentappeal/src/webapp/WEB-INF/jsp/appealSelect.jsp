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

<%
 StudentAppealForm studentAppealForm = (StudentAppealForm)session.getAttribute("StudentAppealForm");
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
			
			//Set Continue button disabled.
			$("#Continue").attr("disabled", "disabled");
			
			//Show correct parts of page depending on appeal of qualifications
			var qualStatusCode1 = $('#qualStatusCode1').val();
			var qualStatusCode2 = $('#qualStatusCode2').val();
			
			if ((qualStatusCode1 == null || qualStatusCode1 == "" || qualStatusCode1 == "undefined" || qualStatusCode1 == "false"|| qualStatusCode1 == false )  
				&& (qualStatusCode2 == null || qualStatusCode2 == "" || qualStatusCode2 == "undefined" || qualStatusCode2 == "false"|| qualStatusCode2 == false )){
				$(".noAppealQual").show();
				$(".doAppealQual").hide();
			}else{
				$(".noAppealQual").hide();
				$(".doAppealQual").show();
			}
			
			var isAppeal = false;
			if (qualStatusCode1 === "AX" || qualStatusCode1 == "TNN"){
				$(".doAppealCheckBox1").show();
				$(".noAppealCheckBox1").hide();
				isAppeal = true;
			}else{
				$(".doAppealCheckBox1").hide();
				$(".noAppealCheckBox1").show();
			}
			if (qualStatusCode2 === "AX" || qualStatusCode2 == "TNN"){
				$(".doAppealCheckBox2").show();
				$(".noAppealCheckBox2").hide();
				isAppeal = true;
			}else{
				$(".doAppealCheckBox2").hide();
				$(".noAppealCheckBox2").show();
			}

			if (isAppeal || isAppeal == true){
				$(".doAppeal").show();
				$(".noAppeal").hide();
				$(".doAppealOther").show();
				$(".doAppealButtons").show();
				$(".noAppealButtons").hide();
			}else{
				$(".doAppeal").hide();
				$(".noAppeal").show();
				$(".doAppealOther").hide();
				$(".doAppealButtons").hide();
				$(".noAppealButtons").show();
			}
			
			var maxLength = 300;
			$('#typeText').keyup(function() {
				var length = $(this).val().length;
				var length = maxLength-length;
				$('#chars').text(length);
			});
		});
		
		function upload(){
			$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>File upload in progress. Please wait...</strong>" });
			doSubmit("Upload");
		}

		function validateSelect(){
			var check1 = $("input[name='appealSelect1']").prop('checked');
			var check2 = $("input[name='appealSelect2']").prop('checked');
			var typeText = $("#typeText").val();
			
			if (check1 != true && check2 != true){
				showError("Warning","Please select at least one Qualification decision to appeal");
				return false;
			}
			if (typeText == null || $.trim(typeText) == "" || $.trim(typeText) == "undefined"){
				showError("Warning","Please enter a meanigful reason or motivation for your appeal");
				return false;
			}
			var onToday = $("#onToday").val();
			doSubmit("Continue");
		}
		
		function doSubmit(input){
			if (input === "Upload"){
				document.studentAppealForm.action='studentAppeal.do?act=appealUpload';
			}else if (input === "Continue"){
				document.studentAppealForm.action='studentAppeal.do?act=appealSelect';
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
	   		 	position:['middle',100],
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
    		  position: { my: 'top', at: 'top+150' },
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

  <noscript><div id="portal_js_warn">This application requires JavaScript. Please enable JavaScript in your Browser.</div></noscript>

<!-- Form -->
<html:form action="/studentAppeal" enctype="multipart/form-data" method="POST" target="_self">

	<html:hidden property="page" value="appealSelect"/>
	
	<input type='hidden' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />
	<input type="hidden" id="docMsg" name="docMsg" value="<bean:write name='studentAppealForm' property='webUploadMsg' />" />
	<input type="hidden" id="qualStatusCode1" name="qualStatusCode1" value="<bean:write name='studentAppealForm' property='qualStatusCode1' />" />
	<input type="hidden" id="qualStatusCode2" name="qualStatusCode2" value="<bean:write name='studentAppealForm' property='qualStatusCode2' />" />
	<input type="hidden" id="qualText" name="qualText" value="<bean:write name='studentAppealForm' property='appealText' />" />

	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<br/>
	<div class="container full-width">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.appeal.heading"/>&nbsp;<bean:write name="studentAppealForm" property="student.academicYear"/>&nbsp;<fmt:message key="page.appeal.heading.extra"/></h3>
				</div>
				<div class="panel-body">
					<div class="noAppealQual" align="center">
						<h1><font color="red"><fmt:message key="page.appeal.none"/></font></h1>
					</div>
					<div class="doAppealQual" style="display: none;">
						<div class="noAppeal" style="display: none;" align="center">
							<sakai:group_heading><font color="red"><fmt:message key="page.appeal.none"/></font></sakai:group_heading>
						</div>
						<div class="doAppeal" style="display: none;">
							<sakai:group_heading><fmt:message key="page.appeal.info"/></sakai:group_heading>
						</div>
						<sakai:group_table>
							<tr>
								<td style="width:40%"><b>Qualification&nbsp;</b></td>
								<td style="width:30%"><b>Specializations&nbsp;</b></td>
								<td style="width:30%"><b>Appeal&nbsp;</b></td>
							</tr>
							<logic:notEqual name="studentAppealForm" property="selQualCode1" value="">
								<logic:notEqual name="studentAppealForm" property="selQualCode1" value="Not Found">
									<tr>
										<td>
											<div class="doAppealCheckBox1" style="display: none;">
												<html:checkbox name="studentAppealForm" property="appealSelect1" value="Y">
													&nbsp;<bean:write name="studentAppealForm" property="selQualCode1"/></html:checkbox>
											</div>
											<div class="noAppealCheckBox1" style="display: none;">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="studentAppealForm" property="selQualCode1"/>
											</div>
										</td>
										<logic:notEqual name="studentAppealForm" property="selSpecCode1" value="">
											<td><bean:write name="studentAppealForm" property="selSpecCode1"/></td>
										</logic:notEqual>
										<logic:equal name="studentAppealForm" property="selSpecCode1" value="">
											<td>N/A - Not Applicable</td>
										</logic:equal>
										<td>
											<bean:write name="studentAppealForm" property="qualStatus1"/>
											<logic:equal name="studentAppealForm" property="qualStatusCode1" value="AX">
												<logic:notEqual name="studentAppealForm" property="qualStatus1Reason" value="">
													Reason: <bean:write name="studentAppealForm" property="qualStatus1Reason"/>
												</logic:notEqual>
											</logic:equal>
										</td>
									</tr>	
								</logic:notEqual>
							</logic:notEqual>
							<logic:notEqual name="studentAppealForm" property="selQualCode2" value="">
								<logic:notEqual name="studentAppealForm" property="selQualCode2" value="Not Found">
									<tr>
										<td>
											<div class="doAppealCheckBox2" style="display: none;">
												<html:checkbox name="studentAppealForm" property="appealSelect2" value="Y">
													&nbsp;<bean:write name="studentAppealForm" property="selQualCode2"/></html:checkbox>
											</div>
											<div class="noAppealCheckBox2" style="display: none;">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="studentAppealForm" property="selQualCode2"/>
											</div>
										</td>
										<logic:notEqual name="studentAppealForm" property="selSpecCode2" value="">
											<td><bean:write name="studentAppealForm" property="selSpecCode2"/></td>
										</logic:notEqual>
										<logic:equal name="studentAppealForm" property="selSpecCode2" value="">
											<td>N/A - Not Applicable</td>
										</logic:equal>
										<td>
											<bean:write name="studentAppealForm" property="qualStatus2"/>
											<logic:equal name="studentAppealForm" property="qualStatusCode2" value="AX">
												<logic:notEqual name="studentAppealForm" property="qualStatus2Reason" value="">
													Reason: <bean:write name="studentAppealForm" property="qualStatus2Reason"/>
												</logic:notEqual>
											</logic:equal>
										</td>
									</tr>
								</logic:notEqual>
							</logic:notEqual>
						</sakai:group_table>
						<div class="doAppealOther" style="display: none;">
							<sakai:group_table>
								<tr>
									<td colspan="3"><fmt:message key="page.appeal.reason"/></td>
								</tr><tr>
									<td colspan="3">
										<textarea id="typeText" name="typeText" rows="2" maxlength="250" ><bean:write name="studentAppealForm" property="appealText"/></textarea>
										<br>
										<font size="1"><span id="chars">300</span> Characters remaining</font>
									</td>
								</tr><tr>
									<td colspan="3">
										<strong><fmt:message key="page.appeal.upload.filespec"/>:</strong>&nbsp;<br/>
										<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
											<tr>
												<td class="small">
													<ul>
														<li><fmt:message key="page.appeal.upload.filespecInfo1"/></li>
														<li><fmt:message key="page.appeal.upload.filespecInfo2"/></li>
													</ul>
												</td>
												<td class="small">
													<ul>
														<li><fmt:message key="page.appeal.upload.filespecInfo3"/></li>
														<li><fmt:message key="page.appeal.upload.filespecInfo4"/></li>
													</ul>
												</td>
											</tr>
										</table>
									</td>
								</tr><tr>
									<td colspan="3">
										<table border="1" cellpadding="1" cellspacing="1" style="width:100%">
								   			<tr>
												<td style="width:30%; height:30px">&nbsp;<strong><fmt:message key="page.studentnr.upload.type"/></strong></td>
												<td style="width:35%; height:30px">&nbsp;<strong><fmt:message key="page.studentnr.upload.browse"/></strong></td>
												<td style="width:35%; height:30px">&nbsp;<strong><fmt:message key="page.studentnr.upload.done"/></strong></td>
											</tr>
											<tr>
										   		<td style="width:30%">
										   			<html:select name="studentAppealForm" property="fileType">
									 					<html:options collection="filelist" property="value" labelProperty="label"/>
													</html:select>
										   		</td>
										   		<td style="width:35%">  		 
										   			<html:file name="studentAppealForm" property="file" maxlength="75" size="75px">&nbsp;</html:file>
										   		</td>
										   		<td style="width:35%">
										   			<div>
										   				<logic:notEmpty name="studentAppealForm" property="appealSourceFiles">
											   				<logic:iterate name="studentAppealForm" property="appealSourceFiles" id="listMsgId">
																	<font color="blue"><bean:write name="listMsgId"/></font><br/>
															</logic:iterate>
														</logic:notEmpty>
										   			</div>
										   		</td> 		  
								   		    </tr>
								   		</table>
									</td>
								</tr>
							</sakai:group_table>
						</div>
					</div>
				</div>
				<div class="panel-footer clearfix">
				<div class="noAppealButtons" style="display: none;">
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');"><fmt:message key="button.quit"/></button>
				</div>
				<div class="doAppealButtons" style="display: none;">
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');">Cancel</button>
					<button class="btn btn-default" type="button" onclick="return upload();"><fmt:message key="button.Upload" /></button>					
					<button class="btn btn-default" type="button" onclick="validateSelect();"><fmt:message key="button.next" /></button>
				</div>
			</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentAppealForm" property="version"/></div>
		
</html:form>
</body>
</sakai:html>