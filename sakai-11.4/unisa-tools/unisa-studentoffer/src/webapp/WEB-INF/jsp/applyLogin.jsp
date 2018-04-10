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
	
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js' />"></script>  
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
			height: 2em;
			line-height: 2; 
		}
	</style>
		
	<script type="text/javascript">  
	
	$(document).ready(function() {
		
		$('form,input,select,textarea').attr("autocomplete", "off");
		
		$("input[name='student.number']").focus();
		
		var docMsg = $('#webLogMsg').val();
		if(docMsg != "" && docMsg != "null" && docMsg != null && docMsg != NaN){
			docMsg = docMsg.replace(/newLine /g,"\n");
			showError("Info", docMsg);
		}
		
	});

	//Click button
	function validate(){
		
		var number = $('input[name="student.number"]').val();
		var surname = $('input[name="student.surname"]').val();
		var firstname = $('input[name="student.firstnames"]').val();

		var bYear = $("select[name='student.birthYear']").find("option:selected").val();
		var bMonth = $("select[name='student.birthMonth']").find("option:selected").val();
		var bDay = $("select[name='student.birthDay']").find("option:selected").val();

		if(number == null || number.trim() == "" || number == "undefinded"){
			showError("Error", "Please enter your Student number");
			return false;
		}
		if(surname == null || surname.trim() == "" || surname == "undefinded"){
			showError("Error", "Please enter your Surname");
			return false;
		}
		if(firstname == null || firstname.trim() == "" || firstname == "undefinded"){
			showError("Error", "Please enter your First name(s)");
			return false;
		}
		if(bYear == null || bYear.trim() == "" || bYear == "undefinded" || bYear == "00"){
			showError("Error", "Please enter the Year you were born");
			return false;
		}
		if(bMonth == null || bMonth.trim() == "" || bMonth == "undefinded" || bMonth == "00"){
			showError("Error", "Please enter the Month you were born");
			return false;
		}
		if(bDay == null || bDay.trim() == "" || bDay == "undefinded" || bDay == "00"){
			showError("Error", "Please enter the Day you were born");
			return false;
		}
		doSubmit("Continue");
	}
		
	function doSubmit(button){
		if (button === "Continue"){
			document.studentOfferForm.action='studentOffer.do?act=applyLogin';
		}else if (button === "Back"){
			document.studentOfferForm.action='studentOffer.do?act=back';
		}else if (button === "Cancel"){
			//document.studentOfferForm.action='studentOffer.do?act=cancel';
			//var url = "/unisa-studentoffer/studentOffer.do?act=stepLoginSelect;
	    	//window.location.href = url;
	    	//return false;
			window.top.location.href = "http://applications.unisa.ac.za/index.html";
  		  	return false;
		}
		document.studentOfferForm.submit();
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

<body>
<!-- Form -->
<html:form action="/studentOffer">
	<html:hidden property="page" value="applyLogin"/>
	
	<input type='hidden' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />
	<input type="hidden" name="webOpenDate" id="webOpenDate" value="<bean:write name='studentOfferForm' property='student.webOpenDate' />"/>
	<input type="hidden" name="allowLogin" id="allowLogin" value="<bean:write name='studentOfferForm' property='allowLogin'/>"/>
	<input type="hidden" name="webLogMsg" id="webLogMsg" value="<bean:write name='studentOfferForm' property='webLoginMsg'/>"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
		
	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.login.heading"/></h3>
				</div>
				<div class="panel-body clearfix">
					<div class="col-md-3 col-sm-3 col-xs-3">
					</div>
					<div class="col-md-6 col-sm-6 col-xs-6 clearfix">
						<table style="width:100%;">
			               	<logic:equal name="studentOfferForm" property="allowLogin" value="true">
					               	<tr>
						              	<td style="width:180px;height:30px" valign="top"><fmt:message key="page.login.studnumber"/></td>
						              	<td style="height:30px"><html:text name="studentOfferForm" property="student.number" maxlength="8" size="24" onfocus="this.select();" />&nbsp;</td>
						            </tr><tr>
					              		<td style="width:180px;height:30px" valign="top"><fmt:message key="page.login.studsurname"/></td>
					              		<td valign="bottom"><html:text name="studentOfferForm" property="student.surname" maxlength="28" size="24"/>&nbsp;</td>
					            	</tr><tr>
					              		<td style="width:180px;height:30px" valign="top"><fmt:message key="page.login.studfullname"/></td>
					              		<td valign="bottom"><html:text name="studentOfferForm" property="student.firstnames" maxlength="60" size="24" onfocus="this.select();" />&nbsp;</td>
					            	</tr><tr>
					            		<td style="width:180px;height:30px" valign="bottom"><fmt:message key="page.login.studbirthdate"/></td>
					            		<td valign="bottom">
							              	<table style="width:100%">
							              		<tr>
							              			<td>Year</td>
							              			<td>Month</td>
							              			<td>Day</td>
							              		</tr>
							              		<tr>
							              			<td>
							              				<html:select name="studentOfferForm" property="student.birthYear">
										 					<html:options collection="yearlist" property="value" labelProperty="label"/>
														</html:select>
													</td>
													<td>
							              				<html:select name="studentOfferForm" property="student.birthMonth">
										 					<html:options collection="monthlist" property="value" labelProperty="label"/>
														</html:select>
													</td>
													<td>
							              				<html:select name="studentOfferForm" property="student.birthDay">
										 					<html:options collection="daylist" property="value" labelProperty="label"/>
														</html:select>
													</td>
							              		</tr>
							              	</table>
										</td>
						            </tr>
							</logic:equal>
							<logic:equal name="studentOfferForm" property="allowLogin" value="false">
								<tr>
									<td colspan="2"><strong><font color="red" size="large"><fmt:message key="page.login.newClosed"/></font></strong></td>
						        </tr>
							</logic:equal>
			        	</table>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-3">
					</div>
				</div>
				<div class="panel-footer clearfix">
					<logic:equal name="studentOfferForm" property="allowLogin" value="true">
						<button class="btn btn-default" type="button" onclick="validate();">Continue</button>
					</logic:equal>
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentOfferForm" property="version"/></div>

</html:form>
</body>
</sakai:html>
