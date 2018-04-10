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
		select {
		    width: 80%;
		    height: 20px;
		    float: right;
		}
		#infoBox {
		  position: relative;		  
		}
		#divTop, #divBot { 
			position: absolute;
		}
		#divTop {
			top: 0; 
			padding: 0 10px;
		}
		#divBot {
			bottom: 0; 
			padding: 0 10px;
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
		    
			$("input[name='Continue']").click(function(){
				//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Continuing...</strong>" });
	        }); 
			$("input[name='Back']").click(function(){
	            //$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Going Back...</strong>" });
	        });
			$("input[name='Cancel']").click(function(){
	            //$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Logging Out...</strong>" });
	        });
		});
		  
		
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

<%
/**Changes**/
StudentRegistrationForm studentRegistrationForm = (StudentRegistrationForm)session.getAttribute("studentRegistrationForm");
String email = studentRegistrationForm.getStudent().getEmailAddress();
boolean emailAddressGood = studentRegistrationForm.getStudent().getEmailAddressGood();
boolean readOnly = false;
boolean hidden = false;
if(emailAddressGood){
	if(email!=null&&email.indexOf("mylife")!=-1){
		readOnly = true;
		hidden = true;
	}
}

%>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="applyNewAddress3"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<br/>
	<div class="container full-width">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading"/></h3>
				</div>
				<div class="panel-body">	
					<table>
						<tr>
							<td colspan="2">&nbsp;<strong><fmt:message key="page.courier"/></strong></td>
						</tr><tr>
							<td colspan="2">&nbsp;<fmt:message key="page.courier3"/></td>
						</tr><tr>
							<td colspan="2"><font size="1">&nbsp;</font></td>
						</tr><tr>
							<td style="width:50%;">
								<table>
									<tr>
										<td colspan="2"><html:text name="studentRegistrationForm" property="student.courierAddress.line1" maxlength="28" size="50"/></td>
									</tr><tr>
										<td colspan="2"><font size="1">&nbsp;</font></td>
									</tr><tr>
										<td colspan="2"><html:text name="studentRegistrationForm" property="student.courierAddress.line2" maxlength="28" size="50"/></td>
									</tr><tr>
										<td colspan="2"><font size="1">&nbsp;</font></td>
									</tr><tr>
										<td colspan="2"><html:text name="studentRegistrationForm" property="student.courierAddress.line3" maxlength="28" size="50"/></td>
									</tr><tr>
										<td colspan="2"><font size="1">&nbsp;</font></td>
									</tr><tr>
										<td colspan="2"><html:text name="studentRegistrationForm" property="student.courierAddress.line4" maxlength="28" size="50"/></td>
									</tr><tr>
										<td colspan="2"><font size="1">&nbsp;</font></td>
									</tr><tr>
										<td colspan="2"><html:text name="studentRegistrationForm" property="student.courierAddress.line5" maxlength="28" size="50"/></td>
									</tr><tr>
										<td colspan="2"><font size="1">&nbsp;</font></td>
									</tr><tr>
										<td colspan="2"><html:text name="studentRegistrationForm" property="student.courierAddress.line6" maxlength="28" size="50"/></td>
									</tr><tr>
										<td colspan="2"><font size="1">&nbsp;</font></td>
									</tr>
									<!-- 
									<logic:notEqual name="studentRegistrationForm" property="addressSubResultCour" value="">
										<tr>
											<td colspan="2">
												<strong><font color="red"><fmt:message key="page.useSuburb"/>&nbsp;
												<bean:write name='studentRegistrationForm' property='addressSubResultCour'/></font></strong>
											</td>
										</tr><tr>
											<td colspan="2"><font size="1">&nbsp;</font></td>
										</tr>
									</logic:notEqual>
									 -->
									<tr>
										<td style="width:115px">&nbsp;<fmt:message key="page.postalcode"/>&nbsp;</td>
										<td align="right"><html:text name="studentRegistrationForm" property="student.courierAddress.areaCode" maxlength="4" size="10" readonly="true" />
											<html:submit property="action.search3"><fmt:message key="button.searchPostalcode"/></html:submit>
										</td>
									</tr><tr>
										<td colspan="2"><font size="1">&nbsp;</font></td>
									</tr><tr>
										<td style="width:115px">&nbsp;<fmt:message key="page.contactnr"/></td>
										<td><html:text name="studentRegistrationForm" property="student.contactNr" maxlength="40" size="32"/></td>
									</tr>
								</table>
							</td>
							<td id="infoBox" style="width:50%">
								<div id="divTop"><i><fmt:message key="page.couradrinfo"/></i></div>
								<div id="divBot">
									<i><fmt:message key="page.postcodeinfo"/></i><br/><br/>
									<i><fmt:message key="page.studentnr.apply.eg1"/></i><br/>
									<i><fmt:message key="page.studentnr.apply.eg4"/></i>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="act"><fmt:message key="button.continue"/></html:submit>
						<html:submit property="act"><fmt:message key="button.back"/></html:submit>
						<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>
	
</html:form>
</body>
</sakai:html>