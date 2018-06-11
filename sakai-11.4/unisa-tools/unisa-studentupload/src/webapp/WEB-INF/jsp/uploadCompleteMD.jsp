<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentupload.forms.StudentUploadForm"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Enumeration"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentupload.ApplicationResources"/>

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
		#cssTable td {
		    text-align:center; 
		    vertical-align:middle;
		}
	</style>
 
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) } 

	//Call when document is ready
	$(document).ready(function() {
		// This will hold our timer
		var myTimer = {};
		// delay 45 seconds
		myTimer = $.timer(60000, function() {
			//redirect to home page
			var url = "/unisa-findtool/default.do?sharedTool=unisa.mdapplications";
	  		window.location.href =url;
		});
		  
		  /**Click Pay Now button**/
			$("#payNow").click(function(){
				var butVal = $(this).val();
				var checkStuNr = $('#tmpStuNr').val();
				var retVal = confirm("Did you make a note of your reference number?\n Your number is: " + checkStuNr+"\nYou will be requied to complete the Master\'s and Doctoral application process from the Unisa website after paying.\nClicking OK to go to Payment portal or click Cancel go directly to the Master's and Doctoral Admission.");
				if( retVal === true ){
					//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Redirecting...</strong>" });
		    		var url = "/unisa-findtool/default.do?sharedTool=unisa.creditcardpayment";
		  	      	/**alert("User wants to go to M&D! " + url);**/
		  	      	window.location.href =url;
		  	      	return false;
		  	   	}else{
		  	   		$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Redirecting...</strong>" });
		  	   		var url = "/unisa-findtool/default.do?sharedTool=unisa.mdapplications";
	  	      		/**alert("User wants to go to M&D! " + url);**/
	  	      		window.location.href =url;
		  		  	return false;
		  	   	}
	        	
			});	
		  
			/**Click Pay Later button**/
			$("#payLater").click(function(){
				var butVal = $(this).val();
				var checkStuNr = $('#tmpStuNr').val();
				var retVal = confirm("Did you make a note of your reference number?\n Your number is: " + checkStuNr+"\nYou must complete your application by clicking OK to go to Master's and Doctoral Applications or click Cancel to take down your student number.");
				if( retVal == true ){
					$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Redirecting...</strong>" });
					var url = "/unisa-findtool/default.do?sharedTool=unisa.mdapplications";
			 	      	/**alert("User wants to go to M&D! " + url);**/
			 	      	window.location.href =url;
			 	      	return false;
		 	   	}else{
		  	      	/**alert("User does not wants to stay!");**/
		  		  	return false;
			   	}
			});	
	});
			
	
</script>

</head>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/studentUpload">
	<html:hidden property="page" value="complete"/>
	<input type="hidden" id="tmpStuNr" name="tmpStuNr" value="<bean:write name="studentUploadForm" property="student.number"/>" />
	<input type="hidden" name="stuAPP" id="stuAPP" value="<bean:write name='studentUploadForm' property='student.stuAPP' />"/>
	<input type="hidden" name="stuSLP" id="stuSLP" value="<bean:write name='studentUploadForm' property='student.stuSLP' />"/>
	<input type="hidden" name="stuSBL" id="stuSBL" value="<bean:write name='studentUploadForm' property='student.stuSBL' />"/>
	<input type="hidden" name="stuHON" id="stuHON" value="<bean:write name='studentUploadForm' property='student.stuHON' />"/>
	<input type="hidden" name="stuPG" id="stuPG" value="<bean:write name='studentUploadForm' property='student.stuPG' />"/>
	<input type="hidden" name="stuMD" id="stuMD" value="<bean:write name='studentUploadForm' property='student.stuMD' />"/>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<div class="container">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading"/></h3>
				</div>
				<div class="panel-body">
					<sakai:group_heading><fmt:message key="page.studentnr.complete.info1"/></sakai:group_heading>
					<br>
					<table style="width:100%">
						<tr>
							<td colspan="2" style="height:30px"><strong><font size="2"><fmt:message key="page.studentnr.completeMD.info2"/></font></strong>&nbsp;</td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.studentnr.complete.info3"/>&nbsp;
								<bean:write name="studentUploadForm" property="student.appTime"/>
							</td>
						</tr><tr>
							<td colspan="2">
								<table style="width:100%">
									<tr>
										<td colspan="2" style="height:30px"><strong>This is the qualification you have selected. &nbsp;</strong></td>
									</tr><tr>
										<td colspan="2" style="height:30px">
											<strong><fmt:message key="page.studentnr.completeMD.contact"/></strong>
										</td>
									</tr><tr>
										<td style="width:165px; height:30px">Proposed Qualification:</td>
										<td><bean:write name="studentUploadForm" property="student.newMDQualFinal"/></td>
									</tr><tr>
										<td style="width:165px; height:30px">Proposed Specialization:</td>
										<td><bean:write name="studentUploadForm" property="student.newMDSpecFinal"/></td>
									</tr><tr>
										<td style="width:165px; height:30px"><fmt:message key="page.studentnr.complete.info4"/></td>
										<td><strong><bean:write name="studentUploadForm" property="student.number"/></strong></td>
									</tr>
								</table>
							</td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.studentnr.complete.info5"/>&nbsp;
							<bean:write name="studentUploadForm" property="student.emailAddress"/></td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.studentnr.complete.info6"/>&nbsp;
							<bean:write name="studentUploadForm" property="student.cellNr"/></td>
						</tr><tr>
							<td colspan="2" style="height:30px"><strong><font size="2"><fmt:message key="page.studentnr.complete.info7"/></font></strong>&nbsp;</td>
						</tr><tr>
							<td colspan="2" style="height:30px"><strong><font size="2"><fmt:message key="page.studentnr.complete.info8"/></font></strong>&nbsp;</td>
						</tr><tr>
							<td style="width:150px; height:30px"><fmt:message key="page.studentnr.complete.info9"/>&nbsp;</td>
							<td style="width:150px; height:30px"></td>
						</tr><tr>
							<td style="width:150px; height:30px"><fmt:message key="page.studentnr.complete.info10"/>&nbsp;</td>
							<td style="width:150px; height:30px; text-align: center;"><div><strong><font size="3" color="red"><bean:write name="studentUploadForm" property="student.number"/></font></strong></div></td>
						</tr><tr>
							<td style="width:150px; height:30px; text-align: center;">
								<div>
									<input type="button" id="payNow" value="Pay online with your credit card" />
								</div>
							</td>
							<td style="width:150px; height:30px; text-align: center;">
								<div>
									<input type="button" id="payLater" value="Pay Later" />
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	
	
</html:form>
</body>
</sakai:html>
