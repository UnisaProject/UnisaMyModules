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
	        /*font-size: 12px !important;*/
			font-weight: normal !important;
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
		    <!-- border-collapse: separate; -->
		    /*font-size: 12px !important;
			font-weight: bold !important;*/
		}
		table.itemSummary td, td.itemSummaryLite {
			padding: .1em;
			/*font-size: 12px !important;*/
			font-weight: normal !important;
		}
		td { 
		    padding: 2px;
		}
		.nav-tabs { 
			border-bottom: none;
		}
		.nav-tabs > li {
		    position:relative;
		    color : white;
		    background-color:#ededed;
		    padding : 2px 2px;
		    border-radius: 5px 5px 0px 0px ;
		    border:0;
		    display: inline-block;
		}
		.nav-tabs > li .close {
		    margin: -3px 0 0 10px;
		    font-size: 18px;
		    padding: 5px 0;
		    float: right;
		}
		.nav-tabs > li > a	{
		    /* adjust padding for height*/
		    padding-top: 4px; 
		    padding-bottom: 4px;
		}
		.nav-tabs > li a[data-toggle=tab] {
		    float: left!important;
		}
		li span{
			display:block;
			white-space: nowrap;
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
	</style>
	
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) }  

		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");
			
			//Hide doFinAid for SLP Students
			var isStuSLP = $("#isStuSLP").val();
			if (isStuSLP == "true"){
				$("#doFinAid").hide();	
				$("input:radio[name='studentApplication.finaidNsfas'][value='N']").prop('checked', true);
				$("input:radio[name='studentApplication.completeQual'][value='N']").prop('checked', true);
				$('#completeText').val(' ');
			}
			
			var qualRadio = $("input:radio[name='studentApplication.completeQual']:checked").val();
			if (qualRadio != null && qualRadio != ""){
				if (this.value === 'Y') {
					$(".doCompleteText").css('visibility', 'visible');
				}else {
					$(".doCompleteText").css('visibility', 'hidden');
			    }
			}
			
			$("input:radio[name='studentApplication.completeQual']").change(function() {
				if (this.value === 'Y') {
					$(".doCompleteText").css('visibility', 'visible');
				}else {
					$(".doCompleteText").css('visibility', 'hidden');
			    }
			});
			
			
			var textLine = $('#textLine').val();
		    if(jQuery.trim(textLine) != null &&  jQuery.trim(textLine) != ""){
		    	var length = 100 - textLine.length();
		    	$('#textLine').text(textLine);
		    	$('#chars').text(length);
		    }
			
			var maxLength = 100;
			$('#completeText').keyup(function() {
				var length = $(this).val().length;
				var length = maxLength-length;
				$('#chars').text(length);
			});
		});

		function validateSelect(){
			//alert("In Validate");
			
			var radioStaff = $("input:radio[name='studentApplication.staffCurrent']:checked").val();
			var radioDeceased = $("input:radio[name='studentApplication.staffDeceased']:checked").val();
			var radioPrison = $("input:radio[name='studentApplication.prisoner']:checked").val();
			var radioComplete = $("input:radio[name='studentApplication.completeQual']:checked").val();
			
			var textComplete = $('#completeText').val();
			
			if (radioStaff == null || radioStaff == "" || radioStaff == "undefined"){
				showError("Error", "Please confirm if you are a current or retired Unisa staff member.");
				return false;
			}
			if (radioDeceased == null || radioDeceased == "" || radioDeceased == "undefined"){
				showError("Error", "Please confirm if you are a dependant of a current, retired or deceased permanent Unisa staff member.");
				return false;
			}
			if (radioPrison == null || radioPrison == "" || radioPrison == "undefined"){
				showError("Error", "Please confirm if you are a Prisoner.");
				return false;
			}
			if (radioComplete == null || radioComplete == "" || radioComplete == "undefined"){
				showError("Error", "Please confirm if you are in the process of completing a qualification.");
				return false;
			}
			if (radioComplete == "Y" && (textComplete == null || textComplete == "" || textComplete == "undefined")){
				showError("Error", "Please enter which qualification you will be completing.");
				return false;
			}
			doSubmit("Continue");
		}
		
		//Click button
		function doSubmit(button){
			if (button === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepRetRadio';
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
	<html:hidden property="page" value="applyRetRadio"/>
	
	<input type="hidden" name="textLine" id="textLine" value="<bean:write name='studentRegistrationForm' property='studentApplication.completeText'/>"/>
	<input type="hidden" id="isStuSLP" name="isStuSLP" value="<bean:write name='studentRegistrationForm' property='student.stuSLP' />" />

	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading"/></h3>
				</div>
				<div class="panel-body">	
					<sakai:group_table>
						<tr>
							<td><strong><fmt:message key="page.required.instruction"/></strong></td>
							<td width="50px" align="center"><fmt:message key="page.yes"/></td>
							<td width="50px" align="center"><fmt:message key="page.no"/></td>
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.currentStaff"/>&nbsp;</td>
							<td><html:radio property="studentApplication.staffCurrent" value="Y"/></td>
							<td><html:radio property="studentApplication.staffCurrent" value="N"/></td>
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.deceasedStaff"/>&nbsp;</td>
							<td><html:radio property="studentApplication.staffDeceased" value="Y"/></td>
							<td><html:radio property="studentApplication.staffDeceased" value="N"/></td>
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.prisoner"/>&nbsp;</td>
							<td><html:radio property="studentApplication.prisoner" value="Y"/></td>
							<td><html:radio property="studentApplication.prisoner" value="N"/></td>
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr>
					</sakai:group_table>
					<div id="doFinAid">
						<sakai:group_table>
						<tr height="20px">
								<td><fmt:message key="page.apply.complete"/></td>
								<td><html:radio property="studentApplication.completeQual" value="Y"/></td>
								<td><html:radio property="studentApplication.completeQual" value="N"/></td>
							</tr><tr height="5px">
								<td colspan="3">&nbsp;</td>
							</tr><tr height="20px">
								<td colspan="3">
									<div class="doCompleteText">
										<fmt:message key="page.apply.completeInfo"/>
									</div>
									<div class="doCompleteText">
										<textarea id="completeText" name="completeText" rows="2" cols="80" maxlength="100"></textarea>
										<br>
										<font size="1"><span id="chars"></span> Characters remaining</font>
									</div>
								</td>
							</tr>
						</sakai:group_table>
					</div>
				</div>
				<div class="panel-footer clearfix">
					<button class="btn btn-default" type="button" onclick="validateSelect();">Continue</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Back');">Back</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div id="hiddenRadio" style="display: none;">
		<input type="radio" name="studentApplication.completeQual" value="0" checked="checked" />
	</div>
	
	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
</body>
</sakai:html>		
