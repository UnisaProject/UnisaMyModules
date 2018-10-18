<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentstatus.forms.StudentStatusForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentstatus.ApplicationResources"/>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<sakai:html>		
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
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-migrate-3.0.1.min.js' />"></script>  
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
	
		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");

		});
		
		function doSubmit(button){
			if (button === "backToOffer"){
				document.studentStatusForm.action='studentStatus.do?act=backToOffer';
			}else{
			document.studentStatusForm.action='studentStatus.do?act=cancel';
			}
			document.studentStatusForm.submit();
		}
		
	</script>

<!-- Form -->
<html:form action="/studentStatus">

	<html:hidden property="page" value="applyStatus"/>
	
	<input type='hidden' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />
	<input type="hidden" name="allowLogin" id="allowLogin" value="<bean:write name='studentStatusForm' property='allowLogin'/>"/>
	<input type="hidden" name="isPayDate" id="isPayDate" value="<bean:write name='studentStatusForm' property='status.payDate'/>"/>
	<input type="hidden" name="isPayFull" id="isPayFull" value="<bean:write name='studentStatusForm' property='status.payFee'/>"/>
	<input type="hidden" name="isPayFee" id="isPayFee" value="<bean:write name='studentStatusForm' property='status.payFull'/>"/>
	<input type="hidden" name="isPayCom" id="isPayCom" value="<bean:write name='studentStatusForm' property='status.payComment'/>"/>
	<input type="hidden" name="statusCode1" id="statusCode1" value="<bean:write name='studentStatusForm' property='qualStatusCode1'/>"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<br/>
	<div class="container full-width">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.status.heading"/>&nbsp;<bean:write name="studentStatusForm" property="student.academicYear"/>&nbsp;<fmt:message key="page.status.heading.extra"/></h3>
				</div>
				<div class="panel-body">
					<logic:equal name="studentStatusForm" property="student.stuExist" value="false">
						<table id="cssTable" style="width:100%">
							<tr>
								<td style="height:50px;color:#A90E13;font-size:14px; margin-bottom:10px;">
				                  	<strong>
				                  		<fmt:message key="page.login.noSTUAPQ1"/><br/>
				                  		<fmt:message key="page.login.noSTUAPQ2"/>
				                  	</strong>
				               	</td>
			               	</tr>
						</table>
					</logic:equal>			               		
					<logic:equal name="studentStatusForm" property="student.stuExist" value="true">
						<sakai:group_table>
						<tr>
							<td colspan="3"><h4>Primary Qualification&nbsp;</h4></td>
						</tr><tr>
							<td style="width:40%"><b>Qualification&nbsp;</b></td>
							<td style="width:30%"><b>Specializations&nbsp;</b></td>
							<td style="width:30%"><b>Status&nbsp;</b></td>
						</tr><tr>
							<td class="dispFont"><bean:write name="studentStatusForm" property="selQualCode1"/></td>
							<logic:notEqual name="studentStatusForm" property="selSpecCode1" value="">
								<td class="dispFont"><bean:write name="studentStatusForm" property="selSpecCode1"/></td>
							</logic:notEqual>
							<logic:equal name="studentStatusForm" property="selSpecCode1" value="">
								<td class="dispFont">N/A - Not Applicable</td>
							</logic:equal>
							<td class="dispFont">
								<bean:write name="studentStatusForm" property="qualStatus1"/>
							</td>
						</tr>	
						<logic:notEqual name="studentStatusForm" property="selQualCode2" value="">
							<logic:notEqual name="studentStatusForm" property="selQualCode2" value="Not Found">
								<tr>
									<td colspan="3"><h4>Alternative Qualification&nbsp;</h4></td>
								</tr><tr>
									<td style="width:40%"><b>Qualification&nbsp;</b></td>
									<td style="width:30%"><b>Specializations&nbsp;</b></td>
									<td style="width:30%"><b>Status&nbsp;</b></td>
								</tr><tr>
									<td class="dispFont"><bean:write name="studentStatusForm" property="selQualCode2"/></td>
									<logic:notEqual name="studentStatusForm" property="selSpecCode2" value="">
										<td class="dispFont"><bean:write name="studentStatusForm" property="selSpecCode2"/></td>
									</logic:notEqual>
									<logic:equal name="studentStatusForm" property="selSpecCode2" value="">
										<td class="dispFont">N/A - Not Applicable</td>
									</logic:equal>
									<td class="dispFont">
										<bean:write name="studentStatusForm" property="qualStatus2"/>
									</td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>
						<tr>
						</sakai:group_table>

					</logic:equal>
					<br/>
					<logic:notEqual name="studentStatusForm" property="status.payDate" value="">
					<hr/>
						<sakai:group_table>
							<tr>
								<td colspan="2">
									<strong>Payment Status: </strong><br/>
								</td>
							</tr><tr>
								<td>
									<strong>Payment Date: </strong><br/>
								</td><td>
									<bean:write name="studentStatusForm" property="status.payDate"/>
								</td>
							</tr><tr>
								<td>
									<strong>Payment Received: </strong><br/>
								</td><td>
									R&nbsp;<bean:write name="studentStatusForm" property="status.payFee"/>
								</td>
							</tr><tr>
								<td>
									<strong>Payment Complete: </strong><br/>
								</td>
								<logic:equal name="studentStatusForm" property="status.payFull" value="true">
									<td>Y</td>
								</logic:equal>
								<logic:notEqual name="studentStatusForm" property="status.payFull" value="true">
									<td>N</td>
								</logic:notEqual>
							</tr>
							<logic:notEqual name="studentStatusForm" property="status.payComment" value="">
								<tr>
									<td>
										<strong>Payment Comment: </strong><br/>
									</td><td>
										<bean:write name="studentStatusForm" property="status.payComment"/>
									</td>
								</tr>
							</logic:notEqual>
						</sakai:group_table>
					</logic:notEqual>
					<logic:equal name="studentStatusForm" property="screeningSitting" value="true">
					<hr/>
						<sakai:group_table>
							<tr>
								<td colspan="2">
									<strong>Exam sitting for Social work screening assessment: </strong><br/>
								</td>
							</tr>
							<logic:notEqual name="studentStatusForm" property="screeningSittingQual1" value="">
								<tr>
									<td>
										<strong><bean:write name="studentStatusForm" property="selQualCode1"/> : </strong>
									</td><td>
										<bean:write name="studentStatusForm" property="screeningSittingQual1"/>
									</td>
								</tr>
							</logic:notEqual>
							<logic:notEqual name="studentStatusForm" property="screeningSittingQual2" value="">
								<tr>
									<td>
										<strong><bean:write name="studentStatusForm" property="selQualCode2"/> : </strong>
									</td><td>
										<bean:write name="studentStatusForm" property="screeningSittingQual2"/>
									</td>
								</tr>
							</logic:notEqual>
							<tr>
								<td>
									<strong>Venue: </strong><br/>
								</td>
								<td><bean:write name="studentStatusForm" property="screeningVenue.venueName"/></td>
							</tr>
							<td>
									<strong>Venue address: </strong><br/>
								</td>
							<td>
								<logic:iterate name="studentStatusForm" property="screeningVenue.addressList" id="record" indexId="index">
										<bean:write name="record"/><br/>									
								</logic:iterate>
							</td>		
						</sakai:group_table>	
					</logic:equal>		
				</div>	
				<div class="panel-footer clearfix">
					<logic:notEqual name="studentStatusForm" property="originatedFrom" value="unisa.studentoffer">
						<button class="btn btn-default" type="button" onclick="doSubmit('Quit');">Quit</button>
				    </logic:notEqual>
					<logic:equal name="studentStatusForm" property="originatedFrom" value="unisa.studentoffer">
						<button class="btn btn-default" type="button" onclick="doSubmit('backToOffer');"><fmt:message key="button.backToOffer" /></button>
					</logic:equal>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentStatusForm" property="version"/></div>
	
</html:form>
</sakai:html>