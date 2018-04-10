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
	 	 
</style>

	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) } 
	
	//Call when document is ready
	$(document).ready(function() {
		
		/**Click Pay Now button**/
		$("#payNow").click(function(){
			var butVal = $(this).val();
			var checkStuNr = $('#tmpStuNr').val();
			var retVal = confirm("Did you make a note of your student number?\n Your number is: " + checkStuNr);
			if( retVal === true ){
				$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Redirecting...</strong>" });
				var url = "/unisa-findtool/default.do?sharedTool=unisa.creditcardpayment";
	  	      	window.location.href =url;
	  	      	return false;
		 	}else{
				return false;
		   	}
		});		
			  
		/**Click Pay Later button**/
		$("#payLater").click(function(){
			var isAdmin = $('#webADM').val();
			if (isAdmin === false || isAdmin === "false"){
				var butVal = $(this).val();
				var checkStuNr = $('#tmpStuNr').val();
				var retVal = confirm("Did you make a note of your student number?\n Your number is: " + checkStuNr);
				if( retVal === true ){
					$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Redirecting...</strong>" });
					var url = "http://applications.unisa.ac.za/index.html";
		  	      	window.top.location.href =url;
		  	      	return false;
			 	}else{
					return false;
			   	}
			}else{
				//redirect to Admin home page
				window.top.location.href = "/unisa-studentregistration/applyForStudentNumber.do?act=loginAdmin";
		  	}
		});		
	});
</script>

</head>

<%
 StudentRegistrationForm studentRegistrationForm = (StudentRegistrationForm)session.getAttribute("studentRegistrationForm");
 %>
 
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="complete"/>
	
	<input type="hidden" id="tmpStuNr" name="tmpStuNr" value="<bean:write name="studentRegistrationForm" property="student.number"/>" />
	<input type="hidden" name="webADM" id="webADM" value="<bean:write name='studentRegistrationForm' property='adminStaff.admin' />"/>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<div class="container full-width">
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
							<td colspan="2" style="height:30px"><strong><font size="2"><fmt:message key="page.studentnr.complete.info2"/></font></strong>&nbsp;</td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.studentnr.complete.info3"/>&nbsp;
								<bean:write name="studentRegistrationForm" property="student.appTime"/>
							</td>
						</tr><tr>
							<td colspan="2">
								<table style="width:100%">
									<tr>
										<td colspan="2" style="height:30px"><strong>These are the qualifications you have selected. &nbsp;</strong></td>
									</tr><tr>
										<td colspan="2" style="height:30px">
											<fmt:message key="page.studentnr.complete.contact1"/><br/>
											<fmt:message key="page.studentnr.complete.contact2"/><br/>
											<fmt:message key="page.studentnr.complete.contact3"/><br/>
											<fmt:message key="page.studentnr.complete.contact4"/><br/>
										</td>
									</tr><tr>
										<td style="width:165px; height:30px">Proposed Qualification:</td>
										<td><bean:write name="studentRegistrationForm" property="student.newQualOneFinal"/></td>
									</tr><tr>
										<td style="width:165px; height:30px">Proposed Specialization:</td>
										<td><bean:write name="studentRegistrationForm" property="student.newSpecOneFinal"/></td>
									</tr><tr>
										<td style="width:165px; height:30px">Optional Qualification:</td>
										<td><bean:write name="studentRegistrationForm" property="student.newQualTwoFinal"/></td>
									</tr><tr>
										<td style="width:165px; height:30px">Optional Specialization:</td>
										<td><bean:write name="studentRegistrationForm" property="student.newSpecTwoFinal"/></td>
									</tr><tr>
										<td style="width:165px; height:30px"><fmt:message key="page.studentnr.complete.info4"/></td>
										<td><strong><bean:write name="studentRegistrationForm" property="student.number"/></strong></td>
									</tr>
								</table>
							</td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.studentnr.complete.info5"/>&nbsp;
							<bean:write name="studentRegistrationForm" property="student.emailAddress"/></td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.studentnr.complete.info6"/>&nbsp;
							<bean:write name="studentRegistrationForm" property="student.cellNr"/></td>
						</tr><tr>
							<td colspan="2"><strong><font size="2"><fmt:message key="page.studentnr.complete.info8"/></font></strong>&nbsp;</td>
						</tr><tr>
							<td style="width:150px; height:30px"><fmt:message key="page.studentnr.complete.info9"/>&nbsp;</td>
							<td style="width:150px; height:30px; text-align: center;"><div><strong><font size="3" color="red"><bean:write name="studentRegistrationForm" property="student.number"/></font></strong></div></td>
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