<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.mdapplications.forms.MdApplicationsForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.*" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

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
	<link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value='/resources/css/bootstrap-theme.css' />" rel="stylesheet"  type="text/css" />
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
			
			var showAdmission	= false;
			var showDocuments	= false;
			var showOffice		= false;
			
			if ($("#isDateWEBMDAPP").val() === "true"){
				showAdmission = true;
				$('#Admission').show();
			}else{
				$('#Admission').hide();
			}
			if ($("#isDateWEBMDDOC").val() === "true"){
				showDocuments = true;
				$('#Documents').show();
			}else{
				$('#Documents').hide();
			}
			if ($("#isDateWEBMDADM").val() === "true"){
				showOffice = true;
				$('#Office').show();
			}else{
				$('#Office').hide();
			}
			
			if (showAdmission != true && showDocuments != true){
				$('#allOpen').hide();
				$('#allClosed').show();
			}else{
				$('#allOpen').show();
				$('#allClosed').hide();
			}
		
		});
	
		function doSubmit(button){
			if (button === "App"){
				document.mdApplicationsForm.action='mdapplications.do?action=studinput&select=app';
				document.mdApplicationsForm.submit();
			}else if (button === "Doc"){
				document.mdApplicationsForm.action='mdapplications.do?action=studinput&select=doc';
				document.mdApplicationsForm.submit();
			}else if (button === "Office"){
				document.mdApplicationsForm.action='mdapplications.do?action=loginAdmin';
				document.mdApplicationsForm.submit();
			}
		}
	</script>

</head>
	<html:form action="/mdapplications">
	<html:hidden property="page" value="step0"/>

	<input type="hidden" name="isDateWEBMDADM" id="isDateWEBMDADM" value="<bean:write name='mdApplicationsForm' property='dateWEBMDADM'/>"/>
	<input type="hidden" name="isDateWEBMDAPP" id="isDateWEBMDAPP" value="<bean:write name='mdApplicationsForm' property='dateWEBMDAPP'/>"/>
	<input type="hidden" name="isDateWEBMDDOC" id="isDateWEBMDDOC" value="<bean:write name='mdApplicationsForm' property='dateWEBMDDOC'/>"/>

	<BR/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<sakai:messages/>
			<sakai:messages message="true"/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="md.heading"/></h3>
				</div>
			</div>
		</div>
		<div id="allClosed" class="col-md-12 col-sm-12 col-xs-12" style="display: none;">
			<div class="panel panel-default">
				<div class="light" align="center">
					<br/>
					<font color="#A90E13">The application period is closed.</font>
					<br/><br/>
				</div>
			</div>
			<br/>&nbsp;<br/>
		</div>						
		<div id="allOpen" style="display: none;">
			<div id="Admission" class="col-md-12 col-sm-12 col-xs-12">
				<div class="panel panel-default">
					<div class="panel-body">
							<fmt:message key="md.info2"/><br/><br/>
							
							<sakai:group_heading><fmt:message key="md.instruction1"/></sakai:group_heading>
							<sakai:group_table>
							<tr>
							  <td><fmt:message key="md.info4"/></td>
							</tr>
							</sakai:group_table>	
					</div>
					<div class="panel-footer clearfix">
						<button class="btn btn-default btn-block" type="button" onclick="doSubmit('App');">Apply for Admission</button>
					</div> 	
				</div>
			</div>
			<div id="Documents" class="col-md-12 col-sm-12 col-xs-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<sakai:group_heading><fmt:message key="md.instruction2"/></sakai:group_heading>
						<sakai:group_table>
						<tr>
							<td colspan="2"><fmt:message key="md.info5"/></td>
						</tr>
						<!-- 
						<tr>
							<td colspan="2"><strong><fmt:message key="md.info6"/></strong></td>
						</tr>
						<tr>
							<td width="50%">
								<table border="0" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td> · <fmt:message key="md.doc.d1"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d2"/></td>				
									</tr><tr>
										<td> · <fmt:message key="md.doc.d3"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d4"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d5"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d6"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d7"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d8"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d9"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d10"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d21"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d23"/></td>
									</tr>
								</table>
							</td>
							<td width="50%" valign="top">
								<table border="0" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td> · <fmt:message key="md.doc.d11"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d12"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d13"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d14"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d15"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d16"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d17"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d18"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d22"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d24"/></td>
									</tr><tr>
										<td> · <fmt:message key="md.doc.d19"/></td>
									</tr>
								</table>
							</td>
						</tr>
						 -->
						</sakai:group_table>
					</div>	
					<div class="panel-footer clearfix">
						<button class="btn btn-default btn-block" type="button" onclick="doSubmit('Doc');">Submit your documents online</button>
					</div> 	
				</div>
			</div>
			<br/>&nbsp;<br/>
		</div>
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
					<button class="btn btn-default btn-block" type="button" onclick="doSubmit('Office');"><fmt:message key="button.submitAdminLogin" /></button>
				</div> 									
			</div>
		</div>
	</div>
		<BR/>
 		<div style="display: none;"><bean:write name="mdApplicationsForm" property="version"/></div>
		<BR/>
	</html:form>
</sakai:html>