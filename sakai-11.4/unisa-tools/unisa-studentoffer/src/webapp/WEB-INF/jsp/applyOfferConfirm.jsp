<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentoffer.forms.StudentOfferForm"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Enumeration"%>
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
	
	//Call when document is ready
	$(document).ready(function() {
		
	});
			  
	/**Exit button**/
		//Click button
		function doSubmit(button){
			if (button === "Quit"){
				document.studentOfferForm.action='studentOffer.do?act=Quit';
			}else if (button === "Back"){
				document.studentOfferForm.action='studentOffer.do?act=Back';
			}else if (button === "Cancel"){
				document.studentOfferForm.action='studentOffer.do?act=Cancel';
			}
			document.studentOfferForm.submit();
		}
	
</script>

</head>
 
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">

<html:form action="/studentOffer">
	<html:hidden property="page" value="applyOfferConfirm"/>
	
	<input type="hidden" id="qualCode1" name="qualCode1" value="<bean:write name="studentOfferForm" property="offerQual1"/>" />
	<input type="hidden" id="qualCode2" name="qualCode2" value="<bean:write name="studentOfferForm" property="offerQual2"/>" />
	<input type="hidden" id="statusCode1" name="statusCode1" value="<bean:write name='studentOfferForm' property='qualStatusCode1' />" />
	<input type="hidden" id="statusCode2" name="statusCode2" value="<bean:write name='studentOfferForm' property='qualStatusCode2' />" />
	<input type="hidden" id="radioOfferQual1" name="radioOfferQual1" value="<bean:write name='studentOfferForm' property='studentApplication.radioOfferQual1' />" />
	<input type="hidden" id="radioOfferQual2" name="radioOfferQual2" value="<bean:write name='studentOfferForm' property='studentApplication.radioOfferQual2' />" />

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<div class="container full-width">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.offer.complete.info1"/></h3>
				</div>
				<div class="panel-body">
					<br>
					<table style="width:100%">
						<tr>
							<td colspan="2"><strong><fmt:message key="page.offer.complete.acceptdecline"/></strong></td>
						</tr><tr>
							<td colspan="2">&nbsp;</td>
						</tr><tr>
							<td width="60%"><strong><fmt:message key="page.studentnr.apply.qualification"/></strong></td>
							<td width="40%"><strong><fmt:message key="page.apply.select.infoSTATUS1"/></strong></td>
						</tr>
						<logic:notEqual name="studentOfferForm" property="offerQual1" value="">
							<logic:notEqual name="studentOfferForm" property="offerQual1" value="Not Found">
								<tr>
									<td width="60%"><bean:write name="studentOfferForm" property="offerQual1"/></td>
									<td width="40%">
										<logic:equal name="studentOfferForm" property="studentApplication.radioOfferAccept" value="1">
											<fmt:message key="page.accepted"/>
										</logic:equal>
										<logic:equal name="studentOfferForm" property="studentApplication.radioOfferAccept" value="2">
											<fmt:message key="page.declined"/>
										</logic:equal>
										<logic:equal name="studentOfferForm" property="studentApplication.radioOfferAccept" value="">
											<fmt:message key="page.accpeted.none"/>
										</logic:equal>
									<td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>
						<!--<logic:notEqual name="studentOfferForm" property="pendingQual1" value="">
							<logic:notEqual name="studentOfferForm" property="pendingQual1" value="Not Found">
								<tr>
									<td width="60%"><bean:write name="studentOfferForm" property="pendingQual1"/></td>
									<td width="40%">										
											<fmt:message key="page.declined"/>										
									<td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>-->
						<logic:notEqual name="studentOfferForm" property="offerQual2" value="">
							<logic:notEqual name="studentOfferForm" property="offerQual2" value="Not Found">
								<tr>
									<td width="60%"><bean:write name="studentOfferForm" property="offerQual2"/></td>
									<td width="40%">
										<logic:equal name="studentOfferForm" property="studentApplication.radioOfferAccept" value="2">
											<fmt:message key="page.accepted"/>
										</logic:equal>
										<logic:equal name="studentOfferForm" property="studentApplication.radioOfferAccept" value="1">
											<fmt:message key="page.declined"/>
										</logic:equal>
										<logic:equal name="studentOfferForm" property="studentApplication.radioOfferAccept" value="">
											<fmt:message key="page.accpeted.none"/>
										</logic:equal>
									<td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>	
						<!--<logic:notEqual name="studentOfferForm" property="pendingQual2" value="">
							<logic:notEqual name="studentOfferForm" property="pendingQual2" value="Not Found">
								<tr>
									<td width="60%"><bean:write name="studentOfferForm" property="pendingQual2"/></td>
									<td width="40%">										
											<fmt:message key="page.declined"/>										
									<td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>-->					
						<tr>
							<td colspan="2"><hr/></td>
						</tr><tr>
							<td colspan="2" style="height:30px"><fmt:message key="page.offer.complete.info3"/>&nbsp;
								<strong><bean:write name="studentOfferForm" property="student.appTime"/></strong>
							</td>
						</tr><tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<logic:notEqual name="studentOfferForm" property="student.emailAddress" value="">
							<tr>
								<td colspan="2" style="height:30px"><fmt:message key="page.offer.complete.info4"/>&nbsp;
									<strong><bean:write name="studentOfferForm" property="student.emailAddress"/></strong>
								</td>
							</tr>
						</logic:notEqual>
						<logic:equal name="studentOfferForm" property="student.emailAddress" value="">
							<tr>
								<td colspan="2" style="height:30px">
									<font size="3" color="red"><strong><fmt:message key="page.offer.complete.noEmail"/></strong></font>
								</td>
							</tr>
						</logic:equal>
					</table>
				</div>
			</div>
			<div class="panel-footer clearfix">
				<sakai:actions>
					<button class="btn btn-default" type="button" onclick="doSubmit('Quit');"><fmt:message key="button.exit"/></button>
				</sakai:actions>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentOfferForm" property="version"/></div>

</html:form>
 </body>
</sakai:html>
		