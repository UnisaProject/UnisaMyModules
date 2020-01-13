<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<%
 StudentRegistrationForm studentRegistrationForm = (StudentRegistrationForm)session.getAttribute("studentRegistrationForm");
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
		tr.noBorder td {
			border: 0;
		}
		tr.border td {
			border: 1px solid black;
		}
		input[type="file"] {
		  /*line-height: 10px;*/
		  line-height: 1ex;
		}
		.alnright { 
			text-align: right; 
		}
		
	</style>	
	
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) } 
	
	var needToConfirm = true;
	
	//Call when document is ready
	$(document).ready(function() {

		// Everything’s finished loaded code here…
		var docMsg = $("#docMsg").val();
		if(docMsg !== "" && docMsg !== "null" && docMsg !== null && docMsg !== NaN){
			docMsg = docMsg.replace(/newLine /g,"\n");
			showError("Info",docMsg);
		}		
		
		$(window).on('beforeunload', function(e){
			if(needToConfirm) {
				var message = "You have uploaded your documents but you still need to submit them. Click on the ‘Submit Documents and Continue’ button before exiting the screen";
				e.returnValue = message;
				return message;
			}
			return;
		});
		
		//Reload previously selected File Type
		var fileSelected = $("#fileSelect").val();
		if (fileSelected != null && fileSelected != "" && fileSelected != "undefined"){
			$("select[name='optionFileBean.doc.docCode']").val(fileSelected);
		}
				
		$("input[name='Upload']").click(function(){
			warnPageLeave = false;
			$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>File upload in progress. Please wait...</strong>" });
        }); 
		$("input[name='Continue']").click(function(){
			warnPageLeave = false;
        }); 
		$("input[name='Logout']").click(function(){
			warnPageLeave = false;
        }); 
		
	});
	
	function doLogout(){
		window.top.location.href = "http://applications.unisa.ac.za/index.html";
		return false;
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
  <noscript><div id="portal_js_warn">This application requires JavaScript. Please enable JavaScript in your Browser.</div></noscript>
<!-- Form -->

<html:form action="/upload.do?method=upload" enctype="multipart/form-data" method="POST" target="_self">
         
	<html:hidden property="page" value="step1b"/>
	<input type="hidden" id="docMsg" name="docMsg" value="<bean:write name='studentRegistrationForm' property='webUploadMsg' />" />
	<input type="hidden" id="fileSelect" name="fileSelect" value="<bean:write name='studentRegistrationForm' property='fileSelected' />" />
	<input type="hidden" id="optionFile" name="optionFile" value="<bean:write name='studentRegistrationForm' property='optionFileBean.file' />" />
	<input type="hidden" id="hiddenButtonUpload" name="hiddenButtonUpload" value="<bean:write name='studentRegistrationForm' property='hiddenButtonUpload' />" />
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<div class="container full-width">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.upload.heading"/></h3>
				</div>
				<div class="panel-body">
						<table border="0" cellpadding="1" cellspacing="1" style="width:100%">
							<tr class="noBorder">
								<td colspan="4"><strong><fmt:message key="page.studentnr.upload.filespec"/>:</strong>&nbsp;</td>
							</tr>
							<tr class="noBorder">
								<td colspan="2" class="small">&nbsp;&nbsp;<fmt:message key="page.studentnr.upload.filespecInfo1"/>&nbsp;</td>
								<td colspan="2" class="small">&nbsp;&nbsp;<fmt:message key="page.studentnr.upload.filespecInfo3"/>&nbsp;</td>
							</tr>
							<tr class="noBorder">
								<td colspan="2" class="small">&nbsp;&nbsp;<fmt:message key="page.studentnr.upload.filespecInfo2"/>&nbsp;</td>
								<td colspan="2" class="small">&nbsp;&nbsp;<fmt:message key="page.studentnr.upload.filespecInfo4"/>&nbsp;</td>
							</tr>
							<tr class="noBorder">
								<td colspan="4">&nbsp;</td>
							</tr>
							<logic:empty name="studentRegistrationForm" property="requiredDocs">
								<tr class="noBorder">
									<td colspan="4"><strong><fmt:message key="page.studentnr.upload.notrequired"/></strong>&nbsp;</td>
								</tr>
							</logic:empty>
							<logic:notEmpty name="studentRegistrationForm" property="requiredDocs">
								<tr class="noBorder">
									<td colspan="4"><strong><fmt:message key="page.studentnr.upload.required"/></strong><br/>
					 					<fmt:message key="page.studentnr.upload.indicator"/><br/><br/>
					 				</td>
					 			</tr>
								<tr class="border">
									<td style="width:15%">&nbsp;<strong><fmt:message key="page.studentnr.upload.completed"/>&nbsp;</strong></td>
									<td style="width:15%">&nbsp;<strong><fmt:message key="page.studentnr.upload.type"/></strong></td>
									<td style="width:30%">&nbsp;<strong><fmt:message key="page.studentnr.upload.browse"/></strong></td>
									<td style="width:40%">&nbsp;<strong><fmt:message key="page.studentnr.upload.done"/></strong></td>
								</tr>
								 <logic:iterate name="studentRegistrationForm" property="requiredDocs" id="requiredDoc" indexId="currentIndex">
									<tr class="border">
										<td align="center">
										 	<logic:notEmpty name="studentRegistrationForm" property='<%="fileBeans["+currentIndex+"].uploaded"%>'>
										 		<label style="color:green"><strong><font size="large">Y</font></strong></label>
										 	</logic:notEmpty>
											<logic:empty name="studentRegistrationForm" property='<%="fileBeans["+currentIndex+"].uploaded"%>'>
												<label style="color:red"><strong><font size="large">N</font></strong></label>
											</logic:empty>
										</td>
										<td>				 
						       				&nbsp;<bean:write   name="requiredDoc" property="docDescription"/>
						       		 		<html:hidden name="studentRegistrationForm" property='<%="fileBeans["+currentIndex+"].doc.docCode"%>'/>
						       		 	</td>
										<td>
											<span style="float:left;"><html:file name="studentRegistrationForm" property='<%="fileBeans["+currentIndex+"].file"%>' maxlength="75" size="75px"></html:file></span>
											<span style="float:right;"><html:submit  property='<%="UploadReq["+currentIndex+"]"%>' value="Upload" onclick="needToConfirm = false;"/></span>
					       		 		</td>
										<td>
					        				 <logic:notEmpty name="studentRegistrationForm" property='<%="fileBeans["+currentIndex+"].uploaded"%>'>
					        				 		 <logic:iterate name="studentRegistrationForm" property='<%="fileBeans["+currentIndex+"].uploaded"%>' id="doc" indexId="docIndex">
					        				 		 	<font color="blue">&nbsp;<bean:write name="doc" property="docName"/></font><br/>
					        				 		 </logic:iterate>
					        				 </logic:notEmpty>
					        			</td>
					        		</tr>
					   			</logic:iterate>
					   		</logic:notEmpty>
							<tr class="noBorder">
								<td colspan="4">&nbsp;</td>
							</tr>
							<tr class="noBorder">
								<td colspan="4"><strong><fmt:message key="page.studentnr.upload.optional"/></strong><br/></td>
							</tr>
				   			<tr class="border">
								<td colspan="2">&nbsp;<strong><fmt:message key="page.studentnr.upload.type"/></strong></td>
								<td>&nbsp;<strong><fmt:message key="page.studentnr.upload.browse"/></strong></td>
								<td>&nbsp;<strong><fmt:message key="page.studentnr.upload.done"/></strong></td>
							</tr>
							<logic:notEmpty name="studentRegistrationForm" property="optionalDocs">
								<tr class="border">
							   		<td colspan="2">
							   			&nbsp;
							   			<html:select name="studentRegistrationForm" property="optionFileBean.doc.docCode" value="-1">
							   		   		<html:option value="-1">Select From List&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html:option>
											<html:options name="studentRegistrationForm" property="optionCodes"	labelProperty="optionLabels" />
										</html:select>
									</td>
									<td>
										<span style="float:left;"><html:file name="studentRegistrationForm" property="optionFileBean.file" maxlength="75" size="75px"></html:file></span>
										<span style="float:right;"><html:submit  property="UploadOpt" value="Upload" onclick="needToConfirm = false;"/></span>
									</td>
									<td>
										<div id="uploadedOptionalDocs">
											<logic:notEmpty name="studentRegistrationForm" property="desc">
												<logic:iterate name ="studentRegistrationForm" property='map' id="m" indexId="mIndex">
													&nbsp;<bean:write name="m" property="key" /> <br>
													<logic:iterate id="v" name="m" property="value">
														<font color="blue">&nbsp;&nbsp;<bean:write name="v"/></font><br>
													</logic:iterate>   
												</logic:iterate>
											</logic:notEmpty>
										</div>
									</td> 		
								</tr>  
							</logic:notEmpty>
						</table>
					</div>
					<div class="panel-footer clearfix">
						<logic:notEqual name="studentRegistrationForm" property="hiddenButtonUpload" value="true" >
							<html:submit  property="Continue" value="Submit Documents and Continue" onclick="needToConfirm = false;"/>
						</logic:notEqual>
						<html:submit  property="Logout" value="Logout and return later" onclick="return doLogout();" />
					</div>
				</div>
			</div>
		</div>
		
		<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
		<div style="display:none; align:center"><br><bean:write name="studentRegistrationForm" property="version"/></div>

</html:form>
</body>
</sakai:html>
